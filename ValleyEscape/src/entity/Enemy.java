/*Class: Enemy
 * Purpose: Code for the enemy character
 * Handles what the enemy does on every frame update
 */

package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.Random;

import main.GamePanel;


public class Enemy extends Entity{
	
	//Variables
	
	GamePanel gp;
	Player player;
	AIMovement ai;
	Random rand = new Random(); 
	public int originX;
	public int originY;
	int randomNum = rand.nextInt(4);
	int wanderMoves = 0;
	boolean returnOrigin = false;
	private int spriteCounter = 0;
	private int spriteNum = 0;
	String direction = "left";
	
	//Constructor
	public Enemy(GamePanel gp, Player player) {
		this.gp = gp;
		this.player = player;
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getEnemyImage();
	}
	
	//METHODS
	
	public void setDefaultValues() {//Sets the default values of the enemy
		speed = 2;
		direction = "down";
		ai = new AIMovement(speed,gp);
	}
	
	public void getEnemyImage() { //Reads in the sprite images
		
		try {

			left1 = ImageIO.read(getClass().getResourceAsStream("/enemy/skeleton_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/enemy/skeleton_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/enemy/skeleton_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/enemy/skeleton_right_2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void update() {//Handle what the enemy is to do every frame update
		
		//Distance between Enemy and Player
		
		int x = player.worldX - this.worldX;
		int y = player.worldY - this.worldY;
		
		//Set direction enemy is facing
		if(x > 0) { direction = "right"; }else { direction = "left"; }
		
		int[] newPos = new int[2];
		
		float distance = ( (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))) / gp.tileSize;
		
		if(distance < 0.5) {
			gp.gameState = gp.deadState;
			return;
		}
		
		//Flee from Player
		if(player.poweredUp == true && distance <= 4) {
			newPos= ai.flee(player.worldX, player.worldY, this.worldX, this.worldY);
			if (gp.cChecker.checkFutureTile(newPos)) {
				worldX = newPos[0];
				worldY = newPos[1];
			}
		}
		//Move to Player
		else if(distance <= 4 ) {
			newPos= ai.seek(player.worldX, player.worldY, this.worldX, this.worldY);
			if (gp.cChecker.checkFutureTile(newPos)) {
				worldX = newPos[0];
				worldY = newPos[1];
			}
		}else {
			//Handle Wandering
			
			//If far away from wandering point
			x = originX - worldX;
			y = originY - worldY;
			
			distance = ( (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))) / gp.tileSize;
			
			if (distance < 2) { returnOrigin = false;}
			
			//Walk back to wandering area
			if (distance > 6 || returnOrigin) {
				returnOrigin = true;
				wanderMoves = 0;
				newPos= ai.seek(originX, originY, this.worldX, this.worldY);
				if (gp.cChecker.checkFutureTile(newPos)) {
					worldX = newPos[0];
					worldY = newPos[1];
				}
			}else {
				//Wander in Random Direction, moves in one direction 20/2 frames. Then stands still for 20 frames, then picks new direction and repeats
				if(wanderMoves < 40) {
					wanderMoves++;
				}else if(wanderMoves < 160){
					wanderMoves++;
					return;
				}else {
					randomNum = rand.nextInt(4);
					wanderMoves = 0;
				}
				
				//Wander in Random Direction
				if(randomNum == 0) {
					newPos= ai.seek(this.worldX - 2, this.worldY, this.worldX, this.worldY);
					direction = "left";
				}else if(randomNum == 1) {
					newPos= ai.seek(this.worldX + 2, this.worldY, this.worldX, this.worldY);
					direction = "right";
				}else if(randomNum ==2) {
					newPos= ai.seek(this.worldX, this.worldY - 2, this.worldX, this.worldY);
				}else {
					newPos= ai.seek(this.worldX, this.worldY + 2, this.worldX, this.worldY);
				}
				
				if (gp.cChecker.checkFutureTile(newPos)) {
					worldX = newPos[0];
					worldY = newPos[1];
				}
				
			}
			
		}
		
	}
	
	
	public void draw(Graphics2D g2) { //Draws the Enemy on the screen
		
		//SpriteCounter
		spriteCounter++;
		if(spriteCounter >= 12) {
			spriteCounter = 0;
			spriteNum = (spriteNum + 1)%2;
		}
		
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		BufferedImage image = left1;
		
		//Select the Sprite that is to be drawn
		
		switch (direction) {
		case "left":
			if (spriteNum == 0) {
				image = left1;
			}else {
				image = left2;
			}
			break;
		case "right":
			if (spriteNum == 0) {
				image = right1;
			}else {
				image = right2;
			}
			break;
		}
		
		//Draw Image
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize  < gp.player.worldX + gp.player.screenX && 
				worldY + gp.tileSize  > gp.player.worldY - gp.player.screenY && 
				worldY - gp.tileSize  < gp.player.worldY + gp.player.screenY) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
		
	}
}
