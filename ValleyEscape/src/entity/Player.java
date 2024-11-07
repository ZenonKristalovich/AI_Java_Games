/*Class: Player
 * Purpose: Handles code related to the player character
 * Movement, sprite updates
 */


package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	//Variables
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	boolean poweredUp = false;
	long startPowerUp = 0;
	
	//ITEMS
	public int key = 0;
	public int coins = 0;
	
	//Constructor
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	//METHODS
	
	public void setDefaultValues() {//Set default values for character
		worldX = gp.tileSize * 34;
		worldY = gp.tileSize * 7;
		speed = 4;
		direction = "down";
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
	
	
	public void update() {//Updates every frame, decides what the character is to do
		boolean inMotion = false;
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			inMotion = true;
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
		
		if (keyH.upPressed == true) {
			direction = "up";
		}
		else if(keyH.downPressed == true) {
			direction = "down";
		}
		else if(keyH.leftPressed == true) {
			direction = "left";
		}
		else if(keyH.rightPressed == true) {
			direction = "right";
		}
		
		//CHECK TILE COLLISION
		collisionOn = false;
		gp.cChecker.checkTile(this);
		
		//Check Object Collision
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);
		//IF COLLISION IS FALSE< PLAYER CAN MOVE
		if(collisionOn == false && inMotion) {
			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		
		if (poweredUp == true){
			if( ((System.currentTimeMillis() / 1000) - startPowerUp) >= 3) {
				poweredUp = false;
			}
		}
			
		
	}
	
	public void pickUpObject(int i) {//Handles what happens when the character interacts/picks up an object
		if (i != 999) {
			
			String objectName = gp.obj[i].name;
			switch(objectName) {
			case "Power_Up":
				poweredUp = true;
				startPowerUp = System.currentTimeMillis() / 1000;
				gp.obj[i] = null;
				break;
			case "Key":
				key++;
				gp.obj[i] = null;
				gp.enemyGroup = true;
				break;
			case "Coin":
				coins++;
				gp.obj[i] = null;
				break;
			case "Gate":
				if (key > 0) {
					key--;
					gp.obj[i] = null;
				}else {
					collisionOn = true;
				}
				break;
			case "Exit":
				gp.levelSetUp();
				setDefaultValues();
				break;
			}
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
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null );
		
	}
	
}
