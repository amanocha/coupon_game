/**
 * The class of the Letter object. It stores all of the information pertaining to a letter, which can 
 * have a value of any of the 26 letters of the alphabet. It allows letters to be displayed and also
 * contains the methods that dictate how letters move and react when they come into contact with other
 * objects. The Game object creates Letter objects at random x positions at the top of the screen,
 * and then they slowly fall down the screen for the user to shoot coupons at.
 * 
 * @author Aninda Manocha
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.Node;

public class Letter {
	public static final int WIDTH = 33;
	public static final int HEIGHT = 34;
	
	private static final int LETTER_SPEED = 60;
	private static final int LETTER_WIDTH = 16; // for displaying the shot letters
	
	private Game game;
	private Group root;
	private char value;
	private String image_name;
	private ImageView letter;
	private int x_position;
	private int y_position;
	
	/*****CONSTRUCTORS*****/
	
	/**
	 * This constructor creates a Letter object, which will interact with the Game object and be 
	 * placed at a certain position. The letter is specified.
	 * @param new_game - the Game object which interacts with the Letter object
	 * @param letter_value - the lowercase letter of the alphabet to be displayed (in uppercase)
	 * @param x - the x coordinate of the top left corner of the Letter
	 * @param y - the y coordinate of the top left corner of the Letter
	 */
	public Letter(Game new_game, char letter_value, int x, int y) {
		game = new_game;
		root = new_game.getRoot();
		value = letter_value;
		image_name = Character.toString(value) + ".png";
		letter = new ImageView();
		x_position = x;
		y_position = y;
	}

	/**
	 * Sets the ImageView object so that it can display the Letter on the screen. The x and y 
	 * coordinates of the ImageView object are also set, so that the Letter is positioned.
	 * @return the ImageView object containing the image of the letter, which is displayed
	 */
	public ImageView drawLetter() {
		letter.setImage(new Image(getClass().getClassLoader().getResourceAsStream(image_name)));
		letter.setX(x_position);
		letter.setY(y_position);
		return letter;
	}
	
	/**
	 * Moves the letter downwards slowly to simulate falling. It disappears if it reaches the bottom
	 * of the screen or if it hits either the coupon printer or a coupon. When it collides with either 
	 * the coupon or coupon printer, it interacts with the Game object in order to either decrement 
	 * the number of lives (if the letter is not in the answer, or if the letter is not the next one 
	 * in the answer in level 2). 
	 * @param time_step - the elapsed time between each frame, which is multiplied by the velocity
	 */
	public void fall(double time_step) {
		letter.setY(letter.getY() + LETTER_SPEED*time_step);
		if (letter.getY() >= 850) {
			disappear();
		}
		if (isHit()) {
			if (game.getCurrentFood().getAnswer().contains(Character.toString(value))) {
				if (game.getLevel() == 1) {
					game.addLetter(this);
				} else {
					if (value == game.getCurrentFood().getAnswer().charAt(game.getFoodIndex())) {
						game.addLetter(this);
					} else {
						game.updateLives();
					}
				}
			} else {
				game.updateLives();
			}
		}
	}
	
	/**
	 * Determines if the letter hit the coupon printer or was hit by a coupon.
	 * @return true if the letter collided with the coupon printer or a coupon and false if otherwise 
	 */
	public boolean isHit() {
		for (int i = 0; i < root.getChildren().size(); i++) {
			Node coupon = root.getChildren().get(i);
			if ((coupon instanceof Rectangle) && letter.intersects(coupon.getBoundsInLocal())) { // the letter hit something (either coupon, printer, or screen)
				if (coupon.equals(game.getCouponPrinterBoundary())) { // the letter hit the printer's boundary
					disappear();
					return true;
				}
				if (((Rectangle)coupon).getFill().equals(Color.WHITE)) { // the letter hit a coupon
					disappear();
					((Rectangle)coupon).setY(-1); // hide the coupon by placing it below the screen
					game.getGameCoupons().remove(coupon);
					root.getChildren().remove(coupon);
					return true;
				} 
			} 
		}
		return false;
	}
	
	/**
	 * Removes the letter from the game, which occurs when it reaches the bottom of the screen or hits
	 * either the coupon printer or is hit by a coupon.
	 */
	public void disappear() {
		letter.setY(letter.getY() + Main.SCREEN_HEIGHT);
		game.getGameLetters().remove(this);
		root.getChildren().remove(letter);
	}
	
	/**
	 * Displays the letter in the top right corner of the screen to indicate to the user that a 
	 * correct letter was hit. This method is only called when the user hits the appropriate letter
	 * (either a letter in the answer in level 1, or the next letter in the answer in level 2).
	 */
	public void display() {
		int display_x_pos = calculateX(value);
		Text food_text = new Text(display_x_pos, Screen.TEXT_Y/2, Character.toString(value));
		food_text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		food_text.setFill(Color.WHITE);
		root.getChildren().add(food_text);
	}
	
	/**
	 * Calculates the x position of the letter to be displayed based on the length of the answer and
	 * the letter's position (index) in the answer (the letter should be positioned further left if it
	 * comes earlier in the answer and further right if it comes later in the answer).
	 * @param current_letter - the letter to be displayed
	 * @return the x position where the letter is to be displayed
	 */
	public int calculateX(char current_letter) {
		int food_length = game.getCurrentFood().getAnswer().length();
		int position = game.getCurrentFood().getAnswer().indexOf(current_letter);
		return Main.SCREEN_WIDTH-(food_length+1)*LETTER_WIDTH + position*LETTER_WIDTH;
	}
	
	/*****GETTERS*****/
	
	/**
	 * This method accesses the value of the letter (one of the 26 letters of the alphabet).
	 * @return the letter as a character
	 */
	public char getValue() {
		return value;
	}
	
	/**
	 * This method accesses the letter's ImageView object.
	 * @return the ImageView object
	 */
	public ImageView getLetter() {
		return letter;
	}
}