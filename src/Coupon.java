/**
 * The class of the Coupon object. It stores all of the information pertaining to a coupon, which is 
 * shot from the center of the coupon printer when the user presses the "up" arrow key. The Game object
 * creates a Coupon object when the user presses the arrow key.
 * 
 * @author Aninda Manocha
 */
import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Coupon {
	public static final int WIDTH = 20;
	public static final int HEIGHT = 10;
	private static final int COUPON_SPEED = 500;
	
	private Game game;
	private Group root;
	private Rectangle coupon;
	
	/*****CONSTRUCTOR*****/
	
	/**
	 * This constructor creates a Coupon object, where the coupon is a rectangle. The coupon is to be 
	 * added to the screen through accessing the root node.
	 * @param new_root - the root node of the game
	 */
	public Coupon(Game new_game, Group new_root) {
		game = new_game;
		coupon = new Rectangle();
		root = new_root;
	}
	
	/**
	 * Sets the dimensions and color of the coupon, which is a Rectangle.
	 * @param x - the x coordinate of the center of the coupon
	 * @param y - the y coordinate of the center of the coupon
	 * @return the Rectangle object that serves as the coupon
	 */
	public Rectangle drawCoupon(int x, int y) {
		coupon.setWidth(WIDTH);
		coupon.setHeight(HEIGHT);
		coupon.setX(x-WIDTH/2);
		coupon.setY(y-HEIGHT/2);
		coupon.setFill(Color.WHITE);
		return coupon;
	}
	
	/**
	 * Moves the coupon upward after it is shot from the coupon printer. It moves quickly relative to
	 * other objects in the game in order to simulate being shot upwards.
	 * @param time_step - the elapsed time between each frame, which is multiplied by the velocity
	 */
	public void travel(double time_step) {
		coupon.setY(coupon.getY() - COUPON_SPEED*time_step);
		if (coupon.getY() <= Screen.TEXT_Y + 5) {
			disappear();
		}
	}
	
	/**
	 * Removes the coupon from the game, which occurs when the coupons reach the top of the screen.
	 */
	public void disappear() {
		game.getGameCoupons().remove(this);
		root.getChildren().remove(coupon);
	}
}