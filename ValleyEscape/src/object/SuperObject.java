/*Class: SuperObject
 * Purpose: Parent class to all objects
 */


package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
	
	//Variables
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	public int width = 1;
	public int height =1;

	//Methods
	
	public void draw(Graphics2D g2, GamePanel gp) {//Draws the Image
	
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX + gp.tileSize * 3 > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize * 3  < gp.player.worldX + gp.player.screenX && 
				worldY + gp.tileSize * 3  > gp.player.worldY - gp.player.screenY && 
				worldY - gp.tileSize * 3 < gp.player.worldY + gp.player.screenY) {
			g2.drawImage(image, screenX, screenY, gp.tileSize * width, gp.tileSize *height, null);
		}
	}
}
