package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

import processing.core.PVector;

public class DancingFlame extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	/**
	 * dancing flame
	 *
	 * @author aa_debdeb
	 * @date 2016/10/10
	 */

	PVector offset1, offset2;
	float scale = 0.01f;
	int c1, c2;
	int mapSize = 250;
	int size = 300;

	public void setupInternal() {
		noStroke();
		offset1 = new PVector(random(10000), random(10000));
		offset2 = new PVector(random(10000), random(10000));
		mousePressed();
	}

	public void mousePressed() {
		c1 = color(random(255), random(255), random(255));
		c2 = color(random(255), random(255), random(255));
	}

	public void draw() {
		background(60);
		translate(width / 2, height / 2);
		for (float radious = mapSize; radious > 0; radious -= 10) {
			fill(map(radious, 0, mapSize, red(c1), red(c2)), map(radious, 0, mapSize, green(c1), green(c2)),
					map(radious, 0, mapSize, blue(c1), blue(c2)));
			beginShape();
			for (float angle = 0; angle < 360; angle += 0.5) {
				float radian = radians(angle);
				float x = radious * cos(radian);
				float y = radious * sin(radian);
				float nx = x + map(noise(x * scale + offset1.x, y * scale + offset1.y, frameCount * 0.015f), 0, 1, -size,
						size);
				float ny = y + map(noise(x * scale + offset2.x, y * scale + offset2.y, frameCount * 0.015f), 0, 1, -size,
						size);
				vertex(nx, ny);
			}
			endShape(CLOSE);
		}
	}

}
