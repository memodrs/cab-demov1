package com.cab;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL urls[] = new URL[30];
	
	public Sound() {
		urls[0] = getClass().getResource("/sound/darkWood.wav");
		urls[1] = getClass().getResource("/sound/card.wav");
		urls[2] = getClass().getResource("/sound/speak.wav");
		urls[3] = getClass().getResource("/sound/dropItem.wav");
		urls[4] = getClass().getResource("/sound/navigate.wav");
		urls[5] = getClass().getResource("/sound/cardGame.wav");
		urls[6] = getClass().getResource("/sound/effekt.wav");
		urls[7] = getClass().getResource("/sound/hit.wav");
		urls[8] = getClass().getResource("/sound/explosion.wav");
		urls[9] = getClass().getResource("/sound/menu.wav");
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(urls[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
	
}
