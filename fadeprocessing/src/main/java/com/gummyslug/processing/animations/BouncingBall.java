package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class BouncingBall extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	private int x = 0;
	private int speed = 10;

	public void setupInternal() {
		smooth();
	}

	public void draw() {
		background(0);

		// Add the current speed to the x location.
		x = x + speed;

		// Remember, || means "or."
		if ((x > width) || (x < 0)) {
			// If the object reaches either edge, multiply speed by -1 to turn
			// it around.
			speed = speed * -1;
		}

		// Display circle at x location
		stroke(0);
		fill(204, 102, 0);
		ellipse(x, 100, 128, 128);
	}
}
