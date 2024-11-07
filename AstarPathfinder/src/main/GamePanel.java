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

import tile.TileEditor;
import tile.TileManager;
import ui.CustomMaps;
import ui.DrawPath;
import ui.RuleScreen;
import ui.TitleScreen;
import ui.UI;
import moving.Player;
import moving.SelectionSquare;
import search.AStarSearch;
import search.Node;

public class GamePanel extends JPanel implements Runnable{

	// SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 16;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol = 16;
	public final int maxWorldRow = 16;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// GAME STATE
	
	public int gameState;
	public final int titleState = 0;
	public final int rulesState = 1;
	public final int createState = 2;
	public final int findPathState = 3;
	public final int drawPathState = 4;
	public final int drawFinalPathState = 5;
	public final int playerWalkState = 6;
	public final int completeState = 7;
	public final int selectState = 8;

	//FPS
	int FPS = 60;
	int currentFrame = 0;
	
	//Objects
	
	public TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public UI ui = new UI(this);
	public DrawPath drawPath = new DrawPath(this);
	SelectionSquare cursor = new SelectionSquare(this, keyH);
	public TileEditor edit = new TileEditor(this, tileM, keyH, cursor);
	AStarSearch search = new AStarSearch();
	Player player = new Player(this, keyH);
	
	List<Node> path;
	
	//Basic
	
	public String completed = "";
	
	//Screens
	public TitleScreen title = new TitleScreen(this, keyH);
	public RuleScreen rules = new RuleScreen(this, keyH);
	public CustomMaps maps = new CustomMaps(this, keyH);
	
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
		completed = "";
		title.appeared = 0;
		rules.appeared = 0;
		maps.appeared = 0;
		edit.appeared = 0;
		drawPath.pos = 0;
		player.pos =0;
		drawPath.timer = 0;
		search.viewed = new ArrayList<>();
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
		}else if(gameState == rulesState) {
			rules.update();
		}else if(gameState == createState) {
			cursor.update();
			edit.update();
		}else if(gameState == findPathState) {
			Node start = new Node(edit.home[0],edit.home[1]);
			Node goal = new Node(edit.goal[0],edit.goal[1]);
			path = search.findPath(tileM, start, goal);
			if (path.size() <= 0) {
				completed = "No Path Found";
				gameState = drawPathState;
				player.setDefaultValues(edit.home[0], edit.home[1], path);
			}else {
				completed = "Search Complete";
				gameState = drawPathState;
			}
		}else if (gameState == drawFinalPathState) {
			try {
			    Thread.sleep(1000);
			    gameState = playerWalkState;
			    player.setDefaultValues(edit.home[0], edit.home[1], path);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		}else if (gameState == playerWalkState) {
			player.update();
		}else if (gameState == completeState) {
			if (keyH.enter) {
				setupGame();
			}
		}else if (gameState == selectState) {
			maps.update();
		}
	}
	
	public void paintComponent(Graphics g) {//Draws the screen
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		if(gameState == titleState) {
			ui.drawTitleScreen(g2);
		}else if(gameState == rulesState) {
			ui.drawRuleScreen(g2);
		}else if(gameState == selectState) {
			ui.drawMapsScreen(g2);
		}else {
			//Tile
			tileM.draw(g2);
			
			//Draw Square
			if(gameState == createState ) {
				cursor.draw(g2);
			}
			
			//Draw the path
			if(gameState == drawPathState ) {
				drawPath.draw(g2, search.viewed, currentFrame%5);
			}
			
			//Draw OverAll Path
			if(gameState == drawFinalPathState) {
				drawPath.drawFinal(g2, search.viewed, Color.RED);
				drawPath.drawFinal(g2, path, Color.BLUE);
			}
			
			//Draw Player
			if(gameState == playerWalkState) {
				player.draw(g2);
			}
			
			//Complete State
			if(gameState == completeState) {
				player.draw(g2);
				ui.drawComplete(g2, completed, search.timeTook);
			}
			
			
		}
		
		g2.dispose();
	}
	
	
}