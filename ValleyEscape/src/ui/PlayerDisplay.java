/*Class: PlayerDisplay
 * Purpose: Displays info on the screen showing player info
 */


package ui;

import java.awt.Graphics2D;

import entity.Player;
import main.GamePanel;

public class PlayerDisplay {
	
	//Variable
	
	Player player;
	GamePanel gp;
	UI ui;
	
	//Constructor
	
	public PlayerDisplay(Player player, GamePanel gp, UI ui) {
		this.player = player;
		this.gp = gp;
		this.ui = ui;
	}
	
	//METHODS
	
	public void draw(Graphics2D g2) {//Draws on the screen
		String level = "" + gp.currentLevel;
		String coins = "" + player.coins;
		String keys = "" + player.key;
		
		ui.charInfo(level, coins, keys, g2);
	}
}
