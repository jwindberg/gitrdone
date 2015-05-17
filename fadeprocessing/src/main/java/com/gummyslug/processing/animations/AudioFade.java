package com.gummyslug.processing.animations;

import processing.core.PImage;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;

public class AudioFade extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/112395*@* */
	/*
	 * !do not delete the line above, required for linking your tweak if you
	 * upload again
	 */

	Minim minim;
	AudioInput in;
	FFT fft;

	int w;
	PImage fade;

	int hVal;
	float rWidth, rHeight;

	public void setupInternal() {

		minim = new Minim(this);
		in = minim.getLineIn(Minim.STEREO, 512);
		fft = new FFT(in.bufferSize(), in.sampleRate());
		fft.logAverages(60, 7);
		stroke(255);
		w = width / fft.avgSize();
		strokeWeight(w);

		background(0);
		fade = get(0, 0, width, height);
		rWidth = width * 0.99f;
		rHeight = height * 0.99f;
		hVal = 0;
	}

	public void draw() {
		background(0);
		tint(255, 255, 255, 254);
		image(fade, (width - rWidth) / 2, (height - rHeight) / 2, rWidth, rHeight);
		noTint();

		fft.forward(in.mix);

		colorMode(HSB);
		stroke(hVal, 255, 255);
		colorMode(RGB);

		for (int i = 0; i < fft.avgSize(); i++) {
			line((i * w) + (w / 2), height, (i * w) + (w / 2), height - fft.getAvg(i) * 4);
		}

		stroke(255);
		fade = get(0, 0, width, height);

		for (int i = 0; i < fft.avgSize(); i++) {
			line((i * w) + (w / 2), height, (i * w) + (w / 2), height - fft.getAvg(i) * 4);
		}

		hVal += 2;
		if (hVal > 255) {
			hVal = 0;
		}
	}

}
