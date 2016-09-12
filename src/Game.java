/**
 * The class of the Game object, which runs the game and creates objects for the game. It contains all
 * of the information pertaining to the game as well as methods for different events in the game (new 
 * level, winning, and losing). It interacts with all of the classes (Main calls it, and it creates
 * Letter, Coupon, CouponPrinter, and Food objects).
 * 
 * @author Aninda Manocha
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.shape.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;
import java.util.Random;
import java.util.ArrayList;

public class Game {
	public static final int BUTTON_WIDTH = 200;
	
	public static final int TEXT_X = 15;
	public static final int TEXT_Y = 60;
	public static final int TEXT_WIDTH = 600;
	public static final int SPLASH_WIDTH = 800;
	
	// the dimensions for the rectangle that covers up the displayed answer after a user finishes guessing it
	public static final int COVER_X = 3*Main.SCREEN_WIDTH/4;
	public static final int COVER_Y = 0;
	public static final int COVER_WIDTH = Main.SCREEN_WIDTH/4;
	public static final int COVER_HEIGHT = TEXT_Y;
	
	public static final int LEVEL_NUM_FOODS = 5; // number of words to guess per level
	
	private Group root;
	private Scene scene;
	private CouponPrinter coupon_printer;
	private Timeline animation;
	private int frame;
	private ArrayList<Food> foods;
	private Food current_food;
	private int food_index;
	private int level;
	private int level_frame;
	private Rectangle level_screen;
	private Text level_text;
	private int lives;
	private int num_foods;
	private Text food_text;
	private ArrayList<String> letters;
	private ArrayList<Letter> game_letters;
	private Button playGame;
	private Rectangle button_screen;
	private Text title_text;
	private Text button_text;
	private boolean start_game;
	
	/*****CONSTRUCTOR*****/
	
	/**
	 * This constructor creates a Game object by setting the global variables within the game.
	 * @param new_root - the root node of the game
	 * @param new_scene - the scene of the game
	 * @param new_animation - the animation of the game
	 */
	public Game(Group new_root, Scene new_scene, Timeline new_animation) {
		root = new_root;
		scene = new_scene;
		coupon_printer = new CouponPrinter();
		animation = new_animation;
		frame = 1;
		foods = new ArrayList<Food>();
		current_food = new Food("", ""); 
		food_index = 0; // the index in the answer (String) of the current letter to hit (for level 2)
		level = 1;
		level_frame = 0; // for determining when to display the new level screen
		level_screen = new Rectangle(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		level_text = new Text();
		lives = 3;
		num_foods = 0;
		food_text = new Text();
		letters = new ArrayList<String>(); // the letters left in the answer for the user to hit
		game_letters = new ArrayList<Letter>(); 
		playGame = new Button("Play");
		button_screen = new Rectangle(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		title_text = new Text();
		button_text = new Text();
		start_game = false;
	}
	
	/**
	 * Initializes and sets up the game by creating the coupon printer and selecting and displaying the
	 * question about food. The level is also set.
	 */
	public void init() {
		addFoods();
		newLevel();
		createSplashScreen();
	}
	
	/**
	 * Creates a splash screen with instructions for the game.
	 */
	public void createSplashScreen() {
		button_screen.setFill(Color.BLACK);
		root.getChildren().add(button_screen);
		
		title_text = new Text(Main.SCREEN_WIDTH/2-SPLASH_WIDTH/2, Main.SCREEN_HEIGHT/8, "CRACKING THE COUPONATOR");
		title_text.setFont(Font.font("Impact", FontWeight.BOLD, 70));
		title_text.setFill(Color.BLUE);
		title_text.setWrappingWidth(SPLASH_WIDTH);
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
		button_text = new Text(Main.SCREEN_WIDTH/2-SPLASH_WIDTH/2, Main.SCREEN_HEIGHT/5, text);
		button_text.setFont(Font.font("Arial", 20));
		button_text.setFill(Color.WHITE);
		button_text.setWrappingWidth(SPLASH_WIDTH);
		button_text.setTextAlignment(TextAlignment.LEFT);
		root.getChildren().add(button_text);
		
		createButton();
	}
	
	/**
	 * Creates a button the user can press when finished reading the instructions and is ready to play 
	 * the game.
	 */
	public void createButton() {
		DropShadow shadow = new DropShadow();
		playGame.setEffect(shadow);
		playGame.setLayoutX(Main.SCREEN_WIDTH/2-BUTTON_WIDTH/2);
		playGame.setLayoutY(3*Main.SCREEN_HEIGHT/4);
		playGame.setMinWidth(BUTTON_WIDTH);
		playGame.setMaxWidth(BUTTON_WIDTH);
		playGame.setFont(Font.font("Impact", FontWeight.BOLD, 50));
		playGame.setTextFill(Color.BLUE);
		playGame.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	startGame();
		    }
		});
		root.getChildren().add(playGame);
	}
	
	/**
	 * Starts the game and removes the splash screen.
	 */
	public void startGame() {
		start_game = true;
		root.getChildren().remove(button_screen);
		root.getChildren().remove(title_text);
		root.getChildren().remove(button_text);
		root.getChildren().remove(playGame);
	}
	
	/**
	 * Creates the coupon printer, which is the object that the player controls. The coupon printer 
	 * is only created once at the beginning of the game and is used throughout the game.
	 * @param root - the root node of the scene graph
	 * @param scene - the scene containing the root node
	 * @return new_coupon_printer - the coupon printer that is created
	 */
	public void createCouponPrinter() {
		coupon_printer = new CouponPrinter(Main.SCREEN_WIDTH/2 - Main.PRINTER_WIDTH/2, Main.SCREEN_HEIGHT - Main.PRINTER_HEIGHT);
		root.getChildren().add(coupon_printer.drawBoundary());
		root.getChildren().add(coupon_printer.drawCouponPrinter());
	}
	
	/**
	 * Initializes the array of foods, which holds all Food objects that the game has to choose from 
	 * (randomly). It then calls the function to pick a Food object.
	 */
	public void addFoods() {
		foods.add(new Food("What is a fruit known for its abundance of vitamin C?", "orange"));
		foods.add(new Food("What is a yellow fruit that is considered to be sour?", "lemon"));
		foods.add(new Food("What is a small fruit that is often made into juice and jelly?", "grape"));
		foods.add(new Food("What is a baked food often used for sandwiches?", "bread"));
		foods.add(new Food("What is a grain that was first discovered in China?", "rice"));
		foods.add(new Food("What is a treat that is often served on birthdays?", "cake"));
		foods.add(new Food("What is a meat that comes from the ocean?", "fish"));
		foods.add(new Food("What is the meat made from a domestic pig?", "pork"));
		foods.add(new Food("What is a thick slice of meat that comes from a cow?", "steak"));
		foods.add(new Food("What is a plant with dark, green leaves?", "spinach"));
		foods.add(new Food("What is a vegetable often consumed as kernels?", "corn"));
		foods.add(new Food("What is a small, green, spherical seed?", "pea"));
		foods.add(new Food("What is a beverage often consumed with cereal?", "milk"));
		foods.add(new Food("What is a drink that is essential to living?", "water"));
		foods.add(new Food("What is a hot beverage made by infusing leaves in liquid?", "tea"));
		
		newFood("");
	}
	
	/**
	 * Randomly selects a Food object from the array of Food objects and then adds all of the 
	 * characters in the answer to an array of letters (which decreases in size as the user hits the 
	 * correct letters).
	 * @param food - the name of the food that was just guess ("" if new game) in order to avoid repeats
	 */
	public void newFood(String food) {
		root.getChildren().clear();
		game_letters.clear();
		letters.clear();
		
		createCouponPrinter();
		displayLives();
		
		Random random_generator = new Random();
		while (food.equals(current_food.getAnswer())) {
			current_food = foods.get(random_generator.nextInt(foods.size()));
		}
		for (int c = 0; c < current_food.getAnswer().length(); c++) {
			char char_letter = current_food.getAnswer().charAt(c);
			letters.add(Character.toString(char_letter));
		}
		
		displayFood();
	}
	
	/**
	 * Displays the question about food at the top left corner of the screen. After displaying 5 food
	 * questions, the level is updated (either level 2 or win).
	 */
	public void displayFood() {
		food_text = new Text(TEXT_X, TEXT_Y/2, current_food.getQuestion());
		food_text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		food_text.setFill(Color.WHITE);
		root.getChildren().add(food_text);
		
		if (num_foods == LEVEL_NUM_FOODS) {
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
	 * Listens for key input. The coupon printer either moves right or left or shoots a coupon. There
	 * are also cheat keys.
	 */
	public void handleKeyInput() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                    	createCoupon(coupon_printer.getXPosition()+Main.PRINTER_WIDTH/2, coupon_printer.getYPosition()+Main.PRINTER_HEIGHT/4); 
                    	break;
                    case LEFT:  
                    	coupon_printer.move("left"); 
                    	break;
                    case RIGHT: 
                    	coupon_printer.move("right");
                    	break;
                    case G: // cheat key to reach the "GAME OVER" screen
                    	gameOver();
                    	break;
                    case N: // cheat key to reach the next level
                    	num_foods++;
                    	newFood(current_food.getAnswer());
                    	break;
                    case W: // cheat key to reach the "YOU WIN!" screen
                    	win();
                    	break;
                    default:
                    	//nothing
                }
            }
        });
	}
	
	/**
	 * Creates a letter, which falls vertically down the screen at a random x position.
	 * @param root - the root node of the scene graph
	 */
	public void createLetter() {
		Random random_generator = new Random();
		
		int good_letter = random_generator.nextInt(3); //33.33% chance 
		char letter;
		if (good_letter == 0) {
			int random_index = random_generator.nextInt(letters.size());
			letter = letters.get(random_index).charAt(0);
		} else if (good_letter == 1 && level == 2){
			letter = current_food.getAnswer().charAt(food_index);
		} else {
			letter = (char) (random_generator.nextInt(26)+97); //convert ASCII code
		}
		
		int x_position = random_generator.nextInt(Main.SCREEN_WIDTH-Letter.WIDTH-Main.PRINTER_WIDTH) + Main.PRINTER_WIDTH/2;
		int y_position = TEXT_Y + 5;
		
		Letter new_letter = new Letter(this, letter, x_position, y_position);
		root.getChildren().add(new_letter.drawLetter());
		game_letters.add(new_letter);
		//addKeyFrame(Main.MILLISECOND_DELAY, () -> new_letter.fall(Main.SECOND_DELAY));
	}
	
	/**
	 * Creates a coupon, which is shot from the center of coupon printer and travels upwards.
	 * @param root - the root node of the scene graph
	 * @param x - the x coordinate of the center of the coupon
	 * @param y - the y coordinate of the center of the coupon
	 */
	public void createCoupon(int x, int y) {
		Coupon new_coupon = new Coupon(root);
		root.getChildren().add(new_coupon.drawCoupon(x, y));
		addKeyFrame(Main.MILLISECOND_DELAY, () -> new_coupon.travel(Main.SECOND_DELAY));
	}
	
	/**
	 * Adds a keyframe to the game's animation. It is called in order to allow letters to 
	 * continuously fall and to allow coupons to continuously travel upwards.
	 * @param duration - the delay before the event (in milliseconds)
	 * @param runnable - the event to be added to the animation
	 */
	public void addKeyFrame(int duration, Runnable runnable) {
		KeyFrame new_frame = new KeyFrame(Duration.millis(duration), e -> runnable.run());
		animation.getKeyFrames().add(new_frame);
	}
	
	/**
	 * Removes a letter (the correct one that was just hit) from the letters array (which contains the
	 * letters left to be hit). It then calls the function to display the letter to inform the user 
	 * that the right letter (or one of the right letters) was hit.
	 * @param letter - the letter that was just hit
	 */
	public void addLetter(Letter letter) {
		String current_letter = Character.toString(letter.getValue());
		if (letters.contains(current_letter)) {
			letters.remove(current_letter);
			food_index++;
			letter.display();
			if (letters.size() == 0) { // no more letters left to hit in the answer, new food
				num_foods++;
				food_index = 0;
				newFood(current_food.getAnswer());
			}
		}
	}
	
	/**
	 * Decrements the number of lives and updates the display. It ends the game if all lives are lost. 
	 */
	public void updateLives() {
		root.getChildren().remove(0);
		lives--;
		
		displayLives();
		
		if (lives == 0) {
			gameOver();
		}
	}
	
	/**
	 * Displays the number of lives under the question.
	 */
	public void displayLives() {
		Text lives_text = new Text(TEXT_X, TEXT_Y, "Lives: " + lives);
		lives_text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		lives_text.setFill(Color.WHITE);
		root.getChildren().add(0, lives_text);
	}
	
	/**
	 * Displays the next level and clears the screen of any letters that were falling in the previous
	 * level if the next level is 2.
	 */
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
			for(int i = 0; i < root.getChildren().size(); i++) {
				Object current_letter = root.getChildren().get(i);
				if (current_letter instanceof ImageView && !(current_letter.equals(coupon_printer.getCouponPrinter()))) {
					remove_letters.add((ImageView)current_letter);
				}
			}
			root.getChildren().removeAll(remove_letters);
		}
	}
	
	/** 
	 * Clears the screen and displays "YOU WIN!". The animation is stopped and the game ends.
	 */
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
	
	/**
	 * Clears the screen and displays "GAME OVER". The animation is stopped and the game ends. 
	 */
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
	
	/**
	 * Runs the game by indefinitely creating frames. It controls whether a level screen is displayed
	 * and when to listen for key input.
	 */
	public void play() {
		if (start_game) {
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
				for(int i = 0; i < game_letters.size(); i++) {
					game_letters.get(i).fall(Main.SECOND_DELAY);
				}
			}
			frame++;
		}
	}
	
	/**GETTERS**/
	
	/**
	 * This method accesses the root node, which the Letter object needs in order to interact with the
	 * game.
	 * @return the root node of the game
	 */
	public Group getRoot() {
		return root;
	}
	
	/**
	 * This method accesses the boundary of the coupon printer, which is used to check for a collision
	 * between the coupon printer and a letter.
	 * @return the boundary of the coupon printer
	 */
	public Rectangle getCouponPrinterBoundary() {
		return coupon_printer.getBoundary();
	}
	
	/**
	 * This method access the current Food object, which contains the question and answer.
	 * @return
	 */
	public Food getCurrentFood() {
		return current_food;
	}
	
	/**
	 * This method accesses the index of the next letter in the answer (String). This is only used in
	 * level 2 when the order in which the letters are hit matters.
	 * @return the index
	 */
	public int getFoodIndex() {
		return food_index;
	}
	
	/**
	 * This method accesses the level of the game (either 1 or 2).
	 * @return the current level 
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * This method accesses the array of letters that are currently in the game.
	 * @return the array of letters 
	 */
	public ArrayList<Letter> getGameLetters() {
		return game_letters;
	}
}