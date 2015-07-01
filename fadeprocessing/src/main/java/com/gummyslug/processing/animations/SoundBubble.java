package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import ddf.minim.AudioInput;
import ddf.minim.Minim;

public class SoundBubble extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	Minim minim;
	AudioInput audioInput;

	Bubble[] _bubbleArray;
	Particle[] _perArray;

	int _rippleSize = 800;
	int _num = 40;
	int _numPer = 40;
	float _gravity = 0.05f;
	float _gravity2 = 0.93f; // for Radius
	float _noiseSeed = 0;
	int _bgColor;
	int _bgColor2;
	int _bgColor3;
	int _perColor;
	float _rotateAngle;
	float _rotateSpped = 0.12f;

	public void setupInternal() {
		colorMode(HSB, 360, 100, 100, 100);
		frameRate(30);
		_bgColor = color(220, 100, 25, 100);
		_bgColor2 = color(220, 100, 17, 100);
		_bgColor3 = color(220, 100, 0, 100);
		_perColor = color(50, 50, 50, 100);
		// =====Audio=====
		minim = new Minim(this);
		audioInput = minim.getLineIn(Minim.STEREO, 2048, 192000.0f);
		// =====Prepare bubbles=====
		_bubbleArray = new Bubble[_num];
		for (int i = 0; i < _num; i++) {
			_bubbleArray[i] = new Bubble(0, 0, color(0, 0, 0), 0);
		}
		// =====Prepare Perticles=====
		_perArray = new Particle[_numPer];
		for (int i = 0; i < _numPer; i++) {
			_perArray[i] = new Particle(random(30, 70), random(-0.01f, 0.01f), i, random(30, 80));
		}
		// =====Prepare stage=====
		scrRefsh();
	}

	public void draw() {
		_noiseSeed += 0.01;
		scrRefsh();
		translate(width / 2, height / 2);
		// =====Draw Perticles=====
		for (int i = 0; i < _numPer; i++) {
			_perArray[i].update();
		}
		// =====Draw Bubbles=====
		_rotateAngle += (noise(_noiseSeed) - 0.5) * _rotateSpped;
		rotate(_rotateAngle);
		drawRip3();
		for (int i = 0; i < _num; i++) {
			_bubbleArray[i].update();
		}
	}

	void scrRefsh() {
		smooth();
		noStroke();
		fill(_bgColor);
		rect(0, 0, width, height);
		fill(_bgColor2);
		ellipse(width / 2f, height / 2f, width * 0.4f, width * 0.4f);
		fill(_bgColor3);
		ellipse(width / 2f, height / 2f, width * 0.2f, width * 0.2f);
	}

	class Particle {
		float orbit, speed;
		int id;
		float perSize;
		float crtPosAngle = -PI * 0.5f;
		int perColor = color(50, 50, 100, 50);
		int perColor2 = color(0xCCCCCC);

		Particle(float tOrbit, float tSpeed, int tId, float tPerSize) {
			orbit = tOrbit;
			speed = tSpeed;
			id = tId;
			perSize = tPerSize;
		}

		void update() {
			noStroke();
			crtPosAngle += (0.5 + log(abs(audioInput.mix.get(0)) + 1)) * speed;
			float x = (orbit + 300 * noise(_noiseSeed + id)) * cos(crtPosAngle);
			float y = (orbit + 300 * noise(_noiseSeed + id)) * sin(crtPosAngle);
			float perDiameter = constrain((int) (perSize + 0.001 * frameCount)
					* log(abs(audioInput.mix.get(id * 10)) + 1), 4, 100);
			// float x =
			// (orbit+3*orbit*log(abs(player.mix.get(0))+1)+100*(noise(_noiseSeed)-0.5))*cos(crtPosAngle*speed);
			// float y =
			// (orbit+3*orbit*log(abs(player.mix.get(0))+1)+100*(noise(_noiseSeed)-0.5))*sin(crtPosAngle*speed);
			fill(perColor);
			ellipse(x, y, perDiameter, perDiameter);
			fill(perColor2);
			ellipse(x, y, perDiameter - 4, perDiameter - 4);

		}
	}

	class Bubble {
		float x, y;
		int bColor;
		float bRadius;

		Bubble(float tx, float ty, int tColor, float tRadius) {
			x = tx;
			y = ty;
			bColor = tColor;
			bRadius = tRadius;
		}

		void update() {
			noStroke();
			fill(bColor);
			ellipse(x, y, bRadius, bRadius);
			fill(_bgColor);
			ellipse(x + bRadius * 0.08f, y + bRadius * 0.08f, bRadius * 0.6f, bRadius * 0.6f);
		}
	}

	void drawRip3() {
		float movRadius = width * 0.40f;
		_bubbleArray[0].x = movRadius * cos(PI * audioInput.mix.get(0) * 2 - PI / 2);
		_bubbleArray[0].y = movRadius * sin(PI * audioInput.mix.get(0) * 2 - PI / 2);
		// _bubbleArray[0].bColor = color(150+150*noise(_noiseSeed), 60,70,
		// 500*abs(player.mix.get(0)));
		_bubbleArray[0].bColor = color(10 + 150 * noise(_noiseSeed), 30, 100, 100);
		_bubbleArray[0].bRadius = audioInput.mix.get(0) * (_rippleSize + 0.01f * frameCount);

		for (int i = _num - 1; i > 0; i--) {
			_bubbleArray[i].x = _bubbleArray[i - 1].x - _bubbleArray[i - 1].x * _gravity;
			_bubbleArray[i].y = _bubbleArray[i - 1].y - _bubbleArray[i - 1].y * _gravity;
			_bubbleArray[i].bColor = _bubbleArray[i - 1].bColor;
			_bubbleArray[i].bRadius = _bubbleArray[i - 1].bRadius * _gravity2;
		}
	}

	void drawRip2() {
		float speed = 0.1f;
		int movRadiusBase = 3000;
		float movRadius = constrain(movRadiusBase * audioInput.mix.get(1), 30, 600);
		int ripColor = color(random(360), random(30, 70), random(60, 90), 20);
		float ripX = width / 2 + movRadius * cos(frameCount * speed);
		float ripY = height / 2 + movRadius * sin(frameCount * speed);
		// fill(ripColor); noStroke();
		noFill();
		stroke(ripColor);
		strokeWeight(1);
		smooth();
		ellipse(ripX, ripY, audioInput.mix.get(1) * _rippleSize, audioInput.mix.get(1) * _rippleSize);
	}

	void drawRip1() {
		int ripColor = color(random(360), random(30, 70), random(50, 100), 100);
		float ripX = width / 2 + random(-300, 300);
		float ripY = height / 2 + random(-200, 200);
		fill(ripColor);
		noStroke();
		smooth();
		ellipse(ripX, ripY, audioInput.mix.get(1) * _rippleSize, audioInput.mix.get(1) * _rippleSize);
	}

}
