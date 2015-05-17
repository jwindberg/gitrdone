package com.gummyslug.processing;

import processing.core.PApplet;

public class SpiroGraph extends PApplet {

	private static final long serialVersionUID = 1L;

	// By Adam Lastowka
	int num = 1;
	int loo = height * num;
	float ro;
	float rt;
	spring[] springs = new spring[loo];

	public void setup() {
		background(0, 0, 0);
		size(700, 700, P2D);
		colorMode(HSB);
		smooth();
		background(0, 0, 0);
		for (int i = 0; i < loo; i++) {
			springs[i] = new spring(0, i / num);
		}
	}

	public void draw() {
		if (keyPressed && key != 'p') {
			background(0, 0, 0);
		}
		for (int i = 0; i < loo; i++) {
			if (mousePressed) {
				springs[i].calc();
			}
			if (!mousePressed) {
				springs[i].slow();
			}
			springs[i].render();
			if (keyPressed && key == 'r') {
				springs[i] = new spring(0, i / num);
			}
		}
		if (keyPressed && key == 'p') {
			saveFrame("output.png");
		}
	}

	class spring {
		float Xpos;
		float Ypos;
		float Xvel;
		float Yvel;
		float Pxpos;
		float Pypos;
		float colorness;

		spring(int tempXpos, int tempYpos) {
			Xpos = tempXpos;
			Ypos = tempYpos;
		}

		void render() {
			Xpos = (Xpos + Xvel);
			Ypos = (Ypos + Yvel);
			stroke(colorness, 100, 255, 20);
			line(Xpos, Ypos, Pxpos, Pypos);
			Pxpos = Xpos;
			Pypos = Ypos;
			colorness = dist(mouseX, mouseY, Xpos, Ypos) / 2;
		}

		void slow() {
			Xvel = Xvel / 1.1f;
			Yvel = Yvel / 1.1f;
		}

		void calc() {
			Xvel = Xvel + (10000f / dist(mouseX, mouseY, Xpos, Ypos) * ((0.009f * (mouseX - Xpos))) / 50);
			Yvel = Yvel + (10000f / dist(mouseX, mouseY, Xpos, Ypos) * ((0.009f * (mouseY - Ypos))) / 50);
		}
	}

}
