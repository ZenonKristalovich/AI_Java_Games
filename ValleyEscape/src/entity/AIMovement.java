/*Class: AIMovement
 * Purpose: Handles calls of AI movement of enemy characters
 */

package entity;

import main.GamePanel;

public class AIMovement {
	//Variables
	GamePanel gp;
	int speed;
	
	//Constructor
	public AIMovement(int speed, GamePanel gp) {
		this.speed = speed;
		this.gp = gp;
	}
	
	//METHODS
	
	public int[] seek(int px, int py, int ex, int ey) {//Move character toward player
		
		// Calculate the direction vectors
        double dx = px - ex;
        double dy = py - ey;

        // Calculate the distance between enemy and player
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Normalize the direction vector
        double normalizedDx = dx / distance;
        double normalizedDy = dy / distance;

        // Calculate new position based on the speed
        double newX = ex + normalizedDx * speed;
        double newY = ey + normalizedDy * speed;
        
        //Return New Position of the Enemy after Moving
        return new int[]{(int)Math.round(newX), (int)Math.round(newY)};
		
	}
	
	public int[] flee(int px, int py, int ex, int ey) {//Move character away from player
		
		// Calculate the direction vectors 
	    double dx = ex - px;
	    double dy = ey - py;

	    // Calculate the distance between enemy and player
	    double distance = Math.sqrt(dx * dx + dy * dy);

	    // Normalize the direction vector
	    double normalizedDx = dx / distance;
	    double normalizedDy = dy / distance;

	    // Calculate new position based on the speed
	    double newX = ex + normalizedDx * speed;
	    double newY = ey + normalizedDy * speed;
	    
	    // Return the new position of the enemy after moving
	    return new int[]{(int)Math.round(newX), (int)Math.round(newY)};
	}
}
