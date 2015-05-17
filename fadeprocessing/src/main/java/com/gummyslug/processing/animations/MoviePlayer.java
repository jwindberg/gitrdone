package com.gummyslug.processing.animations;

import processing.video.Movie;

import com.gummyslug.processing.opc.AnimationApplet;

public class MoviePlayer extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	Movie movie;

	public void setupInternal() {
		movie = new Movie(this, "C:\\Users\\John\\Videos\\Opulent Temple Seattle White Party #2.mp4");
		movie.loop();
	}

	public void draw() {

	}

	@Override
	public void destroy() {
		movie.stop();
	}

}
