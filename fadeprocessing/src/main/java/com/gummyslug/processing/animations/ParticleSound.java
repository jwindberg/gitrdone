package com.gummyslug.processing.animations;

import java.util.ArrayList;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.Minim;

public class ParticleSound extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	Flock flock;
	Minim minim;
	AudioInput in;
	float spud = 0;
	
	int initialFlockSize = 500;

	public void setupInternal() {
		// frameRate(90);
		minim = new Minim(this);
		colorMode(RGB, 255, 255, 255, 100);
		in = minim.getLineIn();
		flock = new Flock();
		// Add an initial set of boids into the system

		for (int i = 0; i < initialFlockSize; i++) {
			flock.addBoid(new Boid(new Vector3D(width / 2, height / 2), 1.0f, 0.05f));
		}
		smooth();
	}

	public void draw() {
		background(0);
		flock.run();
	}

	// Add a new boid into the System
	public void mousePressed() {
		fill(255, 40, 40);
		flock.addBoid(new Boid(new Vector3D(mouseX, mouseY), 20.0f, 0.05f));
	}

	class Flock {

		ArrayList<Boid> boids; // An arraylist for all the boids

		Flock() {
			boids = new ArrayList<>(); // Initialize the arraylist
		}

		void run() {

			for (int i = 0; i < boids.size(); i++) {
				Boid b = (Boid) boids.get(i);
				b.run(boids); // Passing the entire list of boids to each boid
								// individually
			}
		}

		void addBoid(Boid b) {
			boids.add(b);
		}
	}

	class Boid {

		Vector3D loc;
		Vector3D vel;
		Vector3D acc;
		float r;
		float maxforce; // Maximum steering force
		float maxspeed; // Maximum speed

		Boid(Vector3D l, float ms, float mf) {
			acc = new Vector3D(0, 0);
			vel = new Vector3D(random(-1, 1), random(-1, 1));
			loc = l.copy();
			r = 2.0f;
			maxspeed = ms;
			maxforce = mf;
		}

		void run(ArrayList<Boid> boids) {
			flock(boids);
			update();
			borders();
			render();
		}

		// We accumulate a new acceleration each time based on three rules
		void flock(ArrayList<Boid> boids) {
			Vector3D sep = separate(boids); // Separation
			Vector3D ali = align(boids); // Alignment
			Vector3D coh = cohesion(boids); // Cohesion
			// Arbitrarily weight these forces
			float mod = in.left.get(32) * 0;
			sep.mult(mod + 20.0f);
			// float mod = in.left.get(32 )*2000;
			ali.mult(mod + 10.0f);
			coh.mult(10.0f);
			// Add the force vectors to acceleration
			acc.add(sep);
			acc.add(ali);
			acc.add(coh);
		}

		// Method to update location
		void update() {
			float mod = in.left.get(32) * 60;
			// Update velocity
			vel.add(acc);
			// Limit speed
			vel.limit(1 + maxspeed + mod);
			loc.add(vel);
			// Reset acceleration to 0 each cycle
			acc.setXYZ(.001f, .001f, random(-.001f, .001f));
		}

		void seek(Vector3D target) {
			acc.add(steer(target, false));
		}

		void arrive(Vector3D target) {
			acc.add(steer(target, true));
		}

		// A method that calculates a steering vector towards a target
		// Takes a second argument, if true, it slows down as it approaches the
		// target
		Vector3D steer(Vector3D target, boolean slowdown) {
			Vector3D steer; // The steering vector
			Vector3D desired = target.sub(target, loc); // A vector pointing
														// from the location to
														// the target
			float d = desired.magnitude(); // Distance from the target is the
											// magnitude of the vector
			// If the distance is greater than 0, calc steering (otherwise
			// return zero vector)
			if (d > 0) {
				// Normalize desired
				desired.normalize();
				// Two options for desired vector magnitude (1 -- based on
				// distance, 2 -- maxspeed)
				if ((slowdown) && (d < 400.0f)) {
					desired.mult(maxspeed * (d / 3.0f)); // This damping is
															// somewhat
															// arbitrary
				} else {
					desired.mult(maxspeed);
				}
				// Steering = Desired minus Velocity
				steer = target.sub(desired, vel);
				steer.limit(maxforce); // Limit to maximum steering force
			} else {
				steer = new Vector3D(0, 0);
			}
			return steer;
		}

		void render() {
			// Draw a triangle rotated in the direction of velocity
			float theta = vel.heading2D() + radians(90);
			 fill(random(200),random(200),random(200),random(200));
//			fill(in.left.get(32) * 1700);
			noStroke();
			pushMatrix();
			translate(loc.x, loc.y);
			rotate(theta);
			float mod = in.left.get(32) * 300;
			ellipse(r, r, 1, 1 + (mod / 2));
			// beginShape(TRIANGLES);
			// vertex(0, -r*2);
			// vertex(-r, r*2);
			// vertex(r, r*2);
			// endShape();
			popMatrix();
		}

		// Wrap around
		void borders() {
			if (loc.x < -r)
				loc.x = width + r;
			if (loc.y < -r)
				loc.y = height + r;
			if (loc.x > width + r)
				loc.x = -r;
			if (loc.y > height + r)
				loc.y = -r;
		}

		// Separation
		// Method checks for nearby boids and steers away
		Vector3D separate(ArrayList<Boid> boids) {
			float desiredseparation = 3.0f;
			Vector3D sum = new Vector3D(0, 0, 0);
			int count = 0;

			// For every boid in the system, check if it's too close
			for (int i = 0; i < boids.size(); i++) {
				Boid other = (Boid) boids.get(i);
				float d = loc.distance(loc, other.loc);
				// If the distance is greater than 0 and less than an arbitrary
				// amount (0 when you are yourself)
				if ((d > 0) && (d < desiredseparation)) {
					// Calculate vector pointing away from neighbor
					Vector3D diff = loc.sub(loc, other.loc);
					diff.normalize();
					diff.div(d); // Weight by distance
					sum.add(diff);
					count++; // Keep track of how many
				}
			}
			// Average -- divide by how many
			if (count > 0) {
				sum.div((float) count);
			}
			return sum;
		}

		// Alignment
		// For every nearby boid in the system, calculate the average velocity
		Vector3D align(ArrayList<Boid> boids) {
			float neighbordist = 20.0f;
			Vector3D sum = new Vector3D(0, 0, 0);
			int count = 0;

			for (int i = 0; i < boids.size(); i++) {
				Boid other = (Boid) boids.get(i);
				float d = loc.distance(loc, other.loc);
				if ((d > 0) && (d < neighbordist)) {
					sum.add(other.vel);
					count++;
				}
			}
			if (count > 0) {
				sum.div((float) count);
				sum.limit(maxforce);
			}
			return sum;
		}

		// Cohesion
		// For the average location (i.e. center) of all nearby boids, calculate
		// steering vector towards that location
		Vector3D cohesion(ArrayList<Boid> boids) {

			float neighbordist = 15.0f;
			Vector3D sum = new Vector3D(0, 0, 0); // Start with empty vector to
													// accumulate all locations
			int count = 0;

			for (int i = 0; i < boids.size(); i++) {
				Boid other = (Boid) boids.get(i);
				float d = loc.distance(loc, other.loc);
				if ((d > 0) && (d < neighbordist)) {
					sum.add(other.loc); // Add location
					count++;
				}
			}

			if (count > 0) {
				sum.div((float) count);
				return steer(sum, false); // Steer towards the location
			}
			return sum;
		}
	}// Simple Vector3D Class

	static class Vector3D {

		float x;
		float y;
		float z;

		Vector3D(float x_, float y_, float z_) {
			x = x_;
			y = y_;
			z = z_;
		}

		Vector3D(float x_, float y_) {
			x = x_;
			y = y_;
			z = 0f;
		}

		Vector3D() {
			x = 0f;
			y = 0f;
			z = 0f;
		}

		void setX(float x_) {
			x = x_;
		}

		void setY(float y_) {
			y = y_;
		}

		void setZ(float z_) {
			z = z_;
		}

		void setXY(float x_, float y_) {
			x = x_;
			y = y_;
		}

		void setXYZ(float x_, float y_, float z_) {
			x = x_;
			y = y_;
			z = z_;
		}

		void setXYZ(Vector3D v) {
			x = v.x;
			y = v.y;
			z = v.z;
		}

		float magnitude() {
			return (float) Math.sqrt(x * x + y * y + z * z);
		}

		Vector3D copy() {
			return new Vector3D(x, y, z);
		}

		Vector3D copy(Vector3D v) {
			return new Vector3D(v.x, v.y, v.z);
		}

		void add(Vector3D v) {
			x += v.x;
			y += v.y;
			z += v.z;
		}

		void sub(Vector3D v) {
			x -= v.x;
			y -= v.y;
			z -= v.z;
		}

		void mult(float n) {
			x *= n;
			y *= n;
			z *= n;
		}

		void div(float n) {
			x /= n;
			y /= n;
			z /= n;
		}

		void normalize() {
			float m = magnitude();
			if (m > 0) {
				div(m);
			}
		}

		void limit(float max) {
			if (magnitude() > max) {
				normalize();
				mult(max);
			}
		}

		float heading2D() {
			float angle = (float) Math.atan2(-y, x);
			return -1 * angle;
		}

		Vector3D add(Vector3D v1, Vector3D v2) {
			Vector3D v = new Vector3D(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
			return v;
		}

		Vector3D sub(Vector3D v1, Vector3D v2) {
			Vector3D v = new Vector3D(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
			return v;
		}

		Vector3D div(Vector3D v1, float n) {
			Vector3D v = new Vector3D(v1.x / n, v1.y / n, v1.z / n);
			return v;
		}

		Vector3D mult(Vector3D v1, float n) {
			Vector3D v = new Vector3D(v1.x * n, v1.y * n, v1.z * n);
			return v;
		}

		float distance(Vector3D v1, Vector3D v2) {
			float dx = v1.x - v2.x;
			float dy = v1.y - v2.y;
			float dz = v1.z - v2.z;
			return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		}
	}

}
