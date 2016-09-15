/*
 * MASTERPIECE:
 * This entire file is part of my masterpiece. I removed all of the methods that involve displaying 
 * information and placed them in Screen.java, so that this class is much shorter and reads much 
 * more cleanly. I also adjusted a few variable names so that it is clear what they represent. I
 * kept methods short and within 20 lines (excluding comments or whitespace) so that they only 
 * actions that are important to them. I also removed duplicated code by creating new methods and
 * passing in parameters. I have learned a lot about code smells through this class and have been
 * avoiding them as much as possible.
 */

/**
 * The class of the Game object, which runs the game and creates objects for the game. It contains all
 * of the information pertaining to the game as well as methods for different events in the game (new 
 * level, winning, and losing). It interacts with all of the classes (Main calls it, and it creates
 * Letter, Coupon, CouponPrinter, and Food objects).
 * 
 * @author Aninda Manocha
 */

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.input.KeyEvent;
import java.util.Random;
import java.util.ArrayList;

public class Game {
	public static final int LEVEL_NUM_FOODS = 5; // number of words to guess per level
	
	private Group root;
	private Scene scene;
	private Screen screen;
	private CouponPrinter coupon_printer;
	private Timeline animation;
	private int frame;
	private ArrayList<Food> foods;
	private Food current_food;
	private int food_index;
	private int level;
	private int level_frame;
	private int lives;
	private int num_foods;
	private ArrayList<String> remaining_letters;
	private ArrayList<Letter> game_letters;
	private ArrayList<Coupon> game_coupons;
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
		screen = new Screen();
		coupon_printer = new CouponPrinter();
		animation = new_animation;
		frame = 1;
		foods = new ArrayList<Food>();
		current_food = new Food("", ""); 
		food_index = 0; // the index in the answer (String) of the current letter to hit (for level 2)
		level = 0;
		level_frame = 0; // for determining when to display the new level screen
		lives = 3;
		num_foods = 0;
		remaining_letters = new ArrayList<String>(); // the letters left in the answer for the user to hit
		game_letters = new ArrayList<Letter>();
		game_coupons = new ArrayList<Coupon>();
		start_game = false;
	}
	
	/**
	 * Initializes and sets up the game by creating the coupon printer and selecting and displaying the
	 * question about food. The level is also set.
	 */
	public void init() {
		addFoods();
		screen = new Screen(root, animation, this);
		screen.displaySplashScreen();
	}
	
	/**
	 * Initializes the array of foods, which holds all Food objects that the game has to choose from 
	 * (randomly). 
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
	}
	
	/**
	 * Starts the game and clears the screen.
	 */
	public void startGame() {
		start_game = true;
		root.getChildren().clear();
		newLevel();
	}
	
	/**
	 * Updates the level and calls a function to update the display.
	 */
	public void newLevel() {
		level++;
		num_foods = 0;
		if (level > 2) {
			screen.endGame("YOU WIN!");
		} else {
			screen.displayLevel();
			level_frame = frame;
			screen.displayWhiteText("Level " + level);
		}
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
	 * Randomly selects a Food object from the array of Food objects and then adds all of the 
	 * characters in the answer to an array of letters (which decreases in size as the user hits the 
	 * correct letters).
	 * @param food - the name of the food that was just guess ("" if new game) in order to avoid repeats
	 */
	public void newFood(String food) {
		root.getChildren().clear();
		game_coupons.clear();
		game_letters.clear();
		remaining_letters.clear();
		
		Random random_generator = new Random();
		while (food.equals(current_food.getAnswer())) {
			current_food = foods.get(random_generator.nextInt(foods.size()));
		}
		for (int c = 0; c < current_food.getAnswer().length(); c++) {
			char char_letter = current_food.getAnswer().charAt(c);
			remaining_letters.add(Character.toString(char_letter));
		}
		
		createCouponPrinter();
		screen.displayFood(current_food.getQuestion());
		screen.displayLives(lives);
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
                    	screen.endGame("GAME OVER");
                    	break;
                    case N: // cheat key to reach the next level
                    	num_foods++;
                    	if (num_foods == 5) {
                    		newLevel();
                    	} else {
                    		newFood(current_food.getAnswer());
                    	}
                    	break;
                    case W: // cheat key to reach the "YOU WIN!" screen
                    	screen.endGame("YOU WIN!");
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
		int random_selection_number = random_generator.nextInt(3); //33.33% chance of selecting each number
		char letter;
		if (random_selection_number == 0) {
			int random_index = random_generator.nextInt(remaining_letters.size());
			letter = remaining_letters.get(random_index).charAt(0);
		} else if (random_selection_number == 1 && level == 2){
			letter = current_food.getAnswer().charAt(food_index);
		} else {
			letter = (char) (random_generator.nextInt(26)+97); //convert ASCII code
		}
		
		int x_position = random_generator.nextInt(Main.SCREEN_WIDTH-Letter.WIDTH-Main.PRINTER_WIDTH) + Main.PRINTER_WIDTH/2; // ensuring that letter is not placed off the screen
		int y_position = Screen.TEXT_Y + 5;
		
		Letter new_letter = new Letter(this, letter, x_position, y_position);
		root.getChildren().add(new_letter.drawLetter());
		game_letters.add(new_letter);
	}
	
	/**
	 * Creates a coupon, which is shot from the center of coupon printer and travels upwards.
	 * @param root - the root node of the scene graph
	 * @param x - the x coordinate of the center of the coupon
	 * @param y - the y coordinate of the center of the coupon
	 */
	public void createCoupon(int x, int y) {
		Coupon new_coupon = new Coupon(this, root);
		root.getChildren().add(new_coupon.drawCoupon(x, y));
		game_coupons.add(new_coupon);
	}
	
	/**
	 * Removes a letter (the correct one that was just hit) from the letters array (which contains the
	 * letters left to be hit). It then calls the function to display the letter to inform the user 
	 * that the right letter (or one of the right letters) was hit.
	 * @param letter - the letter that was just hit
	 */
	public void addLetter(Letter letter) {
		String current_letter = Character.toString(letter.getValue());
		if (remaining_letters.contains(current_letter)) {
			remaining_letters.remove(current_letter);
			food_index++;
			letter.display();
			if (remaining_letters.size() == 0) { // no more letters left to hit in the answer, new food
				num_foods++;
				food_index = 0;
				if (num_foods == LEVEL_NUM_FOODS) {
					newLevel();
				} else {
					newFood(current_food.getAnswer());
				}
			}
		}
	}
	
	/**
	 * Decrements the number of lives and updates the display. It ends the game if all lives are lost. 
	 */
	public void updateLives() {
		root.getChildren().remove(0);
		lives--;
		if (lives == 0) {
			screen.endGame("GAME OVER");
		} else {
			screen.displayLives(lives);
		}
	}
	
	/**
	 * Runs the game by indefinitely creating frames. It controls whether a level screen is displayed
	 * and when to listen for key input.
	 */
	public void play() {
		if (start_game) {
			if (level_frame >= 0) {
				if (frame-level_frame == 3*Main.FRAMES_PER_SECOND) {
					root.getChildren().clear();
					level_frame = -1;
					newFood("");
				}
			} else {
				handleKeyInput();
				if (frame % (2*Main.FRAMES_PER_SECOND) == 1) {
					createLetter();
				}
				for(int i = 0; i < game_letters.size(); i++) {
					game_letters.get(i).fall(Main.SECOND_DELAY);
				}
				for(int i = 0; i< game_coupons.size(); i++) {
					game_coupons.get(i).travel(Main.SECOND_DELAY);
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
	
	/**
	 * This method accesses the array of coupons that are currently in the game.
	 * @return the array of coupons 
	 */
	public ArrayList<Coupon> getGameCoupons() {
		return game_coupons;
	}
}