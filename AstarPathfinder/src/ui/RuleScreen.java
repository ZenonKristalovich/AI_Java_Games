/*Class: RuleScreen
 * Purpose: Class for handling the rule screen outcomes
 */

package ui;

import main.GamePanel;
import main.KeyHandler;

public class RuleScreen {
	
	//Variables
	
	GamePanel gp;
	KeyHandler keyH;
	public double appeared = 0;
	
	//Constructor
	
	public RuleScreen(GamePanel gp, KeyHandler keyH){
		this.gp = gp;
		this.keyH = keyH;
	}
	
	//METHODS
	
	public void update() {//Checks to see when a player wants to start the game
		if (appeared == 0) {
			appeared = ((System.nanoTime() / 1000000000.0));
		}else {
			double currentTime = ((System.nanoTime() / 1000000000.0));
			if(keyH.enter == true && (currentTime - appeared) > 2) {
				gp.gameState = gp.createState;
			}
		}
	}
}
