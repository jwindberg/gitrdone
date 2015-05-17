package com.gummyslug.processing.animations;

import processing.core.PImage;

import com.gummyslug.processing.opc.AnimationApplet;

public class Clouds extends AnimationApplet {

	private static final long serialVersionUID = 1L;


	PImage clouds;

	public void setupInternal() {
		colorMode(HSB, 100);
		noiseDetail(5, 0.4f);
		loadPixels();

		// Render the noise to a smaller image, it's faster than updating the
		// entire window.
		clouds = createImage(128, 128, RGB);


	}

	public void draw() {
		float hue = (noise(millis() * 0.0001f) * 200f) % 100;
		float z = millis() * 0.0001f;
		float dx = millis() * 0.0001f;

		for (int x = 0; x < clouds.width; x++) {
			for (int y = 0; y < clouds.height; y++) {
				float n = (float) (500f * (noise(dx + x * 0.01f, y * 0.01f, z) - 0.4));
				int c = color(hue, 80 - n, n);
				clouds.pixels[x + clouds.width * y] = c;
			}
		}
		clouds.updatePixels();

		image(clouds, 0, 0, width, height);

	}
}