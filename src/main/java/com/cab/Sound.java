package com.cab;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	int i = -1;
	Clip clip;
	URL urls[] = new URL[30];
	
	public Sound() {
		urls[0] = getClass().getResource("/sound/menu.wav");
		urls[1] = getClass().getResource("/sound/navigate.wav");
		urls[2] = getClass().getResource("/sound/card.wav");
		urls[3] = getClass().getResource("/sound/cardEditor.wav");
		urls[4] = getClass().getResource("/sound/shop.wav");
		urls[5] = getClass().getResource("/sound/cardGame.wav");
		urls[6] = getClass().getResource("/sound/hit.wav");
		urls[7] = getClass().getResource("/sound/explosion.wav");
		urls[8] = getClass().getResource("/sound/effekt.wav");
	}
	
	public void setFile(int i) {
		try {
			this.i = i;
			AudioInputStream ais = AudioSystem.getAudioInputStream(urls[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		setVolume(0.25f);
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		if (this.clip != null) {
			clip.stop();
		}
	}

	public void setVolume(float volume) {
		if (clip != null && clip.isOpen()) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (20.0 * Math.log10(volume)); // Umwandlung in Dezibel
			gainControl.setValue(dB);
		}
	}	
}
