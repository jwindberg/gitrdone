package com.gummyslug.processing.animations;

import processing.core.PFont;

import com.gummyslug.processing.opc.AnimationApplet;

public class ColorWheel extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	int SHADE = 0;
	int TINT = 1;
	int segs = 12; // no. segments around the wheel
	int steps = 6; // no. colour wheel rings
	int backgroundGrey = 127;
	String RGBString = "";
	PFont fontA;

	public void setupInternal() {
		smooth();
		ellipseMode(RADIUS);
		noStroke();
		// fontA = loadFont("Dialog-10.vlw");
		// textFont(fontA, 10);
		noLoop();
	}

	int red = 0;
	int green = 0;
	int blue = 0;

	public void draw() {
		background(red, green, blue);
		createWheel(width / 2, height / 2);

		if (mousePressed) {
			int c = get(mouseX, mouseY);
			red = (int) red(c);
			green = (int) green(c);
			blue = (int) blue(c);

		}
	}

	void createWheel(int x, int y) {
		float rotAdjust, segWidth, interval, nsteps, nsegs;
		float radius = 140.0f;
		noStroke();
		nsteps = steps;
		nsegs = segs;
		rotAdjust = radians(360.0f / nsegs / 2.0f);
		segWidth = radius / nsteps;
		interval = TWO_PI / nsegs;
		for (int j = 0; j < nsteps; j++) {
			int[] cols = { color(255 - (255 / nsteps) * j, 255 - (255 / nsteps) * j, 0),
					color(255f - (255f / nsteps) * j, (255f / 1.5f) - ((255f / 1.5f) / nsteps) * j, 0f),
					color(255 - (255 / nsteps) * j, (255 / 2) - ((255 / 2) / nsteps) * j, 0),
					color((int) (255 - (255 / nsteps) * j), (int) ((255 / 2.5) - ((255 / 2.5) / nsteps) * j), 0),
					color(255 - (255 / nsteps) * j, 0, 0),
					color(255 - (255 / nsteps) * j, 0, (255 / 2) - ((255 / 2) / nsteps) * j),
					color(255 - (255 / nsteps) * j, 0, 255 - (255 / nsteps) * j),
					color((255 / 2) - ((255 / 2) / nsteps) * j, 0, 255 - (255 / nsteps) * j),
					color(0, 0, 255 - (255 / nsteps) * j),
					color(0, (int) 255 - (255 / nsteps) * j, (int) ((255 / 2.5) - ((255 / 2.5) / nsteps) * j)),
					color(0, 255 - (255 / nsteps) * j, 0),
					color((255 / 2) - ((255 / 2) / nsteps) * j, 255 - (255 / nsteps) * j, 0) };
			for (int i = 0; i < nsegs; i++) {
				fill(cols[i]);
				arc(x, y, radius, radius, interval * i + rotAdjust, interval * (i + 1) + rotAdjust);
			}
			radius -= segWidth;
		}
	}

	void drawBlackWhiteSquares() {
		// draw black and white squares at the bottom left corner
		noStroke();
		fill(0);
		rect(5, 280, 15, 15);
		fill(255);
		rect(20, 280, 15, 15);
	}

	public void mousePressed() {
		redraw();
	}

}
