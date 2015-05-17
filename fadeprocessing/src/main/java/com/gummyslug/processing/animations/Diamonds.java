package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class Diamonds extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	float[] sX, sY, dX, dY;
	float d, z;
	int n;
	int c;

	public void setupInternal() {
		setS();
	}

	void setS() {
		n = round(random(1, 5));
		z = random(0.02f, 0.2f);
		sX = new float[n];
		sY = new float[n];
		dX = new float[n];
		dY = new float[n];
		for (int i = 0; i < n; i++) {
			sX[i] = random(width);
			sY[i] = random(height);
			dX[i] = random(-1, 1);
			dY[i] = random(-1, 1);
		}
	}

	public void draw() {
		for (int i = 0; i < n; i++) {
			sX[i] += dX[i];
			sY[i] += dY[i];
			if (sX[i] < 0 || sX[i] > width) {
				dX[i] *= -1;
			}
			if (sY[i] < 0 || sY[i] > height) {
				dY[i] *= -1;
			}
		}
		loadPixels();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				d = 0.0f;
				for (int s = 0; s < n; s++) {
					d += (abs(sX[s] - x) + abs(sY[s] - y)) * z;
				}
				d = sin(d);
				if (d < 0) {
					c = color(0);
				} else {
					c = color(d * 255);
				}
				pixels[y * width + x] = c;
			}
		}
		updatePixels();
	}

	public void keyPressed() {
		setS();
	}

}
