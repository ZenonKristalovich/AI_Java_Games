package tile;

import java.util.Random;

import main.GamePanel;

public class ItemSpawn {
	
	//Variables
	GamePanel gp;
	TileManager tileM;
	
	//Constructor
	public ItemSpawn(GamePanel gp, TileManager tileM) {
		this.gp = gp;
		this.tileM = tileM;
	}
	
	//Generate Objects Over the Map
	public void addObjects() {
		//Add Water Areas
		int waterTiles = 0;
		while (waterTiles < 10) {
			Random random = new Random();
			int posX = random.nextInt(32);
			int posY = random.nextInt(32);
			
			if( tileM.tile[tileM.mapTileNum[posX][posY]].spawnable ) {
				//Can Spawn Item here
				waterTiles += 1;
				tileM.mapTileNum[posX][posY] = 32;//make it water tile
			}
		}
		
		//Add Food Areas
		int foodTiles = 0;
		while (foodTiles < 10) {
			Random random = new Random();
			int posX = random.nextInt(32);
			int posY = random.nextInt(32);
			
			if( tileM.tile[tileM.mapTileNum[posX][posY]].spawnable ) {
				//Can Spawn Item here
				foodTiles += 1;
				tileM.mapTileNum[posX][posY] = 35;//make it food tile
			}
		}
		
		//Add Poison Tiles
		int poisonTiles = 0;
		while (poisonTiles < 3) {
			Random random = new Random();
			int posX = random.nextInt(32);
			int posY = random.nextInt(32);
			
			if( tileM.tile[tileM.mapTileNum[posX][posY]].spawnable ) {
				//Can Spawn Item here
				poisonTiles += 1;
				tileM.mapTileNum[posX][posY] = 33;//make it poison tile
			}
		}
	}
	

}
