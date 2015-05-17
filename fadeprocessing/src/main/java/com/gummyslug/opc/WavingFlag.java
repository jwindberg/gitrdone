package com.gummyslug.opc;

public class WavingFlag extends Pattern {
	private int wavyness;
	private int waveSpeed;
	private int puckeryness;
	private int pos;

	private static final Color RED = Color.getRGB(160, 0, 0);
	private static final Color WHITE = Color.getRGB(255, 255, 255);
	private static final Color BLUE = Color.getRGB(0, 0, 100);
	private static final Color[] flagTable = { BLUE, WHITE, BLUE, WHITE, BLUE, WHITE, BLUE, RED, WHITE, RED, WHITE,
			RED, WHITE, RED, WHITE, RED, WHITE, RED, WHITE, RED };

	public WavingFlag(OpenPixelControl openPixelControl) {
		super(openPixelControl);
	}

	@Override
	public void init() {
		wavyness = 720 + RANDOM.nextInt(720); // Wavyness
		waveSpeed = 4 + RANDOM.nextInt(10); // Wave speed
		puckeryness = 200 + RANDOM.nextInt(200); // Wave 'puckeryness'
		pos = 0; // Current position

	}

	@Override
	public void updatePattern() {
		int stripes = openPixelControl.getWidth();

		long sum, s, x;
		int i, idx1, idx2, a, b;
		int red, green, blue;
		
		sum = 0 ;

		for (i = 0; i < stripes - 1; i++) {
			sum += puckeryness + fixCos((int) (pos + wavyness * i / stripes));
		}

		for (s = 0, i = 0; i < stripes; i++) {
			x = 256L * ((flagTable.length / 3) - 1) * s / sum;
			idx1 = (int) ((x >> 8) * 3);
			idx2 = (int) (((x >> 8) + 1) * 3);
			b = (int) ((x & 255) + 1);
			a = 257 - b;

			red = ((flagTable[idx1].red * a) + (flagTable[idx2].red * b)) >> 8;

			green = ((flagTable[idx1].green * a) + (flagTable[idx2].green * b)) >> 8;

			blue = ((flagTable[idx1].blue * a) + (flagTable[idx2].blue * b)) >> 8;

			openPixelControl.setVertical(i, Color.getRGB(red, green, blue));

			s += puckeryness + fixCos((int) (pos + wavyness * i / stripes));
		}

		pos += waveSpeed;
		if (pos >= 720)
			pos -= 720;

	}

}
