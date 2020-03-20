package Others;

import javafx.geometry.Point2D;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;

public class TreeLine {
    private Point2D p1;
    private Point2D p2;
    private BasicStroke stroke;

    /**
     * TreeLine Constructor
     * sets 2 Point2D's to create line with stroke width.
     * @param p1
     * @param p2
     * @param stroke
     */

    public TreeLine(Point2D p1, Point2D p2, float stroke){
        this.p1 = p1;
        this.p2 = p2;
        this.stroke = new BasicStroke(stroke);
    }


    /**
     * draw
     * draws line on FXGraphics2D object with correct translations for OtherFractals.
     * @param g2d
     */

    public void draw(FXGraphics2D g2d){
        g2d.setStroke(this.stroke);
        g2d.drawLine((int)p1.getX(), (int)p1.getY() * -1 + 540, (int)p2.getX(), (int)p2.getY() * -1 + 540);
    }


    /**
     * draw
     * draws line on Graphics2D object with correct translations for the image.
     * Does NOT take in CameraTransform.
     * @param g
     */

    public void draw(Graphics2D g){
        g.setStroke(this.stroke);
        g.drawLine(
                (int)((p1.getX() + 960)),
                (int)((p1.getY() * -1 + 1080)),
                (int)((p2.getX() + 960)),
                (int)((p2.getY() * -1 + 1080))
        );
    }
}
