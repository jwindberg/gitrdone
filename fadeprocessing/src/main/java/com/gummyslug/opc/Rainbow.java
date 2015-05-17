package com.gummyslug.opc;

public class Rainbow extends Pattern {
	int repetitions;
	int hueInc;
	int pos;

	public Rainbow(OpenPixelControl openPixelControl) {
		super(openPixelControl);
	}

	@Override
	public void init() {
		int numPixels = openPixelControl.getNumPixels();
		// Number of repetitions (complete loops around color wheel); any
		// more than 4 per meter just looks too chaotic and un-rainbow-like.
		// Store as hue 'distance' around complete belt:
		repetitions = (1 + RANDOM.nextInt(4 * ((numPixels + 31) / 32))) * 1536;
		// Frame-to-frame hue increment (speed) -- may be positive or negative,
		// but magnitude shouldn't be so small as to be boring. It's generally
		// still less than a full pixel per frame, making motion very smooth.
		hueInc = 4 + RANDOM.nextInt(repetitions) / numPixels;
		// Reverse speed and hue shift direction half the time.
		if (RANDOM.nextInt(2) == 0)
			repetitions = -repetitions;
		if (RANDOM.nextInt(2) == 0)
			hueInc = -hueInc;
		pos = 0; // Current position

	}

	@Override
	public void updatePattern() {

		int numPixels = openPixelControl.getNumPixels();
		for (int i = 0; i < numPixels; i++) {
			int hue = (int) (pos + repetitions * i / numPixels);
			Color color = Color.getHSV((int) (pos + repetitions * i / numPixels), 255, 255);
			openPixelControl.setPixel(i, color);
		}
		pos += hueInc;

	}

}
