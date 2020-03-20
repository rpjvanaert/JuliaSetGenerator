import General.TabInterface;
import JuliaSet.JuliaSetTab;
import MandelbrotSet.MandelbrotSetTab;
import Others.OtherFractalsTab;
import javafx.application.Application;

import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;


public class Eindopdracht extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab mandelbrotTab = new Tab("MandelbrotSet");
        Tab juliaTab = new Tab("JuliaSet");
        Tab otherTab = new Tab("Sierpinski Triangle");

        TabInterface mandelbrotSetTab = new MandelbrotSetTab();
        mandelbrotTab.setContent(mandelbrotSetTab.getNode());

        TabInterface juliaSetTab = new JuliaSetTab();
        juliaTab.setContent(juliaSetTab.getNode());

        TabInterface otherFractalTab = new OtherFractalsTab();
        otherTab.setContent(otherFractalTab.getNode());

        tabPane.getTabs().addAll(mandelbrotTab, juliaTab, otherTab);

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
