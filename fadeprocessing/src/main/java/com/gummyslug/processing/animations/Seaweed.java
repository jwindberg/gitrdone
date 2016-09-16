package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PVector;

public class Seaweed extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	final int nb = 10;
	SeaWeed[] weeds = new SeaWeed[nb];
	PVector rootNoise = new PVector(random(123456), random(123456));
	int mode = 0;

	@Override
	protected void setupInternal() {
		for (int i = 0; i < nb; i++) {
			weeds[i] = new SeaWeed(random(0, width), height);
		}

	}

	public void draw() {
		background(0);
		background(50);
		for (int i = 0; i < nb; i++) {
			weeds[i].update();
		}
	}

	public void keyPressed() {
		mode = (mode + 1) % 2;
	}

	class SeaWeed {
		final static float DIST_MAX = 4;// length of each segment
		final static float maxNbSeg = 125;// max number of segments
		final static float minNbSeg = 90;// min number of segments
		final static float maxWidth = 30;// max width of the base line
		final static float minWidth = 11;// min width of the base line
		final static float FLOTATION = 5;// flotation constant
		float mouseDist;// mouse interaction distance
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
			mouseDist = map(nbSegments, minNbSeg, maxNbSeg, 25, 60);
			for (int i = 0; i < nbSegments; i++) {
				pos[i] = new PVector(p_x, p_y - i * DIST_MAX);
				cols[i] = myCol.getColor();
			}
		}

		void update() {
			rootNoise.add(new PVector(.02f, .02f));
			PVector mouse = new PVector(mouseX, mouseY);

			pos[0] = new PVector(x, height);
			for (int i = 1; i < nbSegments; i++) {
				float n = noise(rootNoise.x + .003f * pos[i].x, rootNoise.y + .003f * pos[i].y);
				float noiseForce = (.3f - n) * 4;
				pos[i].x += noiseForce;
				pos[i].y -= FLOTATION;

				// mouse interaction
				float d = PVector.dist(mouse, pos[i]);
				if (d < mouseDist)// && pmouseX != mouseX && abs(pmouseX -
									// mouseX) < 12)
				{
					PVector tmpPV = mouse.get();
					tmpPV.sub(pos[i]);
					tmpPV.normalize();
					tmpPV.mult(mouseDist);
					tmpPV = PVector.sub(mouse, tmpPV);
					pos[i] = tmpPV.get();
				}

				PVector tmp = PVector.sub(pos[i - 1], pos[i]);
				tmp.normalize();
				tmp.mult(DIST_MAX);
				pos[i] = PVector.sub(pos[i - 1], tmp);
			}

			myCol.update();
			cols[0] = myCol.getColor();
			for (int i = 0; i < nbSegments; i++) {
				if (i > 0) {
					float t = atan2(pos[i].y - pos[i - 1].y, pos[i].x - pos[i - 1].x) + PI / 2;
					float l = map(i, 0, nbSegments - 1, map(nbSegments, minNbSeg, maxNbSeg, minWidth, maxWidth), 1);
					float c = cos(t) * l;
					float s = sin(t) * l;
				}
			}

			if (mode == 1)
				beginShape();
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
			if (mode == 1)
				endShape();
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

}
