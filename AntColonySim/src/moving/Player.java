/*Class: Player
 * Purpose: Handles the player, moves it along the path found
 */

package moving;

import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import main.GamePanel;
import search.AStarSearch;
import search.Node;

public class Player extends Entity{

	//Variables
	
	private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	private static final String[] DIRECTIONS_V2 = {"UP", "RIGHT", "DOWN", "LEFT"};
	GamePanel gp;
	double startTime;
	AStarSearch search;
	String previousDirection = "LEFT";
	int moves = 5;
	int num = 0;
	
	//Wander Movement
	int goal[] = new int[2];
	boolean hasGoal = false;
	int prevGoal[] = new int[] {0,0};
	int prev = 10; //Store what position the previous movement was if found
	
	//Return Movement
	List<Node> path;
	public int pos = 0;
	boolean calculated = false;
	
	//States
	int playerState;
	int roamState = 1;
	int returnHome = 2;
	
	//booleans
	boolean hasFood = false;
	boolean thirsty = false;
	
	//Constructor
		public Player(GamePanel gp) {
			this.gp = gp;
			this.search = new AStarSearch();
			
			solidArea = new Rectangle(8, 16, 32, 32);
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			
			getPlayerImage();
		}
		
		public void setDefaultValues(int x, int y) {//Set default values for character
			playerState = roamState;
			worldX = gp.tileSize * x;
			worldY = gp.tileSize * y;
			speed = 2;
			direction = "down";
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
		
		public int update() {//Updates every frame, return true if player died
			
			//Sprite Handler
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
			
			
			//What I am standing on
			int tile = gp.tileM.mapTileNum[worldX/gp.tileSize][worldY/gp.tileSize];
			
			
			if (gp.tileM.tile[tile].poison) {
				//Add In death Code
				return -1;
			}else if(thirsty) {
				if (gp.tileM.tile[tile].water) {
					thirsty = false;
				}
			}else {
				if (gp.tileM.tile[tile].food && hasFood == false) {
					hasFood = true;
					playerState = returnHome;
				}
			}
			
			//Movement Options
			
			if(playerState == roamState) {
				this.roaming();
			}else if(playerState == returnHome){
				return this.goHome();
			}
			
			return 0;
		}
		
		
		public void roaming() {//When roaming the grounds looking for stuff
			
			if (hasGoal) {
			    int[] newPos = seek(goal[0]*gp.tileSize, goal[1]*gp.tileSize, worldX, worldY);
			    worldX = newPos[0];
			    worldY = newPos[1];
			}else {
				
				if( moves > 4) {
					Random random = new Random();
					num = random.nextInt(4);
					if(previousDirection == DIRECTIONS_V2[num]) {
						num = (num + 2)%4;
					}

					moves = 0;
				}else {
					moves += 1;
				}			
				
				
				//Check for Collisions
				for( int i = 0; i < DIRECTIONS.length; i++) {
					int pos = (num + i) % 4;
					
					int newX = worldX/gp.tileSize + DIRECTIONS[pos][0];
					int newY = worldY/gp.tileSize + DIRECTIONS[pos][1];
					
					if(newX < 0 || newX >= 32 || newY < 0 || newY >= 32) { continue; }//out of bounds				
					
					if (gp.tileM.tile[ gp.tileM.mapTileNum[newX][newY] ].collision == false) {
						//Tile is Able to Move too!!
						previousDirection =  DIRECTIONS_V2[(pos + 2)%4];
						prevGoal = new int[] {newX,newY};
						goal = new int[] {newX,newY};
						hasGoal = true;
					}
				}
			}
			
			
			
		}
		
		public int goHome() {//returns home after collecting food
			//Calculate Path home
			if (!calculated){
				Node current = new Node(worldX/gp.tileSize,worldY/gp.tileSize);
				Node home = new Node(gp.home[0], gp.home[1]);
				path = search.findPath(gp.tileM, current, home);
				calculated = true;
			}else {
				//Move to Home
				if (!path.isEmpty() ) { //Move The player through the path
				    Node node = path.get(pos); // Access the first node
				    
				    if (worldX == node.x*gp.tileSize && worldY == node.y*gp.tileSize) {
				    	pos += 1;
				    	if (pos >= path.size() ) { 
				    		this.foodDelivered();
				    		return 1;
				    		
				    	}
				    }else {
				    	int[] newPos = seek(node.x*gp.tileSize, node.y*gp.tileSize, worldX, worldY);
				    	worldX = newPos[0];
				    	worldY = newPos[1];
				    }
				}
			}
			return 0;
		}
		
		public void foodDelivered() {//Reset the Ants Values
			hasFood = false;
			thirsty = true;
			playerState = roamState;
			pos = 0;
			calculated = false;
			hasGoal = false;
		}
		
		public int[] seek(int px, int py, int ex, int ey) {//Move to target
		    double dx = px - ex;
		    double dy = py - ey;

		    if (dx == 0 && dy == 0) {
		        hasGoal = false;
		        return new int[]{ex, ey};
		    }

		    // Calculate direction
		    direction = calculateDirection(dx, dy);

		    // Calculate the distance between enemy and player
		    double distance = Math.sqrt(dx * dx + dy * dy);

		    // Normalize the direction vector
		    double normalizedDx = dx / distance;
		    double normalizedDy = dy / distance;

		    // Calculate new position based on the speed
		    double newX = ex + normalizedDx * speed;
		    double newY = ey + normalizedDy * speed;

		    // Return the new position
		    return new int[]{(int) Math.round(newX), (int) Math.round(newY)};
		}
		
		public String calculateDirection(double dx, double dy) { //gives us the directions
		    if (Math.abs(dx) > Math.abs(dy)) {
		        return dx > 0 ? "right" : "left";
		    } else {
		        return dy > 0 ? "down" : "up";
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
