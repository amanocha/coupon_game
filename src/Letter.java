/**
 * This is the letter object, which creates an object for any of the 26 letters of the alphabet.
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
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.Iterator;

public class Letter {
	public static final int WIDTH = 33;
	public static final int HEIGHT = 34;
	
	private static final int LETTER_SPEED = 60;
	private static final int TEXT_WIDTH = 15;
	
	private Game game;
	private Group root;
	private char value;
	private String image_name;
	private Image image;
	private ImageView letter;
	private int x_position;
	private int y_position;
	private String current_food;
	
	public Letter(Game new_game, Group new_root, char letter_value, int x, int y) {
		game = new_game;
		root = new_root;
		value = letter_value;
		image_name = Character.toString(value) + ".png";
		image = new Image(image_name);
		letter = new ImageView();
		x_position = x;
		y_position = y;
		current_food = new_game.getCurrentFood().getAnswer();
	}

	public ImageView drawLetter() {
		letter.setImage(image);
		letter.setX(x_position);
		letter.setY(y_position);
		return letter;
	}
	
	public void fall(double time_step) {
		letter.setY(letter.getY() + LETTER_SPEED*time_step);
		if (letter.getY() >= 700) {
			disappear();
		}
		if (isHit()) {
			if (current_food.contains(Character.toString(value))) {
				display();
			}
			//game.updateLives();
		}
	}
	
	public boolean isHit() {
		ObservableList<Node> coupons = root.getChildren();
		Iterator<Node> iter = coupons.iterator();
		while (iter.hasNext()) {
			Object coupon = iter.next();
			if (coupon instanceof Rectangle) {
				if (letter.intersects(((Rectangle)coupon).getX(), ((Rectangle)coupon).getY(), ((Rectangle)coupon).getWidth(), ((Rectangle)coupon).getHeight())) {
					disappear();
					((Rectangle)coupon).setY(-1);
					return true;
				}
			}
		}
		return false;
	}
	
	public void disappear() {
		letter.setY(Main.SCREEN_HEIGHT);
	}
	
	public void display() {
		int display_x_pos = calculateX(value);
		Text food_text = new Text(display_x_pos, Game.TEXT_Y/2, Character.toString(value));
		food_text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		food_text.setFill(Color.WHITE);
		root.getChildren().add(food_text);
	}
	
	public int calculateX(char current_letter) {
		int food_length = current_food.length();
		int position = current_food.indexOf(current_letter);
		return Main.SCREEN_WIDTH-(food_length+1)*TEXT_WIDTH + position*TEXT_WIDTH;
	}
	
	public char getValue() {
		return value;
	}
	
	public ImageView getLetter() {
		return letter;
	}
}