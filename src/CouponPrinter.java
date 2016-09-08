import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CouponPrinter {
	public static final int KEY_INPUT_SPEED = 20;
	private static final int OFFSET = -10;
	
	private Image image;
	private ImageView coupon_printer;
	private int x_position;
	private int y_position;
	
	public CouponPrinter() {
		image = new Image("printer.png");
		coupon_printer = new ImageView();
	}
	
	public CouponPrinter(int x, int y) {
		image = new Image("printer.png");
		coupon_printer = new ImageView();
		x_position = x;
		y_position = y;
	}

	public ImageView drawCouponPrinter() {
		coupon_printer.setImage(image);
		coupon_printer.setX(x_position);
		coupon_printer.setY(y_position);
		return coupon_printer;
	}
	
	public void move(String direction) {
        if (direction.equals("right") && x_position <= (Main.SCREEN_WIDTH - Main.PRINTER_WIDTH - KEY_INPUT_SPEED)) {
        	coupon_printer.setX(coupon_printer.getX() + KEY_INPUT_SPEED);
        	x_position = (int) coupon_printer.getX();
        }
        if (direction.equals("left") && x_position >= OFFSET) {
        	coupon_printer.setX(coupon_printer.getX() - KEY_INPUT_SPEED);
        	x_position = (int) coupon_printer.getX();
        }
    }
	
	public int getXPosition() {
		return x_position;
	}

	public int getYPosition() {
		return y_position;
	}
}