package com.gummyslug.processing;

import com.gummyslug.processing.opc.OPC;
import com.gummyslug.processing.opc.OPC.Layout;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class TheMatrix extends PApplet {

	private static final long serialVersionUID = 1L;

	/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/58248*@* */
	/*
	 * !do not delete the line above, required for linking your tweak if you
	 * upload again
	 */
	// Matrix code simulator
	// Matthew Pearson 2012
	// www.doctorchronicles.com/mpearson

	private OPC opc;

	String message = "The matrix has you.";
	String alphabet = "#0236901145757_アイウエオカキクケコサシスセソタチテトニヌネヒフヘホマミムメモヤユヨラリルレワヰヱ";
	int rowCount = 30;
	int speed = 2;
	float blurStrength = 0.2f;

	boolean soften = true;
	PFont f;
	float[][] convolution = new float[3][3];
	PImage buffer;

	MatrixLine[] columns;

	class MatrixLine {

		int tick = 0;
		int position = 0;
		int speed;
		char[] chars;
		int col;
		int stuckCharIndex = -1;

		float rowFactor;

		MatrixLine(int colNum) {
			position = -1 * (int) random(10);

			speed = floor(random(2, 8));
			col = colNum;
			chars = new char[rowCount];
			for (int i = 0; i < rowCount; i++)
				chars[i] = ' ';
			rowFactor = min(1, random(0.2f, 1.1f));
		}

		void update() {
			if (position < 0) {
				position++;
				return;
			}
			if (position > rowCount - 1 || (position > rowCount / 2 && random(30) < 1)) {
				position = -1 * (int) random(5);
				return;
			}

			int newCharNum = round(random(0, alphabet.length() - 1));
			chars[position] = alphabet.charAt(newCharNum);
			// print((int)chars[position]);
			// print(" ");

			if (stuckCharIndex < 0) {
				if (random(10) < 1) {
					stuckCharIndex = round(random(rowCount - 1));
				}
			} else {
				if (random(8) > 6)
					stuckCharIndex = -1;
			}
			position++;
		}

		void render() {
			if (++tick > speed) {
				update();
				tick = 0;

			}
			for (int i = 0; i < chars.length; i++) {
				if (i == stuckCharIndex)
					fill(240, 255, 250);
				else
					fill(rowFactor * 250, 255, rowFactor * 250);

				if (i == 10 && i == position && col < message.length()) {
					text(message.charAt(col), 11 * col, 11 * i);
				} else if (i == position || (i == stuckCharIndex && i < position))
					text(chars[i], 11 * col, 11 * i);
			}
		}
	}

	public void setup() {

		// size(440, 330);

		size(OPC.WIDTH, OPC.HEIGHT);
//		opc = new OPC(this);
//		opc.setDefault();
		opc.setLayout(Layout.TWO);
		opc.showLocations(false);

		columns = new MatrixLine[width / 11];
		rowCount = height / 11;

		smooth();
		fill(0);
		rect(0, 0, width, height);
		f = loadFont("ArialUnicodeMS-11.vlw");
		textFont(f, 10);

		for (int i = 0; i < columns.length; i++)
			columns[i] = new MatrixLine(i); // initialize MatrixColumns

		buffer = createImage(width, height, RGB);
		// create The Kernel
		int matrixSize = convolution.length;
		float matrixTotal = 0;
		for (int x = 0; x < matrixSize; x++) {
			for (int y = 0; y < matrixSize; y++) {
				convolution[x][y] = gaussian(x, y);
				matrixTotal += convolution[x][y];
			}
		}
		// normalize The Kernel
		for (int x = 0; x < matrixSize; x++) {
			for (int y = 0; y < matrixSize; y++) {
				convolution[x][y] /= (matrixTotal * 1.005);
				print(convolution[x][y]);
				print(" ");
			}
			print("\n");
		}
	}

	public void draw() {
		for (int i = 0; i < columns.length; i++)
			columns[i].render();
		loadPixels();
		buffer.loadPixels();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				if (soften) {
					int c = convolve(x, y, convolution);
					buffer.pixels[x + y * width] = c;
				} else {
					int c = pixels[x + y * width];
					c = color(0.92f * red(c), 0.98f * green(c), 0.92f * blue(c));
					buffer.pixels[x + y * width] = c;
				}
			}
		}
		buffer.updatePixels();
		image(buffer, 0, 0);
	}

	public void keyPressed() {
		if (key == 's' || key == 'S')
			soften = !soften;
		else if (key == ' ') {
			fill(0);
			rect(0, 0, height, width);
			setup();
		}
	}

	int convolve(int x, int y, float[][] matrix) { // ,PImage img

		float r = 0, g = 0, b = 0;
		int matrixSize = matrix.length;

		int offset = matrixSize / 2;

		for (int i = 0; i < matrixSize; i++) {
			for (int j = 0; j < matrixSize; j++) {

				int xOffset = x + i - offset;
				int yOffset = y + j - offset;

				int xyOffset = xOffset + width * yOffset;

				xyOffset = constrain(xyOffset, 0, pixels.length - 1);

				r += red(pixels[xyOffset]) * matrix[i][j];
				g += green(pixels[xyOffset]) * matrix[i][j];
				b += blue(pixels[xyOffset]) * matrix[i][j];
			}
		}

		r = constrain(r * 0.92f, 0f, 255f);
		g = constrain(g * 0.99f, 0f, 255f);
		b = constrain(b * 0.92f, 0f, 255f);

		return color(r, g, b);
	}

	float gaussian(float x, float y) {
		float x0 = convolution.length / 2;
		float c = x0 / 4;
		c = blurStrength;
		return 1 / ((float) Math.exp((pow(x - x0, 2) + pow(y - x0, 2)) / c));
	}

}
