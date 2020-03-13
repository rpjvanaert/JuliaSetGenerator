import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class MandelbrotSet {
    public static void main(String[] args) throws Exception {
        jul();
        mandelbrot();
    }
    public static void mandelbrot() throws IOException
    {
        int width=3840,height=2160,MAX=100;
        BufferedImage image=new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        float sat=1f;
        for(int row=0;row<height;row++)
        {
            for(int col=0;col<width;col++)
            {
                double c_real=2.0*(col-(width/2))/(width/2);
                double c_img=1.33*(row-(height/2))/(height/2);

                int i=0;
                double x=0,y=0;
                while(x*x+y*y<4 && i<MAX){
                    double x_new=x*x-y*y+c_real;
                    y=2*x*y+c_img;
                    x=x_new;
                    i++;
                }
                float brightness=i<MAX?1f:0;
                float hue=(i)/255.0F;
                image.setRGB(col, row, Color.getHSBColor(hue, sat, brightness).getRGB());
            }
        }
        ImageIO.write(image, "png", new File("vnk.png"));
    }

    public static void jul() throws Exception {

        int width=1920,height=1080,MAX=75;
        BufferedImage image=new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        int black=0x000000;
        int white=0xFFFFFF;
        float sat=1f;
        double c_real=-0.76;
        double c_img=0.11;

        for(int row=0;row<height;row++){
            for(int col=0;col<width;col++){
                double x=2.0*(col-(width/2))/(width/2);
                double y=1.33*(row-(height/2))/(height/2);

                int i=0;
                for(i=0;i<MAX;i++)
                {
                    double x_new=x*x-y*y+c_real;
                    y=2*x*y+c_img;
                    x=x_new;

                    if(x*x+y*y>4)
                        break;

                }

                float brightness=i<MAX?1f:0;
                float hue=(i%256)/255.0F;
                image.setRGB(col, row, Color.getHSBColor(hue, sat, brightness).getRGB());
            }
        }

        ImageIO.write(image, "png", new File("jull.png"));
    }
}
