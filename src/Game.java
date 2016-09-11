import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

public class Game {
	public static final int TEXT_X = 15;
	public static final int TEXT_Y = 60;
	public static final int TEXT_WIDTH = 600;
	
	public static final int COVER_X = 3*Main.SCREEN_WIDTH/4;
	public static final int COVER_Y = 0;
	public static final int COVER_WIDTH = Main.SCREEN_WIDTH/4;
	public static final int COVER_HEIGHT = TEXT_Y;
	
	public static final int LEVEL_ONE = 5;
	
	private Group root;
	private Scene scene;
	private CouponPrinter coupon_printer;
	private Timeline animation;
	private int frame;
	private ArrayList<Food> foods;
	private Food current_food;
	private int level;
	private int level_frame;
	private Rectangle level_screen;
	private Text level_text;
	private int lives;
	private int num_foods;
	private Text food_text;
	private ArrayList<String> letters;
	
	/**public Game() {
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
		letters = new ArrayList<Letter>();
	}**/
	public Game(Group new_root, Scene new_scene, CouponPrinter new_coupon_printer, Timeline new_animation) {
		root = new_root;
		scene = new_scene;
		coupon_printer = new_coupon_printer;
		animation = new_animation;
		frame = 1;
		foods = new ArrayList<Food>();
		current_food = new Food("", "");
		level = 1;
		level_frame = 0;
		level_screen = new Rectangle(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		level_text = new Text();
		lives = 3;
		num_foods = 0;
		food_text = new Text();
		letters = new ArrayList<String>();
	}
	
	public void init() {
		addFoods();
		displayLives();
		newLevel();
	}
	
	public void addFoods() {
		foods.add(new Food("What is a fruit known for its abundance of vitamin C?", "orange"));
		foods.add(new Food("What is a yellow fruit that is considered to be sour?", "lemon"));
		foods.add(new Food("What is a small fruit that is often made into juice and jelly?", "grape"));
		
		newFood("");
	}
	
	public void newFood(String food) {
		root.getChildren().remove(food_text);
		
		Rectangle cover = new Rectangle(COVER_X, COVER_Y, COVER_WIDTH, COVER_HEIGHT);
		cover.setFill(Color.BLACK);
		root.getChildren().add(cover);
		
		Random random_generator = new Random();
		while (food.equals(current_food.getAnswer())) {
			current_food = foods.get(random_generator.nextInt(foods.size()));
		}
		displayFood();
	}
	
	public void displayFood() {
		food_text = new Text(TEXT_X, TEXT_Y/2, current_food.getQuestion());
		food_text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		food_text.setFill(Color.WHITE);
		root.getChildren().add(food_text);
		
		if (num_foods == 5) {
			level++;
			num_foods = 0;
			if (level > 2) {
				win();
			} else {
				newLevel();
			}
		}
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
                    case G:
                    	gameOver();
                    	break;
                    case N:
                    	num_foods++;
                    	newFood(current_food.getAnswer());
                    	break;
                    case W:
                    	win();
                    	break;
                    default:
                    	//nothing
                }
            }
        });
	}
	
	public void addLetter(Letter letter) {
		String current_letter = Character.toString(letter.getValue());
		if (!(letters.contains(current_letter))) {
			letters.add(current_letter);
			System.out.println(current_letter);
			letter.display();
			if (letters.size() == current_food.getAnswer().length()) {
				letters.clear();
				num_foods++;
				newFood(current_food.getAnswer());
			}
		}
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
		
		if (lives == 0) {
			gameOver();
		}
	}
	
	public void newLevel() {
		level_screen.setFill(Color.BLUE);
		root.getChildren().add(level_screen);
		level_frame = frame;
		
		level_text = new Text(Main.SCREEN_WIDTH/2-TEXT_WIDTH/2, Main.SCREEN_HEIGHT/2, "Level " + level);
		level_text.setFont(Font.font("Impact", FontWeight.BOLD, 120));
		level_text.setFill(Color.WHITE);
		level_text.setWrappingWidth(TEXT_WIDTH);
		level_text.setTextAlignment(TextAlignment.CENTER);
		root.getChildren().add(level_text);
		
		ArrayList<ImageView> remove_letters = new ArrayList<ImageView>();
		if (level > 1) {
			Iterator<Node> iter = root.getChildren().iterator();
			while (iter.hasNext()) {
				Object current_letter = iter.next();
				if (current_letter instanceof ImageView && !(current_letter.equals(coupon_printer.getCouponPrinter()))) {
					remove_letters.add((ImageView)current_letter);
				}
			}
			root.getChildren().removeAll(remove_letters);
		}
	}
	
	public void win() {
		root.getChildren().clear();
		Text win = new Text(Main.SCREEN_WIDTH/2-TEXT_WIDTH/2, Main.SCREEN_HEIGHT/2, "YOU WIN!");
		win.setFont(Font.font("Impact", FontWeight.BOLD, 120));
		win.setFill(Color.WHITE);
		win.setWrappingWidth(TEXT_WIDTH);
		win.setTextAlignment(TextAlignment.CENTER);
		root.getChildren().add(win);
		animation.stop();
	}
	
	public void gameOver() {
		root.getChildren().clear();
		Text game_over = new Text(Main.SCREEN_WIDTH/2-TEXT_WIDTH/2, Main.SCREEN_HEIGHT/2, "GAME OVER");
		game_over.setFont(Font.font("Impact", FontWeight.BOLD, 120));
		game_over.setFill(Color.WHITE);
		game_over.setWrappingWidth(TEXT_WIDTH);
		game_over.setTextAlignment(TextAlignment.CENTER);
		root.getChildren().add(game_over);
		animation.stop();
	}
	
	public void play() {
		if (level_frame >= 0) {
			if (frame-level_frame == 3*Main.FRAMES_PER_SECOND) {
				root.getChildren().remove(level_screen);
				root.getChildren().remove(level_text);
				level_frame = -1;
			}
		} else {
			handleKeyInput();
			if (frame % (2*Main.FRAMES_PER_SECOND) == 1) {
				createLetter();
			}
		}
		
		frame++;
	}
	
	public Food getCurrentFood() {
		return current_food;
	}
}