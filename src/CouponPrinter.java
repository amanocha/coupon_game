/**
 * The class of the CouponPrinter object. It stores all of the information pertaining to the coupon
 * printer, which is the object that the user has control over. It also contains the methods that 
 * control the behavior of the coupon printer. The Game object has a method that creates a coupon
 * printer and calls this class to do so. It also accesses the coordinates of the coupon printer in
 * order to determine where to spawn a coupon.
 * 
 * @author Aninda Manocha
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;

public class CouponPrinter {
	/* The pixel size of the printer image is not the real size of the printer (it includes extra
	 * background space). The real size of the printer is needed to determine where to put its
	 * rectangular boundary.
	 */
	private static final int REAL_WIDTH = 126;
	private static final int REAL_HEIGHT = 106;
	private static final int OFFSET = Main.PRINTER_WIDTH/2 - REAL_WIDTH/2;
	private static final int KEY_INPUT_SPEED = 20;
	
	private ImageView coupon_printer;
	private int x_position;
	private int y_position;
	private Rectangle boundary;
	
	/***CONSTRUCTORS***/
	
	/**
	 * This constructor creates an empty CouponPrinter object.
	 */
	public CouponPrinter() {
		coupon_printer = new ImageView();
	}
	
	/**
	 * This constructor creates a CouponPrinter object at a specific position.
	 * @param x - the x coordinate of the top left corner of the CouponPrinter
	 * @param y - the y coordinate of the top left corner of the CouponPrinter
	 */
	public CouponPrinter(int x, int y) {
		coupon_printer = new ImageView();
		x_position = x;
		y_position = y;
		boundary = new Rectangle();
	}

	/**
	 * Sets the ImageView object so that it can display the coupon printer on the screen. The x and y
	 * coordinates of the ImageView object are also set, so that the coupon printer is positioned.
	 * @return the ImageView object containing the image of the coupon printer, which is displayed
	 */
	public ImageView drawCouponPrinter() {
		coupon_printer.setImage(new Image("printer.png"));
		coupon_printer.setX(x_position);
		coupon_printer.setY(y_position);
		return coupon_printer;
	}
	
	/**
	 * Sets the Rectangle object to fit around the display of the printer, so that it serves as a 
	 * boundary of the printer. The boundary is set to be black to blend in with the background.
	 * @return the boundary of the printer as a rectangle
	 */
	public Rectangle drawBoundary() {
		boundary.setX(Main.SCREEN_WIDTH/2 - REAL_WIDTH/2);
		boundary.setY(Main.SCREEN_HEIGHT - REAL_HEIGHT);
		boundary.setWidth(REAL_WIDTH);
		boundary.setHeight(REAL_HEIGHT - 60);
		boundary.setFill(Color.BLACK);
		return boundary;
	}
	
	/**
	 * Moves the printer right or left depending on which arrow key the user pushes.
	 * @param direction - the direction in which the printer should move; either "right" or "left"
	 */
	public void move(String direction) {
        if (direction.equals("right") && x_position <= (Main.SCREEN_WIDTH - Main.PRINTER_WIDTH + OFFSET - KEY_INPUT_SPEED)) {
        	coupon_printer.setX(coupon_printer.getX() + KEY_INPUT_SPEED); // update x position of printer
        	boundary.setX(boundary.getX() + KEY_INPUT_SPEED); // update x position of boundary
        	x_position = (int) coupon_printer.getX();
        }
        if (direction.equals("left") && x_position >= (KEY_INPUT_SPEED - OFFSET)) {
        	coupon_printer.setX(coupon_printer.getX() - KEY_INPUT_SPEED); // update x position of printer
        	boundary.setX(boundary.getX() - KEY_INPUT_SPEED); // update x position of boundary
        	x_position = (int) coupon_printer.getX();
        }
    }
	
	/*****GETTERS*****/
	
	/**
	 * This method accesses the x coordinate of the top left corner of the coupon printer's ImageView
	 * object.
	 * @return the x coordinate
	 */
	public int getXPosition() {
		return x_position;
	}
	
	/**
	 * This method accesses the y coordinate of the top left corner of the coupon printer's ImageView
	 * object.
	 * @return the y coordinate
	 */
	public int getYPosition() {
		return y_position;
	}
	
	/**
	 * This method accesses the coupon printer's ImageView object.
	 * @return the ImageView object
	 */
	public ImageView getCouponPrinter() {
		return coupon_printer;
	}
	
	/**
	 * This method accesses the Rectangle, which serves as the boundary of the printer. It is useful
	 * in determining when a letter collides with the printer.
	 * @return the boundary of the printer as a Rectangle
	 */
	public Rectangle getBoundary() {
		return boundary;
	}
}