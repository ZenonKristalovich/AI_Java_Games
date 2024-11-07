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
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			tile[0].spawnable = true;

			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));

			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tall_grass.png"));

			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_left.png"));
			tile[3].collision = true;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_bottom.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_right.png"));
			tile[5].collision = true;
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_top.png"));
			tile[6].collision = true;
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt_top.png"));
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt_bottom.png"));
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt_right.png"));
			
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt_left.png"));
			
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_bottom_left.png"));
			
			tile[12] = new Tile();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_bottom_right.png"));
			
			tile[13] = new Tile();
			tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_top_right.png"));
			
			tile[14] = new Tile();
			tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_top_left.png"));
			
			tile[15] = new Tile();
			tile[15].image = ImageIO.read(getClass().getResourceAsStream("/tiles/corner_bottom_right.png"));
			
			tile[16] = new Tile();
			tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tiles/corner_bottom_left.png"));
			
			tile[17] = new Tile();
			tile[17].image = ImageIO.read(getClass().getResourceAsStream("/tiles/corner_top_left.png"));
			
			tile[18] = new Tile();
			tile[18].image = ImageIO.read(getClass().getResourceAsStream("/tiles/corner_top_right.png"));
			
			tile[19] = new Tile();
			tile[19].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_top_right.png"));
			tile[19].collision = true;
			
			tile[20] = new Tile();
			tile[20].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_corner.png"));
			tile[20].collision = true;
			
			tile[21] = new Tile();
			tile[21].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_corner_top_right.png"));
			tile[21].collision = true;
			
			tile[22] = new Tile();
			tile[22].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_corner_top_left.png"));
			tile[22].collision = true;
			
			tile[23] = new Tile();
			tile[23].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_corner_bottom_right.png"));
			tile[23].collision = true;
			
			tile[24] = new Tile();
			tile[24].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_corner_bottom_left.png"));
			tile[24].collision = true;
			
			tile[25] = new Tile();
			tile[25].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_v1.png"));
			
			tile[26] = new Tile();
			tile[26].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_v2.png"));
			
			tile[27] = new Tile();
			tile[27].image = ImageIO.read(getClass().getResourceAsStream("/tiles/flower_v1.png"));
			
			tile[28] = new Tile();
			tile[28].image = ImageIO.read(getClass().getResourceAsStream("/tiles/flower_v2.png"));
			
			tile[29] = new Tile();
			tile[29].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt_v1.png"));
			
			tile[30] = new Tile();
			tile[30].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt_v2.png"));
			
			tile[31] = new Tile();
			tile[31].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rock.png"));
			tile[31].collision = true;
			
			tile[32] = new Tile();
			tile[32].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[32].water = true;
			
			tile[33] = new Tile();
			tile[33].image = ImageIO.read(getClass().getResourceAsStream("/tiles/poison.png"));
			tile[33].poison = true;
			
			tile[34] = new Tile();
			tile[34].image = ImageIO.read(getClass().getResourceAsStream("/tiles/home.png"));
			
			tile[35] = new Tile();
			tile[35].image = ImageIO.read(getClass().getResourceAsStream("/tiles/food.png"));
			tile[35].food = true;
			
			

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
