package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class FallingSnow extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	int quantity = 100;
	float[] xPosition = new float[quantity];
	float[] yPosition = new float[quantity];
	int[] flakeSize = new int[quantity];
	int[] direction = new int[quantity];
	int[] color = new int[quantity];
	int minFlakeSize = 1;
	int maxFlakeSize = 5;
	float minStep = 0.1f;
	float maxStep = 0.5f;
	float multiplier = 5f;
	int blue = color(0, 0, 255);
	int white = color(250, 250, 250);

	public void setupInternal() {
		frameRate(15);
		noStroke();
		smooth();

		for (int i = 0; i < quantity; i++) {
			flakeSize[i] = round(random(minFlakeSize, maxFlakeSize));
			xPosition[i] = random(0, width);
			yPosition[i] = random(0, height);
			direction[i] = round(random(0, 1));
			color[i] = round(random(0, 1)) == 1 ? blue : white;
		}

	}

	public void draw() {

		background(0);

		for (int i = 0; i < xPosition.length; i++) {

			fill(color[i]);
			// stroke(255);
			ellipse(xPosition[i], yPosition[i], flakeSize[i] * multiplier, flakeSize[i] * multiplier);

			if (direction[i] == 0) {
				xPosition[i] += map(flakeSize[i], minFlakeSize, maxFlakeSize, minStep, maxStep);
			} else {
				xPosition[i] -= map(flakeSize[i], minFlakeSize, maxFlakeSize, minStep, maxStep);
			}

			yPosition[i] += flakeSize[i] + direction[i];

			if (xPosition[i] > width + flakeSize[i] || xPosition[i] < -flakeSize[i]
					|| yPosition[i] > height + flakeSize[i]) {
				xPosition[i] = random(0, width);
				yPosition[i] = -flakeSize[i];
			}

		}

	}
}
