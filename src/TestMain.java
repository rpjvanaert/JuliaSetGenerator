import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestMain {

    public static void main(String[] args) {
//        for (float x = -1.5f; x < 1.5f; x += 0.1f){
//            for (float y = -1.5f; y < 1.5f; y += 0.4f){
//                JuliaSetLogic juliaSetLogic = new JuliaSetLogic(1920,1080, x, y, 0.0f, 0.0f,  0.001f);
//                juliaSetLogic.setMaxIterations(1000);
//                juliaSetLogic.initJuliaSet();
//                BufferedImage juliaIMG = juliaSetLogic.getImage();
//                try {
//                    ImageIO.write(juliaIMG, "png", new File("resource/JuliaTestDrop/" + juliaSetLogic.getFileNamePreset() + ".png"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        JuliaSetLogic juliaSetLogic = new JuliaSetLogic(1920,1080, 0.25f, 0.0f, -1.0f, 0.0f,  0.002f);
        juliaSetLogic.setMaxIterations(1000);
        juliaSetLogic.initJuliaSet();
        BufferedImage juliaIMG = juliaSetLogic.getImage();
        try {
            ImageIO.write(juliaIMG, "png", new File("resource/JuliaTestDrop/" + juliaSetLogic.getFileNamePreset() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
