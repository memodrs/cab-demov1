package com.cab;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.cab.configs.Variables;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Main {

	static public Variables v = new Variables();
	static public int screenWidth;
	static public int screenHeight;

	public static void main(String[] args) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        // Holen unskalierten Bildschirmabmessungen
        screenWidth = gd.getDisplayMode().getWidth();
        screenHeight = gd.getDisplayMode().getHeight();
		
        //Das scheint irgendwie nicht zu klappen
		System.setProperty("file.encoding", "UTF-8");
	       
		JFrame window = new JFrame();
		window.setLayout(new BorderLayout());

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Curse and Blessing");
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Vollbild
		window.setUndecorated(true); 

		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		gamePanel.startGameThread();
	}
}
