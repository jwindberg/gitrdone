package com.gummyslug.opc;

public class MyPattern extends Pattern {
	private int pos = 0;
	private Color color = new Color(0, 255, 0);

	public MyPattern(OpenPixelControl opc) {
		super(opc);
	}
	
	@Override
	public void init() {
		pos = 0;
	}

	@Override
	public void updatePattern() {
		openPixelControl.clear();
		openPixelControl.setPixel(pos, color);
		pos++;
		if (pos >= openPixelControl.getNumPixels()) {
			pos = 0;
		}

	}

}
