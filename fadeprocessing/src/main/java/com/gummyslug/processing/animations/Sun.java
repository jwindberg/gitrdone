package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class Sun extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	float num;

	public void setupInternal() {
		fill(255, 150, 0, 20);
		noStroke();
	}

	public void draw() {
		background(0);
		translate(width / 2, height / 2);
		for (int i = -180; i < 180; i += 12) {
			float x = sin(radians(i)) * 15;
			float y = cos(radians(i)) * 15;

			pushMatrix();
			translate(x, y);
			rotate(radians(-i));
			for (int q = 0; q < 200; q += 5) {
				float d = map(q, 0, 200, 40, 0);
				float a = sin(-i + q + num) * 2;
				ellipse(a, q, d, d);
			}
			popMatrix();
		}

		num += 0.2;
	}

}
