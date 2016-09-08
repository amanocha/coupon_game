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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 850;
	
	public static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000/FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0/FRAMES_PER_SECOND;
    
    public static final int LETTER_WIDTH = 34;
    public static final int REAL_PRINTER_WIDTH = 200;
    public static final int PRINTER_WIDTH = 162;
	public static final int PRINTER_HEIGHT = 134;
	public static final int PRINTER_X = SCREEN_WIDTH/2 - PRINTER_WIDTH/2;
	public static final int PRINTER_Y = SCREEN_HEIGHT - PRINTER_HEIGHT;
	
    private Timeline animation = new Timeline();
    
	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.BLACK);
		CouponPrinter coupon_printer = createCouponPrinter(root, scene);
		
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                					   e -> playGame(root, scene, coupon_printer));
		
		stage.setTitle("Cracking the Couponator");
		stage.setScene(scene);
		stage.show();
		
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	public void playGame(Group root, Scene scene, CouponPrinter coupon_printer) {
		handleKeyInput(root, scene, coupon_printer);
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
	
	/**
	 * This function creates a letter, which falls vertically down the screen.
	 * 
	 * @param root - the root node of the scene graph
	 */
	public void createLetter(Group root) {
		Random randomGenerator = new Random();
		char letter = (char) (randomGenerator.nextInt(26)+97); //convert ASCII code
		int x_position = randomGenerator.nextInt(SCREEN_WIDTH-LETTER_WIDTH);
		int y_position = 0;
		
		Letter new_letter = new Letter(letter, x_position, y_position);
		root.getChildren().add(new_letter.drawLetter());
		addKeyFrame(() -> new_letter.fall(SECOND_DELAY));
	}
	
	/**
	 * This function creates a coupon, which is shot from the coupon printer.
	 * 
	 * @param root - the root node of the scene graph
	 * @param x - the x coordinate of the center of the coupon
	 * @param y - the y coordinate of the center of the coupon
	 * @return the newly created coupon
	 */
	public void createCoupon(Group root, int x, int y) {
		Coupon new_coupon = new Coupon();
		root.getChildren().add(new_coupon.drawCoupon(x, y));
		addKeyFrame(() -> new_coupon.travel(SECOND_DELAY));
	}
	
	public void addKeyFrame(Runnable runnable) {
		KeyFrame new_frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> runnable.run());
		animation.getKeyFrames().add(new_frame);
	}
	
	public void handleKeyInput(Group root, Scene scene, CouponPrinter coupon_printer) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                    	createCoupon(root, coupon_printer.getXPosition()+REAL_PRINTER_WIDTH/2, coupon_printer.getYPosition()+PRINTER_HEIGHT/4); 
                    	break;
                    case LEFT:  
                    	coupon_printer.move("left"); 
                    	break;
                    case RIGHT: 
                    	coupon_printer.move("right");
                    	break;
                    default:
                    	//nothing
                }
            }
        });
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}