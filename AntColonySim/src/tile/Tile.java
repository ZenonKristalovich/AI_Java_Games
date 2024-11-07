/*Class: Tile
 * Purpose: Basic Tile information
 */


package tile;

import java.awt.image.BufferedImage;

public class Tile {
	
	//Variables
	
	public BufferedImage image;
	public  boolean collision = false;
	public int cost = 1;
	public boolean food = false;
	public boolean water = false;
	public boolean poison = false;
	public boolean spawnable = false;
	
}
