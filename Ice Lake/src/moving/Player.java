/*Class: Player
 * Purpose: Handles the player logic
 */

package moving;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import qlearning.TileStatus;
import qlearning.State;
import qlearning.Action;

public class Player extends Entity{

	//Variables
	
	GamePanel gp;
	KeyHandler keyH;
	public double startTime;
	AIMind ai;
	boolean inAction;
	boolean collided;
	boolean fall;
	int goalPosition[] = new int[2];
	
	//QTABLE STUFF
	State currentState;
	Action currentAction;
	
	//Constructor
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		this.inAction = false;
		this.collided = false;
		this.fall = false;
		
		ai = new AIMind( 0.1, 0.9, 0.8);
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getPlayerImage();
	}
	
	public void setDefaultValues(int x, int y) {//Set default values for character
		worldX = gp.tileSize * x;
		worldY = gp.tileSize * y;
		goalPosition[0] = x;
		goalPosition[1] = y;
		inAction = false;
		collided = false;
		speed = 2;
		direction = "down";
		currentState = calculateState();
		startTime = ((System.nanoTime() / 1000000000.0));
		
		if (gp.round%2 == 0) {
			ai.explorationRate = Math.max(0, ai.explorationRate - 0.2);
		}
	}
	
	public void getPlayerImage() {//Load the sprite images
		
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {//Updates every frame
		
		//Update Sprites
		spriteCounter();
		//System.out.println(gp.tileM.mapTileNum[worldX/gp.tileSize][worldY/gp.tileSize]);
		
		if(gp.tileM.mapTileNum[worldX/gp.tileSize][worldY/gp.tileSize] == 5) {
			//Character fell into water
			this.fall = true;
		}
		
		//If we are in action, continue moving
		if(inAction) {

			completeAction();
		}else {

			currentAction = ai.calculateMove( currentState ); 
			collided = false;
			calculatePosition(currentAction);
			inAction = true;
		}
	}
	
	public State calculateState() { //Gets current state of the player
		
		int posX = goalPosition[0];
		int posY = goalPosition[1];
		//System.out.println(posX + " " + posY);
		
		int directions[][] = new int [][] { {0,1},{0,-1},{-1,0},{1,0},{0,0} };
		TileStatus[] status = new TileStatus [5];
		
		
		for(int i = 0; i < directions.length; i++) {
			int tile = gp.tileM.mapTileNum[posX + directions[i][0]][posY + directions[i][1]];
			if (tile == 0) {
				status[i] = TileStatus.COLLISION;
			}else if(tile == 5) {
				status[i] = TileStatus.WATER;
			}else if(tile == 1){
				status[i] = TileStatus.SOLID;
			}else if(tile == 2){
				status[i] = TileStatus.CRACKED_1;
			}else if(tile == 3){
				status[i] = TileStatus.CRACKED_2;
			}else if(tile == 4){
				status[i] = TileStatus.CRACKED_3;
			}
		}
		
		State state = new State(status[4]);
		state.options(status[1],status[0],status[2],status[3]);
		
		return state;

	}
	
	private void completeAction() { //Finishes up a walk to a specific location
		
		if(goalPosition[0]*gp.tileSize != worldX || goalPosition[1]*gp.tileSize != worldY) {
			int [] pos = seek(goalPosition[0]*gp.tileSize,goalPosition[1]*gp.tileSize, worldX, worldY);
			
			if(pos[0] == 0 && pos[1] == 0) {
				worldX = goalPosition[0];
				worldY = goalPosition[1];
			}else {
				worldX = pos[0];
				worldY = pos[1];
			}

		}else {
			//System.out.println("Arrived");

			inAction = false;
			currentState = calculateState();
			double reward = calculateReward();
			
			if(collided) {
				currentState.current = TileStatus.COLLISION;
				collided = false;
				ai.updateQValue(currentState, currentAction, reward);
			}else if(fall) {
				fall = false;
				ai.updateQValue(currentState, currentAction, reward);
				gp.newRound();
			}else {
				ai.updateQValue(currentState, currentAction, reward);
			}
}
	}
	
	private double calculateReward() { //Calculates the reward the player will recieve
		
		int tile = gp.tileM.mapTileNum[worldX/gp.tileSize][worldY/gp.tileSize];
		double reward = 0;
		if(collided) {
			reward = -50;
		}else {
			if(tile == 5) {
				reward = -100;
			}else if(tile == 1){
				reward = 100;
			}else if(tile == 2){
				reward = 60;
			}else if(tile == 3){
				reward = 30;
			}else if(tile == 4){
				reward = -10;
			}
		}
		return reward;
	}
	
	private void calculatePosition(Action action) { //Calculate the goal position based on the action selected
	    
	    switch (action) {
	        case MOVE_UP:
	            goalPosition[0] = worldX;
	            goalPosition[1] = worldY - gp.tileSize; // Moving up decreases y
	            break;
	        case MOVE_DOWN:
	            goalPosition[0] = worldX;
	            goalPosition[1] = worldY + gp.tileSize; // Moving down increases y
	            break;
	        case MOVE_LEFT:
	            goalPosition[0] = worldX - gp.tileSize; // Moving left decreases x
	            goalPosition[1] = worldY;
	            break;
	        case MOVE_RIGHT:
	            goalPosition[0] = worldX + gp.tileSize; // Moving right increases x
	            goalPosition[1] = worldY;
	            break;
	        default:
	            System.out.println("Invalid action");
	            break;
	    }
	    
	    if ( gp.tileM.mapTileNum[goalPosition[0]/gp.tileSize][goalPosition[1]/gp.tileSize] == 0 ) {
	    	goalPosition[0] = worldX;
            goalPosition[1] = worldY;
	    	collided = true;
	    }
	    
	    goalPosition[0] = goalPosition[0]/gp.tileSize;
	    goalPosition[1] = goalPosition[1]/gp.tileSize;
	    
	}

	
	public int[] seek(int px, int py, int ex, int ey) { //Moves toward goal location
	    // Calculate the direction vectors
	    double dx = px - ex;
	    double dy = py - ey;

	    // Calculate Where to Look
	    if (dx > 0) {
	        direction = "right";
	    } else if (dx < 0) {
	        direction = "left";
	    } else if (dy > 0) {
	        direction = "down";
	    } else if (dy < 0) {
	        direction = "up";
	    }

	    // Calculate the distance between enemy and player
	    double distance = Math.sqrt(dx * dx + dy * dy);

	    // Normalize the direction vector
	    double normalizedDx = dx / distance;
	    double normalizedDy = dy / distance;

	    // Calculate new position based on the speed
	    double newX = ex + normalizedDx * speed;
	    double newY = ey + normalizedDy * speed;

	    // Return the new position of the Enemy after moving
	    return new int[]{(int) Math.round(newX), (int) Math.round(newY)};
	}

	
	private void spriteCounter() { //Used to handle sprite changes for animation
		spriteCounter++;
		if (spriteCounter > 12) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum ==2) {
				spriteNum =1;
			}
			spriteCounter = 0;
		}
	}
	
	public void draw(Graphics2D g2) {//Draws the player on the screen
		
		BufferedImage image = null;
		
		//Select the sprite
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;
		}
		//Draw Player
		g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null );
		
	}
	
}
