package com.gummyslug.processing.animations;

import processing.core.PImage;

import com.gummyslug.processing.opc.AnimationApplet;

public class Orbits extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	PImage clouds;
	PImage dot1;
	PImage dot2;
	float px, py;

	public void setupInternal() {
		frameRate(20);
		dot1 = loadImage("data/greenDot.png");
		dot2 = loadImage("data/purpleDot.png");
	}

	public void draw() {
		background(0);
		blendMode(ADD);

		// Smooth out the mouse location
		px += (mouseX - px) * 0.1;
		py += (mouseY - py) * 0.1;

		float a = millis() * 0.001f;
		float r = py * 0.5f;
		float dotSize = r * 4;

		float dx = width / 2 + cos(a) * r;
		float dy = height / 2 + sin(a) * r;

		// Draw it centered at the mouse location
		image(dot1, dx - dotSize / 2, dy - dotSize / 2, dotSize, dotSize);

		// Another dot, mirrored around the center
		image(dot2, width - dx - dotSize / 2, height - dy - dotSize / 2, dotSize, dotSize);

	}
}