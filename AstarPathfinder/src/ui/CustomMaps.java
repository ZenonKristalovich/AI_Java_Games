/*Class: Custom Maps
 * Purpose: Screen controls for selecting a custom Map
 */

package ui;

import main.GamePanel;
import main.KeyHandler;

public class CustomMaps {
	//Variables
	
	GamePanel gp;
	KeyHandler keyH;
	public double appeared = 0;
	
	//Constructor
	
	public CustomMaps(GamePanel gp, KeyHandler keyH){
		this.gp = gp;
		this.keyH = keyH;
	}
	
	//METHODS
	
	public void update() {//Checks to see when a player wants to start the game
		if (appeared == 0) {
			appeared = ((System.nanoTime() / 1000000000.0));
		}else {
			double currentTime = ((System.nanoTime() / 1000000000.0));
			if(keyH.onePressed == true && (currentTime - appeared) > 2) {
				gp.tileM.loadMap("/maps/map02.txt");
				gp.edit.home = new int[] {1,1};
				gp.edit.goal = new int[] {8,9};
				gp.gameState = gp.findPathState;
			}else if(keyH.twoPressed) {
				gp.tileM.loadMap("/maps/map03.txt");
				gp.edit.home = new int[] {2,7};
				gp.edit.goal = new int[] {13,7};
				gp.gameState = gp.findPathState;
			}else if(keyH.threePressed) {
				gp.tileM.loadMap("/maps/map04.txt");
				gp.edit.home = new int[] {3,3};
				gp.edit.goal = new int[] {10,10};
				gp.gameState = gp.findPathState;
			}else if(keyH.fourPressed) {
				gp.tileM.loadMap("/maps/map05.txt");
				gp.edit.home = new int[] {1,1};
				gp.edit.goal = new int[] {8,9};
				gp.gameState = gp.findPathState;
			}
		}
	}
}
