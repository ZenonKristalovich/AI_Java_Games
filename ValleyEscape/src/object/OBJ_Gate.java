/*Class: OBJ_Gate
 * Purpose: Info on the gate object
 */


package object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Gate extends SuperObject {

	public  OBJ_Gate() {
		name = "Gate";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Gate.png"));
			height = 3;
			solidArea = new Rectangle(0,0,48,48*3);
		}catch(IOException e) {
			e.printStackTrace();
		}
		

	}
}
