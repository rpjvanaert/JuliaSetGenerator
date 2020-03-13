import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestMain {

    public static void main(String[] args) {
        float golderRatio = 1.6180339887f;
//        JuliaSetLogic juliaSetLogic = new JuliaSetLogic(3840,2160, -0.0f, 0.0f, -0.0f, 0.0f,  0.001f);
        JuliaSetLogic juliaSetLogic = new JuliaSetLogic(1920,1080, golderRatio - 2f, golderRatio - 1f, 0.0f, 0.0f, 0.0025f);
        juliaSetLogic.setMaxIterations(1000);
        juliaSetLogic.initJuliaSet();
        BufferedImage juliaIMG = juliaSetLogic.getImage();
        try {
            ImageIO.write(juliaIMG, "png", new File("resource/JuliaTestDrop/" + juliaSetLogic.getFileNamePreset() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(juliaSetLogic.getFileNamePreset());
    }


}
