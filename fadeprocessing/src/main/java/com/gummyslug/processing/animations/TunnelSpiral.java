package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class TunnelSpiral extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	float counter = 0;

	int NUM_SQUARES = 50;

	int teal = color(153, 255, 204);
	int pink = color(221, 85, 136);
	int seaG = color(171, 230, 117);
	int blue = color(51, 34, 99);

	public void draw() {
		background(0);
		int from = lerpColor(pink, blue, (sin(counter * 20) + 1) / 2);
		int to = lerpColor(teal, seaG, (sin(counter * 20) + 1) / 2);
		translate(getWidth() / 2, getHeight() / 2);
		for (int i = 1; i < NUM_SQUARES; i++) {
			rotate(counter);
			int f = lerpColor(from, to, (float) (i) / (float) (NUM_SQUARES));
			stroke(f);
			fill(f);
			rect(i, i, getWidth() / 3 - (i * 2), getHeight() / 3 - (i * 2));
		}
		counter += 0.0007;
	}
}
