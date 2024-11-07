
COMP 452 AI for Game Developers
Assignment 1
Zenon Kristalovich

Game Design:

The game is a basic top down game, with the goal of escaping the area by avoiding skeletons.
The player needs to pick up the key in order to unlock the gate to escape. After escaping you 
proceed to level 2, which has the same goal and so on. 

Logic:

Main creates the screen for the game to be displayed on, but the GamePanel class
handles the games functionality. By setting the game to run at 60 FPS and runs
updates and redraws the screen every frame. 

The map is made by reading in info from a text file, and turned into an array. The map also only
draws what is to appear on the screen to avoid unneeded drawing.

Every frame all the updates are called by GamePanel, and allows other classes such as enemy and player 
to be updated when game is in playState.

How to Compile and Run:

The game was made with java version 1.8 in mind. So wrong versions of java can lead
to the program not running. To launch the game, there is a executable jar file called 
"Dont_Get_Caught!". 

How to play:

Use AWSD to move the character around
Player spawns in and can collect coins and a key in order to escape while avoiding the skeletons
A key is used to unlock the gate and escape
A power up is found in the center of the map. After collecting, skeletons will fear you for 3 seconds
Once you pick up a key, the skeletons are alerted and will charge you in a group. 

Steering Behaviors:

Seek: When the player is within range, the skeleton will walk towards the player. 

Flee: When the player picks up the power up, the skeleton will instead move away from the player

Wander: A wander region is set up around a skeletons start point. The skeleton will move
up,down, left or right by chance, with breaks in between movements. 

Formation Mode: When the player picks up the keys, all 3 enemies will move toward the player no matter 
how far away the player is. A leader is selected, the leader focuses on moving toward the player. The 
others base there positions on where the leader is located and form a form of triangle pointing toward the player.

How to Cause Behaviors:

Seek: Move within 4 tiles of an enemy

Flee: Move within 4 tiles of an enemy when the power up is active

Wander: Enemy is on screen and farther then 4 tiles from the player

Formation: After collecting a key and the skeletons have time to group up. As they are spread out, it takes time for them to walk to position

Bugs:

Enemy Sprite: Enemy sprite doesn't look the best and it doesn't always look the desired direction
Collision: Rarely collision checks will fail

