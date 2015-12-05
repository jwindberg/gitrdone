package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PImage;

public class LustreKaleidoscope04 extends AnimationApplet {

	private static final long serialVersionUID = 1L;
	Fruit[] fruits = new Fruit[10];

	int[] hue = { 10, 40, 60, 90 };

	private PImage logo;

	public void setupInternal() {

		logo = loadImage("data/Lustre_Logo_Alpha.png");

		colorMode(HSB, 360, 100, 100);
		background(360);
		noStroke();

		for (int i = 0; i < fruits.length; i++) {
			fruits[i] = new Fruit(random(width), random(height), random(0.1f, 0.5f));
		}
	}

	public void draw() {
		background(0);
		imageMode(CENTER);

		for (int i = 0; i < fruits.length; i++) {
			fruits[i].display();
			fruits[i].move();
		}
	}

	class Fruit {
		float x;
		float y;
		float S;

		float theta = random(TWO_PI);
		float theta_spd;

		float spd = random(0.3f, 1.0f);
		float x_spd = 0;
		float y_spd = 0;

		Fruit(float tmpX, float tmpY, float tmpS) {
			x = tmpX;
			y = tmpY;
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

			image(logo, 0, 0, 400 * S, 400 * S);

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
