package Others;

import javafx.geometry.Point2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class SierpinskiLogic {
    private ArrayList<Shape> triangles;

    public SierpinskiLogic(){
        this.triangles = new ArrayList<>();
    }

    public ArrayList<Shape> getTriangles(){ return this.triangles; }

    public void displayTriangles(int order, Point2D p1, Point2D p2, Point2D p3){
        if (order == 0){
            GeneralPath triangle = new GeneralPath();
            triangle.moveTo(p1.getX(), p1.getY());
            triangle.lineTo(p2.getX(), p2.getY());
            triangle.lineTo(p3.getX(), p3.getY());
            triangle.lineTo(p1.getX(), p1.getY());
            triangle.closePath();
            this.triangles.add(triangle);

        } else {
            Point2D p12 = p1.midpoint(p2);
            Point2D p23 = p2.midpoint(p3);
            Point2D p31 = p3.midpoint(p1);

            displayTriangles(order - 1, p1, p12, p31);
            displayTriangles(order - 1, p12, p2, p23);
            displayTriangles(order - 1, p31, p23, p3);
        }
    }

    public void displayTriangles(Graphics2D g, AffineTransform inverse, int order, Point2D p1, Point2D p2, Point2D p3){
        if (order == 0){
            g.draw(new Line2D.Double(
                    (p1.getX() - inverse.getTranslateX()),
                    (p1.getY() - inverse.getTranslateY()),
                    (p2.getX() - inverse.getTranslateX()),
                    (p2.getY() - inverse.getTranslateY()))
            );
            g.draw(new Line2D.Double(
                    (p3.getX() - inverse.getTranslateX()),
                    (p3.getY() - inverse.getTranslateY()),
                    (p2.getX() - inverse.getTranslateX()),
                    (p2.getY() - inverse.getTranslateY())
            ));
            g.draw(new Line2D.Double(
                    (p1.getX() - inverse.getTranslateX()),
                    (p1.getY() - inverse.getTranslateY()),
                    (p3.getX() - inverse.getTranslateX()),
                    (p3.getY() - inverse.getTranslateY()))
            );
        } else {
            Point2D p12 = p1.midpoint(p2);
            Point2D p23 = p2.midpoint(p3);
            Point2D p31 = p3.midpoint(p1);

            displayTriangles(g, inverse, order - 1, p1, p12, p31);
            displayTriangles(g, inverse, order - 1, p12, p2, p23);
            displayTriangles(g, inverse, order - 1, p31, p23, p3);
        }
    }
}