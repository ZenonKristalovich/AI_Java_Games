/*Class: DrawPath
 * Purpose: Handles Drawing paths the player makes
 */

package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import main.GamePanel;
import search.Node;

public class DrawPath {
	//Variables
	
	GamePanel gp;
	public int pos = 0;
	public int timer = 0;
	
	//Constructor
	
	public DrawPath(GamePanel gp){
		this.gp = gp;
	}
	
	//METHODS
	
	public void draw(Graphics2D g2,List<Node> path, int frame){//Draws the total area the path finder looks at

		if (frame == 1) { pos += 1;}
		if (pos >= path.size()) { 
			pos = path.size(); 
			timer += 1;
			}
		
		for (int i = 0; i < pos; i++) {
		    Node node = path.get(i);
		    g2.setColor(Color.RED);
			g2.fillRect(node.x*gp.tileSize, node.y*gp.tileSize, gp.tileSize, gp.tileSize);
		}
		
		if (timer == 15) {
			if (gp.completed == "Search Complete") {
				gp.gameState = gp.drawFinalPathState;
			}else {
				gp.gameState =gp.completeState;
			}
		}
		
	}
	
	public void drawFinal(Graphics2D g2,List<Node> path, Color color) {//Draws the final path, the path the character will take
		for (int i = 0; i < path.size(); i++) {
		    Node node = path.get(i);
		    g2.setColor(color);
			g2.fillRect(node.x*gp.tileSize, node.y*gp.tileSize, gp.tileSize, gp.tileSize);
		}
	}
}
