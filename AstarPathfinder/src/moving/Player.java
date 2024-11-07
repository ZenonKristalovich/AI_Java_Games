/*Class: Player
 * Purpose: Handles the player, moves it along the path found
 */

package moving;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import search.Node;

public class Player extends Entity{

	//Variables
	
	GamePanel gp;
	KeyHandler keyH;
	List<Node> path;
	public int pos = 0;
	double startTime;
	
	//Constructor
		public Player(GamePanel gp, KeyHandler keyH) {
			this.gp = gp;
			this.keyH = keyH;
			
			solidArea = new Rectangle(8, 16, 32, 32);
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			
			getPlayerImage();
		}
		
		public void setDefaultValues(int x, int y, 	List<Node> path) {//Set default values for character
			worldX = gp.tileSize * x;
			worldY = gp.tileSize * y;
			speed = 2;
			direction = "down";
			this.path = path;
			startTime = ((System.nanoTime() / 1000000000.0));
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
			
			
			if (!path.isEmpty() && ((System.nanoTime() / 1000000000.0)- startTime) > 2 ) { //Move The player through the path
			    Node node = path.get(pos); // Access the first node
			    
			    if (worldX == node.x*gp.tileSize && worldY == node.y*gp.tileSize) {
			    	pos += 1;
			    	if (pos >= path.size() ) { 
			    		gp.gameState = gp.completeState;
			    	}
			    }else {
			    	int[] newPos = seek(node.x*gp.tileSize, node.y*gp.tileSize, worldX, worldY);
			    	worldX = newPos[0];
			    	worldY = newPos[1];
			    }
			}
			
		}
		
		public int[] seek(int px, int py, int ex, int ey) {//Move character toward location
			
			// Calculate the direction vectors
	        double dx = px - ex;
	        double dy = py - ey;
	        
	        //Calculate Where to Look
	        if(dx > 0) {
	        	direction = "right";
	        }else if (dx < 0 ) {
	        	direction = "left";
	        }else if (dy > 0) {
	        	direction  = "down";
	        }else if (dy < 0) {
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
	        
	        //Return New Position of the Enemy after Moving
	        return new int[]{(int)Math.round(newX), (int)Math.round(newY)};
			
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
