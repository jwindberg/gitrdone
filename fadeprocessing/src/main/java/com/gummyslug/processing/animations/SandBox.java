package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.AudioListener;
import ddf.minim.AudioSource;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;

public class SandBox extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	Minim minim;
	BeatDetect beat;
	BeatListener bl;
	private AudioInput audioInput;

	float kickSize, snareSize, hatSize;

	public void setupInternal() {
		noStroke();
		rectMode(CENTER);

		minim = new Minim(this);
		audioInput = minim.getLineIn(Minim.MONO, 2048, 192000.0f);

		noStroke();
		minim = new Minim(this);

		beat = new BeatDetect(audioInput.bufferSize(), audioInput.sampleRate());
		beat.setSensitivity(100);
		kickSize = snareSize = hatSize = 5;
		bl = new BeatListener(beat, audioInput);

	}

	int n = 0;

	public void draw() {
		background(255);

		if (beat.isKick())
			kickSize = 30;
		if (beat.isSnare())
			snareSize = 30;
		if (beat.isHat())
			hatSize = 30;

		kickSize = constrain(kickSize * 0.95f, 5f, 30f);
		snareSize = constrain(snareSize * 0.95f, 5f, 30f);
		hatSize = constrain(hatSize * 0.95f, 5f, 30f);

		translate(width / 2, height / 2);
		for (int i = 0; i < audioInput.bufferSize() - 1; i++) {
			rotateX(PI / 6);
			fill(255, 10, 150, 150);
			ellipse(i, i, kickSize, kickSize);
			rotateY(PI / 6);
			fill(255, 255, 10, 150);
			ellipse(i, i, snareSize, snareSize);
			rotateZ(PI / 3);
			fill(10, 180, 255, 150);
			ellipse(i, i, hatSize, hatSize);
		}
	}

	public void stop() {
		audioInput.close();
		minim.stop();
		super.stop();
	}

	class BeatListener implements AudioListener {
		private BeatDetect beat;
		private AudioSource source;

		BeatListener(BeatDetect beat, AudioSource source) {
			this.source = source;
			this.source.addListener(this);
			this.beat = beat;
		}

		public void samples(float[] samps) {
			beat.detect(source.mix);
		}

		public void samples(float[] sampsL, float[] sampsR) {
			beat.detect(source.mix);
		}
	}

}