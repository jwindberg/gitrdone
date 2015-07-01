package com.gummyslug.processing;

import java.util.Random;

public class PColor {
	
	private static final Random RANDOM = new Random();

	public int red;
	public int green;
	public int blue;
	
	public static PColor getRandom() {
		return new PColor(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255));
	}

	public static PColor getRGB(int red, int green, int blue) {
		return new PColor(red, green, blue);
	}

	public static PColor getRGB(byte red, byte green, byte blue) {
		return new PColor(red, green, blue);
	}

	public static PColor getHSV(int h, int s, int v) {
		int r, g, b, lo;

		int s1;
		long v1;

		// Hue
		h %= 1536; // -1535 to +1535
		if (h < 0)
			h += 1536; // 0 to +1535
		lo =  (h & 255); // Low byte = primary/secondary color mix
		switch (h >> 8) { // High byte = sextant of colorwheel
		case 0:
			r = 255;
			g = lo;
			b = 0;
			break; // R to Y
		case 1:
			r =  255 - lo;
			g =  255;
			b = 0;
			break; // Y to G
		case 2:
			r = 0;
			g =  255;
			b = lo;
			break; // G to C
		case 3:
			r = 0;
			g = 255 - lo;
			b =  255;
			break; // C to B
		case 4:
			r = lo;
			g = 0;
			b =  255;
			break; // B to M
		default:
			r =  255;
			g = 0;
			b = 255 - lo;
			break; // M to R
		}

		s1 = s + 1;
		r = 255 - (((255 - r) * s1) >> 8);
		g = 255 - (((255 - g) * s1) >> 8);
		b = 255 - (((255 - b) * s1) >> 8);
		return new PColor(r, g, b);
	}

	public PColor() {
		this.red = 0;
		this.green = 0;
		this.blue = 0;
	}

	public PColor(int red, int green, int blue) {
		this.red = red % 256;
		this.green = green % 256;
		this.blue = blue % 256;
	}

	@Override
	public String toString() {
		return "{" + red + "," + green + "," + blue + ")";
	}

}
