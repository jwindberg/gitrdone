package com.gummyslug.processing.animations;

import java.util.LinkedList;
import java.util.List;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PVector;

public class SeaweedWithFish extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	final int weedCount = 10;
	final int fishCount = 10;
	List<SeaWeed> weeds = new LinkedList<>();
	List<Fish> school = new LinkedList<>();
	PVector rootNoise = new PVector(random(123456), random(123456));
	int mode = 0;

	@Override
	protected void setupInternal() {
		for (int i = 0; i < weedCount; i++) {
			weeds.add(new SeaWeed(random(0, width), height));
		}

		for (int i = 0; i < 10; i++) {
			school.add(new Fish());
		}

	}

	public void draw() {
		background(0);
		strokeWeight(1);
		for (SeaWeed weed : weeds) {
			weed.update();
		}
		strokeWeight(30);
		strokeJoin(ROUND);
		stroke(0, 150, 255);
		fill(0);
		for (Fish fish : school) {
			fish.draw();
			fish.boundaries();
		}
	}

	public void keyPressed() {
		mode = (mode + 1) % 2;
	}

	class SeaWeed {
		final static float DIST_MAX = 4;// length of each segment
		
		final static float minNbSeg = 20;// min number of segments
		final static float maxNbSeg = 70;// max number of segments
	
	
		final static float FLOTATION = 5;
		int nbSegments = (int) random(minNbSeg, maxNbSeg);// number of segments
		PVector[] pos;// position of each segment
		int[] cols;// colors array, one per segment
		MyColor myCol = new MyColor();
		PVector rootNoise = new PVector(random(123456), random(123456));// noise
																		// water
																		// effect
		float x;// x origin of the weed

		SeaWeed(float p_x, float p_y) {
			pos = new PVector[nbSegments];
			cols = new int[nbSegments];
			x = p_x;
			for (int i = 0; i < nbSegments; i++) {
				pos[i] = new PVector(p_x, p_y - i * DIST_MAX);
				cols[i] = myCol.getColor();
			}
		}

		void update() {
			rootNoise.add(new PVector(.02f, .02f));

			pos[0] = new PVector(x, height);
			for (int i = 1; i < nbSegments; i++) {
				float n = noise(rootNoise.x + .003f * pos[i].x, rootNoise.y + .003f * pos[i].y);
				float noiseForce = (.3f - n) * 4;
				pos[i].x += noiseForce;
				pos[i].y -= FLOTATION;

				PVector tmp = PVector.sub(pos[i - 1], pos[i]);
				tmp.normalize();
				tmp.mult(DIST_MAX);
				pos[i] = PVector.sub(pos[i - 1], tmp);
			}

			myCol.update();
			cols[0] = myCol.getColor();

			if (mode == 1) {
				beginShape();
			}
			for (int i = 0; i < nbSegments; i++) {
				if (mode == 0) {
					stroke(0, 100);
					fill(cols[i]);
					float r = (30 * cos(map(i, 0, nbSegments - 1, 0, HALF_PI)));
					ellipse(pos[i].x, pos[i].y + 10, r, r);
				} else {
					noFill();
					stroke(cols[i]);
					strokeWeight(nbSegments - i + (int) (30 * cos(map(i, 0, nbSegments - 1, 0, HALF_PI))));
					curveVertex(pos[i].x, pos[i].y + 10);
				}
			}
			if (mode == 1) {
				endShape();
			}
		}
	}

	class MyColor {
		float R, G, B, Rspeed, Gspeed, Bspeed;
		final static float minSpeed = .2f;
		final static float maxSpeed = .8f;
		final float minG = 120;

		MyColor() {
			init();
		}

		public void init() {
			R = random(0, minG);
			G = random(minG, 255);
			B = random(0, minG);
			Rspeed = (random(1) > .5 ? 1 : -1) * random(minSpeed, maxSpeed);
			Gspeed = (random(1) > .5 ? 1 : -1) * random(minSpeed, maxSpeed);
			Bspeed = (random(1) > .5 ? 1 : -1) * random(minSpeed, maxSpeed);
		}

		public void update() {
			Rspeed = ((R += Rspeed) > minG || (R < 0)) ? -Rspeed : Rspeed;
			Gspeed = ((G += Gspeed) > 255 || (G < minG)) ? -Gspeed : Gspeed;
			Bspeed = ((B += Bspeed) > minG || (B < 0)) ? -Bspeed : Bspeed;
		}

		public int getColor() {
			return color(R, G, B);
		}
	}

	class Fish {
		PVector location;
		PVector velocity;
		/* Just to add some individuality to the fish wiggle */
		float s = random(-90, 90);
		float d = random(0.2f, 0.4f);

		float red = random(0, 255);
		float green = random(0, 255);
		float blue = random(0, 255);

		Fish() {
			location = new PVector(random(width), random(height));
			/* Make a random velocity */
			velocity = new PVector(random(-1, 1), random(-1, 1));
		}

		void draw() {
			stroke(red, green, blue);
			// fill(red, green, blue, 100);
			location.add(velocity);
			pushMatrix();
			translate(location.x, location.y);
			scale(d);
			/* Get the direction and add 90 degrees. */
			rotate(velocity.heading() - radians(90));
			beginShape();
			for (int i = 0; i <= 180; i += 20) {
				float x = sin(radians(i)) * i / 3;
				float angle = sin(radians(i + s + frameCount * 5)) * 50;
				vertex(x - angle, i * 2);
				vertex(x - angle, i * 2);
			}
			/*
			 * Started from the top now we are here. We need to now start to
			 * draw from where the first for loop left off. Otherwise un ugly
			 * line will appear down the middle. To see what I mean uncomment
			 * the below line and comment the other line.
			 */
			for (int i = 180; i >= 0; i -= 20) {
				float x = sin(radians(i)) * i / 3;
				float angle = sin(radians(i + s + frameCount * 5)) * 50;
				vertex(-x - angle, i * 2);
				vertex(-x - angle, i * 2);
			}
			endShape();
			popMatrix();
		}

		void boundaries() {
			/* Instead of changing the velocity when the fish */
			if (location.x < -100)
				location.x = width + 100;
			if (location.x > width + 100)
				location.x = -100;
			if (location.y < -100)
				location.y = height + 100;
			if (location.y > height + 100)
				location.y = -100;
		}
	}

}
