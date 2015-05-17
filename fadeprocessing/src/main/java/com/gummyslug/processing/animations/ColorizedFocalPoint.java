package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class ColorizedFocalPoint extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	float theta;
	int count = 0;

	public void setupInternal() {
		size(500, 500);
		colorMode(HSB);
		frameRate(20);
		theta = 0;
	}

	public void draw() {
		theta += TWO_PI / 45;
		count += 1;
		background(0);

		float cx = width / 2f + width / 2.5f * cos(theta);
		float cy = height / 2f + height / 2.5f * sin(theta);

		for (float x = 0; x < width; x += 5) {
			for (float y = 0; y < height; y += 5) {
				float dist = sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy));
				float hue = map(dist, 0f, 1.414f * width, 0f, 255f);
				stroke(hue, 255, 255);
				line(x, y, cx, cy);
			}
		}
		if (theta <= TWO_PI) {
			// save("theta"+str(count)+".png");
		}
	}

}
