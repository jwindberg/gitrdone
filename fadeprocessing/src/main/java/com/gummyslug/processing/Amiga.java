package com.gummyslug.processing;

import processing.core.PVector;

import com.gummyslug.processing.opc.AnimationApplet;

public class Amiga extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	float RADIUS = 150.0f;
	float STEP_LONGITUDE = 22.5f;
	float STEP_LATITUDE = 22.5f;

	PVector PREV = new PVector(RADIUS + 3f, RADIUS + 3f, 2.0f);
	PVector POS = new PVector(RADIUS + 5f, RADIUS + 5f, 2.0f);
	float ROT = 0f;

	public void setupInternal() {
		textureMode(NORMAL);
		sphereDetail(8);
		frameRate(30);
	}

	public void draw() {
		background(0);

		PVector temp = new PVector(POS.x, POS.y, POS.z);

		POS.x = POS.x + (POS.x - PREV.x);
		POS.y = POS.y + (POS.y - PREV.y);
		PREV.x = temp.x;
		PREV.y = temp.y;

		ROT += 2;

		if (POS.x + RADIUS >= getWidth()) {
			POS.x = getWidth() - RADIUS;
			PREV.x = (getWidth() - RADIUS) + (POS.x - PREV.x);
		}
		if (POS.x - RADIUS <= 0) {
			POS.x = RADIUS;
			PREV.x = RADIUS - (PREV.x - POS.x);
		}

		if (POS.y + RADIUS >= getHeight()) {
			POS.y = getHeight() - RADIUS;
			PREV.y = (getHeight() - RADIUS) + (POS.y - PREV.y);
		}
		if (POS.y - RADIUS <= 0) {
			POS.y = RADIUS;
			PREV.y = RADIUS - (PREV.y - POS.y);
		}

		drawBall(POS, ROT);

	}

	void drawBall(PVector pos, float rot) {
		pushMatrix();
		translate(pos.x, pos.y, pos.z);
		rotateY(radians(rot));
		boolean colorToggle = false;
		for (int i = 0; i < 180 / STEP_LATITUDE; i++) {
			drawBallBand(i * STEP_LATITUDE, (i + 1) * STEP_LATITUDE, colorToggle);
			colorToggle = !colorToggle;
		}
		popMatrix();
	}

	void update() {

	}

	void drawBallBand(float long_lo, float long_hi, boolean colorToggle) {

		noStroke();

		PVector vert_ne, vert_nw, vert_se, vert_sw;

		vert_ne = new PVector(0, 0, 0);
		vert_nw = new PVector(0, 0, 0);
		vert_se = new PVector(0, 0, 0);
		vert_sw = new PVector(0, 0, 0);

		for (float lat_deg = 0; lat_deg <= (360 - STEP_LATITUDE); lat_deg += STEP_LATITUDE) {
			if (colorToggle)
				fill(0.8f * 255f, 0.1f * 255f, 0.1f * 255f);
			else
				fill(0.95f * 255f, 0.95f * 255f, 0.95f * 255f);

			colorToggle = !colorToggle;

			float long_hi_rad = radians(long_hi);
			float long_lo_rad = radians(long_lo);

			vert_ne.y = vert_nw.y = (float) cos(long_hi_rad) * RADIUS;
			vert_sw.y = vert_se.y = (float) cos(long_lo_rad) * RADIUS;

			/*
			 * Assign each X,Z with sin,cos values scaled by latitude radius
			 * indexed by longitude. Eg, long=0 and long=180 are at the poles,
			 * so zero scale is sin(longitude), while long=90 (sin(90)=1) is at
			 * equator.
			 */

			float lat_deg_rad = radians(lat_deg);
			float lat_deg_step = radians(lat_deg + STEP_LATITUDE);
			float long_lo_step = radians(long_lo + STEP_LONGITUDE);

			vert_ne.x = (float) cos(lat_deg_rad) * (RADIUS * (float) sin(long_lo_step));
			vert_se.x = (float) cos(lat_deg_rad) * (RADIUS * (float) sin(long_lo_rad));
			vert_nw.x = (float) cos(lat_deg_step) * (RADIUS * (float) sin(long_lo_step));
			vert_sw.x = (float) cos(lat_deg_step) * (RADIUS * (float) sin(long_lo_rad));

			vert_ne.z = (float) sin(lat_deg_rad) * (RADIUS * (float) sin(long_lo_step));
			vert_se.z = (float) sin(lat_deg_rad) * (RADIUS * (float) sin(long_lo_rad));
			vert_nw.z = (float) sin(lat_deg_step) * (RADIUS * (float) sin(long_lo_step));
			vert_sw.z = (float) sin(lat_deg_step) * (RADIUS * (float) sin(long_lo_rad));

			// PVector vert_norm = vert_ne.cross(vert_nw);
			beginShape();

			vertex(vert_ne.x, vert_ne.y, vert_ne.z);
			vertex(vert_nw.x, vert_nw.y, vert_nw.z);
			vertex(vert_sw.x, vert_sw.y, vert_sw.z);
			vertex(vert_se.x, vert_se.y, vert_se.z);

			endShape();
		}
	}

}
