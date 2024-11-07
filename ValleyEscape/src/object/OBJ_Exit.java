/*Class: OBJ_Exit
 * Purpose: Info on the exit object
 */


package object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Exit extends SuperObject{
	
	public  OBJ_Exit() {
		name = "Exit";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Exit.png"));
			height = 3;
			solidArea = new Rectangle(0,0,48,48*3);
		}catch(IOException e) {
			e.printStackTrace();
		}
		

	}
}
