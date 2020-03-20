package MandelbrotSet;

import General.Complex;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelSetLogic {
    private int[][] iterations;
    private float stepSize;
    private int maxIterations;
    private float focusPointR, focusPointI;
    private int resWidth, resHeight;
    private float cReal, cImag;
    private float hueCycleSpeed;


    /**
     * MandelSetLogic Constructor
     *
     * Sets up the mandelbrot set with every needed parameter (does NOT compute/init).
     *
     * @param resWidth
     * @param resHeight
     * @param focusPointR
     * @param focusPointI
     * @param stepSize
     */
    public MandelSetLogic(int resWidth, int resHeight, float focusPointR, float focusPointI, float stepSize){
        this.stepSize = stepSize;
        this.resWidth = resWidth; this.resHeight = resHeight;
        this.focusPointR = focusPointR; this.focusPointI = focusPointI;
        this.iterations = new int[resWidth][resHeight];
        this.maxIterations = 500;
        this.cReal = cReal; this.cImag = cImag;
        this.hueCycleSpeed = 0.002f;
    }

    /**
     * getFileNamePreset
     * returns String that contains that puts the render in the right format of the fileName.
     * @return String
     */

    public String getFileNamePreset(){
        return "Mandlebrot@(" + this.focusPointR + " + " + this.focusPointI + "i)wZoom_" + (1/this.stepSize) + "x_HCS(" + this.hueCycleSpeed +")_res(" + this.resWidth + "x" + this.resHeight + ")px";
    }

    /**
     * getImage
     * returns the BufferedImage from the iterations done in the init method.
     * @return BufferedImage
     */

    public BufferedImage getImage(){
        BufferedImage img = new BufferedImage(this.resWidth, this.resHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < this.resWidth; ++x){
            for (int y = 0; y < this.resHeight; ++y){
                float iterate = this.iterations[x][y];
                float bright;
                float sat;
                if (iterate < 0){
                    bright = 0.0f;
                    sat = 0.0f;
                } else if (iterate < 10){
                    bright = iterate * 0.01f;
                    sat = 0.1f;
                } else {
                    bright = 1.0f;
                    sat = 1.0f;
                }
                img.setRGB(x, y, Color.getHSBColor(iterate * this.hueCycleSpeed, sat, bright).getRGB());
            }
        }
        return img;
    }

    /**
     * setHueCycleSpeed
     * sets the speed of which the color rotates per iteration.
     * @param hueCycleSpeed float
     */

    public void setHueCycleSpeed(float hueCycleSpeed){ this.hueCycleSpeed = hueCycleSpeed; }

    /**
     * init
     * initializes MandelbrotSet with all set parameters.
     * Puts in amount of iterations in an int matrix array with correct resolution width and height.
     */
    public void init(){
        int indexR = 0;
        int indexI = 0;
        float width = (float)resWidth * stepSize;

        float rMin = focusPointR - 0.5f * width;
        float rMax = focusPointR + 0.5f * width;

        float iMin = focusPointI - 0.5f * (float)resHeight * stepSize;
        float iMax = focusPointI + 0.5f * (float)resHeight * stepSize;

        for (float r = rMin; r < rMax; r += stepSize){
            for (float i = iMin; i < iMax; i += stepSize){
                if (indexI >= resHeight){
                    break;
                }
                this.iterations[indexR][indexI] = iterations(r, i, cReal, cImag);
                indexI++;
            }
            indexI = 0;
            indexR++;
            if (indexR >= resWidth){
                break;
            }
        }
    }

    /**
     * iterations
     * Iterates until iterated more than maxIterations or above complex above 2.
     * returns amount of iterations.
     * @param cReal
     * @param cImag
     * @param real
     * @param imag
     * @return int
     */

    private int iterations(float cReal, float cImag, float real, float imag){
        Complex c = new Complex(cReal, cImag);
        Complex z = new Complex(real, imag);
        for (int i = 0; i < this.maxIterations; ++i){
            z.square();
            z.addToThis(c);
            if (z.getReal() >= 2 || z.getImaginary() >= 2){
                return i;
            }
        }
        return -1;
    }

    /**
     * setMaxIterations
     * Sets max amount of iterations done in init.
     * @param amount
     */

    public void setMaxIterations(int amount){
        if (amount > 0){
            this.maxIterations = amount;
        }
    }
}

