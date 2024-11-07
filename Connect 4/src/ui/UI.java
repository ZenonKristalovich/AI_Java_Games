/*Class: UI
 * Purpose: Handles the drawing of all overlay ui screens
 */


package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class UI {
	
	//Variables
	
	GamePanel gp;
	BufferedImage title;
	BufferedImage grid;
	
	//Constructor
	
	public UI(GamePanel gp) {
		this.gp =gp;
		
		try {
			this.title = ImageIO.read(getClass().getResourceAsStream("/maps/title.png"));
			this.grid = ImageIO.read(getClass().getResourceAsStream("/maps/grid.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//METHODS
	
	public void drawTitleScreen(Graphics2D g2) {//Draw title Screen
		
			
		//TITLE NAME
		g2.drawImage(title, 0, 0, gp.tileSize * gp.maxScreenCol, gp.tileSize * gp.maxScreenRow, null );
		
	}	
	
	public void drawEndScreen(Graphics2D g2, String text) {//Draw title Screen
			
		//Text Creation
		if(text != "Draw") {
			text = text + " Wins";
		}
		
		
		//TITLE NAME
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,82F));
		
		int x = getXforCenteredText(g2,text);
		int y = gp.tileSize * 1;
		
		g2.setColor(Color.black);
		g2.drawString(text, x, y);
		

		//PUSH BUTTON TO START
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		text = "Press E to go back to Main Menu" ;
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 2;
		g2.drawString(text, x, y);
		
	}	
	
	public void drawOverLay(Graphics2D g2) {//Draw title Screen
			
			//Info
		g2.drawImage(grid, 0, 0, gp.tileSize * gp.maxScreenCol, gp.tileSize * gp.maxScreenRow, null );

		}	
	
	public int getXforCenteredText(Graphics2D g2, String text) { //Find location for a text so its centered
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	
	
}