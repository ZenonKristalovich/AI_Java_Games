/*Class: AIToken
 * Purpose: Is the object responsible for the AI token 
 */

package moving;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

import java.util.Random;

public class AIToken extends Token {

		//Constructor
		public AIToken(GamePanel gp){
			super.gp = gp;

			//token
			try {
				super.token = ImageIO.read(getClass().getResourceAsStream("/player/yellowToken.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		public void update() { //handles what move to make
			//Call Random
			int column = gp.board.makeAIMove();
			gp.falling.reinitialize(column + 2, 1, "/player/yellowToken.png", false);
			gp.gameState = gp.fallState;
			
		}
}
