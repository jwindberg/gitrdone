package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class BullsEye extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	int red = color(255f, 0f, 0f);
	int black = 0;

	int size = 0;
	int step = 0;
	int speed = 2;
	int offset = 0;

	int centerX = 0;
	int centerY = 0;

	@Override
	public void colorMode(int mode) {
		// TODO Auto-generated method stub
		super.colorMode(mode);
	}

	public void setupInternal() {
		size = (int) Math.sqrt((double) (width * width + height * height));
		step = size / 5;
		centerX = width / 2;
		centerY = width / 2;
	}

	public void draw() {
		background(0);
		noStroke();

		if (mousePressed) {
			centerX = mouseX;
			centerY = mouseY;

		}
		for (int i = size; i > 0; i -= step) {

			fill(red);
			ellipse(centerX, centerY, i + offset, i + offset);
			fill(black);
			ellipse(centerX, centerY, i - (step / 2) + offset, i - (step / 2) + offset);
		}
		offset += speed;
		if (offset > step) {
			offset = 0;

		}
	}

}
