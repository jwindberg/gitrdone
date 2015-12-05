package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PImage;

public class LustreKaleidoscope01 extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	private PImage logo;
	int numLogos = 6;

	float offSet = 0f;
	float speed = 0.01f;

	public void setupInternal() {
		logo = loadImage("data/Lustre_Logo_Alpha.png");
		imageMode(CENTER);
		rectMode(CENTER);

	}

	public void draw() {
		translate(width / 2, height / 2);
		offSet += speed;
		if (offSet > TWO_PI) {
			offSet -= TWO_PI;
		}
		background(0);
		rotate(offSet);
		float x = getWidth() / 4f;
		float y = getHeight() / 4f;

		for (int t = 0; t < numLogos; t++) {
			image(logo, x, y, 150, 150);
			rotate(TWO_PI / numLogos);
		}

	}

}
