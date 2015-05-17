package com.gummyslug.processing;

import com.gummyslug.processing.opc.AnimationApplet;
import com.gummyslug.processing.opc.OPC.Layout;
import com.gummyslug.processing.opc.Pixel;

public class GridPixels extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	private Pixel[][] myPixels = new Pixel[32][32];

	public Layout getLedLayout() {
		return Layout.FOUR;
	}

	public void setupInternal() {
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {

				if (y % 2 == 0) {
					myPixels[x][y] = opc.getPixel(x + 16 * y);
					myPixels[x + 16][y] = opc.getPixel(x + 16 * y + 256);
					myPixels[x][y + 16] = opc.getPixel(x + 16 * y + 512);
					myPixels[x + 16][y + 16] = opc.getPixel(x + 16 * y + 768);
				} else {
					myPixels[x][y] = opc.getPixel((15 - x) + 16 * y);
					myPixels[x + 16][y] = opc.getPixel((15 - x) + 16 * y + 256);
					myPixels[x][y + 16] = opc.getPixel((15 - x) + 16 * y + 512);
					myPixels[x + 16][y + 16] = opc.getPixel((15 - x) + 16 * y + 768);
				}
			}
		}

	}

	int currentX = 0;

	public void draw() {
		background(0);
		for (int currentY = 0; currentY < 32; currentY++) {
			Pixel pixel = myPixels[currentX][currentY];
			ellipse(pixel.x, pixel.y, 10, 10);
		}
		currentX++;
		if (currentX > 31) {
			currentX = 0;
		}

	}
}
