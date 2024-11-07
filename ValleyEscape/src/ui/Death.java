/*Class: Death
 * Purpose: Handles the UI screen when a player dies
 */


package ui;
import main.KeyHandler;

import main.GamePanel;

public class Death {
	
	//Variables
	
	GamePanel gp;
	KeyHandler keyH;
	
	//Constructor
	
	public Death(GamePanel gp, KeyHandler keyH){
		this.gp = gp;
		this.keyH = keyH;
	}
	
	//METHODS
	
	public void update() {//Checks to see when you want to leave the Death screen
		if(keyH.enter == true) {
			gp.gameState = gp.playState;
			gp.gameRestart();
		}
	}
}