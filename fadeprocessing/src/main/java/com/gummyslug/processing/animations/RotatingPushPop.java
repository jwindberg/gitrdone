package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class RotatingPushPop extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	float a; // Angle of rotation
	float offset = (float) (Math.PI / 24.0f); // Angle offset between boxes
	int num = 12; // Number of boxes

	public void setupInternal() {
		noStroke();
	}

	@Override
	protected String getRenderer() {
		return P3D;
	}

	public void draw() {

		lights();

		background(0, 0, 26);
		translate(width / 2, height / 2);

		for (int i = 0; i < num; i++) {
			float gray = map(i, 0, num - 1, 0, 255);
			pushMatrix();
			fill(gray);
			rotateY(a + offset * i);
			rotateX(a / 2 + offset * i);
			box(200);
			popMatrix();
		}

		a += 0.01;
	}

}
