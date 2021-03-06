package com.gummyslug.opc;

import java.util.Random;

public class Color {
	
	private static final Random RANDOM = new Random();

	public int red;
	public int green;
	public int blue;
	
	public static Color getRandom() {
		return new Color(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255));
	}

	public static Color getRGB(int red, int green, int blue) {
		return new Color(red, green, blue);
	}

	public static Color getRGB(byte red, byte green, byte blue) {
		return new Color(red, green, blue);
	}

	public static Color getHSV(int h, int s, int v) {
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

		// Value (brightness) and 24-bit color concat merged: similar to above,
		// add
		// 1 to allow shifts, and upgrade to long makes other conversions
		// implicit.
		v1 = v + 1;

//		r = (int) (r * v1);
//		g = (int) (g * v1);
//		b = (int) (b * v1);

		return new Color(r, g, b);
	}

	public Color() {
		this.red = 0;
		this.green = 0;
		this.blue = 0;
	}

	public Color(int red, int green, int blue) {
		this.red = red % 256;
		this.green = green % 256;
		this.blue = blue % 256;
	}

	@Override
	public String toString() {
		return "{" + red + "," + green + "," + blue + ")";
	}

}
