package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class RedSquare extends AnimationApplet {

	@Override
	protected void setupInternal() {

	}

	@Override
	public void draw() {
		fill(255, 0, 0);
		rect(0f, 0f, getHeight(), getWidth());
	}

}
