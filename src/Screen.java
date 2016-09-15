/*
 * MASTERPIECE:
 * This entire file is part of my masterpiece. I added all of the methods that involve displaying 
 * information in Game.java and placed them here, so that this class is much shorter and reads much 
 * more cleanly. By adding methods here, I removed several unnecessary global variables in Game.java
 * so that the class reads more easily. The methods here are also clear in what they accomplish and 
 * work well with the object; a screen displays information, so it makes sense to create a Screen 
 * class that takes care of displaying information. This class is the highlight of the masterpiece
 * because it shows that I have reorganized and cleaned up my code based on the code smells and code
 * conventions I have learned in class (i.e. no duplicate code, use appropriate variable names, 
 * avoid unnecessary variables/methods).
 */

/**
 * The class of the Screen object, which contains methods to display information (colorful screens,
 * text, etc.)
 * 
 * @author Aninda Manocha
 */
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Screen {
	public static final int TEXT_WIDTH = 600;
	public static final int SPLASH_TEXT_WIDTH = 800;
	public static final int BUTTON_WIDTH = 200;
	public static final int TEXT_X = 15;
	public static final int TEXT_Y = 60;
	
	private Group root;
	private Timeline animation;
	private Game game;
	
	/*****CONSTRUCTORS*****/
	
	/**
	 * This is a blank constructor that isn't used by the game, but is needed in order to create a
	 * Screen global variable in Game.java.
	 */
	public Screen() {
		
	}
	
	/**
	 * This constructor creates a Screen object with access to global variables from Game.java as
	 * well as the game itself.
	 * @param new_root - the root node
	 * @param new_animation - the animation of the game
	 * @param new_game - the game itself
	 */
	public Screen(Group new_root, Timeline new_animation, Game new_game) {
		root = new_root;
		animation = new_animation;
		game = new_game;
	}
	
	/**
	 * Creates a splash screen with instructions for the game and a button for the user to press when ready to play.
	 */
	public void displaySplashScreen() {
		//button_screen.setFill(Color.BLACK);
		//root.getChildren().add(button_screen);
		
		Text title_text = new Text(Main.SCREEN_WIDTH/2-SPLASH_TEXT_WIDTH/2, Main.SCREEN_HEIGHT/8, "CRACKING THE COUPONATOR");
		title_text.setFont(Font.font("Impact", FontWeight.BOLD, 70));
		title_text.setFill(Color.BLUE);
		title_text.setWrappingWidth(SPLASH_TEXT_WIDTH);
		title_text.setTextAlignment(TextAlignment.CENTER);
		root.getChildren().add(title_text);
		
		String text = "\t You are a college student with little money, but you need to buy yourself "
				+ "food to fuel your brain. So naturally, when you receive a coupon with a $100 "
				+ "value for meals at your favorite restaurant, you are ecstatic. You quickly run to "
				+ "the restaurant to buy yourself a delicious dinner. You are at the register about "
				+ "to pay for your food, when you learn that in order to redeem the coupon, you have "
				+ "to enter a series of codes into the restaurant's machine, the Couponator. You "
				+ "must know and spell out these codes in order to receive your $100 worth of food "
				+ "(that isn't necessarily all being spent on dinner in one night). These codes are "
				+ "the names of foods and you will be given a question that describes a specific food "
				+ "for you to guess. \n \t In order to spell the food, you must shoot the appropriate "
				+ "letters with a coupon, as you control a coupon printer. You have three lives. If "
				+ "you hit a letter that is not the correct answer to the food question, then you lose "
				+ "a life. In level 1, you do not have to spell out the food with the letters in order, "
				+ "but in level 2 you do. So, you will lose a life if you hit a letter out of order in "
				+ "level 2. If you hit a letter correctly, you will see it displayed in the top right "
				+ "corner of the screen. Additionally, if an unwanted letter hits the coupon printer, "
				+ "you will lose a life (if you hit a wanted letter more than once nothing happens), so "
				+ "avoid all letters that are not in the name of the food to guess and aim to spell the "
				+ "food correctly. Good luck!";
		
		Text instruction_text = new Text(Main.SCREEN_WIDTH/2-SPLASH_TEXT_WIDTH/2, Main.SCREEN_HEIGHT/5, text);
		instruction_text.setFont(Font.font("Arial", 20));
		instruction_text.setFill(Color.WHITE);
		instruction_text.setWrappingWidth(SPLASH_TEXT_WIDTH);
		instruction_text.setTextAlignment(TextAlignment.LEFT);
		root.getChildren().add(instruction_text);
		
		createButton();
	}
	
	/**
	 * Creates a button the user can press when finished reading the instructions and is ready to play 
	 * the game.
	 */
	public void createButton() {
		DropShadow shadow = new DropShadow();
		Button playGame = new Button("Play");
		playGame.setEffect(shadow);
		playGame.setLayoutX(Main.SCREEN_WIDTH/2-BUTTON_WIDTH/2);
		playGame.setLayoutY(3*Main.SCREEN_HEIGHT/4);
		playGame.setMinWidth(BUTTON_WIDTH);
		playGame.setMaxWidth(BUTTON_WIDTH);
		playGame.setFont(Font.font("Impact", FontWeight.BOLD, 50));
		playGame.setTextFill(Color.BLUE);
		playGame.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	game.startGame();
		    }
		});
		root.getChildren().add(playGame);
	}
	
	/**
	 * Creates a blue screen and displays the current level.
	 */
	public void displayLevel() {
		Rectangle level_screen = new Rectangle(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		level_screen.setFill(Color.BLUE);
		root.getChildren().add(level_screen);
	}
	
	/**
	 * Displays the question about food at the top left corner of the screen.
	 */
	public void displayFood(String question) {
		Text food_text = new Text(TEXT_X, TEXT_Y/2, question);
		food_text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		food_text.setFill(Color.WHITE);
		root.getChildren().add(food_text);
	}
	
	/**
	 * Displays the number of lives under the food question.
	 */
	public void displayLives(int lives) {
		Text lives_text = new Text(TEXT_X, TEXT_Y, "Lives: " + lives);
		lives_text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		lives_text.setFill(Color.WHITE);
		root.getChildren().add(0, lives_text);
	}
	
	/**
	 * Creates a text object with large white text in the center of the screen.
	 * @param display_text - text to display
	 */
	public void displayWhiteText(String display_text) {
		int center_x = Main.SCREEN_WIDTH/2 - TEXT_WIDTH/2;
		int center_y = Main.SCREEN_HEIGHT/2;
		Text text = new Text(center_x, center_y, display_text);
		text.setFont(Font.font("Impact", FontWeight.BOLD, 120));
		text.setFill(Color.WHITE);
		text.setWrappingWidth(TEXT_WIDTH);
		text.setTextAlignment(TextAlignment.CENTER);
		root.getChildren().add(text);
	}
	
	/**
	 * Clears the screen and displays either "YOU WIN!" or "GAME OVER" depending on what situation the player is in. 
	 * The animation is stopped and the game ends. 
	 * @param text - the text to be displayed; either "YOU WIN!" or "GAME OVER"
	 */
	public void endGame(String display_text) {
		root.getChildren().clear();
		displayWhiteText(display_text);
		animation.stop();
	}
}