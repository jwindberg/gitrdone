package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class ColorPulse extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	int hue, saturation = 255, value = 100;
	int hueStep = 6, saturationStep = 5, valueStep = 4;

	public void setupInternal() {
		colorMode(HSB);
		background(0);
	}

	public void draw() {

		fill(hue, saturation, value);
		rect(0, 0, height, width);

		if (value < 0 || value > 255) {
			valueStep = -valueStep;
			if (valueStep == Math.abs(valueStep)) {
				if (hue < 0 || hue > 255) {
					hueStep = -hueStep;
				}
				hue += hueStep;
				System.out.println(hue);
			}

		}
		value += valueStep;

	}

}
