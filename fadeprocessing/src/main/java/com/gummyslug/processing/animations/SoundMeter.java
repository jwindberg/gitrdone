package com.gummyslug.processing.animations;

import java.util.Arrays;
import java.util.Collections;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.Minim;

public class SoundMeter extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	private Minim minim;
	private AudioInput audioInput;
	private float minLevel = 0f;
	private float maxLevel = 0f;
	private int sampleSize = 10;
	private Float[] samples = new Float[sampleSize];
	private int currentSample = 0;
	private int currentColor;

	public SoundMeter() {
		minim = new Minim(this);
		audioInput = minim.getLineIn(Minim.MONO, 2048, 192000.0f);
		for (int i = 0; i < sampleSize; i++) {
			samples[i] = 0f;
		}
	}

	public void setupInternal() {
		background(0);
		colorMode(HSB);
		smooth();
		opc.showLocations(false);
	}

	public void draw() {
		background(0);
		float level = audioInput.mix.level();
		samples[currentSample] = level;
		currentSample++;
		if (currentSample >= sampleSize) {
			currentSample = 0;
		}

		// if (level > maxLevel) {
		// maxLevel = level;
		// System.out.println("New Max!!!: " + maxLevel);
		// }
		// if (level < minLevel) {
		// minLevel = level;
		// System.out.println("New Min!!:  " + minLevel);
		// }

		maxLevel = Collections.max(Arrays.asList(samples));

		float newHeight = map(level, minLevel, maxLevel, 0, height);
		fill(currentColor, 100, 100);
		currentColor+=1;
		if (currentColor > 254) {
			currentColor = 0;
		}
		rect(0, height - newHeight, width, height);

	}

}
