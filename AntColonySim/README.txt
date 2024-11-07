
COMP 452 AI for Game Developers
Assignment 1
Zenon Kristalovich

Game Design:

The game is a basic top down game, where an ant nest is near the center of the map.
Food, water and poison is spawned randomly around the map. The ants wander around the map
until the find food, come back to base and drop food off get thirsty, and spawn a new ant.
Now the ant must find water before it collects more food. There is also poison around, if 
an ant steps on it they die.

Logic:

Main creates the screen for the game to be displayed on, but the GamePanel class
handles the games functionality. By setting the game to run at 60 FPS and runs
updates and redraws the screen every frame. 

The map is made by reading in info from a text file, and turned into an array. Characters and such appear
small on map as i wanted to see the whole ant colony simulation.

Every frame all the updates are called by GamePanel, and allows other classes to run depending on what state it is in.

They wander by selecting a random position to walk to. I have them attempt the same position a few times to make it look less
random in there movements.

The path finding is done using an A* search. We use a priority cue to select best option to look at,
using a distance between the position and goal position as a heuristic to decide best option available. 
This one does not use diagonals for searching, the wandering I didn't have it, so i thought it would be 
good to not have it for this either.

How to Compile and Run:

The game was made with java version 1.8 in mind. So wrong versions of java can lead
to the program not running. To launch the game, there is a executable jar file called 
"Ant Colony.jar". 

How to play:

Simply enter a number 1-4 for the starting ants in the colony.

Finite State Machine:

The Player class has two State machines in its update function. First is deciding what to do about 
the current object it is standing on, if poison it dies, if thirsty check for water, not thirsty 
check for food. Then we have a state machine to decide the movement, if it is either wandering or 
moving back to the home ant hill. This method effectively implements two state machines: one for 
handling environmental interactions and another for managing player movement.

Bugs:

Wandering: Sometimes when wandering and they go against a collision object, in this case rocks.They will 
irregularly move back and forth briefly before moving on

Note: There is no limit to the amount of ants there can be. I tested mine and it starts lagging around 250 ants

