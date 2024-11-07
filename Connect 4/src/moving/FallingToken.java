/*Class: FallingToken
 * Purpose: Handles the logic of a token falling into place
 */

package moving;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class FallingToken extends Token {
	
	//Variables
	int speed = 4;
	
	//Current Position
	int worldX;
	int worldY;
	
	//Bot Or Player
	boolean player = true;
	
	//Start
	int startTileX;
	int startTileY;
	//End
	int endTileY = 0;
	
	//Constructor
	public FallingToken(GamePanel gp) {
		this.gp = gp;
	}
	
	//Methods
	
	public void reinitialize(int tileX, int tileY, String tokenImage, boolean player) { //resets the object to handle a new token
		this.startTileX = tileX;
		this.startTileY = tileY;
		this.player = player;
		this.worldX = tileX * gp.tileSize;
		this.worldY = tileY * gp.tileSize;
		
		try {
			token = ImageIO.read(getClass().getResourceAsStream(tokenImage));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		//Calculate Drop Location
		dropLocation();
	}
	
	private void dropLocation() { //Finds the location where it wants to fall to
		//Find Drop Location
		for(int i = 8; i > 2; i-- ) {
			if (gp.tileM.mapTileNum[startTileX][i] == 4) {
				endTileY = i;
				return;
			}
		}
		
	}
	
	public void update() { //handles the falling and outcome after falling
		
		if(worldY == endTileY * gp.tileSize) {
			if(player) {
				gp.board.updateBoard(0, startTileX, endTileY);
				gp.tileM.mapTileNum[startTileX][endTileY] = 2;
				gp.gameState = gp.botState;
				gp.board.checkWin(0, startTileX, endTileY);
				gp.whosTurn = "Bot";
			}else {
				gp.board.updateBoard(1, startTileX, endTileY);
				gp.tileM.mapTileNum[startTileX][endTileY] = 1;
				gp.gameState = gp.playerState;
				gp.board.checkWin(1, startTileX, endTileY);
				gp.whosTurn = "You";
				gp.newRound();
			}
		}else {	
			int pos [] = seek(startTileX * gp.tileSize, endTileY * gp.tileSize, worldX, worldY);
			worldX = pos[0];
			worldY = pos[1];
		}
		
	}
	
	public int[] seek(int px, int py, int ex, int ey) {//Handles gravity, where it should be
		
		// Calculate the direction vectors
        double dx = px - ex;
        double dy = py - ey;
        
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
	
	public void draw(Graphics2D g2) { //Draws the token
		//Draw Token
		g2.drawImage(token, worldX, worldY, gp.tileSize, gp.tileSize, null );
	}
}
