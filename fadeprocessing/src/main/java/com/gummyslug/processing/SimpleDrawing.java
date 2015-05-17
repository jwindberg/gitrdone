package com.gummyslug.processing;

import com.gummyslug.processing.opc.OPC;
import com.gummyslug.processing.opc.OPC.Layout;

import processing.core.PApplet;

public class SimpleDrawing extends PApplet {
	/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/26614*@* */
	/*
	 * !do not delete the line above, required for linking your tweak if you
	 * upload again
	 */
	
	private OPC opc;
	
	float oldX;
	float oldY;
	int redC = color(255, 0, 0);
	int greenC = color(0, 255, 0);
	int blueC = color(0, 0, 255);
	int yellow = color(247, 240, 0);
	int orange = color(247, 112, 0);
	int violet = color(110, 0, 220);
	int blueGreen = color(0, 247, 146);
	int yellowGreen = color(157, 250, 0);
	int pink = color(255, 28, 97);
	int yellowOrange = color(255, 159, 3);
	int white = color(255);
	int black = color(0);
	float masterStroke = 1;

	public void setup() {
//		size(500, 500);
		size(OPC.WIDTH, OPC.HEIGHT);
		opc = new OPC(this);
		opc.setLayout(Layout.TWO);
		opc.showLocations(false);
		smooth();
		background(0);

	}

	public void draw() {
		fill(0);
		strokeWeight(1);
		fill(redC);
		rect(10, 10, 25, 25);
		fill(blueC);
		rect(35, 10, 25, 25);
		fill(greenC);
		rect(10, 35, 25, 25);
		fill(yellow);
		rect(35, 35, 25, 25);
		fill(orange);
		rect(10, 60, 25, 25);
		fill(violet);
		rect(35, 60, 25, 25);
		fill(blueGreen);
		rect(10, 85, 25, 25);
		fill(yellowGreen);
		rect(35, 85, 25, 25);
		fill(pink);
		rect(10, 110, 25, 25);
		fill(yellowOrange);
		rect(35, 110, 25, 25);
		fill(white);
		rect(10, 135, 25, 25);
		fill(black);
		rect(35, 135, 25, 25);
		line(450, 30, 500, 30);
		strokeWeight(4);
		line(450, 50, 500, 50);
		strokeWeight(8);
		line(450, 80, 500, 80);
		strokeWeight(1);
		fill(255);
		rect(250, 10, 50, 50);

		if (mousePressed) {
			if (mouseX > 10 && mouseX < 35) {
				if (mouseY > 10 && mouseY < 35) {
					stroke(redC);
				}
				if (mouseY > 35 && mouseY < 60) {
					stroke(greenC);
				}
				if (mouseY > 60 && mouseY < 85) {
					stroke(orange);
				}
				if (mouseY > 85 && mouseY < 110) {
					stroke(blueGreen);
				}
				if (mouseY > 110 && mouseY < 135) {
					stroke(pink);
				}
				if (mouseY > 135 && mouseY < 160) {
					stroke(white);
				}
			}
			if (mouseX > 35 && mouseX < 60) {
				if (mouseY > 10 && mouseY < 35) {
					stroke(blueC);
				}
				if (mouseY > 35 && mouseY < 50) {
					stroke(yellow);
				}
				if (mouseY > 60 && mouseY < 85) {
					stroke(violet);
				}
				if (mouseY > 85 && mouseY < 110) {
					stroke(yellowGreen);
				}
				if (mouseY > 110 && mouseY < 135) {
					stroke(yellowOrange);
				}
				if (mouseY > 135 && mouseY < 160) {
					stroke(black);
				}
			}
			if (mousePressed) {
				if (mouseX > 450 && mouseX < 500) {
					if (mouseY > 10 && mouseY < 40) {
						masterStroke = 1;
					}
				}
				if (mouseX > 450 && mouseX < 500) {
					if (mouseY > 40 && mouseY < 70) {
						masterStroke = 4;
					}
				}
				if (mouseX > 450 && mouseX < 500) {
					if (mouseY > 70 && mouseY < 100) {
						masterStroke = 7;
					}
				}
				strokeWeight(masterStroke);
			}
			if (mousePressed) {
				if (mouseX > 250 && mouseX < 300) {
					if (mouseY > 10 && mouseY < 60) {
						background(255);
					}
				}
			}
			if (mousePressed) {
				line(mouseX, mouseY, oldX, oldY);
			}
		}
		oldX = mouseX;
		oldY = mouseY;
	}
}
