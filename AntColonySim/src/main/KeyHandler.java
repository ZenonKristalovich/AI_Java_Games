/*Class: KeyHandler
 * Purpose: Keeps track of what the user is clicking
 */


package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	//Variables
	
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean enter;
	public boolean onePressed,twoPressed,threePressed,fourPressed,fivePressed,sixPressed;
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {//checks when a key is pressed
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_E) {
			enter = true;
		}
		if(code == KeyEvent.VK_1) {
			onePressed = true;
		}
		if(code == KeyEvent.VK_2) {
			twoPressed = true;
		}
		if(code == KeyEvent.VK_3) {
			threePressed = true;
		}
		if(code == KeyEvent.VK_4) {
			fourPressed = true;
		}
		if(code == KeyEvent.VK_5) {
			fivePressed = true;
		}
		if(code == KeyEvent.VK_6) {
			sixPressed = true;
		}
		

		
	}

	@Override
	public void keyReleased(KeyEvent e) {//checks when key is released
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_E) {
			enter = false;
		}
		if(code == KeyEvent.VK_1) {
			onePressed = false;
		}
		if(code == KeyEvent.VK_2) {
			twoPressed = false;
		}
		if(code == KeyEvent.VK_3) {
			threePressed = false;
		}
		if(code == KeyEvent.VK_4) {
			fourPressed = false;
		}
		if(code == KeyEvent.VK_5) {
			fivePressed = false;
		}
		if(code == KeyEvent.VK_6) {
			sixPressed = false;
		}
	}

}
