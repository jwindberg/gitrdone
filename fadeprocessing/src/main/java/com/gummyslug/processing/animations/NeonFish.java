package com.gummyslug.processing.animations;

import java.util.ArrayList;
import java.util.List;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PVector;

public class NeonFish extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	List<Fish> school = new ArrayList<Fish>();

	@Override
	protected void setupInternal() {
		/* Gives a nice glow feel */
		strokeWeight(30);
		/*
		 * IMPORTANT: because we are using vertex's to make our fish, the line
		 * joining becomes spiky when the strokeWeight is bigger than 1.
		 */
		strokeJoin(ROUND);
		stroke(0, 150, 255);
		/* Add 100 fish */
		for (int i = 0; i < 10; i++) {
			school.add(new Fish());
		}
	}

	public void draw() {
		background(0);
		fill(0, 30);
//		rect(-10, -10, width + 20, height + 20);
		// fill(0, 150, 255, 20);
		fill(0, 0, 0);
		fill(0, 0, 0, 100);
		for (Fish fish : school) {
			fish.draw();
			fish.boundaries();
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
//			fill(red, green, blue, 100);
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
