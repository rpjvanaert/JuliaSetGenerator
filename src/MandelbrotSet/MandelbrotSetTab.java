package MandelbrotSet;

import General.TabInterface;
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
import java.io.IOException;

public class MandelbrotSetTab implements TabInterface {
    private HBox hBox;
    private VBox vBox;
    private Canvas canvas;
    private FXGraphics2D g2d;
    private Camera camera;

    private Button buttonRender, buttonReset, buttonSave;
    private Label labelFocusR, labelFocusI, labelStepSize, labelIterations, labelHueCycleSpeed;
    private TextField tfFocusR, tfFocusI, tfStepSize, tfIterations, tfHueCycleSpeed;

    private Stage popUp;
    private Label labelError;

    private MandelSetLogic mandelbrotSetLogic;
    private BufferedImage renderIMG = null;

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

        this.buttonSave = new Button("Save Image");
        this.buttonSave.setOnAction(event -> {
            if(this.renderIMG != null){
                try {
                    ImageIO.write(this.renderIMG, "png", new File("saveFolder/" + this.mandelbrotSetLogic.getFileNamePreset() + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                this.saveError();
            }
        });

        this.labelFocusR = new Label("Focus Point Real:");
        this.labelFocusI = new Label("Focus Point Imaginary:");
        this.labelStepSize = new Label("Step size per pixel(render-zoom):");
        this.labelIterations = new Label("Max amount of iterations");
        this.labelHueCycleSpeed = new Label("Hue Cycle Speed");

        this.tfFocusR = new TextField(); this.tfFocusI = new TextField();
        this.tfStepSize = new TextField(); this.tfIterations = new TextField();
        this.tfHueCycleSpeed = new TextField();



        this.hBox.getChildren().addAll(
                this.buttonRender, this.buttonReset,
                this.labelFocusR, this.tfFocusR,
                this.labelFocusI, this.tfFocusI,
                this.labelStepSize, this.tfStepSize,
                this.labelIterations, this.tfIterations,
                this.labelHueCycleSpeed, this.tfHueCycleSpeed,
                this.buttonSave
        );
        this.setNormalMandelbrot();
        this.vBox.getChildren().addAll(this.hBox, this.canvas);

        this.popUp = new Stage();
        VBox errorVBOX = new VBox();
        this.labelError = new Label();
        errorVBOX.getChildren().add(this.labelError);
        this.popUp.setScene(new Scene(errorVBOX));
        this.popUp.setTitle("Error Pop-up");
        Image icon = new Image(getClass().getResourceAsStream("/GUI-Images/error.png"));
        this.popUp.getIcons().add(icon);
        this.popUp.setWidth(400);
        this.popUp.setHeight(120);

        this.camera = new Camera(this.canvas);


        new AnimationTimer(){
            public void handle(long now){
                draw();
            }
        }.start();
    }

    public void draw(){
        AffineTransform inverse = this.camera.getInverse();
        this.g2d.clearRect(
                -1,
                -1,
                (int)(inverse.getTranslateX() + 1920 * inverse.getScaleX()),
                (int)(inverse.getTranslateY() + 1080 * inverse.getScaleY())
        );

        this.g2d.setTransform(this.camera.getTransform(1920,1080, false));
        this.g2d.drawImage(this.renderIMG, null, null);
    }

    private void tfError() {
        this.labelError.setText("Make sure every TextField is formatted right.\n" +
                "Every TextField should be a float.\n" +
                "Except Iterations, this should be an integer and bigger than 0.\n" +
                "Examples: float: 0.0f  or  integer: 100");
        this.popUp.show();
    }

    private void saveError() {
        this.labelError.setText("Render Image before saving Image!");
        this.popUp.show();
    }

    private boolean readTextFields() {
        try{
            float focusR = Float.parseFloat(this.tfFocusR.getText());
            float focusI = Float.parseFloat(this.tfFocusI.getText());
            float stepSize = Float.parseFloat(this.tfStepSize.getText());
            int iterations = Integer.parseInt(this.tfIterations.getText());
            float hueCycleSpeed = Float.parseFloat(this.tfHueCycleSpeed.getText());
            this.setMandelbrot(focusR, focusI, stepSize, iterations, hueCycleSpeed);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    private void render() {
        this.mandelbrotSetLogic.init();
        this.renderIMG = this.mandelbrotSetLogic.getImage();
        this.camera = new Camera(this.canvas);
    }
    private void setMandelbrot(float focusR, float focusI, float stepSize, int iterations, float hueCycleSpeed){
        this.mandelbrotSetLogic = new MandelSetLogic(1920, 1080, focusR, focusI, stepSize);
        this.mandelbrotSetLogic.setMaxIterations(iterations);
        this.mandelbrotSetLogic.setHueCycleSpeed(hueCycleSpeed);
    }

    private void setNormalMandelbrot(){
        this.tfFocusR.setText("0.0f"); this.tfFocusI.setText("0.0f"); this.tfStepSize.setText("0.002f"); this.tfIterations.setText("1000"); this.tfHueCycleSpeed.setText("0.002f");
        this.setMandelbrot(0.0f, 0.0f, 0.002f, 1000, 0.002f);
    }

    public Node getNode(){ return this.vBox; }
}
