package com.gummyslug.processing.animations;

import gifAnimation.Gif;

import com.gummyslug.processing.opc.AnimationApplet;

public class GifAnimator extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	private Gif myAnimation;

	public void setupInternal() {
		myAnimation = new Gif(this, "WarpedLikableBubblefish.gif");
		myAnimation.play();
	}

	public void draw() {
		background(0);
		image(myAnimation, (width - myAnimation.width) / 2, (height - myAnimation.height) / 2);
	}
}
