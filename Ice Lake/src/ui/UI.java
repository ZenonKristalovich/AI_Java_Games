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
		g2.setColor(new Color(173,216,230));
		g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
			
		//TITLE NAME
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,82F));
		String text = "AI Ice Survival";
		
		int x = getXforCenteredText(g2,text);
		int y = gp.tileSize * 3;
		
		g2.setColor(new Color(0, 0, 139));
		g2.drawString(text, x, y);
		

		//PUSH BUTTON TO START
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		text = "Push 'E' to Start Game" ;
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 10;
		g2.drawString(text, x, y);
		
		
	}
	
	public void drawOverLay(Graphics2D g2, int round, double time) { //Displays info while AI is surviving
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));
		int elapsedTime = (int) ((System.nanoTime() / 1000000000.0) - time);

		String text = "Round: " + round + " Time Alive: " + elapsedTime;
		
		int x = gp.tileSize;
		int y = gp.tileSize;
		
		g2.setColor(Color.BLACK);
		g2.drawString(text, x, y);
	}
	
	
	public int getXforCenteredText(Graphics2D g2, String text) { //Find location for a text so its centered
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	
	
}