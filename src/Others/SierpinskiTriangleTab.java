package Others;

import General.Camera;
import General.TabInterface;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import sun.awt.OSInfo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;


public class SierpinskiTriangleTab implements TabInterface {

    private HBox hBox;
    private VBox vBox;
    private Canvas canvas;
    private FXGraphics2D g2d;
    private Camera camera;

    private ArrayList<Shape> triangles;
    private Point2D anchor1, anchor2, anchor3;

    private Button buttonPlus, buttonMin, buttonSave;
    private Label labelOrder;
    private TextField tfOrder;

    private Stage popUp;
    private Label labelError;

    private int order;

    private BufferedImage renderIMG;
    private Graphics2D g;

    public SierpinskiTriangleTab(){
        this.hBox = new HBox();
        this.vBox = new VBox();
        this.canvas = new Canvas(1920,1080);
        this.g2d = new FXGraphics2D(this.canvas.getGraphicsContext2D());

        this.triangles = new ArrayList<>();
        this.anchor1 = new Point2D(0, -500);
        this.anchor2 = new Point2D(-600, 400);
        this.anchor3 = new Point2D(600, 400);

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

        this.hBox.getChildren().addAll(this.labelOrder, this.buttonPlus, this.buttonMin, this.tfOrder, this.buttonSave);
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
                update();
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
        this.g2d.setStroke(new BasicStroke(5.0f / ((float)Math.pow(1.5f, this.order))));
        for (Shape each : this.triangles){
            this.g2d.draw(each);
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

    private void update(){
        if (!this.readTextFields()){
            this.tfError();
        }
    }

    private boolean readTextFields(){
        try{
            int order = Integer.parseInt(this.tfOrder.getText());
            if (order != this.order){
                this.order = order;
            }
            if (this.order > 11){
                this.order = 11;
                this.tfOrder.setText("11");
            }
            this.triangles = new ArrayList<>();
            this.setSierpinski();
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
        g.setStroke(new BasicStroke(5.0f / ((float)Math.pow(1.5f, this.order))));

        AffineTransform inverse = this.camera.getInverse();

        Point2D a1 = new Point2D(this.anchor1.getX()/inverse.getScaleX(), this.anchor1.getY()/inverse.getScaleY());
        Point2D a2 = new Point2D(this.anchor2.getX()/inverse.getScaleX(), this.anchor2.getY()/inverse.getScaleY());
        Point2D a3 = new Point2D(this.anchor3.getX()/inverse.getScaleX(), this.anchor3.getY()/inverse.getScaleY());

        this.displayTriangles(inverse, this.order, a1, a2, a3);

        try {
            ImageIO.write(this.renderIMG, "png", new File("SaveFolder/SierpinskiTriangle@_n(" + this.order + ")_zoom(" + 1/inverse.getScaleX() + ")@(" + inverse.getTranslateX() + "x_" + inverse.getTranslateY() + "y).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void tfError(){
        this.labelError.setText("Make sure every TextField is formatted right.\n" +
                "The order TextField should be an integer.\n" +
                "Example: 5."
        );
        this.popUp.show();
        this.tfOrder.setText("0");
    }

    private void setSierpinski(){
        displayTriangles(this.order, this.anchor1, this.anchor2, this.anchor3);
    }

    private void setTextFields(){
        this.tfOrder.setText("" + this.order);
    }

    public Node getNode(){ return this.vBox;}
}
