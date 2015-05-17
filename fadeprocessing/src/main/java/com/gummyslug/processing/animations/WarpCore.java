package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class WarpCore extends AnimationApplet {

	private static final long serialVersionUID = 1L;
	int blockWidth;
	int blockHeight;
	int step;
	int current = 0;

	@Override
	protected void setupInternal() {
		blockWidth = width / 8;
		blockHeight = height / 16;
		step = 8;
	}

	@Override
	public void draw() {
		background(0);
		fill(204, 102, 0);
//		rotate(.75f);
		rect(current, 0 , blockWidth, height);
		current += step;
		if (current > width) {
			current = 0-blockWidth;
		}
	
	}

}
