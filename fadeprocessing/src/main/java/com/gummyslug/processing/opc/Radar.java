package com.gummyslug.processing.opc;

public class Radar extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	int bgcolor = color(0, 0, 0);
	int gridcolor = color(102, 250, 81, 60);
	int radarcolor = color(66, 173, 51, 200);
	int sweepercolor = color(102, 250, 81);
	int flashcolor = color(102, 250, 81);
	float s;
	float rond;
	int fadeout;
	int fadeouttime = 50;

	boolean rd = false;
	float rNrX;
	float rNrY;

	public void draw() {
		background(bgcolor);
		smooth();
		sweeper();
		flash();
	}

	void sweeper() {
		rond = map(millis(), 0, 6000, 0, TWO_PI);
		strokeWeight(7);
		float f = 0.01f;
		for (int i = 38; i > 0; i--) {
			stroke(sweepercolor, 2 * i);
			line(width / 2, height / 2, (width / 2 + cos(rond - f) * height), (height / 2 + sin(rond - f) * height));
			f += 0.02;
		}
	}

	void flash() {
		fill(flashcolor, fadeout);
		noStroke();
		rect(-5, -5, width + 5, height + 5);
		if (cos(rond) >= 0.00 && sin(rond) <= -0.99) {
			fadeout = fadeouttime;
			rd = false;
		}
		if (fadeout <= fadeouttime && fadeout > 0) {
			fadeout--;
		}
	}

}
