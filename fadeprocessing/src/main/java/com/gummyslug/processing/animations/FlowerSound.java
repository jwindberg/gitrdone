package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;

public class FlowerSound extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	float size = 1024 / 10;
	int[] randomCoordX = new int[(int) size];
	int[] randomCoordY = new int[(int) size];
	int[] colorade = new int[(int) size];
	float count = 0;

	Minim minim;
	AudioInput in;
	FFT fft;

	public void setupInternal() {
		smooth();
		noStroke();
		background(0);
		minim = new Minim(this);
		in = minim.getLineIn();

		for (int i = 0; i < size; i++) {
			randomCoordX[i] = (int) random(-3, 3);
			randomCoordY[i] = (int) random(-3, 3);
			// colorade[i] = color(map(i, 0, size, 0, 255), 200, map(i, 0, size,
			// 200, 0), 100);
			colorade[i] = color(200, map(i, 0, size, 0, 255), map(i, 0, size, 200, 0), 100);

		}
	}

	public void draw() {
		display();
	}

	void display() {
		fill(0);
		rect(0, 0, width, height);
		translate(width / 2, height / 2);
		pushMatrix();
		rotate(radians(count));
		for (int i = 0; i < size; i++) {
			float multiplier = in.left.get(i) * (size - i) * 40;
			fill(colorade[i]);
			bezier(0, 0, randomCoordY[i] * multiplier, randomCoordX[i] * multiplier, (randomCoordX[i] + 1) * multiplier,
					(randomCoordY[i] + 1) * multiplier, 0, 0);
		}
		count += 1;
		popMatrix();
	}

}
