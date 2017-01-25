package com.gummyslug.processing.animations;

import java.util.ArrayList;
import java.util.List;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PVector;

public class HypnoSnakes extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	private static final int NUM_SEGMENTS = 30;
	private static final int SEGMENT_LENGTH = 5;
	private static final int NUM_SNAKES = 15;
	
	private List<Snake> snakes = new ArrayList<>(NUM_SNAKES);

	public void setupInternal() {
		strokeWeight(3);
		float t = 1;
		for (int i = 0; i < 360; i += NUM_SNAKES) {
			// Angle, Velocity direction
			snakes.add(new Snake(i + NUM_SNAKES / 2, t *= -1));
		}
	}

	public void draw() {
		background(20);
		for (int i = 0; i < snakes.size(); i++) {
			Snake s = (Snake) snakes.get(i);
			s.draw();
			s.move();
		}
	}

	class Snake {
		PVector[] pos = new PVector[NUM_SEGMENTS];
		PVector loc;

		float index, dir;

		Snake(float ind, float dir) {
			loc = new PVector(width / 2, height / 2);
			index = ind;
			this.dir = dir;
			for (int i = 0; i < NUM_SEGMENTS; i++) {
				pos[i] = new PVector(i, i);
			}
		}

		void draw() {
			dragSegment(0, loc);
			for (int i = 0; i < pos.length - 1; i++) {
				dragSegment(i + 1, pos[i]);
			}
		}

		void move() {
			float a = sin(radians((frameCount * dir) * 6)) * 120;
			// Set velocity here as it does not need to be accessed outside the
			// object function
			PVector vel = new PVector(sin(radians((frameCount * dir) + a + index)) * 5,
					cos(radians((frameCount * dir) + a + index)) * 5);
			loc.add(vel);
		}

		void dragSegment(int i, PVector loc) {
			float angle = atan2(loc.y - pos[i].y, loc.x - pos[i].x);
			pos[i].x = loc.x - cos(angle) * SEGMENT_LENGTH;
			pos[i].y = loc.y - sin(angle) * SEGMENT_LENGTH;

			pushMatrix();
			translate(pos[i].x, pos[i].y);
			rotate(atan2(loc.y - pos[i].y, loc.x - pos[i].x));
			// Change red and green but leave blue 125
			stroke(125 + sin(radians(i * 10 + frameCount)) * 125, 125 + cos(radians(i * 10 - frameCount)) * 125, 125);
			line(0, 0, SEGMENT_LENGTH, 0);
			popMatrix();
		}
	}

}
