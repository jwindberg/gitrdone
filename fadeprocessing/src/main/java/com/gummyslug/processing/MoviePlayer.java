package com.gummyslug.processing;

import processing.core.PApplet;
import processing.video.Movie;

public class MoviePlayer extends PApplet {

	private static final long serialVersionUID = 1L;

	private static final String PATH = "C:\\Users\\John\\Videos\\discoball.mov";

	private Movie movie;

	public void setup() {
		size(640, 360);
		frameRate(30);
		movie = new Movie(this, PATH);
		movie.play();
		movie.speed(5);
		movie.volume(0);
	}

	public void movieEvent(Movie movie) {
		movie.read();
	}

	public void draw() {
		image(movie, 0, 0, width, height);
	}

}
