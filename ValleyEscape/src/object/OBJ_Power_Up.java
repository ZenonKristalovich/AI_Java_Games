/*Class: OBJ_Power_Up
 * Purpose: Info on the Power up
 */


package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Power_Up extends SuperObject{
	
	public  OBJ_Power_Up() {
		name = "Power_Up";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/power_up.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		

	}
}
