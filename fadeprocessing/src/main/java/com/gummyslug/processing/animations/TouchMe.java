package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class TouchMe extends AnimationApplet {

	@Override
	protected void setupInternal() {
		background(0);
	}

	public void draw() {
		background(0);
		System.out.println(mouseX + ", " + mouseY);
	}



}
