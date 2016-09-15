game
====

Aninda Manocha
First project for CompSci 308 Fall 2016

README
======

I spent around 30 hours working on this project individually, beginning on August 29, 2016 and finishing on September 12, 2016. My role was to implement an interactive 2D game with clear winning and losing conditions, characters that move and interact with the player, and two levels. I read a lot of JavaFX documentation (docs.oracle.com/javafx/) in addition to readings about the architecture and examples before and while I was coding my game. These readings helped me understand how to set up a Java application, create an animation (game loop), and use different objects and packages in JavaFX (i.e. javafx.scene.*) This project includes six files:

1. Main.java
2. Game.java
3. CouponPrinter.java
4. Coupon.java
5. Letter.java
6. Food.java

In order to start the project, run Main.java. All six files are needed to properly test the project (play the game) and the project uses 27 .png image files (a.png,...,z.png, and printer.png). There are no command line arguments; the application can simply be run. The left, right, and up arrow keys are used to play the game, and there are three cheat keys (that were mainly used for debugging purposes):

1. "N" - advances to the next food question (there are 5 food questions per level, so if you advance 5 times, you will reach the next level)
2. "W" - advances to the "YOU WIN!" screen
3. "G" - advances to the "GAME OVER" screen

There are no known bugs or problems with the project's functionality, however, keep in mind that in level 2, the letters must be hit in order (so don't be surprised if you hit a letter that's in the answer, but you lose a life; it's not a bug) as a means of increasing difficulty. There aren't any extra features in the project either, but I think the game would be improved if there were more reactions when a letter was hit. For example, a red "-1" could appear when the player hits the wrong letter, there could be a counter at the top counting down the number of words until the next level, or there could be encouraging words that appear like "Good job!" or "Nice try!" after the player hits certain letters. These features would make the game more interactive and fun to play.