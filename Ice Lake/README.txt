
COMP 452 AI for Game Developers
Assignment 3
Zenon Kristalovich

Game Design:

A basic survival game where the player has to avoid falling into water as long as it can

Logic:

Main creates the screen for the game to be displayed on, but the GamePanel class
handles the games functionality. By setting the game to run at 60 FPS and runs
updates and redraws the screen every frame. 

The map is made by reading in info from a text file, and turned into an array.

Every frame all the updates are called by GamePanel, and allows other classes to run depending on what state it is in.


How to Compile and Run:

The game was made with java version 1.8 in mind. So wrong versions of java can lead
to the program not running. To launch the game, there is a executable jar file called 
"Ice_Lake.jar". 

How to play:

Press 'e' to start the game. And watch the ant learn. Press 'w' to go back to home screen

Qtable:

The player uses a qtable in order to learn. Action file displays what types of actions, a player can take. State describes the current state the player is in
TileStatus is used for the State class to describe the ice around it. Qtable stores the qtable. 

The player Starts with a high explore rate, akak random movement, in order to fill out the table. Every 2 rounds the explore rate decreases to a min of 0.

The AIMind class handles the transfers involved with the qtable. Updating the qtable and fetching the information

The Player class handles creating new states, calculating rewards etc that are needed to work with the qtable.