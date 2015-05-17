package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class Fruits extends AnimationApplet {

	private static final long serialVersionUID = 1L;
	Fruit[] fruits = new Fruit[10];

	int[] hue = { 10, 40, 60, 90 };

	public void setupInternal() {

		colorMode(HSB, 360, 100, 100);
		background(360);
		noStroke();

		for (int i = 0; i < fruits.length; i++) {
			fruits[i] = new Fruit(random(width), random(height), hue[(int) random(4f)], random(0.1f, 0.5f));
		}
	}

	public void draw() {
		background(0);

		for (int i = 0; i < fruits.length; i++) {
			fruits[i].display();
			fruits[i].move();
		}
	}

	class Fruit {
		float x;
		float y;
		int hue;
		float S;

		float theta = random(TWO_PI);
		float theta_spd;

		float spd = random(0.3f, 1.0f);
		float x_spd = 0;
		float y_spd = 0;

		Fruit(float tmpX, float tmpY, int tmpHue, float tmpS) {
			x = tmpX;
			y = tmpY;
			hue = tmpHue;
			S = tmpS;

			switch ((int) random(4)) {
			case 0:
				x_spd = spd;
				theta_spd = spd;
				break;
			case 1:
				x_spd = -spd;
				theta_spd = -spd;
				break;
			case 2:
				y_spd = spd;
				theta_spd = spd;
				break;
			case 3:
				y_spd = -spd;
				theta_spd = -spd;
				break;
			}
		}

		void display() {
			pushMatrix();
			translate(x, y);
			rotate(radians(theta));

			fill(hue, 50, 100);
			ellipse(0, 0, 200 * S, 200 * S);
			fill(hue, 10, 100);
			ellipse(0, 0, 180 * S, 180 * S);

			fill(hue, 80, 100);
			for (int i = 0; i < 10; i++) {
				pushMatrix();
				translate(0, 0);
				rotate(radians(360 * i / 10));

				beginShape();
				vertex(0, -10 * S);
				vertex(-20 * S, -70 * S);
				vertex(20 * S, -70 * S);
				endShape();
				ellipse(0, -70 * S, 40 * S, 20 * S);
				popMatrix();
			}
			popMatrix();
		}

		void move() {
			x += x_spd;
			y += y_spd;

			theta += theta_spd;

			if (x > width + 100 * S) {
				x = -100 * S;
			}
			if (x < -100 * S) {
				x = width + 100 * S;
			}
			if (y > height + 100 * S) {
				y = -100 * S;
			}
			if (y < -100 * S) {
				y = height + 100 * S;
			}
		}
	}

}
