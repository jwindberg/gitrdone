package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class Graphing2dEquation extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	public void draw() {
		loadPixels();
		float n = (mouseX * 10.0f) / width;
		float w = 16.0f; // 2D space width
		float h = 16.0f; // 2D space height
		float dx = w / width; // Increment x this amount per pixel
		float dy = h / height; // Increment y this amount per pixel
		float x = -w / 2; // Start x at -1 * width / 2
		for (int i = 0; i < width; i++) {
			float y = -h / 2; // Start y at -1 * height / 2
			for (int j = 0; j < height; j++) {
				float r = sqrt((x * x) + (y * y)); // Convert cartesian to polar
				float theta = atan2(y, x); // Convert cartesian to polar
				// Compute 2D polar coordinate function
				float val = sin(n * cos(r) + 5 * theta); // Results in a value
															// between -1 and 1
				// float val = cos(r); // Another simple function
				// float val = sin(theta); // Another simple function
				// Map resulting vale to grayscale value
				pixels[i + j * width] = color((val + 1.0f) * 255.0f / 2.0f); // Scale
																				// to
																				// between
																				// 0
																				// and
																				// 255
				y += dy; // Increment y
			}
			x += dx; // Increment x
		}
		updatePixels();
	}

	@Override
	protected void finalize() throws Throwable {
		opc.clear();
	}
}
