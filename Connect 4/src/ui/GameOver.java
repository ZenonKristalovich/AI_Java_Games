/*Class: GameOver
 * Purpose: handles the gameover page
 */

package ui;

import main.GamePanel;
import main.KeyHandler;

public class GameOver {
	
	//Variables
	
		GamePanel gp;
		KeyHandler keyH;
		
		//Constructor
		
		public GameOver(GamePanel gp, KeyHandler keyH){
			this.gp = gp;
			this.keyH = keyH;
		}
		
		//METHODS
		
		public void update() {//Checks to see if user wants to go to home page
			if (keyH.enter) {
				gp.gameState = gp.titleState;
				gp.setupGame();
				gp.title.appeared = 0;
			}
		}
}
