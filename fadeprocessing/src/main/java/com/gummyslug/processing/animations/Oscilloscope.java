package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class Oscilloscope extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	/*
	 * Oscilloscope Lissajous + Bowditch Visualization
	 * https://en.wikipedia.org/wiki/Lissajous_curve
	 */

	Minim minim;
	AudioPlayer player;

	float gain = 100;
	int tbase = 2048;
	int xCenter;
	int yCenter;
	float[] playerBuffer;

	public void setupInternal() {
		frameRate(60);
		xCenter = width / 2;
		yCenter = height / 2;
		minim = new Minim(this);
		player = minim.loadFile("C:\\Users\\jwind\\Music\\Jimni Cricket - loserforlife.mp3", 2048);
		playerBuffer = new float[player.bufferSize()];
		player.play();
	}

	public void draw() {
		background(0);
		stroke(183, 233, 255);
		strokeWeight(2);
		beginShape();
		for (int i = 0; i < player.bufferSize() - 1; i++) {
			// line(x1, y1, x2, y2)
			// line(xCenter+player.right.get(i+1)*640,
			// yCenter-player.left.get(i+1)*360, xCenter, yCenter);
			point(xCenter + player.right.get(i + 1) * 640, yCenter - player.left.get(i + 1) * 360);
		}
		endShape();
	}

	public void stop() {
		player.close();
		minim.stop();
		super.stop();
	}

}
