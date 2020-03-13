import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestMain {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        JuliaSetLogic juliaSetLogic = new JuliaSetLogic(1920,1080, 0.254f, 0.0004f,  0.000001f);
        juliaSetLogic.setMaxIterations(1000);
        juliaSetLogic.initJuliaSet();
        BufferedImage juliaIMG = juliaSetLogic.getImage();
        long mid = System.currentTimeMillis();
        try {
            ImageIO.write(juliaIMG, "png", new File("resource/JuliaTestDrop/" + juliaSetLogic.getFileNamePreset() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Calculating Pixels: " + (mid - start));
        System.out.println("Saving Image: " + (System.currentTimeMillis() - mid));
        System.out.println("Total time: " + (System.currentTimeMillis() - start));
        System.out.println("Saved: " + juliaSetLogic.getFileNamePreset());
    }


}
