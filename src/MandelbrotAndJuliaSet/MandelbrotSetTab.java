package MandelbrotAndJuliaSet;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.image.BufferedImage;

//@TODO readTextFields(); Read every textField and return false if not right format.
//@TODO tfError(); Error pop up for wrong textField formatting.

public class MandelbrotSetTab {
    private HBox hBox;
    private VBox vBox;
    private Canvas canvas;
    private FXGraphics2D g2d;

    private Button buttonRender, buttonReset;
    private Label labelFocusR, labelFocusI, labelStepSize, labelIterations;
    private TextField tfFocusR, tfFocusI, tfStepSize, tfIterations;

    private Stage popUp;
    private Label labelError;
    private String stringError;

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
        this.labelStepSize = new Label("Distance between each pixel:");
        this.labelIterations = new Label("Max amount of iterations");

        this.tfFocusR= new TextField(); this.tfFocusI = new TextField();
        this.tfStepSize = new TextField(); this.tfIterations = new TextField();

        this.hBox.getChildren().addAll(
                this.buttonRender, this.buttonReset,
                this.labelFocusR, this.tfFocusR,
                this.labelFocusI, this.tfFocusI,
                this.labelStepSize, this.tfStepSize,
                this.labelIterations, this.tfIterations
        );
        this.setNormalMandelbrot();
        this.vBox.getChildren().addAll(this.hBox, this.canvas);

        this.popUp = new Stage();
        VBox errorVBOX = new VBox();
        this.labelError = new Label("Make sure every TextField is formatted right.\nEvery TextField should be a float.\nExcept Iterations, that should be float.\n");
        errorVBOX.getChildren().add(this.labelError);
        this.popUp.setScene(new Scene(errorVBOX));
    }

    private void tfError() {
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
        this.g2d.clearRect(0,0, 1920, 1080);
        this.g2d.drawImage(this.renderIMG, null, null);
    }
    private void setMandelbrot(float focusR, float focusI, float stepSize, int iterations){
        this.mandelbrotSetLogic = new JuliaSetLogic(1920, 1080, focusR, focusI, stepSize);
        this.mandelbrotSetLogic.setMaxIterations(iterations);
    }

    private void setNormalMandelbrot(){
        this.tfFocusR.setText("0.0f"); this.tfFocusI.setText("0.0f"); this.tfStepSize.setText("0.002f"); this.tfIterations.setText("1000");
        this.setMandelbrot(0.0f, 0.0f, 0.002f, 1000);
    }

    public Node getNode(){ return this.vBox; }
}
