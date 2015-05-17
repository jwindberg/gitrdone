package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.Minim;

public class CircularAudio extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	Minim minim;
	AudioInput audioInput;

	float color1 = 35;
	float color2 = 45;
	float color3 = 65;
	float color4 = 75;

	public void setupInternal() {
		background(0);
		colorMode(HSB, 100, 100, 100, 100);

		minim = new Minim(this);
		audioInput = minim.getLineIn(Minim.STEREO, 2048, 192000.0f);
		smooth();
		opc.showLocations(false);

	}

	public void draw() {
		noStroke();
		fill(0, 5);
		rect(0, 0, width, height);
		pushMatrix();
		translate(width / 2, height / 2);
		rotate(radians(frameCount % 360 * 2));
		for (int j = 0; j < 360; j++) {

			if (audioInput.mix.get(j) * 200 > 50) {
				stroke(color1, 100, 100);
			} else {
				stroke(color2, 100, 100);
			}

			line(cos(j) * 50, sin(j) * 50, cos(j) * abs(audioInput.left.get(j)) * 200 + cos(j) * 50, sin(j)
					* abs(audioInput.right.get(j)) * 200 + sin(j) * 50);
		}
		for (int k = 360; k > 0; k--) {

			if (audioInput.mix.get(k) * 200 > 25) {
				stroke(color3, 100, 100);
			} else {
				stroke(color4, 100, 100);
			}

			line(cos(k) * 50, sin(k) * 50, cos(k) * abs(audioInput.right.get(k)) * 200 + cos(k) * 50, sin(k)
					* abs(audioInput.left.get(k)) * 200 + sin(k) * 50);
		}

		popMatrix();
	}

	public void keyPressed() {

		if (key == 'r') {
			color1 = 0;
			color2 = 5;
			color3 = 90;
			color4 = 95;
		}

		if (key == 'g') {
			color1 = 35;
			color2 = 45;
			color3 = 65;
			color4 = 75;
		}

	}

	public void stop() {

		audioInput.close();
		minim.stop();
		super.stop();

	}

}
