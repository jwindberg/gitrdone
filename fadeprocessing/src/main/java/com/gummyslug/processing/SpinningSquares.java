package com.gummyslug.processing;

import com.gummyslug.processing.opc.OPC;
import com.gummyslug.processing.opc.OPC.Layout;

import processing.core.PApplet;

public class SpinningSquares extends PApplet {

	private static final long serialVersionUID = 1L;

	private OPC opc;

	Square[] squares; // array for the square objects
	int numSquares = 20; // number of square in simulation

	int minSquareSize = 50;
	int maxSquareSize = 100;
	float minVelocity = 0f;
	float maxVelocity = 1f;

	public void setup() {
		colorMode(HSB, 255);
		size(OPC.WIDTH, OPC.HEIGHT);
		opc = new OPC(this);
		opc.setLayout(Layout.TWO);
		smooth();
		strokeWeight(3); // boundaries thicker
		squares = new Square[numSquares]; // create the array of the given size
		for (int i = 0; i < numSquares; i++) {
			squares[i] = new Square();
		}
	}

	public void draw() {
		background(0); // repaint a white background in each frame
		for (int i = 0; i < numSquares; i++) {
			squares[i].render(); // tell every square to repaint itself (after
									// rotating a bit)
		}
	}

	class Square {
		float x, y; // position
		float xV, yV; // velocities
		float angle = 0; // angle
		float size; // width=height
		float rotationSpeed; // speed of rotation
		float hue; 

		Square() {
			this.size = random(minSquareSize, maxSquareSize);
			this.x = size + random(width - size * 2);
			this.y = size + random(height - size * 2);
			this.rotationSpeed = random(0.01f, 0.1f);
			this.hue = random(255);
			this.xV = random(minVelocity, maxVelocity);
			this.yV = random(minVelocity, maxVelocity);

			// randomly invert
			if (OPC.RANDOM.nextBoolean()) {
				this.rotationSpeed = -this.rotationSpeed;
			}
			if (OPC.RANDOM.nextBoolean()) {
				this.xV = -this.xV;
			}
			if (OPC.RANDOM.nextBoolean()) {
				this.yV = -this.yV;
			}

		}

		void render() {
			x += xV;
			y += yV;

			if (x < size/2 || x > (width - size/2)) {
				xV = -xV;
			}
			if (y < size/2 || y > (height - size/2)) {
				yV = -yV;
			}

			fill(hue, 255, 255); // set the gray value as color to fill
			angle += rotationSpeed; // adjust the angle according to the
									// rotation speed s
			pushMatrix(); // used for local transformation
			translate(x, y); // the center of the local coordinate system is the
								// center of the square
			rotate(angle); // rotate the coordinate system
			rect(-size / 2, -size / 2, size, size); // draw the square
			popMatrix(); // used for local transformation
		}
	}

}
