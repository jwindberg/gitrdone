package com.gummyslug.processing.animations;

import processing.core.PImage;

import com.gummyslug.processing.opc.AnimationApplet;

public class FlashLight extends AnimationApplet {

	private static final long serialVersionUID = 1L;
	private PImage dot;

	public void setupInternal() {
		// Load a sample image
		dot = loadImage("data/Lustre_Logo.jpg");
	}

	public void draw() {
		background(0);

		// Draw the image, centered at the mouse location
		float dotSize = (float) (getHeight() * 0.3);
		image(dot, mouseX - dotSize / 2, mouseY - dotSize / 2, dotSize, dotSize);
	}

}
