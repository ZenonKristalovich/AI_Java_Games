package tile;

import main.GamePanel;
import main.KeyHandler;
import moving.SelectionSquare;

public class TileEditor {
	
	//Variables
	GamePanel gp;
	TileManager tileM;
	KeyHandler keyH;
	SelectionSquare cursor;
	public double appeared = 0;
	
	public int home [] = {0,0};
	public int goal [] = {15,15};
	
	//Constructor
	public TileEditor(GamePanel gp, TileManager tileM, KeyHandler keyH,SelectionSquare cursor) {
		this.gp = gp;
		this.tileM = tileM;
		this.keyH = keyH;
		this.cursor = cursor;
		
		tileM.mapTileNum[home[0]][home[1]] = 30;
		tileM.mapTileNum[goal[0]][goal[1]] = 27;
		
	}
	
	//METHODS
	
	public void update() {//Edits The map base on what is pressed
		
		if (appeared == 0) {
			appeared = ((System.nanoTime() / 1000000000.0));
		}
		
		boolean notHome = true;
		boolean notGoal = true;
		//Check if Position is a Home or Goal Position
		if ( (cursor.worldX/gp.tileSize == home[0]) && (cursor.worldY/gp.tileSize == home[1]) ) {
			notHome = false;
		}
		
		if( (cursor.worldX/gp.tileSize == goal[0]) && (cursor.worldY/gp.tileSize == goal[1]) ) {
			notGoal = false;
		}
		
		
		if (keyH.onePressed && notHome && notGoal) {
			tileM.mapTileNum[cursor.worldX/gp.tileSize][cursor.worldY/gp.tileSize] = 1;//Set open terrain
		}
		else if(keyH.twoPressed  && notHome && notGoal) {
			tileM.mapTileNum[cursor.worldX/gp.tileSize][cursor.worldY/gp.tileSize] = 0;//Set Grass land
		}
		else if(keyH.threePressed && notHome && notGoal) {
			tileM.mapTileNum[cursor.worldX/gp.tileSize][cursor.worldY/gp.tileSize] = 2;//Set Swamp land
		}
		else if(keyH.fourPressed  && notHome && notGoal) {
			tileM.mapTileNum[cursor.worldX/gp.tileSize][cursor.worldY/gp.tileSize] = 31;//Set Obstacle
		}
		else if(keyH.fivePressed && notGoal) {
			tileM.mapTileNum[home[0]][home[1]] = 1; //remove old Start
			tileM.mapTileNum[cursor.worldX/gp.tileSize][cursor.worldY/gp.tileSize] = 30;//Set Start
			home[0] = cursor.worldX/gp.tileSize;
			home[1] = cursor.worldY/gp.tileSize;
		}
		else if(keyH.sixPressed && notHome) {
			tileM.mapTileNum[goal[0]][goal[1]] = 1;// Remove old end
			tileM.mapTileNum[cursor.worldX/gp.tileSize][cursor.worldY/gp.tileSize] = 27;//Set End
			goal[0] = cursor.worldX/gp.tileSize;
			goal[1] = cursor.worldY/gp.tileSize;
		}
		else if(keyH.enter) {
			double currentTime = ((System.nanoTime() / 1000000000.0));
			if(keyH.enter == true && (currentTime - appeared) > 2) {
				gp.gameState = gp.findPathState;
			}
		}
	}
	
}
