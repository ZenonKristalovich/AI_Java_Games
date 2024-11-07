
COMP 452 AI for Game Developers
Assignment 3
Zenon Kristalovich

Game Design:

A connect 4 game against an AI opponent. Play tokens back and fourth until a 4 in a row occurs.

Logic:

Main creates the screen for the game to be displayed on, but the GamePanel class
handles the games functionality. By setting the game to run at 60 FPS and runs
updates and redraws the screen every frame. 

The map is made by reading in info from a text file, and turned into an array.

Every frame all the updates are called by GamePanel, and allows other classes to run depending on what state it is in.

In the Board class, we have a minimax function used for deciding what the player is to do.

How to Compile and Run:

The game was made with java version 1.8 in mind. So wrong versions of java can lead
to the program not running. To launch the game, there is a executable jar file called 
"Connect_4.jar". 

How to play:

Press 'e' to start the game, and use 'a' and 'd' to select the column to place the token. And press 's' to drop token

AI Thinking:

A minimax function in the board class handles its choices. Choices are made to a depth of 4. With alpha beta pruning involved.
The branches are evaluated, giving points to determine how good they are. Lots of points given for a connect 4, loss of points 
for enemies connect 4. and minimal points for pairing up tokens

Note: AI works fairly well, but sometimes does not do smart decisions. 