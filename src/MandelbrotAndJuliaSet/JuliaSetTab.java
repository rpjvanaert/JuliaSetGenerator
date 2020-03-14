package MandelbrotAndJuliaSet;


import General.Camera;
import javafx.animation.AnimationTimer;
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

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class JuliaSetTab implements TabInterface {
    private HBox hBox;
    private VBox vBox;
    private Canvas canvas;
    private FXGraphics2D g2d;
    private Camera camera;

    private JuliaSetLogic juliaSetLogic;
    private BufferedImage renderIMG;

    private Button buttonRender, buttonReset;
    private Label labelNullR, labelNullI, labelFocusR, labelFocusI, labelStepSize, labelIterations;
    private TextField tfNullR, tfNullI, tfFocusR, tfFocusI, tfStepSize, tfIterations;

    private Stage popUp;
    private Label labelError;

    public JuliaSetTab(){
        this.hBox = new HBox();
        this.vBox = new VBox();
        this.canvas = new Canvas(1920, 1080);
        this.g2d = new FXGraphics2D(this.canvas.getGraphicsContext2D());

        this.buttonRender = new Button("Render");
        this.buttonRender.setOnAction(event -> {
            if (this.readTextFields()){
                this.render();
            } else {
                this.tfError();
            }
        });

        this.buttonReset = new Button("Reset TextFields");
        this.buttonRender.setOnAction(event -> {
            this.setNormalJuliaSet();
        });

        this.labelNullR = new Label("Null Point Real:");
        this.labelNullI = new Label("Null Point Imaginary:");
        this.labelFocusR = new Label("Focus Point Real:");
        this.labelFocusI = new Label("Focus Point Imaginary:");
        this.labelStepSize = new Label("Step size per pixel(render-zoom):");
        this.labelIterations = new Label("Max amount of iterations");

        this.tfNullR = new TextField(); this.tfNullI = new TextField();
        this.tfFocusR = new TextField(); this.tfFocusI = new TextField();
        this.tfStepSize = new TextField(); this.tfIterations = new TextField();

        this.hBox.getChildren().addAll(
                this.buttonRender, this.buttonReset,
                this.labelNullR, this.tfNullR,
                this.labelNullI, this.tfNullI,
                this.labelFocusR, this.tfFocusR,
                this.labelFocusI, this.tfFocusI,
                this.labelStepSize, this.tfStepSize,
                this.labelIterations, this.tfIterations
        );
        this.vBox.getChildren().addAll(this.hBox, this.canvas);
        this.setNormalJuliaSet();

        this.popUp = new Stage();
        VBox errorVBOX = new VBox();
        this.labelError = new Label(
                "Make sure every TextField is formatted right.\n" +
                        "Every TextField should be a float.\n" +
                        "Except Iterations, that should be an integer and bigger than 0.\n" +
                        "Examples: float: 0.0f  or  integer: 10");
        errorVBOX.getChildren().add(this.labelError);
        this.popUp.setScene(new Scene(errorVBOX));
        this.popUp.setTitle("Error Pop-up");
        Image icon = new Image(getClass().getResourceAsStream("/GUI-Images/error.png"));
        this.popUp.getIcons().add(icon);
        this.popUp.setWidth(400);
        this.popUp.setHeight(120);

        this.camera = new Camera(this.canvas, this.g2d);
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

    public void draw(){
        if (this.camera.getInverse() == null){
            this.g2d.clearRect(0,0, 1920, 1080);
        } else {
            AffineTransform inverse = this.camera.getInverse();
            this.g2d.clearRect(
                    -1,
                    -1,
                    (int)(inverse.getTranslateX() + 1920 * inverse.getScaleX()),
                    (int)(inverse.getTranslateY() + 1080 * inverse.getScaleY())
            );
        }

        this.g2d.setTransform(this.camera.getTransform(1920,1080, false));
        this.g2d.drawImage(this.renderIMG, null, null);
    }

    private void tfError(){ this.popUp.show(); }

    private boolean readTextFields() {
        try{
            float nullR = Float.parseFloat(this.tfNullR.getText());
            float nullI = Float.parseFloat(this.tfNullI.getText());
            float focusR = Float.parseFloat(this.tfFocusR.getText());
            float focusI = Float.parseFloat(this.tfFocusI.getText());
            float stepSize = Float.parseFloat(this.tfStepSize.getText());
            int iterations = Integer.parseInt(this.tfIterations.getText());
            this.setJuliaSet(nullR, nullI, focusR, focusI, stepSize, iterations);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    private void render() {
        this.juliaSetLogic.initMandlebrotSet();
        this.renderIMG = this.juliaSetLogic.getImage();
        this.camera = new Camera(this.canvas, this.g2d);
    }

    private void setJuliaSet(float nullR, float nullI, float focusR, float focusI, float stepSize, int iterations){
        this.juliaSetLogic = new JuliaSetLogic(1920, 1080, nullR, nullI, focusR, focusI, stepSize);
        this.juliaSetLogic.setMaxIterations(iterations);
    }

    private void setNormalJuliaSet(){
        this.tfNullR.setText("-0.8f"); this.tfNullI.setText("0.156f"); this.tfFocusR.setText("0.0f"); this.tfFocusI.setText("0.0f"); this.tfStepSize.setText("0.001f"); this.tfIterations.setText("1000");
    }

    public Node getNode(){
        return this.vBox;
    }
}
