package com.gummyslug.processing;

import com.gummyslug.processing.opc.OPC;
import com.gummyslug.processing.opc.OPC.Layout;

import processing.core.PApplet;

public class Rainbow extends PApplet {

	private static final long serialVersionUID = 1L;

	private OPC opc;

	private int repetitions;
	private int hueInc;
	private int pos;

	private int gridWidth = 32;
	private int gridHeight = 16;

	public void fillSquare(int x, int y, int color) {
		fill(color);
		float startX = (x - 1.0f) * (getWidth() / gridWidth);
		float endX = (x) * (getWidth() / gridWidth);
		float startY = (y - 1.0f) * (getHeight() / gridHeight);
		float endY = (y - 1.0f) * (getHeight() / gridHeight);
		rect(startX, startY, endX, endY);
	}

	public void setup() {
		size(OPC.WIDTH, OPC.HEIGHT);
		colorMode(HSB, 255);
		opc = new OPC(this);
		opc.setLayout(Layout.TWO);

		int numPixels = opc.getPixelCount();
		// Number of repetitions (complete loops around color wheel); any
		// more than 4 per meter just looks too chaotic and un-rainbow-like.
		// Store as hue 'distance' around complete belt:
		repetitions = (1 + opc.nextRandomInt(4 * ((numPixels + 31) / 32))) * 1536;
		// Frame-to-frame hue increment (speed) -- may be positive or negative,
		// but magnitude shouldn't be so small as to be boring. It's generally
		// still less than a full pixel per frame, making motion very smooth.
		hueInc = 4 + opc.nextRandomInt(repetitions) / numPixels;
		// Reverse speed and hue shift direction half the time.
		if (opc.nextRandomInt(2) == 0)
			repetitions = -repetitions;
		if (opc.nextRandomInt(2) == 0)
			hueInc = -hueInc;
		pos = 0; // Current position
	}

	public void draw() {
		int i = 0;
		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				int hue = (pos + repetitions * i / (gridWidth * gridHeight)) % 255;
				fillSquare(x, y, color(hue, 25, 255));
				i++;
			}

		}
		pos += hueInc;
	}

}
