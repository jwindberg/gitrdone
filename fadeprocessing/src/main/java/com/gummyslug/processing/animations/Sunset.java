package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class Sunset extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	/*
	 * OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/114116*@*
	 */
	/*
	 * !do not delete the line above, required for linking your tweak if you
	 * upload again
	 */
	float x, y;
	float y1, y2;
	float h, s, b, a;
	float sw;

	public void setupInternal() {

		colorMode(HSB);
		background(0x025BA7);

		ellipseMode(CENTER);

		noCursor();
	}

	public void draw() {

		noStroke();
		fill(y / height * 255, 128, 64, 2);
		rect(0f, 0f, width, height * .17f);

		// sun
		fill(4, 196, 255, 16);
		noStroke();
		// stroke(255,1);
		ellipse(width * .5f + random(-5f, 5f), height * .1f + random(-5f, 5f), random(height * .07f, height * .11f),
				random(height * .07f, height * .11f));

		// sun reflection
		fill(4, 196, 255, 8);
		noStroke();
		ellipse(width * .5f + random(-22f, 22f), height * .55f + random(-5f, 5f),
				random(height * .044f, height * .077f), random(height * .77f, height * .88f));

		// sun bursts
		noStroke();

		a = map(y, 0, height, 3, 2) + random(-1, 1);
		s = 211;

		h = map(y, 0, height, 16, 8) + random(-5, 5);
		b = 128;

		fill(h, s, 255f, a * .5f);
		ellipse(width * .5f, height * .17f + random(-5f, 5f), width * 1.4f, y * .17f);

		a *= .5;
		s *= .4;
		fill(255 - h, s, b, a);
		ellipse(width * .5f, height * .1f, width * 2f, y * 4f);

		horzLine(77, .17f);

		// horizon
		stroke(4, 196, 255, random(1, 11));
		line(0f, height * .17f + random(-33f, 33f), width, height * .15f + random(-33f, 33f));

		y += .4;
		if (y > height)
			y = 0;

	}

	void horzLine(int amount, float topY) {
		for (int i = 0; i < amount; i++) {

			y1 = random(height * topY, height);

			h = map(y1, 0, height, 96, 8); // random(96, 128);
			s = map(y1, 0, height, 8, 255); // random(22, 255);
			b = map(y1, 0, height, 64, 196); // random(22, 255);
			a = map(y1, 0, height, 44, 11);

			stroke(h, s, b, a);

			x = random(width);

			// y2 = random(height*topY, height*1.5);

			// line(x, y1 , x+random(-10,10), y2 );

			strokeWeight(random(8));
			line(0, y1 + random(-33, 33), width, y1 + random(-33, 33));
		}
	}

}
