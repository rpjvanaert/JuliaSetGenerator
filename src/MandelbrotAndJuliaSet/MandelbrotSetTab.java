package MandelbrotAndJuliaSet;

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
import General.Camera;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

//@TODO readTextFields(); Read every textField and return false if not right format.
//@TODO tfError(); Error pop up for wrong textField formatting.

public class MandelbrotSetTab implements TabInterface{
    private HBox hBox;
    private VBox vBox;
    private Canvas canvas;
    private FXGraphics2D g2d;
    private Camera camera;

    private Button buttonRender, buttonReset;
    private Label labelFocusR, labelFocusI, labelStepSize, labelIterations;
    private TextField tfFocusR, tfFocusI, tfStepSize, tfIterations;

    private Button zoomIn, zoomOut;
    private float stepSize;

    private Stage popUp;
    private Label labelError;

    private JuliaSetLogic mandelbrotSetLogic;
    private BufferedImage renderIMG;

    public MandelbrotSetTab(){
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
        this.buttonReset.setOnAction(event -> {
            this.setNormalMandelbrot();
        });

        this.labelFocusR = new Label("Focus Point Real:");
        this.labelFocusI = new Label("Focus Point Imaginary:");
        this.labelStepSize = new Label("Step size per pixel(render-zoom):");
        this.labelIterations = new Label("Max amount of iterations");

        this.tfFocusR = new TextField(); this.tfFocusI = new TextField();
        this.tfStepSize = new TextField(); this.tfIterations = new TextField();

        this.zoomIn = new Button("+");
        this.zoomIn.setOnAction(event -> {
            this.stepSize /= 1.50f;
            this.tfStepSize.setText("" + this.stepSize);
        });
        this.zoomOut = new Button("-");
        this.zoomOut.setOnAction(event -> {
            this.stepSize *= 1.50f;
            this.tfStepSize.setText("" + this.stepSize);
        });


        this.hBox.getChildren().addAll(
                this.buttonRender, this.buttonReset,
                this.labelFocusR, this.tfFocusR,
                this.labelFocusI, this.tfFocusI,
                this.labelStepSize, this.zoomIn, this.zoomOut, this.tfStepSize,
                this.labelIterations, this.tfIterations
        );
        this.setNormalMandelbrot();
        this.vBox.getChildren().addAll(this.hBox, this.canvas);

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

    private void tfError() {
        this.popUp.show();
    }

    private boolean readTextFields() {
        try{
            float focusR = Float.parseFloat(this.tfFocusR.getText());
            float focusI = Float.parseFloat(this.tfFocusI.getText());
            float stepSize = Float.parseFloat(this.tfStepSize.getText());
            int iterations = Integer.parseInt(this.tfIterations.getText());
            this.setMandelbrot(focusR, focusI, stepSize, iterations);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    private void render() {
        this.mandelbrotSetLogic.initMandlebrotSet();
        this.renderIMG = this.mandelbrotSetLogic.getImage();
        this.camera = new Camera(this.canvas, this.g2d);
    }
    private void setMandelbrot(float focusR, float focusI, float stepSize, int iterations){
        this.mandelbrotSetLogic = new JuliaSetLogic(1920, 1080, focusR, focusI, stepSize);
        this.mandelbrotSetLogic.setMaxIterations(iterations);
    }

    private void setNormalMandelbrot(){
        this.stepSize = 0.002f;
        this.tfFocusR.setText("0.0f"); this.tfFocusI.setText("0.0f"); this.tfStepSize.setText("" + this.stepSize); this.tfIterations.setText("1000");
        this.setMandelbrot(0.0f, 0.0f, 0.002f, 1000);
    }

    public Node getNode(){ return this.vBox; }
}
