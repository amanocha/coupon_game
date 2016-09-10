import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;
import java.util.Random;
import java.util.ArrayList;

public class Game {
	public static final int TEXT_X = 15;
	public static final int TEXT_Y = 60;
	
	private Group root;
	private Scene scene;
	private CouponPrinter coupon_printer;
	private Timeline animation;
	private int frame;
	private ArrayList<Food> foods;
	private Food current_food;
	private int level;
	private int lives;
	private Text food_text;
	
	public Game() {
		root = new Group();
		scene = new Scene(root, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, Color.BLACK);
		coupon_printer = new CouponPrinter(Main.PRINTER_X, Main.PRINTER_Y);
		animation = new Timeline();
		frame = 1;
		foods = new ArrayList<Food>();
		current_food = new Food("", "");
		level = 1;
		lives = 3;
		food_text = new Text();
	}
	public Game(Group new_root, Scene new_scene, CouponPrinter new_coupon_printer, Timeline new_animation) {
		root = new_root;
		scene = new_scene;
		coupon_printer = new_coupon_printer;
		animation = new_animation;
		frame = 1;
		foods = new ArrayList<Food>();
		current_food = new Food("", "");
		level = 1;
		lives = 3;
		food_text = new Text();
		addFoods();
		displayLives();
	}
	
	public void addFoods() {
		foods.add(new Food("What is a fruit known for its abundance of vitamin C?", "orange"));
		foods.add(new Food("What is a yellow fruit that is considered to be sour?", "lemon"));
		
		newFood("");
	}
	
	public void newFood(String food) {
		Random random_generator = new Random();
		while (food.equals(current_food.getAnswer())) {
			current_food = foods.get(random_generator.nextInt(foods.size()));
		}
		displayFood();
	}
	
	public void displayFood() {
		Text food_text = new Text(TEXT_X, TEXT_Y/2, current_food.getQuestion());
		food_text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		food_text.setFill(Color.WHITE);
		root.getChildren().add(food_text);
	}
	
	/**
	 * This function creates a letter, which falls vertically down the screen.
	 * 
	 * @param root - the root node of the scene graph
	 */
	public void createLetter() {
		Random random_generator = new Random();
		char letter = (char) (random_generator.nextInt(26)+97); //convert ASCII code
		int x_position = random_generator.nextInt(Main.SCREEN_WIDTH-Letter.WIDTH-Main.REAL_PRINTER_WIDTH) + Main.REAL_PRINTER_WIDTH/2;
		int y_position = TEXT_Y + 5;
		
		Letter new_letter = new Letter(this, root, letter, x_position, y_position);
		root.getChildren().add(new_letter.drawLetter());
		addKeyFrame(Main.MILLISECOND_DELAY, () -> new_letter.fall(Main.SECOND_DELAY));
	}
	
	/**
	 * This function creates a coupon, which is shot from the coupon printer.
	 * 
	 * @param root - the root node of the scene graph
	 * @param x - the x coordinate of the center of the coupon
	 * @param y - the y coordinate of the center of the coupon
	 */
	public void createCoupon(int x, int y) {
		Coupon new_coupon = new Coupon(root);
		root.getChildren().add(new_coupon.drawCoupon(x, y));
		addKeyFrame(Main.MILLISECOND_DELAY, () -> new_coupon.travel(Main.SECOND_DELAY));
	}
	
	public void addKeyFrame(int duration, Runnable runnable) {
		KeyFrame new_frame = new KeyFrame(Duration.millis(duration), e -> runnable.run());
		animation.getKeyFrames().add(new_frame);
	}
	
	public void handleKeyInput() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                    	createCoupon(coupon_printer.getXPosition()+Main.REAL_PRINTER_WIDTH/2, coupon_printer.getYPosition()+Main.PRINTER_HEIGHT/4); 
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
	
	public void displayLives() {
		Text lives_text = new Text(TEXT_X, TEXT_Y, "Lives: " + lives);
		lives_text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		lives_text.setFill(Color.WHITE);
		root.getChildren().add(0, lives_text);
	}
	
	public void updateLives() {
		root.getChildren().remove(0);
		lives--;
		
		Text lives_text = new Text(TEXT_X, TEXT_Y, "Lives: " + lives);
		lives_text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		lives_text.setFill(Color.WHITE);
		root.getChildren().add(0, lives_text);
	}
	
	public void play() {
		handleKeyInput();
		if (frame % (2*Main.FRAMES_PER_SECOND) == 1) {
			createLetter();
		}
		
		frame++;
	}
	
	public Food getCurrentFood() {
		return current_food;
	}
}