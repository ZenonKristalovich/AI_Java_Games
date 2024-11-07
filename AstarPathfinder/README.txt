
COMP 452 AI for Game Developers
Assignment 2 Part 1
Zenon Kristalovich

Game Design:

The game is a basic top down game, You can make a map for an AI "Ant" to find its 
way through to the goal position. Showing the tiles it looks at and the final path.

The display of showing the path finding is not in real time. After the ant makes it 
home the game will display the time it took. 

Logic:

Main creates the screen for the game to be displayed on, but the GamePanel class
handles the games functionality. By setting the game to run at 60 FPS and runs
updates and redraws the screen every frame. 

The map is made by reading in info from a text file, and turned into an array. 

Every frame all the updates are called by GamePanel, handles the states of the game to decide what 
is going on at a certain point

The path finding is done using an A* search. We use a priority cue to select best option to look at,
using a distance between the position and goal position as a heuristic to decide best option available. 

How to Compile and Run:

The game was made with java version 1.8 in mind. So wrong versions of java can lead
to the program not running. To launch the game, there is a executable jar file called 
"AStarPathFinding.jar". 

Bugs:

Not exactly a Bug but there is a timer set for when you can leave a screen. this was due to with 
no timer being set you can immediately skip a screen by accident

When doing diagonal searches, there is a chance for a slight weird walking path to the goal. This is because
when backtracking it may take a diagonal path over a straight path because when searching it was better. It is hard to
explain. Its not super bad in opinion, but kind of annoying