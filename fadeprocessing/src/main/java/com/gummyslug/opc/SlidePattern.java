package com.gummyslug.opc;

public class SlidePattern extends Pattern {
	private Color color = Color.getRandom();
	int pos = 0;
	int direction = RANDOM.nextInt() % 2;
	int inc = 1;

	public SlidePattern(OpenPixelControl openPixelControl) {
		super(openPixelControl);
	}

	@Override
	public void init() {
		pos = 0;
		direction = RANDOM.nextInt() % 2;
		if (RANDOM.nextInt() % 2 == 0) {
			inc = -inc;
		}
	}

	@Override
	public void updatePattern() {
		openPixelControl.clear();
		int width = openPixelControl.getWidth();
		if (direction == 0)
			openPixelControl.setHorizontal(pos, color);
		else
			openPixelControl.setVertical(pos, color);
		pos += inc;
		if (pos >= width) {
			pos = 0;
		}
		if (pos < 0) {
			pos = width - 1;
		}

	}

}
