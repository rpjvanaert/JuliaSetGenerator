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
 * Changed and commented by Ralf van Aert on 20-3-2020.
 */

public class Camera {
	private Point2D centerPoint = new Point2D.Double(0,0);
	private double zoom = 1;
	private double rotation = 0;
	private Point2D lastMousePos;
	private AffineTransform inverse;

	/**
	 * Camera Constructor
	 *
	 * creates inverse without transformations and sets setOnAction for mouse on canvas.
	 *
	 * @param canvas
	 */

	public Camera(Canvas canvas) {
		this.inverse = new AffineTransform();

		canvas.setOnMousePressed(this::mousePressed);
		canvas.setOnMouseDragged(this::mouseDragged);
		canvas.setOnScroll(this::mouseScroll);
	}


	/**
	 * getTransform
	 *
	 * returns AffineTransform that needs to be done according to mouse actions done.
	 * creates inverseTransform for the AffineTransform returned.
	 *
	 * @param windowWidth
	 * @param windowHeight
	 * @param center
	 * @return AffineTransform
	 */

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


	/**
	 * mousePressed
	 * action handler for when mouse is pressed.
	 * saves position of mouse as lastMousePos.
	 *
	 * @param e
	 */

	private void mousePressed(MouseEvent e){
		lastMousePos = new Point2D.Double(e.getX(), e.getY());
	}


	/**
	 * mouseDragged
	 * action handler for when mouse is dragged.
	 * centerPoint is changed according to place mouse is dragged with right mouse button.
	 * also saves mousePosition as lastMousePos afterwards.
	 *
	 * @param e
	 */

	private void mouseDragged(MouseEvent e) {
		if(e.getButton() == MouseButton.SECONDARY) {
			centerPoint = new Point2D.Double(
					centerPoint.getX() - (lastMousePos.getX() - e.getX()) / zoom,
					centerPoint.getY() - (lastMousePos.getY() - e.getY()) / zoom
			);
			lastMousePos = new Point2D.Double(e.getX(), e.getY());
		}
	}


	/**
	 * mouseScroll
	 * action handler for when mouse is scrolled.
	 * modifies zoom according to zoom.
	 *
	 * @param e
	 */

	private void mouseScroll(ScrollEvent e) {
		zoom *= (1 + e.getDeltaY()/250.0f);
	}


	/**
	 * getInverse
	 * returns the InverseTransform that has been saved in the last getTransform call
	 * or an empty AffineTransform if there hasn't been a camera transform done.
	 * @return
	 */

	public AffineTransform getInverse(){ return this.inverse;}
}
