import javafx.scene.shape.*;
import javafx.scene.paint.Color;

public class Coupon {
	public static final int WIDTH = 20;
	public static final int HEIGHT = 10;
	private static final int COUPON_SPEED = 500;
	private Rectangle coupon;
	
	public Coupon() {
		coupon = new Rectangle();
	}
	
	public Rectangle drawCoupon(int x, int y) {
		coupon.setWidth(WIDTH);
		coupon.setHeight(HEIGHT);
		coupon.setX(x-WIDTH/2);
		coupon.setY(y-HEIGHT/2);
		coupon.setFill(Color.WHITE);
		return coupon;
	}
	
	public void travel(double time_step) {
		coupon.setY(coupon.getY() - COUPON_SPEED*time_step);
	}
}