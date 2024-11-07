/*Class: TitleScreen
 * Purpose: A basic title screen, waits for when player wants to start
 */


package ui;
import main.KeyHandler;

import main.GamePanel;

public class TitleScreen {
	
	//Variables
	
	GamePanel gp;
	KeyHandler keyH;
	
	//Constructor
	
	public TitleScreen(GamePanel gp, KeyHandler keyH){
		this.gp = gp;
		this.keyH = keyH;
	}
	
	//METHODS
	
	public void update() {//Checks to see when a player wants to start the game
		if (keyH.onePressed) {
			gp.ants = 1;
			gp.setupGame();
		}else if(keyH.twoPressed) {
			gp.ants = 2;
			gp.setupGame();
		}else if(keyH.threePressed) {
			gp.ants = 3;
			gp.setupGame();
		}else if(keyH.fourPressed) {
			gp.ants = 4;
			gp.setupGame();
		}
	}
}
