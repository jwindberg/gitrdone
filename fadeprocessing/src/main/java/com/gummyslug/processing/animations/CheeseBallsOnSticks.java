package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PConstants;

public class CheeseBallsOnSticks extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	int wnum = 20;
	windmill[] wm = new windmill[wnum];
	float fr = 0;

	public void setupInternal() {
		background(0);
		fill(255);

		translate(width / 2, height / 2);
		for (int j = 0; j < wnum; j++) {
			wm[j] = new windmill(2 * PI * j / wnum);
		}
	}

	protected String getRenderer() {
		return PConstants.P3D;
	}

	public void draw() {

		ambientLight(200, 80, 0);
		translate(width / 2, height / 2);
		pointLight(255, 100, 0, 0, 0, 0);
		background(0);
		for (int k = 0; k < wnum; k++) {
			pushMatrix();
			rotateZ(fr / 3 + k * 2 * PI / wnum);
			translate(100, 0);
			wm[k].show();
			popMatrix();
		}
		fr = fr + 0.01f;
	}

	class windmill {
		int r = 50;
		float fdeg;

		windmill(float _fdeg) {
			fdeg = _fdeg;
		}

		void show() {
			for (int i = 0; i < 4; i++) {

				pushMatrix();
				rotateY(fdeg + fr);
				stroke(255);
				line(0, 0, r, 0);
				translate(r, 0);
				ellipse(7.5f, 0f, 1f, 1f);
				noStroke();
				sphere(15);
				popMatrix();
				rotateY(PI / 2);
			}

		}
	}

}
