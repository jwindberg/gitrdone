package com.gummyslug.opc;


public class SineWaveChase extends Pattern {

	private int hue;
	private int repetitions;
	private int incSpeed;
	private int pos;

	public SineWaveChase(OpenPixelControl opc) {
		super(opc);
	}

	public void init() {
		hue = RANDOM.nextInt(1536);
		repetitions = (1 + RANDOM.nextInt(4 * ((openPixelControl.getNumPixels() + 31) / 32))) * 720;
		incSpeed = 4 + RANDOM.nextInt(repetitions) / openPixelControl.getNumPixels();
		if ((RANDOM.nextInt() % 2) == 0) {
			incSpeed = -incSpeed;
		}
		pos = 0;
	}

	@Override
	public void updatePattern() {
		int foo = 0;
		for (int i = 0; i < openPixelControl.getNumPixels(); i++) {
			foo = fixSin(pos + repetitions * i / openPixelControl.getNumPixels());
			Color color = (foo >= 0) ? Color.getHSV(hue, 254 - (foo * 2), 255) : Color.getHSV(hue, 255, 254 + foo * 2);
			openPixelControl.setPixel(i, color);
		}
		pos += incSpeed;

	}





}
