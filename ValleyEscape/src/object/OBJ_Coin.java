/*Class: OBJ_Coin
 * Purpose: Info on the coin object
 */


package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Coin extends SuperObject {
	
	public  OBJ_Coin() {
		name = "Coin";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/coin.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		

	}
}
