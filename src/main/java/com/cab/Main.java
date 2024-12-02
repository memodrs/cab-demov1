package com.cab;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;

import com.cab.configs.Variables;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

	static public Variables v;
	static public int screenWidth;
	static public int screenHeight;
	static public int screenHalfWidth;
	static public int screenHalfHeight;
	static public float scale;

	public static void main(String[] args) {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
		String os = System.getProperty("os.name").toLowerCase();
		
		try {
			Files.createDirectories(Paths.get(System.getProperty("user.home"), "Documents", "CurseAndBlessing"));
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Unskalierten Bildschirmabmessungen
        int physicalScreenWidth = gd.getDisplayMode().getWidth();
        int physicalScreenHeight = gd.getDisplayMode().getHeight();

		if (os.contains("win")) {
			// Skalierte Bildschirmabmessungen
			AffineTransform transform = gc.getDefaultTransform();
			int scaledScreenWidth = (int) (physicalScreenWidth * transform.getScaleX());
			scale = (float) scaledScreenWidth / physicalScreenWidth;
		} else {
			scale = 1;
		}

		screenWidth = (int) (physicalScreenWidth / scale);
		screenHeight = (int) (physicalScreenHeight / scale);


		screenHalfWidth = screenWidth / 2;
		screenHalfHeight = screenHeight / 2;

		v = new Variables();
		
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
