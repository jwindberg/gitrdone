package com.gummyslug.processing.animations;

import java.util.ArrayList;

import com.gummyslug.processing.opc.AnimationApplet;

public class FallingSnowflakes extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/39064*@* */
	/*
	 * !do not delete the line above, required for linking your tweak if you
	 * upload again
	 */
	int numStars = 5;
	int minStarSize = 50;
	int maxStarSize = 100;
	String graphicsMode = P2D;
	ArrayList<FallingStar> fallingChars;

	public void setupInternal() {
		frameRate(30);

		colorMode(RGB, 255, 255, 255, 255);
		fallingChars = new ArrayList<FallingStar>(); // Create an empty
														// ArrayList
		for (int i = 0; i < numStars; i++)
			// now add some elements for initial seeding
			createChar(1);
	}

	public void draw() {
		background(0);
		for (int i = fallingChars.size() - 1; i >= 0; i--) {
			FallingStar fc = fallingChars.get(i);
			if (fc.notVisible()) {
				fallingChars.remove(i);
				createChar(8);
			} else {
				fc.fall();
				fc.display();
			}
		}
		// if ((frameCount & 2) > 0) {
		// int x = (int) random(4);
		// for (int j = 0; j < x; j++) {
		// createChar(4); // top 1/4th
		// createChar(8); // top 1/8th
		// }
		// }
	}

	class FallingStar {
		int mx;
		float my;
		int mAlpha;
		float mSize;
		float mRotate;
		int mnPoints;

		// constructor
		FallingStar(int x, int y) {
			mx = x;
			my = y;
			mAlpha = 255;
			mSize = random(minStarSize, maxStarSize);
			mRotate = random(TWO_PI);
			mnPoints = 5 + (int) random(5);
		}

		boolean notVisible() {
			if (my < 0) {
				return false;
			} else
				return (my > height) || (mAlpha < 0) || (mSize < 1.5);
		}

		void display() {
			if (!notVisible()) {
				fill(color(255, 255, 255), mAlpha);
				pushMatrix();
				translate(mx - mSize / 2, my - mSize / 2);
				rotate(mRotate);
				star(mnPoints, mSize, mSize / 4);
				popMatrix();
			}
		}

		void fall() {
			mAlpha = mAlpha - 1;
			my = my + 1 + mSize / 5;
			mSize = mSize * 0.99f;
		}
	}

	void createChar(int n) {
		int x = (int) random(width);
		int y = (int) random(height / n);
		if (n == 1) // Start by adding some elements to whole screen
			fallingChars.add(new FallingStar(x, y));
		else
			// top-up
			fallingChars.add(new FallingStar(x, -y)); // Start by adding some
														// elements
	}

	void star(int nPoints, float rad1, float rad2) {
		float angle = TWO_PI / nPoints;
		float angle2 = angle / 2;
		float origAngle = 0.0f;
		beginShape();
		fill(255, 255, 255);
		stroke(255, 255, 255);
		strokeWeight(1);
		for (int i = 0; i < nPoints; i++) {
			float y1 = rad1 * sin(origAngle);
			float x1 = rad1 * cos(origAngle);
			float y2 = rad2 * sin(origAngle + angle2);
			float x2 = rad2 * cos(origAngle + angle2);
			vertex(x1, y1);
			vertex(x2, y2);
			origAngle += angle;
		}
		endShape(CLOSE);
	}

}
