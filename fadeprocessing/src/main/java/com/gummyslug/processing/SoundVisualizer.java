package com.gummyslug.processing;

import com.gummyslug.processing.opc.OPC;
import com.gummyslug.processing.opc.OPC.Layout;

import processing.core.PApplet;
import ddf.minim.AudioInput;
import ddf.minim.AudioOutput;
import ddf.minim.AudioSignal;
import ddf.minim.Minim;

public class SoundVisualizer extends PApplet {

	private static final long serialVersionUID = 1L;

	private OPC opc;
	
	Minim minim;
	AudioInput in;
	AudioOutput out;
	PlayBack pb;
	float[] samples = new float[0];
	boolean record = false;
	int bufferSize = 1280;
	int newBufferSize = bufferSize;

	public void setup() {
		
		size(OPC.WIDTH, OPC.HEIGHT, P3D);
		opc = new OPC(this);
		opc.setLayout(Layout.TWO);

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
			line(i, 240 + in.left.get(i) * 1500, i + 1, 240 + in.left.get(i + 1) * 5);
		}

		if (record == true) {
			// Increase the size of samples[] to accomodate the new sound data
			int samplesOriginalLength = samples.length;
			print(samplesOriginalLength);
			samples = expand(samples, newBufferSize);
			println("-" + samples.length);
			// Copy the new data into samples
			// samples = concat(samples, in.left);
			for (int i = 0; i < in.bufferSize(); i++) {
				samples[samplesOriginalLength + i] = in.left.get(i);
			}
			newBufferSize += bufferSize;
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

	public void keyPressed() {
		if (key == ' ') {
			record = true;
		}
	}

	public void keyReleased() {
		if (key == ' ') {
			record = false;
		}
	}

	public void stop() {
		// always close Minim audio classes when you are done with them
		in.close();
		minim.stop();
		super.stop();
	}

}
