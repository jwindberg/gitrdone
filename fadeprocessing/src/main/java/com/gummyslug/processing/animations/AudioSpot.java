package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;

public class AudioSpot extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	Minim minim;
	AudioInput audioInput;
//	FFT fft;

	int renk = 100;
	int x;
	int y;

	public void setupInternal() {

		colorMode(HSB, 100);
		background(0);
		smooth();
		noStroke();
		noCursor();
		// Construction of the minim object
		minim = new Minim(this);
		audioInput = minim.getLineIn(Minim.STEREO, 2048, 192000.0f);
//		fft = new FFT(audioInput.bufferSize(), audioInput.sampleRate());
		opc.showLocations(false);

	}

	public void draw() {
	
//		fft.forward(audioInput.mix);

		if (renk < 100) {
			renk = renk + 1;
		} else {
			renk = 0;
		}

		for (int i = 0; i < audioInput.bufferSize() - 1; i++) {
			float x = random(0, 600);
			float y = random(0, 600);

			if (i == 0) {
				ellipse(x, y, audioInput.left.get(i) * 400, audioInput.right.get(i) * 400);
			}
			if (audioInput.left.get(i) > 0.7) {
				background(0); // changes the bg if the pitch is low

			}
			// else {
			// background (0,0,0,0) ;
			// }

		}

		noStroke();
		fill(renk, 100, 100);

	}

}
