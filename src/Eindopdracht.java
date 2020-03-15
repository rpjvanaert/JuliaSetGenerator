import General.TabInterface;
import MandelbrotAndJuliaSet.JuliaSetTab;
import MandelbrotAndJuliaSet.MandelbrotSetTab;
import Others.SierpinskiTriangleTab;
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
        Tab sierpinskiTab = new Tab("Sierpinski Triangle");

        TabInterface mandelbrotSetTab = new MandelbrotSetTab();
        mandelbrotTab.setContent(mandelbrotSetTab.getNode());

        TabInterface juliaSetTab = new JuliaSetTab();
        juliaTab.setContent(juliaSetTab.getNode());

        TabInterface sierpinskiTriangleTab = new SierpinskiTriangleTab();
        sierpinskiTab.setContent(sierpinskiTriangleTab.getNode());

        tabPane.getTabs().addAll(mandelbrotTab, juliaTab, sierpinskiTab);

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
