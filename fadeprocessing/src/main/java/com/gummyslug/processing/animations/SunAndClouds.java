package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PImage;

public class SunAndClouds extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	float num;
	
	PImage clouds;

	public void setupInternal() {
		fill(255, 150, 0, 20);
		noStroke();
		
		colorMode(HSB, 100);
		noiseDetail(5, 0.4f);
		loadPixels();

		// Render the noise to a smaller image, it's faster than updating the
		// entire window.
		clouds = createImage(128, 128, RGB);
	}

	
	public void draw() {
		background(0);
		drawClouds();
		drawSun();
		
	}
	public void drawSun() {
	
		translate(width / 2, height / 2);
		for (int i = -180; i < 180; i += 12) {
			float x = sin(radians(i)) * 15;
			float y = cos(radians(i)) * 15;

			pushMatrix();
			translate(x, y);
			rotate(radians(-i));
			for (int q = 0; q < 200; q += 5) {
				float d = map(q, 0, 200, 40, 0);
				float a = sin(-i + q + num) * 2;
				ellipse(a, q, d, d);
			}
			popMatrix();
		}

		num += 0.2;
	}
	
	public void drawClouds() {
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
