game
====

Aninda Manocha
First project for CompSci 308 Fall 2016

DESIGN
======

The goal of this project was to design a 2D interactive game using proper coding conventions and with clear, readable, and well-documented code. This entailed keeping all methods within 20 lines of code, keeping all classes within 200 lines of code, properly delegating tasks to the appropriate classes, and avoiding code smells. In order to add a new feature, create a method in Game.java with an appropriate name detailing what the new feature does. Game.java has control over the game and oversees everything. The methods in Game.java should be clear enough to know where to call the method with the new feature. When I was adding new features, I would create them in this manner so that I could focus on the function of the feature. Then, I would figure out how to integrate it into the game and its story. 

The major decisions I made involved implementing collision detection in the Letter.java class, placing a majority of the control of the game in the Game.java class, and only using Main.java to set up the game and run the game loop. I thought it seemed reasonable to have the Main.java class have a limited number of responsibilities because its purpose is simply to run the Java application. I did not want to clutter it with several methods. By creating a Game.java class, it is more clear where the game methods belong. I chose to implement collision detection in the letter class because I would have to check for collisions between letters and coupons, as well as letters and the coupon printer. Letters are the only objects in common between these two pairs, so it seemed logical to use Letter.java to check for physical interaction between objects. I also considered implementing it in Game.java, but realized this would involve checking the intersection between every pair of objects, and this process is less efficient than knowing one object and checking its intersection with all other objects in the game.

In order to simplify ambiguities in my project's functionality, I tried to use appropriate variable and method names, so that it was easy to follow what actions occur and when. Creating classes for each of the objects clarified their responsibilities and actions as well. Their interactions also became more apparent because they would call each other and since they are separate entities, it is clear when one depends on another.