package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.AudioOutput;
import ddf.minim.AudioSignal;
import ddf.minim.Minim;

public class SoundTowerMeter extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	Minim minim;
	AudioInput in;
	AudioOutput out;
	PlayBack pb;
	float[] samples = new float[0];
	int bufferSize = 1280;
	int newBufferSize = bufferSize;

	public void setupInternal() {

		minim = new Minim(this);
		minim.debugOn();

		pb = new PlayBack();

		// get a line in from Minim, default bit depth is 16
		in = minim.getLineIn(Minim.MONO, bufferSize);

		// get a line out from Minim, default sample rate is 44100, default bit
		// depth is 16
		out = minim.getLineOut(Minim.MONO, bufferSize);
		out.addSignal(pb);

		for (int i = 0; i < samples.length; i++) {
			samples[i] = 0.0f;
		}
	}

	public void draw() {

		fill(0, 20);
		noStroke();
		rect(0, 0, width, height);
		colorMode(HSB);
		background(0);
		stroke(255);
		// draw the waveforms
		for (int i = 0; i < in.bufferSize() - 1; i++) {

			float value = in.left.get(i);
			float h = map(value, -.4f, .4f, 0f, 360f);
			float s = map(value, -.4f, .4f, 60f, 360f);
			stroke(h, s, 360);
			// line(i, 240 + in.left.get(i) * 1500, i + 1, 240 + in.left.get(i +
			// 1) * 5);
			line(width / 2 + in.left.get(i) * width * 3, i,

			width / 2 + in.left.get(i + 1) * 5, i + 1);

		}

	}

	int samplesPlayHead = 0;

	class PlayBack implements AudioSignal {
		public void generate(float[] samp) {
			// for(int i = 0; i < in.bufferSize() - 1; i++) {
			// samp[i] = in.left.get(i);
			// }

			if (samples.length != 0) {
				for (int i = 0; i < bufferSize; i++) {
					samp[i] = samples[samplesPlayHead + i];
				}
				samplesPlayHead += bufferSize;
				if (samplesPlayHead >= samples.length) {
					samplesPlayHead = 0;
				}
			}
		}

		// this is a stricly mono signal
		public void generate(float[] left, float[] right) {
			generate(left);
			generate(right);
		}

	}

	public void stop() {
		// always close Minim audio classes when you are done with them
		in.close();
		minim.stop();
		super.stop();
	}

}
