import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Coupon {
	public static final int WIDTH = 20;
	public static final int HEIGHT = 10;
	private static final int COUPON_SPEED = 500;
	
	private Group root;
	private Rectangle coupon;
	
	public Coupon(Group new_root) {
		coupon = new Rectangle();
		root = new_root;
	}
	
	public Rectangle drawCoupon(int x, int y) {
		coupon.setWidth(WIDTH);
		coupon.setHeight(HEIGHT);
		coupon.setX(x-WIDTH/2);
		coupon.setY(y-HEIGHT/2);
		coupon.setFill(Color.WHITE);
		return coupon;
	}
	
	public void disappear() {
		root.getChildren().remove(coupon);
	}
	
	public void travel(double time_step) {
		coupon.setY(coupon.getY() - COUPON_SPEED*time_step);
		if (coupon.getY() <= Game.TEXT_Y + 5) {
			disappear();
		}
	}
}