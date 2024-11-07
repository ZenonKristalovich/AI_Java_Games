/*Class: GamePanel
 * Purpose: Controls the overall flow of the game. Handles calls between classes to make game functional
 */


package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import moving.AIToken;
import moving.FallingToken;
import moving.PlayerToken;
import tile.TileManager;
import ui.GameOver;
import ui.TitleScreen;
import ui.UI;


public class GamePanel extends JPanel implements Runnable{

	// SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 tile
	final int scale = 5;
	
	public final int tileSize = originalTileSize * scale; //80x80 tile
	public final int maxScreenCol = 11;
	public final int maxScreenRow = 10;
	public final int screenWidth = tileSize * maxScreenCol; // 880
	public final int screenHeight = tileSize * maxScreenRow; // 800
	
	// WORLD SETTINGS
	public final int maxWorldCol = 11;
	public final int maxWorldRow = 10;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// GAME STATE
	
	public int gameState;
	public final int titleState = 0;
	public final int playerState = 1;
	public final int botState = 2;
	public final int outcomeState = 3;
	public final int fallState = 4;
	public String whosTurn = "";
	String winner = "";
	

	//FPS
	int FPS = 60;
	int currentFrame = 0;
	
	//Objects
	
	public TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public UI ui = new UI(this);
	public Board board = new Board(this);
	public PlayerToken player;
	public AIToken ai;
	public FallingToken falling = new FallingToken(this);
	int tokens = 0;
	
	//Screens
	public TitleScreen title = new TitleScreen(this, keyH);
	public GameOver gameOver = new GameOver(this,keyH);
	
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
		tileM.loadMap("/maps/map01.txt");
		player = new PlayerToken(this, keyH);
		ai = new AIToken(this);
		board.cleanBoard();
		tokens =0;

	}
	
	public void newRound() { //Called after both ai and player play one token
		player = new PlayerToken(this, keyH);
		ai = new AIToken(this);
		whosTurn = "You";
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
	
	public void checkWin(int token ) { //Checks win
		gameState = outcomeState;
		if (token == 0) {
			winner = "You";
		}else {
			winner = "Bot";
		}
	}
	
	public void update() {//Calls all of the updates needed among all classes
		
		currentFrame = (currentFrame + 1)%60;
		
		if(gameState == titleState) {
			title.update();
		}else if(gameState == playerState) {
			player.update();
		}else if(gameState == fallState) {
			falling.update();
		}else if(gameState == botState) {
			ai.update();
		}else if(gameState == outcomeState) {
			gameOver.update();
		}
		
	}
	
	public void paintComponent(Graphics g) {//Draws the screen
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		if(gameState == titleState) {
			ui.drawTitleScreen(g2);
		}else if (gameState == playerState){
			
			//BACKGROUND COLOR
			g2.setColor(new Color(192, 192, 192));
			g2.fillRect(0,0,screenWidth, screenHeight);
				
			//Draw Tokens
			player.draw(g2);
			tileM.draw(g2);
			
			//Draw Grid
			ui.drawOverLay(g2);

		}else if (gameState == fallState) {
			
			//BACKGROUND COLOR
			g2.setColor(new Color(192, 192, 192));
			g2.fillRect(0,0,screenWidth, screenHeight);
				
			//Draw Tokens
			falling.draw(g2);
			tileM.draw(g2);
			
			//Draw Grid
			ui.drawOverLay(g2);
		}else if (gameState == botState) {
			
			//BACKGROUND COLOR
			g2.setColor(new Color(192, 192, 192));
			g2.fillRect(0,0,screenWidth, screenHeight);
				
			//Draw Tokens
			tileM.draw(g2);
			
			//Draw Grid
			ui.drawOverLay(g2);
		}else if(gameState == outcomeState){
			//BACKGROUND COLOR
			g2.setColor(new Color(192, 192, 192));
			g2.fillRect(0,0,screenWidth, screenHeight);
				
			//Draw Tokens
			tileM.draw(g2);
			
			//Draw Grid
			ui.drawOverLay(g2);
			
			ui.drawEndScreen(g2, winner);
		}
		
		g2.dispose();
	}
	
	
}