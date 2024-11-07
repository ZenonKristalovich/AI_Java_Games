/*Class: SelectionSquare
 * Purpose: Handles the selection square when doing map creation
 */

package moving;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

public class SelectionSquare {
	
	//Variables
	
	GamePanel gp;
	KeyHandler keyH;
	public int worldX,worldY;
	double lastMove = 0;
	int newPos;
	
	//Constructor
	
	public SelectionSquare(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		this.setDefaultValues();
	}
	
	//METHODS
	
	public void setDefaultValues() {//Set default values for selection square
		worldX = 0;
		worldY = 0;
	}
	
	public void update() {//Updates every frame, decides what happens to the square
		
		double currentTime = ((System.nanoTime() / 1000000000.0));
		if ( (currentTime - lastMove) >= 0.18) {
			if (keyH.upPressed == true) {
				lastMove = System.nanoTime() / 1000000000.0;
				newPos = (worldY - gp.tileSize) / gp.tileSize;
				if (newPos >= 0 && newPos < 16) {
					worldY = worldY - gp.tileSize;
				}
			}
			else if(keyH.downPressed == true) {
				lastMove = System.nanoTime() / 1000000000.0;
				newPos = (worldY + gp.tileSize) / gp.tileSize;
				if (newPos >= 0 && newPos < 16) {
					worldY = worldY + gp.tileSize;
				}
			}
			else if(keyH.leftPressed == true) {
				lastMove = System.nanoTime() / 1000000000.0;
				newPos = (worldX - gp.tileSize) / gp.tileSize;
				if (newPos >= 0 && newPos < 16) {
					worldX = worldX - gp.tileSize;
				}
			}
			else if(keyH.rightPressed == true) {
				lastMove = System.nanoTime() / 1000000000.0;
				newPos = (worldX + gp.tileSize) / gp.tileSize;
				if (newPos >= 0 && newPos < 16) {
					worldX = worldX + gp.tileSize;
				}
			}
		}
				
	}
	
public void draw(Graphics2D g2) {//Draws the square on the screen
		
		//Draw Select Square
		g2.setStroke(new BasicStroke(5)); // Set the stroke thickness to 5
		g2.setColor(Color.RED);
		g2.drawRect(worldX, worldY, gp.tileSize, gp.tileSize);
		
	}
	
}
