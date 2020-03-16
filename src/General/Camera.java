package General;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;


/**
 * Created by johan on 15-2-2017.
 * Edited and adapted by Ralf van Aert on 14-3-2020.
 */
public class Camera {
	private Point2D centerPoint = new Point2D.Double(0,0);
	private double zoom = 1;
	private double rotation = 0;
	private Point2D lastMousePos;
	private Canvas canvas;
	private Resizable resizable;
	private FXGraphics2D g2d;
	private AffineTransform inverse = new AffineTransform();

	public Camera(Canvas canvas, FXGraphics2D g2d) {
		this.canvas = canvas;
		this.g2d = g2d;

		canvas.setOnMousePressed(e -> {lastMousePos = new Point2D.Double(e.getX(), e.getY());});
		canvas.setOnMouseDragged(e -> mouseDragged(e));
		canvas.setOnScroll(e-> mouseScroll(e));
	}

	public AffineTransform getTransform(int windowWidth, int windowHeight, boolean center)  {
		AffineTransform tx = new AffineTransform();
		if (center){
			tx.translate(windowWidth/2, windowHeight/2);
		}
		tx.scale(zoom, zoom);
		tx.translate(centerPoint.getX(), centerPoint.getY());
		tx.rotate(rotation);
		try {
			this.inverse = tx.createInverse();
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		return tx;
	}

	public void mouseDragged(MouseEvent e) {
		if(e.getButton() == MouseButton.MIDDLE) {
			centerPoint = new Point2D.Double(
					centerPoint.getX() - (lastMousePos.getX() - e.getX()) / zoom,
					centerPoint.getY() - (lastMousePos.getY() - e.getY()) / zoom
			);
			lastMousePos = new Point2D.Double(e.getX(), e.getY());
		}
	}

	public void mouseScroll(ScrollEvent e) {
		zoom *= (1 + e.getDeltaY()/250.0f);
	}

	public AffineTransform getInverse(){ return this.inverse;}
}
