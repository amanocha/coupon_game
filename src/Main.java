/**
 * This is the main program for the game. 
 * 
 * @author Aninda Manocha
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 850;
	
	public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000/FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0/FRAMES_PER_SECOND;
    
    public static final int REAL_PRINTER_WIDTH = 200;
    public static final int PRINTER_WIDTH = 162;
	public static final int PRINTER_HEIGHT = 134;
	public static final int PRINTER_X = SCREEN_WIDTH/2 - PRINTER_WIDTH/2;
	public static final int PRINTER_Y = SCREEN_HEIGHT - PRINTER_HEIGHT;
	
    private Timeline animation = new Timeline();
    private Game new_game;
    
	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.BLACK);
		CouponPrinter coupon_printer = createCouponPrinter(root, scene);
		
		new_game = new Game(root, scene, coupon_printer, animation);
		new_game.init();
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                					   e -> new_game.play());
		
		stage.setTitle("Cracking the Couponator");
		stage.setScene(scene);
		stage.show();
		
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	/**
	 * This function creates the coupon printer, which is the object that the player controls. The coupon
	 * printer is only created once at the beginning of the game and is used throughout the game.
	 * 
	 * @param root - the root node of the scene graph
	 * @param scene - the scene containing the root node
	 * @return new_coupon_printer - the coupon printer that is created
	 */
	public CouponPrinter createCouponPrinter(Group root, Scene scene) {
		CouponPrinter new_coupon_printer = new CouponPrinter(PRINTER_X, PRINTER_Y);
		root.getChildren().add(new_coupon_printer.drawCouponPrinter());
		return new_coupon_printer;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}