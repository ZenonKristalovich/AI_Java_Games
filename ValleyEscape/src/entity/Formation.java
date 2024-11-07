/*Class: Formation
 * Purpose: Handles calls of AI movement in a formation
 */

package entity;

import main.GamePanel;

public class Formation {
	
	//Variables
	
	public Enemy enemies[] = new Enemy[3];
	public GamePanel gp;
	public Player player;
	private int leader = 10000;
	private float leaderDistance = 10000;
	public AIMovement ai;
	public int baseSpeed;
	
	//Constructor
	
	public Formation(GamePanel gp, Player player,Enemy[] enemy) {
		this.gp = gp;
		this.player = player;
		this.enemies = enemy;
		this.ai = new AIMovement(enemies[0].speed,gp);
		this.baseSpeed = enemy[0].speed;
	}
	
	//METHODS
	
	public void Update() { //Handles what the formation does every frame
		
		//Select Leader Enemy, the one closest to the character
		
		for(int i = 0; i < enemies.length; i++) {
			float distance = this.getDistance(enemies[i]);
			if(distance < 0.5) {
				gp.gameState = gp.deadState;
				return;
			}
			if(distance < leaderDistance) {
				leader = i;
				leaderDistance = distance;			
			}
		}
		
		//Set Speeds
		enemies[leader].speed = baseSpeed;
		enemies[(leader + 1)%enemies.length].speed = baseSpeed + 10;
		enemies[(leader + 2)%enemies.length].speed = baseSpeed + 10;
		
		// First Attempt to Move Leader
		int playerLocation[] = new int[] {player.worldX,player.worldY};
		this.enemyToPosition( playerLocation ,enemies[leader]);
		
		//Get Normalization
		double directionVector[] = this.normalize(player.worldX,player.worldY, enemies[leader].worldX, enemies[leader].worldY);
		double[] perpendicularVector = {-directionVector[1], directionVector[0]};
		int separation = gp.tileSize * 2;
		
		
		//Get Goon positions, the enemies that stand behind the leader
		int[] goon1 = {enemies[leader].worldX + (int) (perpendicularVector[0] * separation), enemies[leader].worldY + (int) (perpendicularVector[1] * separation)};
        int[] goon2 = {enemies[leader].worldX - (int) (perpendicularVector[0] * separation), enemies[leader].worldY - (int) (perpendicularVector[1] * separation)};
        
        //Make Goons move to their position
        this.enemyToPosition(goon1,enemies[ (leader +1)%enemies.length ]);
        this.enemyToPosition(goon2,enemies[ (leader +2)%enemies.length ]);
	}
	
	private float getDistance(Enemy enemy) {//Calculates the distance from the enmy to the player
		
		int x = player.worldX - enemy.worldX;
		int y = player.worldY - enemy.worldY;
		
		float distance = ( (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))) / gp.tileSize;
		
		return distance;
	}
	
	private double[] normalize(int px, int py, int ex, int ey) {//Get the normalization between player and enemy
			
		// Calculate the direction vectors
        double dx = px - ex;
        double dy = py - ey;

        // Calculate the distance between enemy and player
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Normalize the direction vector
        double normalizedDx = dx / distance;
        double normalizedDy = dy / distance;
        
        return (new double[] {normalizedDx, normalizedDy});
	}
	
	private void enemyToPosition(int[] pos, Enemy enemy) { //Attempts to move the enemy to there target position
		
		int newPos[] = ai.seek(pos[0],pos[1],enemy.worldX,enemy.worldY);
		if (gp.cChecker.checkFutureTile(newPos)) {
			enemy.worldX = newPos[0];
			enemy.worldY = newPos[1];
		}
	}
	
}
