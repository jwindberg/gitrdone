package com.gummyslug.processing.animations;

import java.util.ArrayList;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PVector;

public class PerlinOscillator extends AnimationApplet {

	/**
	 * 2D Perlin Oscilallator
	 *
	 * @author aa_debdeb
	 * @date 2016/01/10
	 */

	float noiseX, noiseY;
	ArrayList<Osc> oscs;

	public void setupInternal() {
		smooth();
		frameRate(30);

		noiseX = random(100);
		noiseY = random(100);
		oscs = new ArrayList<Osc>();
		for (int x = 0; x <= 500; x += 25) {
			for (int y = 0; y <= 500; y += 25) {
				oscs.add(new Osc(new PVector(x, y)));
			}
		}
	}

	public void draw() {
		background(0);
		for (Osc osc : oscs) {
			osc.display();
		}
	}

	class Osc {
		PVector pos;
		float rad;

		Osc(PVector pos) {
			this.pos = pos;
			rad = random(TWO_PI);
		}

		void display() {
			float diameter = map(sin(rad), -1, 1, 10, 24);
			noStroke();
			fill(map(sin(rad), -1, 1, 0, 255), map(sin(rad), -1, 1, 139, 20), map(sin(rad), -1, 1, 139, 147));
			ellipse(pos.x, pos.y, diameter, diameter);
			rad += map(noise(noiseX + pos.x * 0.05f, noiseY + pos.y * 0.05f), 0f, 1f, PI / 128, PI / 6);
			if (rad > TWO_PI) {
				rad -= TWO_PI;
			}
		}

	}

}
