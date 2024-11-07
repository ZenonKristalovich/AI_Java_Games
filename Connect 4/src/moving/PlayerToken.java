/*Class: PlayerToken
 * Purpose: Handles logic with the player and the token they have
 */

package moving;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class PlayerToken extends Token{


	//states
	public int state;
	public int selectState = 0;
	public int fallState = 1;
	
	//Position
	int worldTileX = 0;
	int worldTileY = 0;
	
	//Valid Selection Tiles
	int y = 1;
	int xbot = 2;
	int xtop = 8;
	double lastMove = 0;
	
	//Constructor
	public PlayerToken(GamePanel gp, KeyHandler keyH){
		super.gp = gp;
		super.keyH = keyH;
		this.state = this.selectState;
		//Start Position
		worldTileX = 5;
		worldTileY = y;
		//token
		try {
			super.token = ImageIO.read(getClass().getResourceAsStream("/player/redToken.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//Methods
	
	public void update() { //handles positioning of the token and when to drop it
		//Check if Selection is Done
		if (keyH.downPressed) {
			if (super.validPosition(worldTileX) ){
				state = fallState;
			}
		}
		
		//Check What to do
		if(state == selectState) {
			updateSelect();
		}else {
			updateFall();
		}
		
	}
	
	public void updateSelect() { //Moves token left and right
		
		double currentTime = ((System.nanoTime() / 1000000000.0));
		
		if ( (currentTime - lastMove) >= 0.18) {
			if(keyH.leftPressed == true && (worldTileX - 1) >= xbot) {
				lastMove = System.nanoTime() / 1000000000.0;
				worldTileX -= 1;
			}
			else if(keyH.rightPressed == true && (worldTileX + 1) <= xtop) {
				lastMove = System.nanoTime() / 1000000000.0;
				worldTileX += 1;
			}
		}
	}
	
	public void updateFall() { //Sets the token to begin falling
		(gp.falling).reinitialize(worldTileX, worldTileY, "/player/redToken.png", true);
		gp.gameState = gp.fallState;
	}
	
	public void draw(Graphics2D g2) { //Draws token
		//Draw Token
		g2.drawImage(token, worldTileX*gp.tileSize, worldTileY*gp.tileSize, gp.tileSize, gp.tileSize, null );
	}

}
