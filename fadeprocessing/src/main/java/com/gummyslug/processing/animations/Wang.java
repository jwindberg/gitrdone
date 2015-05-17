package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class Wang extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	/*
	 * **INSTRUCTIONS** Start with a black plane and a 2500 dots. Make the
	 * points bounce realistically off the walls in avoidance of your mouse.
	 * When the points reach the edge, they will change color.
	 */

	Ball[] ball;
	int objectnumber = 2500;
	int x = 0;
	int y = 0;

	public void setupInternal() {

		ball = new Ball[objectnumber];

		for (int i = 0; i < objectnumber; i++) {
			ball[i] = new Ball(0x940B35, random(50f, 500f), random(10f, 400f), random(1f, 7f), random(1, 5), 10f, 20f);
		}
	}

	public void draw() {
		background(0x280915);

		for (int i = 0; i < objectnumber; i++) {
			ball[i].display();
			ball[i].move();
		}
	}

	class Ball {

		float size = 10;
		float xpos;
		float ypos;
		float xspeed;
		float yspeed;
		float radius;
		float radius2;
		int c;
		int xdirection = 1;
		int ydirection = 1;

		Ball(int c_, float xp, float yp, float xs, float ys, float r, float r2) {
			c = c_;
			xpos = xp;
			ypos = yp;
			xspeed = xs;
			yspeed = ys;
			radius = r;
			radius2 = r2;
		}

		void display() {
			// avoid cursor
			double distance = dist(mouseX, mouseY, xpos, ypos); // distance btwn
																// cursor and
																// ball
			double xdistance = abs(mouseX - xpos); // xdist + ydist between
													// cursor and ball
			double ydistance = abs(mouseY - ypos);
			boolean isxTowards = abs((xpos + (xspeed * xdirection)) - xpos) < xdistance; // is
																							// ball
																							// traveling
																							// toward
																							// x
																							// direction
																							// or
																							// y
																							// direction
			boolean isyTowards = abs((ypos + (yspeed * ydirection)) - ypos) < ydistance;
			boolean hitwall = false;
			if (isxTowards && distance < 50) { // move ball opposite direction
												// of forbidden zone
				xdirection *= -1;
				xpos = xpos + 70 * xdirection;
				if (xpos < 0) {
					xpos = 0;
					xdirection = 1;
					hitwall = true;
				}
				if (xpos > width) {
					xpos = width;
					xdirection = -1;
					hitwall = true;
				}
			}
			if (isyTowards && distance < 50) { // repeat for y direction
				ydirection *= -1;
				ypos = ypos + 70 * ydirection;
				if (ypos < 0) {
					ypos = 0;
					ydirection = 1;
					hitwall = true;
				}
				if (ypos > height) {
					ypos = height;
					ydirection = -1;
					hitwall = true;
				}
			}
			// ellipseMode();
			// color change
			noStroke();
			smooth();
			fill(c);
			ellipse(xpos, ypos, 10, 10);
			if (radius > 10) {
				radius--;
			}
		}

		void move() { // moves balls w/out mouse interference
			xpos = xpos + (xspeed * xdirection);
			ypos = ypos + (yspeed * ydirection);
			if (xpos > width - 10 || xpos < 10) {
				xdirection *= -1;
				radius = radius2;
				float dice = random(0, 4); // random color change RGBY
				int oldC = c; // if if old color repeats, goes to grey
				if (dice >= 0 && dice < 1) {
					c = 0xFFFF00;
				}
				if (dice >= 1 && dice < 2) {
					c = 0xFF0000;
				}
				if (dice >= 2 && dice < 3) {
					c = 0x00FF00;
				}
				if (dice >= 3 && dice < 4) {
					c = 0x0000FF;
				}

			}
			if (ypos > height - 10 || ypos < 10) { // for y direction
				ydirection *= -1;
				radius = radius2;
				int oldC = c;
				float dice = random(0, 4);
				if (dice >= 0 && dice < 1) {
					c = 0xFFFF00;
				}
				if (dice >= 1 && dice < 2) {
					c = 0xFF0000;
				}
				if (dice >= 2 && dice < 3) {
					c = 0x00FF00;
				}
				if (dice >= 3 && dice < 4) {
					c = 0x0000FF;
				}

				if (c == oldC) {
					c = (int) random(0, 255);
				}

			}
		}

	}

}
