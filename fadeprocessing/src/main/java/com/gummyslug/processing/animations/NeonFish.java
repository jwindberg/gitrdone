package com.gummyslug.processing.animations;

import java.util.ArrayList;
import java.util.List;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PVector;

public class NeonFish extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	private static final int numFish = 12;
	private boolean randomColor = true;

	List<Fish> school = new ArrayList<Fish>();

	@Override
	protected void setupInternal() {
		strokeWeight(20);
		strokeJoin(ROUND);
		stroke(0, 150, 255);
		for (int i = 0; i < numFish; i++) {
			float red = randomColor ? random(0, 255) : 0;
			float green = randomColor ? random(0, 255) : 150;
			float blue = randomColor ? random(0, 255) : 150;
			school.add(new Fish(red, green, blue));
		}
	}

	public void draw() {
		background(0);
		fill(0, 0, 0, 100);
		for (Fish fish : school) {
			fish.draw();
		}
	}

	private class Fish {
		PVector location = new PVector(random(width), random(height));
		PVector velocity = new PVector(random(-1, 1), random(-1, 1));
		private float direction = random(-90, 90);
		private float size = random(0.1f, 0.3f);
		private float red;
		private float green;
		private float blue;

		public Fish(float red, float green, float blue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		void draw() {
			stroke(red, green, blue);
			location.add(velocity);
			pushMatrix();
			translate(location.x, location.y);
			scale(size);
			/* Get the direction and add 90 degrees. */
			rotate(velocity.heading() - radians(90));
			beginShape();
			for (int i = 0; i <= 180; i += 20) {
				float x = sin(radians(i)) * i / 3;
				float angle = sin(radians(i + direction + frameCount * 5)) * 50;
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
				float angle = sin(radians(i + direction + frameCount * 5)) * 50;
				vertex(-x - angle, i * 2);
				vertex(-x - angle, i * 2);
			}
			endShape();
			popMatrix();
			boundaries();
		}

		void boundaries() {

			float offSet = width * size;

			if (location.x < -offSet)
				location.x = width + offSet;
			if (location.x > width + offSet)
				location.x = -offSet;

			if (location.y < -offSet)
				location.y = height + offSet;
			if (location.y > height + offSet)
				location.y = -offSet;
		}
	}

}
