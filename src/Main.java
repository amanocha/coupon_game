/**
 * This is the main program that sets up the user interface and runs the game.
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
    
    public static final int PRINTER_WIDTH = 200;
	public static final int PRINTER_HEIGHT = 134;
	
	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.BLACK);
		
		Timeline animation = new Timeline();
	    Game new_game = new Game(root, scene, animation);
	    new_game.init();
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> new_game.play());
		
		stage.setTitle("Cracking the Couponator");
		stage.setScene(scene);
		stage.show();
		
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}