package com.gummyslug.processing.animations;

import processing.core.PConstants;

import com.gummyslug.processing.opc.AnimationApplet;

public class RotatingSphere extends AnimationApplet {

	private static final long serialVersionUID = 1L;
	float a = 0;
	float b = 0;
	float changeb = 3;

	protected String getRenderer() {
		return PConstants.P3D;
	}

	public void setupInternal() {
		noCursor();
	}

	public void draw() {
		background(0);
		translate(mouseX, mouseY, -50);
		strokeWeight(2);
		// noStroke();
		rotateX(a);
		rotateY(a);
		rotateZ(a);
		a = a + .05f;
		if (mousePressed == true) {
			fill(255, 0, 255, 50);
			sphere(b);
			b = b + changeb;
			{
				if (b > width - 150) {
					changeb = -3;
				}
				if (b < 0) {
					changeb = 3;
				}
			}
		} else {
			fill(255, 255, 255, 50);
			sphere(100);
		}
	}

}
