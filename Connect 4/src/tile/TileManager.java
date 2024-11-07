/*Class: TileManager
 * Purpose: Keeps all the tiles, and handles map details
 */


package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	//Variables
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	//Constructor
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[36];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/map01.txt");
	}
	
	//METHODS
	
	public void getTileImage() {//Creates all the tiles to be used
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/player/empty.png"));
			tile[0].collision = true;

			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/player/yellowToken.png"));

			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/player/redToken.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/player/empty.png"));
			
			

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
	
	
	public void draw(Graphics2D g2) {//Draws the map, but only what is on the screen
		
		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			
			g2.drawImage(tile[tileNum].image, worldX, worldY, gp.tileSize, gp.tileSize, null);

			
			worldCol++;

			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;

				worldRow++;

			}
		}
		
	}
	
}
