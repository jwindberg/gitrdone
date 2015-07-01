package com.gummyslug.processing.animations;

import processing.core.PImage;
import traer.physics.Particle;
import traer.physics.ParticleSystem;

import com.gummyslug.processing.opc.AnimationApplet;

public class SoapBubbles extends AnimationApplet {

	/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/71543*@* */
	/*
	 * !do not delete the line above, required for linking your tweak if you
	 * upload again
	 */
	/*
	 * Repelling Soap Bubbles Jennifer Presto September 22, 2012
	 * 
	 * Object controlled by mouse repels all the other bubbles; bubbles very
	 * mildly repel one another
	 * 
	 * Code adapted from Bouncy Balls by Jeffrey Traer Bernstein, included with
	 * his Processing physics library.
	 * 
	 * Source code can be found here:
	 * http://murderandcreate.com/physics/bouncy_balls/Bouncy_Balls.txt
	 */

	int numParticles = 50;
	Particle mouse;
	Particle[] baseParticles = new Particle[numParticles];
	EachParticle[] allParticles = new EachParticle[numParticles];
	ParticleSystem physics;

	PImage star;

	public void setupInternal() {
		frameRate(46);
		smooth();
		ellipseMode(CENTER);
		rectMode(CENTER);
		noStroke();
		noCursor();
		colorMode(HSB, 360, 100, 100);

		star = loadImage("soapBubbles_cursor.png");

		cursor(star);

		physics = new ParticleSystem();
		mouse = physics.makeParticle();
		mouse.makeFixed();

		// creates the base particles using the physics library
		// these particles will become arguments in the EachParticle constructor
		// method
		for (int i = 0; i < numParticles; i++) {
			baseParticles[i] = physics.makeParticle(1.0f, random(0, width/4.0f), random(0, height), 0);
		}

		// inserts these newly created particles into class
		// to create objects with varying size and color

		for (int i = 0; i < numParticles; i++) {
			allParticles[i] = new EachParticle(color(map(i, 0, numParticles, 230, 360), 100, 100), random(10, 60),
					baseParticles[i]);
		}

		// all particles are repelled from the mouse

		for (int i = 0; i < numParticles; i++) {
			physics.makeAttraction(mouse, baseParticles[i], -2125, 10);
		}

		// all particles have a mild repulsion from one another

		for (int i = 0; i < numParticles - 1; i++) {
			for (int j = i + 1; j < numParticles; j++) {
				physics.makeAttraction(baseParticles[i], baseParticles[j], -50, 5);
			}
		}
	}

	public void draw() {
		background(0);
		mouse.position().set(mouseX, mouseY, 0);

		for (int i = 0; i < numParticles; i++) {
			handleBoundaryCollisions(baseParticles[i]);
		}

		for (int i = 0; i < numParticles; i++) {
			handleBoundaryCollisions(baseParticles[i]);
		}

		physics.tick();

		stroke(0);
		fill(0);

		// line below would create a rectangle at mouse position
		// commented out because cursor is drawn with a star image
		// rect(mouse.position().x(), mouse.position().y(), 35, 35);

		for (int i = 0; i < numParticles; i++) {
			allParticles[i].display();
		}
	}

	// really basic collision strategy:
	// sides of the window are walls
	// if it hits a wall pull it outside the wall and flip the direction of the
	// velocity
	// the collisions aren't perfect so we take them down a notch too
	void handleBoundaryCollisions(Particle p) {
		if (p.position().x() < 0 || p.position().x() > width)
			p.velocity().set(-0.7f * p.velocity().x(), p.velocity().y(), 0);
		if (p.position().y() < 0f || p.position().y() > height)
			p.velocity().set(p.velocity().x(), -0.7f * p.velocity().y(), 0);
		p.position().set(constrain(p.position().x(), 0, width), constrain(p.position().y(), 0, height), 0);
	}

	class EachParticle {
		int shading; // color of particles
		float bigness; // size of particles
		Particle all;

		public EachParticle(int _shading, float _bigness, Particle _all) {
			shading = _shading;
			bigness = _bigness;
			all = _all;
		}

		void display() {
			fill(shading);
			ellipse(all.position().x(), all.position().y(), bigness, bigness);
		}
	}

}
