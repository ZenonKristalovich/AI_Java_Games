/*Class: AssetSetter
 * Purpose: Places all the objects in the world
 */


package main;

import object.OBJ_Coin;
import object.OBJ_Exit;
import object.OBJ_Gate;
import object.OBJ_Key;
import object.OBJ_Power_Up;

import java.util.Random;

public class AssetSetter {
	
	//Variables
	
	GamePanel gp;
	Random rand = new Random(); 
	
	int[][] coinLocations;
	int[][] keyLocations;
	
	//Constructor
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		rand = new Random(); 
		coinLocations = new int[][] { {23,20},{15,9},{21, 33}, {31,16}, {27,34}, {31, 35} };
		keyLocations = new int[][] { {28,15},{17,10},{16, 38}, {24,16}, {29,17}, {32, 37} };
	}
	
	//METHODS
	
	public void setObjects() {//Place the objects in there locations
		
		// Generate a random value 
        int randomNum = rand.nextInt(coinLocations.length);
		
		//Place The Key
		
		gp.obj[0]= new OBJ_Key();
		gp.obj[0].worldX = keyLocations[randomNum][0] * gp.tileSize;
		gp.obj[0].worldY = keyLocations[randomNum][0] * gp.tileSize;
		
		//Place Coins
		
        for( int x = 0; x < 3; x++) {
        	gp.obj[x + 1]= new OBJ_Coin();
    		gp.obj[x + 1].worldX = coinLocations[( x + randomNum)%coinLocations.length ][0] * gp.tileSize;
    		gp.obj[x + 1].worldY = coinLocations[( x + randomNum)%coinLocations.length ][1] * gp.tileSize;
        }
        
        //Generate PowerUp
        gp.obj[4]= new OBJ_Power_Up();
		gp.obj[4].worldX = 23 * gp.tileSize;
		gp.obj[4].worldY = 23 * gp.tileSize;
		
		//Place Gate
		gp.obj[5]= new OBJ_Gate();
		gp.obj[5].worldX = 10 * gp.tileSize;
		gp.obj[5].worldY = 24 * gp.tileSize;
		
		//Place Exit
		gp.obj[6]= new OBJ_Exit();
		gp.obj[6].worldX = 8 * gp.tileSize;
		gp.obj[6].worldY = 24 * gp.tileSize;
		
	}

}
