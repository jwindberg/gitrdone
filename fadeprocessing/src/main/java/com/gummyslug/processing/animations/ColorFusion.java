package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class ColorFusion extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	float theta = 0f, sz = 0f, frms = 600f;
	int num = 250, _min, _max;

	public void setupInternal() {
		colorMode(HSB, num, 100, 100, 100);
		_min = width / 15;
		_max = width / 10;
	}

	public void draw() {
		background(0);
		noStroke();
		for (int i = 0; i < num; i++) {
			fill(i, 90, 100, 25);
			float offSet = TWO_PI / num * i;
			float y = map(sin(theta + offSet), -1f, 1f, height * .2f, height * .8f);
			float x = map(sin(theta - offSet), -1f, 1f, width * .2f, width * .8f);
			float sz = map(sin(theta + offSet * 3), -1, 1, _min, _max);
			ellipse(x, y, sz * 1.5f, sz);
		}
		theta += TWO_PI / frms;
	}

	public void windowResized() {
		resize(width, height);
	}

}
