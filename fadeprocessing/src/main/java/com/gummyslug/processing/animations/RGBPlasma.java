package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

// Adapted for Processing from "Plasma Fractal" - Written January, 2002 by Justin Seyster (thanks for releasing into public domain, Justin).

public class RGBPlasma extends AnimationApplet {

	private static final long serialVersionUID = 1L;
/*
	 * RGB Plasma
	 *
	 * Created by Rene Hangstrup Møller
	 *
	 * Warming up for the Processing workshop at Open Space Aarhus
	 * http://osaa.dk/wiki/index.php/Processingworkshops
	 */
	 
	int[] table = new int[256];
	 
public	void setupInternal() {
	  frameRate(30);
	 
	  // One period of the sinus function shifted to range [0-255] 
	  for (int i = 0; i < 256; i++) {
	    table[i] = (int)(128 + 127.0f * sin(i * TWO_PI / 256.0f));
	  }
	   
	}
	 
public	void draw() {
	  // grab some samples, hmm could have used lookup table...
	  int t = (int)(128 + 127.0 * sin(0.0013f * (float)millis()));
	  int t2 = (int)(128 + 127.0 * sin(0.0023f * (float)millis()));
	  int t3 = (int)(128 + 127.0 * sin(0.0007f * (float)millis()));
	   
	  loadPixels();
	   
	  for (int y = 0; y < width; y++) {
	    for (int x = 0; x < height; x++) {
	      // Define a function for each color component that depends on the
	      // x,y coordinate and time. Use the lookup table for nice swirly movement.
	      // There is no deeper logic here, I just experimented with the functions
	      // untill I found something that looked pleasing.
	      int r = table[(x / 5 + t / 4 + table[(t2 / 3 + y / 8) & 0xff]) & 0xff];
	      int g = table[(y / 3 + t + table[(t3 + x / 5) & 0xff]) & 0xff];
	      int b = table[(y / 4 + t2 + table[(t + g / 4 + x / 3) & 0xff]) & 0xff];
	      pixels[x + y * width] = color(r, g, b);
	    }
	  }
	   
	  updatePixels();
	}

}
