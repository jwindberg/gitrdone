package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class SunriseSunset extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	/*
	 * OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/25762*@*
	 */
	/*
	 * !do not delete the line above, required for linking your tweak if you
	 * upload again
	 */
	float decolores = random(1);
	float angle = random(0.0f, 40.0f);

	public void setupInternal() {
		smooth();
		// frameRate(70);
	}

	public void draw() {
		background(0, 0, 255);

		for (int i = 0; i < 100; i = i + 1) {
			// constrain(mouseX/2,100,100);
			rotate(2);
			fill(0, random(100, 255), 0);
			triangle(300, 300, i * 19, 600, 500, 300);

			translate(mouseX, mouseY);
			fill(200, random(0, 100), 0);
			rotate(1.1f);
			triangle(250, 250, 200, 400, i - 2, 300);

			// noStroke();
			fill(random(25, 255), 0, 200);
			// 245,7,214 pink

			angle += 0.9;
			ellipse(800, 600, 30, 30);
		}
	}

	public void keyPressed() {

		// if (key=='s');{
		// saveImage();
		// }
		if (key == 'i') {
			filter(INVERT);
		}
		if (key == 'b') {
			filter(BLUR, 2);
		}
		if (key == 'g') {
			filter(GRAY);
		}

		if (key == 'e') {
			filter(ERODE);
		}
	}

}
