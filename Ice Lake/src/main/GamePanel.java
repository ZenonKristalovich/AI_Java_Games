/*Class: GamePanel
 * Purpose: Controls the overall flow of the game. Handles calls between classes to make game functional
 */


package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import tile.TileManager;
import ui.TitleScreen;
import ui.UI;
import moving.Player;


public class GamePanel extends JPanel implements Runnable{

	// SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 18;
	public final int maxScreenRow = 18;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol = 18;
	public final int maxWorldRow = 18;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// GAME STATE
	
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	
	public int round = 0;
	
	//FPS
	int FPS = 60;
	int currentFrame = 0;
	
	//Objects
	
	public TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public UI ui = new UI(this);
	Player player = new Player(this, keyH);
	
	//Basic
	
	//Screens
	public TitleScreen title = new TitleScreen(this, keyH);
	
	//Constructor
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	//METHODS
	
	public void setupGame() {//Sets up basic game needs
		gameState = titleState;
		title.appeared = 0;
		player.setDefaultValues(7,8);
		round += 1;
	}
	
	public void newRound() { //Sets Values For a Basic Round
		player.setDefaultValues(7,8);
		round += 1;
		tileM.loadMap("/maps/map01.txt");
	}
	

	public void startGameThread() {//used for creating a thread to run the game
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	
	@Override
	public void run() {//Constantly is run, sets the game FPS to 60 and calls updates every frame
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		double lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}

	}
	
	public void update() {//Calls all of the updates needed among all classes
		
		currentFrame = (currentFrame + 1)%60;
		
		if(gameState == titleState) {
			title.update();
		}else if (gameState == playState) {
			if(keyH.upPressed == true) {
				gameState = titleState;
				return;
			}
			keyH.enter = false;
			player.update();
			if (currentFrame == 30) {
				tileM.update();
			}
		}
		
	}
	
	public void paintComponent(Graphics g) {//Draws the screen
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		if(gameState == titleState) {
			ui.drawTitleScreen(g2);
		}else if(gameState == playState) {
			tileM.draw(g2);
			player.draw(g2);
			ui.drawOverLay(g2, round, player.startTime);
		}
	
		
		g2.dispose();
	}
	
	
}