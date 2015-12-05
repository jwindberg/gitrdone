package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PImage;

public class StarField extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	int reps = 1000;
	float[] ypos = new float[reps];
	float[] xpos = new float[reps];
	int instance = 0;
	int startingPointX = 350;
	int startingPointY = 350;
	int refreshPoint = 50;

	private PImage dot;

	public void setupInternal() {
		smooth();
		for (instance = 0; instance < reps; instance++) {
			xpos[instance] = (int) (random(-startingPointX, startingPointX));
			ypos[instance] = (int) (random(-startingPointY, startingPointY));
		}

		dot = loadImage("data/lustre-logo.gif");
	}

	public void draw() {
		if (mousePressed) {
		} else {
			background(0);
		}

		pushMatrix();
		translate(width / 2, height / 2);
		for (instance = 0; instance < reps; instance++) {
			stroke(255, instance);
			point(xpos[instance], ypos[instance]);
			xpos[instance] = xpos[instance] + (xpos[instance]) / 100.0f;
			ypos[instance] = ypos[instance] + (ypos[instance]) / 100.0f;
			if ((xpos[instance] < -width) || (xpos[instance] > width) || (ypos[instance] < -height)
					|| (ypos[instance] > height)) {
				xpos[instance] = random(-refreshPoint, refreshPoint);
				ypos[instance] = random(-refreshPoint, refreshPoint);
			}
		}
		popMatrix();

		float dotHeight = (float) (getHeight() * 0.3);
		float dotWidth = 0.7f * dotHeight;
		image(dot, mouseX - dotWidth / 2, mouseY - dotHeight / 2, dotWidth, dotHeight);
	}

}
