package Others;

import javafx.geometry.Point2D;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;

public class Line {
    private Point2D p1;
    private Point2D p2;

    public Line(Point2D p1, Point2D p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public void draw(FXGraphics2D g2d){
        g2d.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
    }

    public void draw(Graphics2D g){
        g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
    }
}
