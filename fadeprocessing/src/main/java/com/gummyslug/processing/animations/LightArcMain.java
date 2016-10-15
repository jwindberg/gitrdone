package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class LightArcMain extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	int r;
	int g;
	int b;
	int c;

	int numOfArcs = 20;
	int strokeWidth = 10;
	int rDist = 0;
	LightArc lArcsMobile[] = new LightArc[numOfArcs];

	public void setupInternal() {
		noFill();
		smooth();
		strokeWeight(strokeWidth);
		strokeCap(SQUARE);
		for (int i = 0; i < numOfArcs; i++) {
			r = round(random(0, 255));
			g = round(random(0, 255));
			b = round(random(0, 255));
			c = color(r, g, b);
			rDist = i * 20;
			lArcsMobile[i] = new LightArc(rDist, c);
		}
	}

	public void draw() {
		background(0);
		for (int j = 0; j < numOfArcs; j++) {
			lArcsMobile[j].update();
		}
	}

	class LightArc {
		int randAlph;
		int dia;
		float arcStartDeg;
		float arcStopDeg;
		float arcStartRad;
		float arcStopRad;
		float arcLength;
		int xPos;
		int yPos;
		float speed;
		int arcColour;
		int direction;

		LightArc(int tempDia, int c) {
			arcStartDeg = random(0, 360);
			arcLength = random(0, 360);
			arcStopDeg = arcStartDeg + arcLength;
			dia = tempDia;
			arcColour = c;
			direction = round(random(0, 1));
			speed = random(0.1f, 1f);
		}

		void update() {
			arcStartRad = radians(arcStartDeg);
			arcStopRad = radians(arcStopDeg);
			stroke(arcColour);
			arc(width / 2, height / 2, dia, dia, arcStartRad, arcStopRad);
			if (direction > 0) {
				arcStartDeg += speed;
				arcStopDeg += speed;
			} else {
				arcStartDeg -= speed;
				arcStopDeg -= speed;
			}
		}
	}

}
