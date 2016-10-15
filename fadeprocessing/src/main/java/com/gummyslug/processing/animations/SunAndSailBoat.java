package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class SunAndSailBoat extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	/*
	 * OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/347857*@*
	 */
	/*
	 * !do not delete the line above, required for linking your tweak if you
	 * upload again
	 */
	float mov = 1;

	public void setupInternal() {

	}

	public void draw() {
		mov = mov + 1;

		// Cielo
		fill(60 + mouseY / 2, 180 - mouseY, 210 - mouseY);
		rect(0, 0, 500, 250);

		// Sol
		noStroke();
		fill(255, 255 - mouseY / 2, 0);
		ellipse(250, mouseY, 120, 120);

		Mar();

		Reflejo();

		Barco();

		Nubes();

		// Texto
		fill(255, mov - 2500);
		text("Adios Shurr!", 420, 200);

		fill(60 + mouseY / 2, 180 - mouseY, 210 - mouseY, mov - 2800);
		rect(420, 162, 100, 40);

		// Noche
		fill(0, 0, 0, mouseY - 100);
		rect(0, 0, 500, 500);
	}

	void Mar() {
		fill(0, 65, 130);
		rect(0, 250, 500, 5);

		fill(0, 50, 100);
		rect(0, 255, 500, 10);

		fill(0, 40, 80);
		rect(0, 265, 500, 20);

		fill(0, 30, 60);
		rect(0, 285, 500, 35);

		fill(0, 22, 45);
		rect(0, 320, 500, 70);

		fill(0, 15, 30);
		rect(0, 390, 500, 110);
	}

	void Reflejo() {
		fill(255, 120, mouseY / 3, mouseY - 120);
		rect(60 + mouseY / 2, 250, 380 - mouseY, 5);

		fill(220, 110, mouseY / 3, mouseY - 120);
		rect(63 + mouseY / 2, 255, 374 - mouseY, 10);

		fill(200, 90, mouseY / 3, mouseY - 140);
		rect(65 + mouseY / 2, 265, 369 - mouseY, 20);

		fill(190, 90, mouseY / 3, mouseY - 180);
		rect(70 + mouseY / 2, 285, 360 - mouseY, 35);

		fill(120, 80, mouseY / 3, mouseY - 220);
		rect(76 + mouseY / 2, 320, 348 - mouseY, 70);

		fill(120, 80, mouseY / 3, mouseY - 250);
		rect(80 + mouseY / 2, 390, 340 - mouseY, 110);
	}

	void Barco() {
		fill(255);
		triangle(30 + mov / 6, 241, 50 + mov / 6 - mouseX / 40, 240, 28 + mov / 6, 205);
		triangle(27 + mov / 6, 241, 10 + mov / 6 + mouseX / 40, 242 - mouseX / 400, 25 + mov / 6, 210);

		fill(120, 90, 70);
		quad(4 + mov / 6, 250, 50 + mov / 6, 250, 58 + mov / 6, 242, 0 + mov / 6, 245);

		// reflejo barco
		fill(120, 90, 70, 60);
		quad(4 + mov / 6, 250, 50 + mov / 6, 250, 58 + mov / 6, 258, 0 + mov / 6, 255);

		fill(255, 50);
		triangle(30 + mov / 6, 259, 50 + mov / 6 - mouseX / 40, 260, 28 + mov / 6, 295);
		triangle(27 + mov / 6, 259, 10 + mov / 6 + mouseX / 40, 258 + mouseX / 400, 25 + mov / 6, 290);
	}

	void Nubes() {
		fill(255);

		ellipse(340 + 125 - mov / 10, 100, 40, 40);
		ellipse(450 + 125 - mov / 10, 90, 60, 60);
		ellipse(390 + 125 - mov / 10, 80, 80, 80);
		rect(340 + 125 - mov / 10, 100, 110, 20);

		ellipse(110 + 125 - mov / 10, 110, 80, 80);
		ellipse(50 + 125 - mov / 10, 120, 60, 60);
		ellipse(160 + 125 - mov / 10, 130, 40, 40);
		rect(46 + 125 - mov / 10, 130, 110, 20);
	}

}
