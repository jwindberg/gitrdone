package com.gummyslug.processing.animations;

import java.util.ArrayList;
import java.util.List;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PVector;

public class OceanFish extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	List<Fish> school = new ArrayList<Fish>();
	float y;

	@Override
	protected void setupInternal() {

		y = height * .25f;

		for (int i = 0; i < 10; i++) {
			school.add(new Fish());
		}
	}

	public void draw() {
		background(0);
		wave();
		fish();
	}

	public void fish() {
		strokeWeight(30);
		strokeJoin(ROUND);

		for (Fish fish : school) {
			fish.draw();
			fish.boundaries();
		}
	}

	public void wave() {
		smooth();
		noFill();
		strokeWeight(1);
		stroke(30, 100, 150);

		for (int i = 0; i < 100; i += 1) {
			beginShape();
			vertex(0, height);
			for (int x = 0; x < width; x += 4) {

				vertex(x, y - i + (noise(x * .01f, (frameCount * .01f), i * .01f) * 400));

			}
			vertex(width, height);
			endShape();

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
			rotate(velocity.heading2D() - radians(90));
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
				// for (int i = 0; i < 180; i+=20){
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
