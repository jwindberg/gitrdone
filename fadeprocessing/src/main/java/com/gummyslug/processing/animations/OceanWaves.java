package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class OceanWaves extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	float y;

	public void setupInternal() {
		y = height * .25f;
		smooth();

	}

	public void draw() {
		background(0);
		noFill();
		stroke(30, 200, 250);

		for (int i = 0; i < 100; i += 1) {
			beginShape();
			vertex(0, height);
			for (int x = 0; x < width; x += 4) {

				vertex(x, y - i + (noise(x * .01f, (frameCount * .01f), i * .01f) * 400));

			}
			vertex(width, height);
			endShape();

		}

	}

}
