package PresetShow;

import javafx.scene.image.Image;
import java.io.File;
import java.util.ArrayList;

public class ImageShow {
    private ArrayList<Image> presetImages;
    private ArrayList<String> imageNames;
    private int index;

    public ImageShow(String imageFolder){
        //set Image save folder and initialize with preset size for images and names.
        File savedFolder = new File(imageFolder);
        this.presetImages = new ArrayList<>(savedFolder.listFiles().length);
        this.imageNames = new ArrayList<>(savedFolder.listFiles().length);

        //Read ImageFolder and add Image and name to arrayLists
        for (File each : savedFolder.listFiles()){
            this.presetImages.add(new Image(each.toURI().toString()));
            this.imageNames.add(each.getName().replace(".png", ""));
        }
        this.index = 0;
    }

    /**
     * next
     * adds 1 to index, sets index 0 if as big as amount of images.
     */
    public void next(){
        ++this.index;
        if (this.index >= this.presetImages.size()){
            this.index = 0;
        }
    }

    /**
     * getIMG
     * returns Image of current index.
     * @return Image
     */
    public Image getIMG(){
        return this.presetImages.get(this.index);
    }

    /**
     * getName
     * returns name of current index.
     * @return String
     */
    public String getName(){
        return this.imageNames.get(this.index);
    }
}
