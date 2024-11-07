/*Class: Token
 * Purpose: Parent class to token classes
 */

package moving;

import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

public class Token {
	
	//Variables
	GamePanel gp;
	KeyHandler keyH;
	BufferedImage token;
	
	//Methods
	
	public boolean validPosition(int x) { //checks if position is valid area for a token to be placed
		
		for(int i = 8; i > 2; i-- ) {
			if (gp.tileM.mapTileNum[x][i] == 4) {
				return true;
			}
		}
		
		return false;
	}
}
