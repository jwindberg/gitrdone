package com.gummyslug.processing.animations;

import java.util.ArrayList;

import com.gummyslug.processing.opc.AnimationApplet;

public class Fire extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	ArrayList<Curves> lines = new ArrayList<Curves>();

	float noiseScale = 100;
	float nz = 0;
	float line_lenght = 10;
	float drawCount = 30;

	public void setupInternal() {
		
		opc.showLocations(false);
		// setup
		background(0);
	}

	//////////////////////////////////////////////////////////////////

	public void draw() {

		// background (0);

		lineDraw();

		fadeToBG();

	}

	//////////////////////////////////////////////////////////////////
	// FUNCTION

	void lineDraw() {

		for (int i = 0; i < 5; i++) {
			// lines.add ( new Curves( (width / 2 + random( 0 , 100)) - 100 ,
			// (height / 2 + random( 0 , 50)) - 100));
			lines.add(new Curves((mouseX + random(0, 70)) - 35, (mouseY + random(0, 50)) - 50));
		}

		noFill();

		for (int i = 0; i < lines.size(); i++) {

			Curves crv = lines.get(i);
			// println(crv.x1);

			float b = noise(crv.x4 / noiseScale, crv.y4 / noiseScale, nz) * -PI;

			crv.addCoord(cos(b) * line_lenght, sin(b) * line_lenght);

			strokeWeight(20 * (drawCount - crv.n) / drawCount + 1);
			// stroke ( 240 + random(5,15) , 20 + random(0,50) , random(0,10),
			// (drawCount - crv.n) / drawCount );
			stroke(240 + random(5, 15), 20 + random(0, 50), random(0, 10), 50);

			curve(crv.x1, crv.y1, crv.x2, crv.y2, crv.x3, crv.y3, crv.x4, crv.y4);
			// curve ( 0 , 0 , 10 , 50 , 35 , 55 , 80 , 120);

			if (crv.n >= drawCount) {
				lines.remove(i);
				i--;

			}

		}
		nz += 0.01;

	}

	void fadeToBG() {
		noStroke();
		fill(0, 10);
		rect(0, 0, width, height);

	}

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////

	//
	class Curves {
		float x1, x2, x3, x4;
		float y1, y2, y3, y4;

		int n = 0;

		Curves(float tmpX, float tmpY) {

			x1 = x2 = x3 = x4 = tmpX;
			y1 = y2 = y3 = y4 = tmpY;
		}

		void addCoord(float tmpdX, float tmpdY) {
			x1 = x2;
			x2 = x3;
			x3 = x4;
			x4 += tmpdX;

			y1 = y2;
			y2 = y3;
			y3 = y4;
			y4 += tmpdY;
			n++;
		}
	}

}
