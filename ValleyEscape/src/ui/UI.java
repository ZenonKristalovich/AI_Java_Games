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
	BufferedImage dead;
	BufferedImage player;
	BufferedImage skeleton;
	
	//Constructor
	
	public UI(GamePanel gp) {
		this.gp =gp;
		
		try {
			dead = ImageIO.read(getClass().getResourceAsStream("/player/player_dead.png"));
			player = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
			skeleton = ImageIO.read(getClass().getResourceAsStream("/enemy/skeleton_left_1.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//METHODS
	
	public void drawTitleScreen(Graphics2D g2) {//Draw title Screen
		
		//BACKGROUND COLOR
		g2.setColor(new Color(70,120,80));
		g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
			
		//TITLE NAME
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,82F));
		String text = "Don't Get Caught!";
		
		int x = getXforCenteredText(g2,text);
		int y = gp.tileSize * 3;
		
		g2.setColor(Color.black);
		g2.drawString(text, x, y);
		
		//Draw Character Images
		
		g2.drawImage(player, 2*gp.tileSize , gp.screenHeight/2 - 2*gp.tileSize, gp.tileSize * 4, gp.tileSize * 4, null );
		g2.drawImage(skeleton, gp.screenWidth - 6*gp.tileSize , gp.screenHeight/2 - 2*gp.tileSize, gp.tileSize * 4, gp.tileSize * 4, null );
		
		//PUSH BUTTON TO START
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		text = "Push 'E' to Start Game" ;
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 10;
		g2.drawString(text, x, y);
		
	}
	
	public void drawDeadScreen(Graphics2D g2) {//Draw Death Screen
		
		//BACKGROUND COLOR
		g2.setColor(new Color(70,120,80));
		g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
			
		//TITLE NAME
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,82F));
		String text = "You have Died";
		
		int x = getXforCenteredText(g2,text);
		int y = gp.tileSize * 3;
		
		g2.setColor(Color.black);
		g2.drawString(text, x, y);
		
		//Draw Dead Image
		
		g2.drawImage(dead, gp.screenWidth/2 - 2*gp.tileSize , gp.screenHeight/2 - 2*gp.tileSize, gp.tileSize * 4, gp.tileSize * 4, null );
		
		//PUSH BUTTON TO START
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		text = "Push 'E' to Try Again" ;
		x = getXforCenteredText(g2,text);
		y = gp.tileSize * 10;
		g2.drawString(text, x, y);
		
	}
	
	
	
	public void charInfo(String level, String coins, String keys, Graphics2D g2) {//Display character info on screen
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,28F));
		g2.setColor(Color.black);
		int x = gp.tileSize/2;
		int y = gp.tileSize;
		g2.drawString("Level " + level, x , y);
		g2.drawString("Coins =  " + coins, x , y + gp.tileSize/2 );
		g2.drawString("Key = " + keys, x , y * 2);
	}
	
	
	public int getXforCenteredText(Graphics2D g2, String text) { //Find location for a text so its centered
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
}