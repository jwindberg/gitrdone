package com.gummyslug.processing.animations;

import java.util.Random;

import processing.core.PConstants;
import processing.core.PGraphics;

import com.gummyslug.processing.opc.AnimationApplet;

public class DiscoBall extends AnimationApplet {

	private static final long serialVersionUID = -3186672587779932318L;

	private static final Random RANDOM = new Random();

	int numBlobs = 3;

	int[] blogPx = { 0, 90, 90 };
	int[] blogPy = { 0, 120, 45 };

	int[] blogDx = { 1, 1, 1 };
	int[] blogDy = { 1, 1, 1 };

	PGraphics pg;
	int[][] vy, vx;

	protected String getRenderer() {
		return PConstants.P2D;
	}

	public void setupInternal() {
		pg = createGraphics(150, 150, P2D);
		vy = new int[numBlobs][pg.height];
		vx = new int[numBlobs][pg.width];
	}

	public void draw() {
		for (int i = 0; i < numBlobs; ++i) {
			blogPx[i] += blogDx[i];
			blogPy[i] += blogDy[i];

			// bounce across screen
			if (blogPx[i] < 0) {
				blogDx[i] = 1;
			}
			if (blogPx[i] > pg.width) {
				blogDx[i] = -1;
			}
			if (blogPy[i] < 0) {
				blogDy[i] = 1;
			}
			if (blogPy[i] > pg.height) {
				blogDy[i] = -1;
			}

			for (int x = 0; x < pg.width; x++) {
				vx[i][x] = (int) (sq(blogPx[i] - x));
			}

			for (int y = 0; y < pg.height; y++) {
				vy[i][y] = (int) (sq(blogPy[i] - y));
			}
		}

		pg.beginDraw();
		pg.loadPixels();
		for (int y = 0; y < pg.height; y++) {
			for (int x = 0; x < pg.width; x++) {
				int m = 1;
				for (int i = 0; i < numBlobs; i++) {

					m += 60000 / (vy[i][y] + vx[i][x] + 1);
				}
				pg.pixels[x + y * pg.width] = color(0, m + x, (x + m + y) / 2);
			}
		}
		pg.updatePixels();
		pg.endDraw();

		image(pg, 0, 0, width, height);
	}

}
