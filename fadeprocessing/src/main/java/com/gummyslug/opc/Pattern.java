package com.gummyslug.opc;

import java.util.Random;

public abstract class Pattern  {

	protected static Random RANDOM = new Random();

	protected OpenPixelControl openPixelControl;

	public Pattern(OpenPixelControl openPixelControl) {
		this.openPixelControl = openPixelControl;
		init();
	}

	public abstract void init();

	public abstract void updatePattern();
	
	public void writePixels() {
		openPixelControl.writePixels();
	}



	int fixSin(int angle) {
		angle %= 720; // -719 to +719
		if (angle < 0)
			angle += 720; // 0 to +719
		return (angle <= 360) ? SINE_TABLE[(angle <= 180) ? angle : // Quadrant
																	// 1
				(360 - angle)] : // Quadrant 2
				-SINE_TABLE[(angle <= 540) ? (angle - 360) : // Quadrant 3
						(720 - angle)]; // Quadrant 4
	}

	int fixCos(int angle) {
		angle %= 720; // -719 to +719
		if (angle < 0)
			angle += 720; // 0 to +719
		return (angle <= 360) ? (angle <= 180) ? SINE_TABLE[180 - angle] : // Quad 1
				- SINE_TABLE[angle - 180] : // Quad 2
				((angle <= 540) ? -SINE_TABLE[540 - angle] : // Quad 3
						SINE_TABLE[angle - 540]); // Quad 4
	}

	private static int[] SINE_TABLE = { 0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20, 21, 22, 23,
			24, 25, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52,
			53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 77,
			78, 79, 80, 81, 82, 83, 83, 84, 85, 86, 87, 88, 88, 89, 90, 91, 92, 92, 93, 94, 95, 95, 96, 97, 97, 98, 99,
			100, 100, 101, 102, 102, 103, 104, 104, 105, 105, 106, 107, 107, 108, 108, 109, 110, 110, 111, 111, 112,
			112, 113, 113, 114, 114, 115, 115, 116, 116, 117, 117, 117, 118, 118, 119, 119, 120, 120, 120, 121, 121,
			121, 122, 122, 122, 123, 123, 123, 123, 124, 124, 124, 124, 125, 125, 125, 125, 125, 126, 126, 126, 126,
			126, 126, 126, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127 };

}
