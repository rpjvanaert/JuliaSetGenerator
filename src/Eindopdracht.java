import java.awt.*;
import java.awt.geom.*;

import MandelbrotAndJuliaSet.MandelbrotSetScene;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;


public class Eindopdracht extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab mandelbrotTab = new Tab("MandelbrotSet");
        MandelbrotSetScene mandelbrotSetScene = new MandelbrotSetScene();
        mandelbrotTab.setContent(mandelbrotSetScene.getNode());
        tabPane.getTabs().addAll(mandelbrotTab);

        stage.setScene(new Scene(tabPane));
        stage.setTitle("Ralf van Aert 2D-ComputerGraphics Eindopdracht");
        stage.setResizable(false);
        stage.setHeight(1080);
        stage.setWidth(1920);
        stage.show();
    }


    public static void main(String[] args) {
        launch(Eindopdracht.class);
    }
}
