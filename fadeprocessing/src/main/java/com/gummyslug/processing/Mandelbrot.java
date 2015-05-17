package com.gummyslug.processing;

import com.gummyslug.processing.opc.OPC;

import processing.core.PApplet;

public class Mandelbrot extends PApplet {

	private static final long serialVersionUID = 1L;
	
	private OPC opc;
	
	int w = OPC.WIDTH;
	int h = OPC.HEIGHT;
	
	double factor = 400;

	int maxVal = 4;
	int maxIter = 64;
	double minX = -(w/factor);
	double maxX = w/factor;
	double minY = -(h/factor);
	double maxY = h/factor;
	
	int colors = 1000;
	boolean doRender = true;
	boolean overSample = false;
	
	

	public void setup() {
		
		size(OPC.WIDTH, OPC.HEIGHT, P2D);
//		opc = new OPC(this);
//		opc.setDefault();
		colorMode(HSB, colors);

////		textMode(SCREEN);
//		PFont arial = createFont("Arial", 12);
//		textFont(arial);
	}

	public void draw() {
		if (doRender)
			render();
	}

	public void mouseClicked() {
		double mouX = mouseX / factor + minX;
		double mouY = mouseY / factor + minY;

		minX = (minX + mouX) / 2;
		maxX = (maxX + mouX) / 2;
		minY = (minY + mouY) / 2;
		maxY = (maxY + mouY) / 2;
		factor *= 2;

		doRender = true;
	}

	public void keyReleased() {
		if (keyCode == 49) {
			maxIter /= 2;
			doRender = true;
		}
		if (keyCode == 50) {
			maxIter *= 2;
			doRender = true;
		}
		/*
		 * if(keyCode == 51){ maxVal /= 2; doRender = true; } if(keyCode == 52){
		 * maxVal *= 2; doRender = true; }
		 */
		if (keyCode == 53) {
			overSample = !overSample;
			doRender = true;
		}
	}

	public void render() {
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (overSample) {
					int hsbH = 0;
					int hsbB = 0;
					for (int s = 0; s < 4; s++) {
						float nx = (s == 0f || s == 2f) ? 0f : 0.5f;
						float ny = (s == 0f || s == 1f) ? 0f : 0.5f;
						double cx = (x + nx) / factor + minX;
						double cy = (y + ny) / factor + minY;
						double[] i = iterate(cx, cy);
						hsbH += (int) (i[0] / maxIter * colors);
						hsbB += (int) (i[1] / maxVal * colors);
					}
					stroke(hsbH / 4, colors, hsbB / 4);
					point(x, y);
				} else {
					double cx = x / factor + minX;
					double cy = y / factor + minY;
					double[] i = iterate(cx, cy);
					int hsbH = (int) (i[0] / maxIter * colors);
					int hsbB = (int) (i[1] / maxVal * colors);
					stroke(hsbH, colors, hsbB);
					point(x, y);
				}
			}
		}
		String osText = "Oversampling: " + (overSample ? "on" : "off");
		String itText = "Iterations: " + maxIter;
		text(osText + "\n" + itText, 10, 20);
		doRender = false;
	}

	double[] iterate(double cx, double cy) {
		int i = 0;
		double val = 0;
		double x = 0;
		double y = 0;
		double[] result = new double[2];

		while (val <= maxVal && i < maxIter) {
			double xNew = x * x - y * y + cx;
			double yNew = 2 * x * y + cy;
			x = xNew;
			y = yNew;
			i++;
			val = (float) (x * x + y * y);
		}
		result[0] = i;
		result[1] = val;

		return result;
	}

}
