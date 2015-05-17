package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;

/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/47528*@* */
/* !do not delete the line above, required for linking your tweak if you upload again */

public class AudioWheel extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/47528*@* */
	/*
	 * !do not delete the line above, required for linking your tweak if you
	 * upload again
	 */

	Minim minim = new Minim(this);
	AudioInput audioInput;

	int nb = 200, n = 30;
	int coeff1 = -2, coeff2 = 6;

	float rad = 80, w1 = rad * 2 * PI / n, w2;

	Boolean sounding = true;
	int bgcolor = 0x21120A;

	public void setupInternal() {
		rectMode(CORNERS);
		colorMode(HSB, 100);
		smooth();
		w2 = (width - 30f) / n;
		strokeWeight(2);
		stroke(15);
		audioInput = minim.getLineIn(Minim.STEREO, 2048, 192000.0f);
	}

	public void draw() {
		background(0);

		FFT fft = new FFT(audioInput.bufferSize(), audioInput.sampleRate());
		fft.linAverages(nb);
		fft.forward(audioInput.mix);

		for (int i = 0; i < n; i++) {
			fill(i * 100 / (n), 100, 100);
			pushMatrix();
			translate(width / 2, height / 2 - 100);
			rotate(i * 2 * PI / (n));
			rect(-w1 / 2, rad, w1 / 2, rad - 10 + fft.getAvg(i) * coeff1);
			popMatrix();
			rect(15 + i * w2, height - 12, 15 + (i * w2) + w2, height - 15 - fft.getAvg(i) * coeff2);
		}
	}
}
