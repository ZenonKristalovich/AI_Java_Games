/*Class: GamePanel
 * Purpose: Controls the overall flow of the game. Handles calls between classes to make game functional
 */


package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Enemy;
import entity.Formation;
import entity.Player;
import object.SuperObject;
import tile.TileManager;
import ui.Death;
import ui.PlayerDisplay;
import ui.TitleScreen;
import ui.UI;

public class GamePanel extends JPanel implements Runnable{

	// SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 tile
	final int scale = 4;
	
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// GAME STATE
	
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int deadState = 2;
	
	public boolean enemyGroup = false;
	
	public int currentLevel = 0;
	
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public Player player = new Player(this, keyH );
	public Enemy enemy [];
	public SuperObject obj[] = new SuperObject[10];
	public UI ui = new UI(this);
	public Formation triangleForm;
	
	//Screens
	public TitleScreen title = new TitleScreen(this, keyH);
	public Death playerDeath = new Death(this,keyH);
	public PlayerDisplay display = new PlayerDisplay(player, this, ui );
	
	//Constructor
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		this.enemy = new Enemy[] {new Enemy(this,player),new Enemy(this,player),new Enemy(this,player)};
	}
	
	//METHODS
	
	public void setupGame() {//Sets up basic game needs
		gameState = titleState;
		
		enemy[0].originX = 23 * tileSize;
		enemy[0].originY = 25 * tileSize;
		enemy[1].originX = 18 * tileSize;
		enemy[1].originY = 12 * tileSize;
		enemy[2].originX = 31 * tileSize;
		enemy[2].originY = 34 * tileSize;
		
		triangleForm = new Formation(this,player, enemy);
	}
	
	public void levelSetUp() {//Sets up a level, and level progression
		enemyGroup = false;
		aSetter.setObjects();
		currentLevel++;
		
		enemy[0].worldX = 23 * tileSize;
		enemy[0].worldY = 25 * tileSize;
		enemy[1].worldX = 18 * tileSize;
		enemy[1].worldY = 12 * tileSize;
		enemy[2].worldX = 31 * tileSize;
		enemy[2].worldY = 34 * tileSize;
	}
	
	public void gameRestart() {//Restarts the overall game
		currentLevel = 0;
		player.coins = 0;
		player.key = 0;
		player.setDefaultValues();
		levelSetUp();
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
		
		if(gameState == titleState) {
			title.update();
		}if(gameState == deadState) {
			playerDeath.update();
		}else {
			player.update();
			
			if(enemyGroup) {
				triangleForm.Update();
			}else
				for(int i =0; i < enemy.length;i++) {
					enemy[i].update();
			}
		}
	
	}
	
	public void paintComponent(Graphics g) {//Draws the screen
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		
		if(gameState == titleState) {
			ui.drawTitleScreen(g2);
		}else if(gameState == deadState){
			ui.drawDeadScreen(g2);
		}else {
			//Tile
			tileM.draw(g2);
			
			//Object
			for(int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					obj[i].draw(g2,  this);
				}
			}
			
			//Player
			player.draw(g2);
			
			//Enemy
			for(int i =0; i < enemy.length;i++) {
				enemy[i].draw(g2);
			}
			
			//Draw Display
			display.draw(g2);
		}
		
		g2.dispose();
	}
	
	
}
