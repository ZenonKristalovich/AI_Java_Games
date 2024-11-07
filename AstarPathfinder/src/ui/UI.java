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
		String text = "A* PathFinding";
		
		int x = getXforCenteredText(g2,text);
		int y = gp.tileSize * 3;
		
		g2.setColor(Color.black);
		g2.drawString(text, x, y);
		

		//PUSH BUTTON TO START
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		text = "Push 'E' to Start Game" ;
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 10;
		g2.drawString(text, x, y);
		
		text = "Push '1' to select a Custom Map" ;
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 12;
		g2.drawString(text, x, y);
		
	}
	
	public void drawMapsScreen(Graphics2D g2) {//Draw Custom Maps Screen
			
			//BACKGROUND COLOR
			g2.setColor(new Color(70,120,80));
			g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
				
			//TITLE NAME
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,82F));
			String text = "Custom Maps";
			
			int x = getXforCenteredText(g2,text);
			int y = gp.tileSize * 3;
			
			g2.setColor(Color.black);
			g2.drawString(text, x, y);
			
			//maps Options
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
			
			text = "Press 1 for Maze Map";
			x = getXforCenteredText(g2,text);
			y = gp.tileSize * 5;
			g2.drawString(text, x, y);
			
			text = "Press 2 for GrassVsTerrain Map";
			x = getXforCenteredText(g2,text);
			y = gp.tileSize * 6;
			g2.drawString(text, x, y);
			
			text = "Press 3 for No Way Out Map";
			x = getXforCenteredText(g2,text);
			y = gp.tileSize * 7;
			g2.drawString(text, x, y);
			
			text = "Press 4 for Maze Map V2";
			x = getXforCenteredText(g2,text);
			y = gp.tileSize * 8;
			g2.drawString(text, x, y);
			
		}
	
	public void drawRuleScreen(Graphics2D g2) {//Draw Rules Screen
			
		//BACKGROUND COLOR
		g2.setColor(new Color(70,120,80));
		g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
			
		//Rules
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));
		String text = "How To Play";
		
		int x = getXforCenteredText(g2,text);
		int y = gp.tileSize * 3;
		
		g2.setColor(Color.black);
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));

		text = "Press 1 to set Open Terrain";
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 5;
		g2.drawString(text, x, y);
		
		text = "Press 2 to set Grassland";
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 6;
		g2.drawString(text, x, y);
		
		text = "Press 3 to set SwampLand";
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 7;
		g2.drawString(text, x, y);
		
		text = "Press 4 to set Obstacle";
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 8;
		g2.drawString(text, x, y);
		
		text = "Press 5 to set Home";
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 9;
		g2.drawString(text, x, y);
		
		text = "Press 6 to set Goal";
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 10;
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));
		
		text = "Press E to Start";
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 13;
		g2.drawString(text, x, y);
		
	}
	
	public void drawComplete(Graphics2D g2, String text, double timeTook) {//Draw the after path find screen, displaying info about the search
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));
		g2.setColor(Color.black);
		
		int x = getXforCenteredText(g2,text);
		int y = gp.tileSize * 3;
		
		g2.drawString(text, x, y);
				
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		text = "Calculation Time = " + Double.parseDouble(String.format("%.3f", timeTook)) + " seconds";
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 6;
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));
		text = "Press E to Return to Menu";
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 10;
		g2.drawString(text, x, y);
		
		
	}
	
	
	public int getXforCenteredText(Graphics2D g2, String text) { //Find location for a text so its centered
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	
	
}