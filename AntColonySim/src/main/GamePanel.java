/*Class: GamePanel
 * Purpose: Controls the overall flow of the game. Handles calls between classes to make game functional
 */


package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import tile.ItemSpawn;
import tile.TileManager;
import ui.GameOver;
import ui.TitleScreen;
import ui.UI;
import moving.Player;
import search.AStarSearch;
import search.Node;

public class GamePanel extends JPanel implements Runnable{

	// SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 tile
	final int scale = 2;
	
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 32;
	public final int maxScreenRow = 32;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol = 32;
	public final int maxWorldRow = 32;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// GAME STATE
	
	public int gameState;
	public final int titleState = 0;
	public final int simulationState = 1;
	public final int gameOverState = 2;
	

	//FPS
	int FPS = 60;
	int currentFrame = 0;
	
	//Objects
	
	public TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public UI ui = new UI(this);
	ArrayList<Player> players = new ArrayList<Player>();
	ItemSpawn spawner = new ItemSpawn(this, tileM);
	
	List<Node> path;
	
	//Basic
	
	public int ants = 0;
	
	//locations
	public int home[] = new int[] {13,13};
	public int startLocations[][] = new int[][] { {13,14},{13,12},{12,13},{14,13} };
	
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
		players.clear();
		tileM.loadMap("/maps/map01.txt");
		
		gameState = simulationState;
		
		//Create the Ants
		for(int i = 0; i < ants; i++){
			players.add(new Player(this));
			players.get(i).setDefaultValues(startLocations[i][0], startLocations[i][1]);
		}
		
		//Spawn items
		spawner.addObjects();
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
		}else if(gameState == simulationState) {
			
			if(keyH.upPressed) {
				gameState = gameOverState;
			}
			
			//Update Ants
			for(int i = 0; i < ants; i++){
				int outcome = players.get(i).update();
				if (outcome == -1) {
					players.remove(i);
					ants -= 1;
					if (ants <= 0) {
						gameState = gameOverState;
					}
				}else if( outcome == 1) {
					players.add(new Player(this));
					players.get(ants).setDefaultValues(startLocations[1][0], startLocations[1][1]);
					ants += 1;
				}
			}
		}else if(gameState == gameOverState) {
			gameOver.update();
		}
	}
	
	public void paintComponent(Graphics g) {//Draws the screen
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		if(gameState == titleState) {
			ui.drawTitleScreen(g2);
		}else if (gameState == simulationState){
			//Tile
			tileM.draw(g2);
			
			//Draw Ants
			for(int i = 0; i < ants; i++){
				players.get(i).draw(g2);
			}
			//Draw overlay
			ui.drawOverLay(g2);
		}else if (gameState == gameOverState) {
			ui.drawEndScreen(g2);
		}
		
		g2.dispose();
	}
	
	
}