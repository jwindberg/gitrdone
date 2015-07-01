package com.gummyslug.processing.animations;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.AudioListener;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;

public class MusicBall extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	Minim minim;
	AudioInput audioInput;
	BeatDetect beat;
	BeatListener bl;
	List<Ball> balls = new ArrayList<Ball>();

	public void setupInternal() {
		// always start Minim before you do anything with it
		minim = new Minim(this);
		audioInput = minim.getLineIn(Minim.STEREO, 2048, 192000.0f);
		frameRate(30);
		smooth();

		beat = new BeatDetect(audioInput.bufferSize(), audioInput.sampleRate());

		beat.setSensitivity(0);
		bl = new BeatListener(beat, audioInput);

		noStroke();
		opc.showLocations(false);
	}

	public void draw() {

		fill(0, 0, 0, 45);
		rect(0, 0, width, height);
		// use the mix buffer to draw the waveforms.
		// because these are MONO files, we could have used the left or right
		// buffers and got the same data
//		boolean kick = beat.isKick();
//		boolean hat = beat.isHat();
//		boolean snare = beat.isSnare();
		if (beat.isRange(5, 15, 2)) {
			int col = color(random(255), random(255), random(255));
			for (int j = 0; j < abs(audioInput.mix.level() * 50); j++) {
				float y = random(height);
				float x = random(width);
				for (int i = 0; i < abs(audioInput.mix.level() * 100); i++) {
					balls.add(new Ball(x, y, audioInput.mix.get(0) * 70, col));
				}
			}
		}
		for (int i = 0; i < balls.size(); i++) {
			Ball b = (Ball) balls.get(i);
			b.update();
			if (!b.alive) {
				balls.remove(b);
				i--;
			}
		}
	}

	public void stop() {
		// always close Minim audio classes when you are done with them
		audioInput.close();
		minim.stop();

		super.stop();
	}

	public class Ball {
		PVector loc = new PVector();
		PVector speed = new PVector(random(-2, 2), random(-2, 2));
		int col;
		boolean alive = true;
		int age = 0;

		public Ball(float x, float y, float mag, int col) {
			loc.x = x;
			loc.y = y;
			speed.normalize();
			speed.mult(mag);
			this.col = col;
		}

		public void update() {
			age += 3;
			speed.y += .1;
			loc.add(speed);
			if (loc.y > height || age >= 255)
				alive = false;
			fill(red(col), blue(col), green(col), 255 - age);
			ellipse(loc.x, loc.y, 5, 5);
		}

	}

	class BeatListener implements AudioListener {
		private BeatDetect beat;
		private AudioInput source;

		BeatListener(BeatDetect beat, AudioInput source) {
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
