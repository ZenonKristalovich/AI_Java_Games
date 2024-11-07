/*Class: UI
 * Purpose: Handles the drawing of all overlay ui screens
 */


package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


import main.GamePanel;

public class UI {
	
	//Variables
	
	GamePanel gp;
	
	//Constructor
	
	public UI(GamePanel gp) {
		this.gp =gp;
	}
	
	//METHODS
	
	public void drawTitleScreen(Graphics2D g2) {//Draw title Screen
		
		//BACKGROUND COLOR
		g2.setColor(new Color(70,120,80));
		g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
			
		//TITLE NAME
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,82F));
		String text = "Ant Colony";
		
		int x = getXforCenteredText(g2,text);
		int y = gp.tileSize * 3;
		
		g2.setColor(Color.black);
		g2.drawString(text, x, y);
		

		//PUSH BUTTON TO START
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		text = "Push '1-4' to Select Starting Ants" ;
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 20;
		g2.drawString(text, x, y);
		
	}	
	
	public void drawEndScreen(Graphics2D g2) {//Draw title Screen
		
		//BACKGROUND COLOR
		g2.setColor(new Color(70,120,80));
		g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
			
		//TITLE NAME
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,82F));
		String text = "GameOver";
		
		int x = getXforCenteredText(g2,text);
		int y = gp.tileSize * 3;
		
		g2.setColor(Color.black);
		g2.drawString(text, x, y);
		

		//PUSH BUTTON TO START
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		text = "Press E to go back to Main Menu" ;
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 10;
		g2.drawString(text, x, y);
		
	}	
	
	public void drawOverLay(Graphics2D g2) {//Draw title Screen
			
			//Info
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
			String text = "Press W To End Simulation";
			
			int x = gp.tileSize * 2;
			int y = gp.tileSize * 2;
			
			g2.setColor(Color.black);
			g2.drawString(text, x, y);
			
			//Display Ant Amount
			text = "Ants = " + gp.ants;
			
			x = gp.tileSize * 2;
			y = gp.tileSize * 3;
			
			g2.setColor(Color.black);
			g2.drawString(text, x, y);
			
			
		}	
	
	public int getXforCenteredText(Graphics2D g2, String text) { //Find location for a text so its centered
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	
	
}