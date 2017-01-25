package com.gummyslug.processing.animations;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.gummyslug.processing.opc.AnimationApplet;

public class Clock extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setupInternal() {

	}

	public void draw() {
		// background(0);
		String text = ZonedDateTime.now().format(DateTimeFormatter.ISO_TIME);
		// text(text, 0, 0, HEIGHT, WIDTH);

		background(0);
		stroke(153);
		textSize(20);

		textAlign(CENTER, CENTER);

		text(text, 50 , HEIGHT/2);

	}

}
