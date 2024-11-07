/*Class: TileManager
 * Purpose: Keeps all the tiles, and handles map details
 */


package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	//Variables
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	Random rand = new Random();
	int frame = 0;
	
	//Constructor
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[32];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/map01.txt");
	}
	
	//METHODS
	
	public void getTileImage() {//Creates all the tiles to be used
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[0].collision = true;
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ice.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ice_crack_1.png"));
			tile[2].crack_level = 1;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ice_crack_2.png"));
			tile[3].crack_level = 2;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ice_crack_3.png"));
			tile[4].crack_level = 3;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[5].death = true;
			
			

		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadMap(String map) {//Loads the map from a text file
		try {
			InputStream is= getClass().getResourceAsStream(map);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
				
			}
			br.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	public void update() { //updates the tiles for cracking
		
		frame += 1;
		if (frame != 3) {
			return;
		}else {
			frame  = 0;
		}
		
		for(int i = 0; i < mapTileNum.length; i++) {
			for(int j = 0; j < mapTileNum[i].length; j++) {
				int tile = mapTileNum[i][j];
				
				if (tile == 0 || tile == 5) {
					continue;
				}else {
					int randomNumber = rand.nextInt(10) + 1;
					if (randomNumber == 5) {
						mapTileNum[i][j] = tile + 1;
					}
				}
				
			}
		}
	}
	
	
	public void draw(Graphics2D g2) {//Draws the map, but only what is on the screen
		
		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			
			g2.drawImage(tile[tileNum].image, worldX , worldY , gp.tileSize, gp.tileSize, null);

			
			worldCol++;

			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;

				worldRow++;

			}
		}
		
	}
	
}
