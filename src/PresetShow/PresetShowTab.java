package PresetShow;

import General.TabInterface;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class PresetShowTab implements TabInterface {
    private BorderPane borderPane;
    private Label nameIMG;
    private ImageView imageView;
    private ImageShow imageShow;
    private String prefixLabel;

    public PresetShowTab(){
        // Initializes BorderPane, ImageView, label and ImageShow Objects.
        this.borderPane = new BorderPane();
        this.imageShow = new ImageShow("resource/SavedImages");
        this.imageView = new ImageView();
        this.prefixLabel = "Fractal Image currently shown:\t\t\t\t ";
        this.nameIMG = new Label(this.prefixLabel + this.imageShow.getName());

        // Sets ImageView to correct Image and onMouseClicked event.
        this.imageView.setImage(this.imageShow.getIMG());
        this.imageView.setOnMouseClicked(event -> { this.mouseClicked();});

        // Places Label and ImageView in BorderPane.
        this.borderPane.setTop(this.nameIMG);
        this.borderPane.setCenter(this.imageView);
    }

    /**
     * mouseClicked
     * next image in show. Sets ImageView and Label to it.
     */
    public void mouseClicked(){
        this.imageShow.next();
        this.imageView.setImage(this.imageShow.getIMG());
        this.nameIMG.setText(this.prefixLabel + this.imageShow.getName());
    }

    /**
     * getNode
     * returns BorderPane, itself.
     * @return Node
     */
    public Node getNode(){ return this.borderPane; }
}
