package Others;

import General.Camera;
import General.TabInterface;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class SierpinskiTriangleTab implements TabInterface {

    private HBox hBox;
    private VBox vBox;
    private Canvas canvas;
    private FXGraphics2D g2d;
    private Camera camera;

    private ArrayList<Shape> triangles;
    private Point2D sierPoint1, sierPoint2, sierPoint3;

    private ArrayList<TreeLine> treeLines;
    private Point2D root;

    private ArrayList<TreeLine> kocLines;

    private Button buttonRender, buttonPlus, buttonMin, buttonSave;
    private Label labelOrder;
    private TextField tfOrder;

    private ComboBox selectionBox;
    private String sierp, tree, koch, selected;

    private Stage popUp;
    private Label labelError;

    private int order;
    private int renderOrder;

    private BufferedImage renderIMG;
    private Graphics2D g;

    public SierpinskiTriangleTab(){
        this.hBox = new HBox();
        this.vBox = new VBox();
        this.canvas = new Canvas(1920,1080);
        this.g2d = new FXGraphics2D(this.canvas.getGraphicsContext2D());

        this.triangles = new ArrayList<>();
        this.sierPoint1 = new Point2D(0, -500);
        this.sierPoint2 = new Point2D(-600, 400);
        this.sierPoint3 = new Point2D(600, 400);

        this.treeLines = new ArrayList<>();
        this.root = new Point2D(0,100);

        this.buttonRender = new Button("Render");
        this.buttonRender.setOnAction(event -> {
            if (!this.readTextFields()){
                this.tfError();
            }
            if (!this.readComboBox()){
                this.comboBoxError();
            }
            this.triangles = new ArrayList<>();
            this.treeLines = new ArrayList<>();
            if (this.selected == this.sierp){
                this.setSierpinski();
            } else if (this.selected == this.tree){
                this.setTree();
            } else if (this.selected == this.koch){
                this.setKoch();
            }

        });

        this.buttonPlus = new Button("+");
        this.buttonPlus.setOnAction(event -> {
            ++this.order;
            this.setTextFields();
        });

        this.buttonMin = new Button("-");
        this.buttonMin.setOnAction(event -> {
            --this.order;
            if (this.order < 0){
                this.order = 0;
            }
            this.setTextFields();
        });

        this.buttonSave = new Button("Save");
        this.buttonSave.setOnAction(event -> this.saveCanvas());

        this.labelOrder = new Label("Order: ");

        this.tfOrder = new TextField("0");

        this.sierp = "Sierpinski Triangle";
        this.tree = "Tree Fractal";
        this.koch = "Koch Snowflake";
        this.selected = this.sierp;
        this.selectionBox = new ComboBox();
        this.selectionBox.getItems().addAll(this.sierp, this.tree, this.koch);
        this.selectionBox.setValue(this.sierp);

        this.hBox.getChildren().addAll(this.selectionBox, this.buttonRender, this.labelOrder, this.buttonPlus, this.buttonMin, this.tfOrder, this.buttonSave);
        this.vBox.getChildren().addAll(this.hBox, this.canvas);

        this.popUp = new Stage();
        VBox errorVBox = new VBox();
        this.labelError = new Label();
        errorVBox.getChildren().add(this.labelError);
        this.popUp.setScene(new Scene(errorVBox));
        this.popUp.setTitle("Error pop-up");
        Image icon = new Image(getClass().getResourceAsStream("/GUI-Images/error.png"));
        this.popUp.getIcons().add(icon);
        this.popUp.setWidth(400);
        this.popUp.setHeight(120);

        this.camera = new Camera( this.canvas, this.g2d);
        this.canvas.setOnMouseDragged(event -> {
            this.camera.mouseDragged(event);
        });

        this.canvas.setOnScroll(event -> {
            this.camera.mouseScroll(event);
        });

        new AnimationTimer(){
            public void handle(long now){
                draw();
            }
        }.start();
    }

    private void draw(){
        this.g2d.setBackground(Color.blue);
        if (this.camera.getInverse() == null){
            this.g2d.clearRect(0,0,1920,1080);
        } else {
            AffineTransform inverse = this.camera.getInverse();
            this.g2d.clearRect(
                    (int)inverse.getTranslateX(),
                    (int)inverse.getTranslateY(),
                    (int)(inverse.getScaleX() * (1920) - inverse.getTranslateX()),
                    (int)(inverse.getScaleY() * (1080) - inverse.getTranslateY())
            );
        }

        this.g2d.setTransform(this.camera.getTransform(1920, 1080, true));

        this.g2d.setPaint(Color.WHITE);
        this.g2d.setStroke(new BasicStroke(5.0f / ((float)Math.pow(1.5f, this.renderOrder))));
        if (this.selected == this.sierp) {
            this.g2d.setStroke(new BasicStroke(5.0f / ((float)Math.pow(1.5f, this.renderOrder))));
            for (Shape each : this.triangles) {
                this.g2d.draw(each);
            }
        } else if (this.selected == this.tree){
            this.g2d.setStroke(new BasicStroke(2.0f));
            for (TreeLine each : this.treeLines){
                each.draw(this.g2d);
            }
        }
    }

    private void displayTriangles(int order, Point2D p1, Point2D p2, Point2D p3){
        if (order == 0){
            GeneralPath triangle = new GeneralPath();
            triangle.moveTo(p1.getX(), p1.getY());
            triangle.lineTo(p2.getX(), p2.getY());
            triangle.lineTo(p3.getX(), p3.getY());
            triangle.lineTo(p1.getX(), p1.getY());
            triangle.closePath();
            this.triangles.add(triangle);

        } else {
            Point2D p12 = p1.midpoint(p2);
            Point2D p23 = p2.midpoint(p3);
            Point2D p31 = p3.midpoint(p1);

            displayTriangles(order - 1, p1, p12, p31);
            displayTriangles(order - 1, p12, p2, p23);
            displayTriangles(order - 1, p31, p23, p3);
        }
    }

    private void displayTriangles(AffineTransform inverse, int order, Point2D p1, Point2D p2, Point2D p3){
        if (order == 0){
            g.draw(new Line2D.Double(
                    (p1.getX() - inverse.getTranslateX()),
                    (p1.getY() - inverse.getTranslateY()),
                    (p2.getX() - inverse.getTranslateX()),
                    (p2.getY() - inverse.getTranslateY()))
            );
            g.draw(new Line2D.Double(
                    (p3.getX() - inverse.getTranslateX()),
                    (p3.getY() - inverse.getTranslateY()),
                    (p2.getX() - inverse.getTranslateX()),
                    (p2.getY() - inverse.getTranslateY())
            ));
            g.draw(new Line2D.Double(
                    (p1.getX() - inverse.getTranslateX()),
                    (p1.getY() - inverse.getTranslateY()),
                    (p3.getX() - inverse.getTranslateX()),
                    (p3.getY() - inverse.getTranslateY()))
            );
        } else {
            Point2D p12 = p1.midpoint(p2);
            Point2D p23 = p2.midpoint(p3);
            Point2D p31 = p3.midpoint(p1);

            displayTriangles(inverse, order - 1, p1, p12, p31);
            displayTriangles(inverse, order - 1, p12, p2, p23);
            displayTriangles(inverse, order - 1, p31, p23, p3);
        }
    }

    private boolean readTextFields(){
        try{
            int order = Integer.parseInt(this.tfOrder.getText());
            this.renderOrder = order;
            if (order != this.order){
                this.order = order;
            }
            if(this.selected == this.sierp){
                if (this.order > 9){
                    this.order = 9;
                    this.renderOrder = 9;
                    this.tfOrder.setText("9");
                }
            } else if (this.selected == this.tree){
                if (this.order > 15){
                    this.order = 15;
                    this.renderOrder = 15;
                    this.tfOrder.setText("15");
                }
            }
        } catch (Exception e){
            if (this.tfOrder.getText().isEmpty()){
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private void saveCanvas(){
        this.renderIMG = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D)this.renderIMG.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0,0, 1920, 1080);
        g.setColor(Color.WHITE);


        AffineTransform inverse = this.camera.getInverse();
        if (this.selected == this.sierp){
            g.setStroke(new BasicStroke(5.0f / ((float)Math.pow(1.5f, this.order))));
            Point2D a1 = new Point2D(this.sierPoint1.getX()/inverse.getScaleX(), this.sierPoint1.getY()/inverse.getScaleY());
            Point2D a2 = new Point2D(this.sierPoint2.getX()/inverse.getScaleX(), this.sierPoint2.getY()/inverse.getScaleY());
            Point2D a3 = new Point2D(this.sierPoint3.getX()/inverse.getScaleX(), this.sierPoint3.getY()/inverse.getScaleY());

            this.displayTriangles(inverse, this.order, a1, a2, a3);

            try {
                ImageIO.write(this.renderIMG, "png", new File("SaveFolder/SierpinskiTriangle@_n(" + this.renderOrder + ")_zoom(" + 1/inverse.getScaleX() + ")@(" + inverse.getTranslateX() + "x_" + inverse.getTranslateY() + "y).png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (this.selected == this.tree){
            System.out.println("Scale: " + inverse.getScaleX());
            System.out.println("Translate X: " + inverse.getTranslateX());
            System.out.println("Translate Y: " + inverse.getTranslateY());
            for (TreeLine each : this.treeLines){
                each.draw(g);
            }
            try {
                ImageIO.write(this.renderIMG, "png", new File("SaveFolder/TreeFractal@_n(" + this.renderOrder + ")_zoom(" + 1/inverse.getScaleX() + ")@(" + inverse.getTranslateX() + "x_" + inverse.getTranslateY() + "y).png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void comboBoxError() {
        this.labelError.setText("There is a comboBox error I've never gotten myself\n" +
                "Good job on breaking this application!");
    }

    private boolean readComboBox() {
        String selectedString = this.selected;
        try{
            selectedString = (String)this.selectionBox.getSelectionModel().getSelectedItem();
        } catch (Exception e){
            return false;
        }

        if (selectedString == this.sierp){
            this.selected = this.sierp;
        } else if (selectedString == this.tree){
            this.selected = this.tree;
        } else if (selectedString == this.koch){
            this.selected = this.koch;
        }
        return true;
    }

    private void tfError(){
        this.labelError.setText("Make sure every TextField is formatted right.\n" +
                "The order TextField should be an integer.\n" +
                "Example: 5."
        );
        this.popUp.show();
        this.tfOrder.setText("0");
    }

    private void displayTree(int order, Point2D origin, double lengthDiff, double degree, double rightDiff, double leftDiff){
        Point2D nextOrigin = new Point2D(origin.getX() + 100 * Math.cos(degree) * lengthDiff, origin.getY() + 100 * Math.sin(degree) * lengthDiff);
        TreeLine add = new TreeLine(origin, nextOrigin, (float)(order + 9)  / 9.0f);
        this.treeLines.add(add);
        if (order != 0){
            double degree1 = degree + rightDiff;
            double degree2 = degree - leftDiff;
            displayTree(order - 1, nextOrigin, lengthDiff, degree1, rightDiff, leftDiff);
            displayTree(order - 1, nextOrigin, lengthDiff, degree2, rightDiff, leftDiff);
        }
    }

    private void displayKoch(int order){
        //call kochLine 6 times with order
    }

    private void kochLine(int order, Point2D p1, Point2D p5){
        if (order == 0){
            // draw the line
            this.kocLines.add(new TreeLine(p1, p5, (float)(order + 9)  / 9.0f));
        } else {
            Point2D p3 = p1.midpoint(p5);
            Point2D p2 = p1.midpoint(p3);
            Point2D p4 = p3.midpoint(p5);

        }
    }

    private void setTree(){
        this.displayTree(this.order, this.root, 0.5, Math.PI * 0.5, 0.2, 0.20);
    }

    private void setKoch(){
        //@TODO call koch fractal recursive function
    }

    private void setSierpinski(){
        displayTriangles(this.order, this.sierPoint1, this.sierPoint2, this.sierPoint3);
    }

    private void setTextFields(){
        this.tfOrder.setText("" + this.order);
    }

    public Node getNode(){ return this.vBox;}
}
