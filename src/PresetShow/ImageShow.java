package PresetShow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageShow {
    private ArrayList<BufferedImage> presetImages;
    private int index;

    public ImageShow(){
        File savedFolder = new File("SavedImages");
        this.presetImages = new ArrayList<>(savedFolder.listFiles().length);
        for (File each : savedFolder.listFiles()){
            try {
                this.presetImages.add(ImageIO.read(each));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.index = 0;
    }

    public void next(){
        ++this.index;
        if (this.index >= this.presetImages.size()){
            this.index = 0;
        }
    }

    public BufferedImage getIMG(){
        return this.presetImages.get(this.index);
    }
}
