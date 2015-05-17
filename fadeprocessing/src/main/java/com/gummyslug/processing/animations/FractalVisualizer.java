package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.Minim; // import the minim sound library in Processing
import ddf.minim.analysis.FFT;

public class FractalVisualizer extends AnimationApplet {

	private static final long serialVersionUID = 1L;
	// This program makes fractal images based on sound

	FFT fft;
	int highest = 0;

	Minim minim; // declare Minim variable called bob
	AudioInput audioInput;
	int buffer = 1024; // buffer size in samples for file playback
	float volume = 0;

	public void setupInternal() {
		background(0);
		smooth();
		minim = new Minim(this); // create a Minim object
		audioInput = minim.getLineIn(Minim.STEREO, 2048, 192000.0f);
		fft = new FFT(audioInput.left.size(), 44100);
		opc.showLocations(false);
	}

	void form(float x, float y, float a, float c) {
		float nx = x + volume * cos(a);
		float ny = y + volume * sin(a);
		stroke((255 - (2 * fft.getBand(highest))), 2 * fft.getBand(highest), fft.getBand(highest), random(50, 75));
		line(x, y, nx, ny);
		if (c > 0) {
			form(nx, ny, a + mouseX, c - 1);
			form(nx, ny, a + mouseY, c - 1);
		}
	}

	public void draw() {
		fft.forward(audioInput.left);
		highest = 0;
		for (int n = 0; n < fft.specSize(); n++) {
			// draw the line for frequency band n, scaling it by 4 so we can see
			// it a bit better
			line(n, height, n, height - fft.getBand(n) * 4);

			// find frequency with highest amplitude
			if (fft.getBand(n) > fft.getBand(highest))
				highest = n;
		}
		// background(0);
		volume = (audioInput.left.get(1) + audioInput.right.get(1)); // get vol.
																		// for
																		// both
		// channels
		volume = ((volume + 2) * 50);
		form(mouseX, mouseY, -HALF_PI, 6);
		// frameRate(10);
	}

	public void mousePressed() {
		background(0);
		form(mouseX, mouseY, -HALF_PI, 6);
	}

	public void stop() {
		audioInput.close(); // close audio I/O
		minim.stop(); // stop the Minim object
		super.stop();
	}

}
