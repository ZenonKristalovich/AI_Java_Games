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
	public double appeared = 0;
	
	//Constructor
	
	public TitleScreen(GamePanel gp, KeyHandler keyH){
		this.gp = gp;
		this.keyH = keyH;
	}
	
	//METHODS
	
	public void update() {//Checks to see when a player wants to start the game
		if (appeared == 0) {
			appeared = ((System.nanoTime() / 1000000000.0));
		}else {
			double currentTime = ((System.nanoTime() / 1000000000.0));
			if(keyH.enter == true && (currentTime - appeared) > 1) {
				System.out.println("Test");
				gp.gameState = gp.playState;
			}
		}
	}
}
