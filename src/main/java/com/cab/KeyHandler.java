package com.cab;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	public boolean blockBtn;
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, fPressed, gPressed, iPressed, qPressed;

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		setKeyStatus(code, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		setKeyStatus(code, false);	
		blockBtn = false;
	}
	
	
	private void setKeyStatus(int code, Boolean keyPressed) {
		if(code == KeyEvent.VK_W) {
			upPressed = keyPressed;
		} else if(code == KeyEvent.VK_S) {
			downPressed = keyPressed;		
		} else if(code == KeyEvent.VK_A) {
			leftPressed = keyPressed;		
		} else if(code == KeyEvent.VK_D) {
			rightPressed = keyPressed;		
		} else if (code == KeyEvent.VK_ENTER) {
			enterPressed = keyPressed;
		} else if (code == KeyEvent.VK_F) {
			fPressed = keyPressed;
		} else if (code == KeyEvent.VK_I) {
			iPressed = keyPressed;
		} else if (code == KeyEvent.VK_Q) {
			qPressed = keyPressed;
		} else if (code == KeyEvent.VK_G) {
			gPressed = keyPressed;
		}
	}
}