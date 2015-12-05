package com.gummyslug.processing.animations;

import java.util.ArrayList;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PImage;

public class LustreKaleidoscope05 extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	private PImage logo;

	ArrayList<Curves> lines = new ArrayList<Curves>();

	float noiseScale = 100;
	float nz = 0;
	float line_lenght = 15;
	float drawCount = 30;
	int flameX;
	int flameY;
	int step = 2;

	public void setupInternal() {

		logo = loadImage("data/Lustre_Logo_Alpha.png");
		imageMode(CENTER);

		opc.showLocations(false);
		background(0);
	}

	public void draw() {
		lineDraw();
		fadeToBG();
		image(logo, getWidth() / 4f, getHeight() / 2f, 250, 250);
		image(logo, 3f * getWidth() / 4f, getHeight() / 2f, 250, 250);
	}

	void lineDraw() {
		flameX += step;
		if (flameX > width) {
			flameX = 0;
		}
		flameY = height - 2;

		for (int i = 0; i < 5; i++) {
			lines.add(new Curves((flameX + random(0, 140)) - 35, (flameY + random(0, 50)) - 50));
		}

		noFill();

		for (int i = 0; i < lines.size(); i++) {

			Curves crv = lines.get(i);

			float b = noise(crv.x4 / noiseScale, crv.y4 / noiseScale, nz) * -PI;

			crv.addCoord(cos(b) * line_lenght, sin(b) * line_lenght);

			strokeWeight(20 * (drawCount - crv.n) / drawCount + 1);
			stroke(240 + random(5, 15), 20 + random(0, 50), random(0, 10), 50);

			curve(crv.x1, crv.y1, crv.x2, crv.y2, crv.x3, crv.y3, crv.x4, crv.y4);

			if (crv.n >= drawCount) {
				lines.remove(i);
				i--;
			}
		}
		nz += 0.01;
	}

	void fadeToBG() {
		noStroke();
		fill(0, 10);
		rect(0, 0, width, height);

	}

	class Curves {
		float x1, x2, x3, x4;
		float y1, y2, y3, y4;
		int n = 0;

		Curves(float tmpX, float tmpY) {

			x1 = x2 = x3 = x4 = tmpX;
			y1 = y2 = y3 = y4 = tmpY;
		}

		void addCoord(float tmpdX, float tmpdY) {
			x1 = x2;
			x2 = x3;
			x3 = x4;
			x4 += tmpdX;
			y1 = y2;
			y2 = y3;
			y3 = y4;
			y4 += tmpdY;
			n++;
		}
	}

}
