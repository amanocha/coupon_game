/**
 * This is the letter object, which creates an object for any of the 26 letters of the alphabet.
 * 
 * @author Aninda Manocha
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Letter {
	private static final int LETTER_SPEED = 60;
	
	private char value;
	private String image_name;
	private Image image;
	private ImageView letter;
	private int x_position;
	private int y_position;
	
	public Letter(char letter_value, int letter_x_position, int letter_y_position) {
		value = letter_value;
		image_name = Character.toString(value) + ".png";
		image = new Image(image_name);
		letter = new ImageView();
		x_position = letter_x_position;
		y_position = letter_y_position;
	}

	public ImageView drawLetter() {
		letter.setImage(image);
		letter.setX(x_position);
		letter.setY(y_position);
		return letter;
	}
	
	public void fall(double time_step) {
		letter.setY(letter.getY() + LETTER_SPEED*time_step);
		if (letter.getY() == 700) {
			disappear();
		}
	}
	
	public void disappear() {
		letter.setImage(null);
	}
	
	public char getValue() {
		return value;
	}
	
	public ImageView getLetter() {
		return letter;
	}
}