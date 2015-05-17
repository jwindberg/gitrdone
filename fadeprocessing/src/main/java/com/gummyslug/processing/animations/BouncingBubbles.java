package com.gummyslug.processing.animations;

import java.util.Random;

import com.gummyslug.processing.opc.AnimationApplet;

public class BouncingBubbles extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	private static final Random RANDOM = new Random();

	int numBalls = 12;
	float spring = 0.05f;
	float gravity = 0.03f;
	float friction = -0.9f;
	Ball[] balls = new Ball[numBalls];

	int[] colarray = { color(100, 255, 35), color(220, 200, 85), color(185, 65, 200), color(0, 145, 35),
			color(245, 35, 200) };

	private int getRandomColor() {
		return colarray[RANDOM.nextInt(colarray.length)];
	}

	public void setupInternal() {
		for (int i = 0; i < numBalls; i++) {
			balls[i] = new Ball(random(width), random(height), random(30, 70), i, getRandomColor(), balls);
		}
		noStroke();
	}

	public void draw() {
		background(0);
		for (Ball ball : balls) {
			ball.collide();
			ball.move();
			ball.display();
		}
	}

	class Ball {

		float x, y;
		float diameter;
		float vx = 0;
		float vy = 0;
		int id;
		Ball[] others;
		int color;

		Ball(float xin, float yin, float din, int idin, int color, Ball[] oin) {
			x = xin;
			y = yin;
			diameter = din;
			id = idin;
			others = oin;
			this.color = color;
		}

		void collide() {
			for (int i = id + 1; i < numBalls; i++) {
				float dx = others[i].x - x;
				float dy = others[i].y - y;
				float distance = sqrt(dx * dx + dy * dy);
				float minDist = others[i].diameter / 2 + diameter / 2;
				if (distance < minDist) {
					float angle = atan2(dy, dx);
					float targetX = x + cos(angle) * minDist;
					float targetY = y + sin(angle) * minDist;
					float ax = (targetX - others[i].x) * spring;
					float ay = (targetY - others[i].y) * spring;
					vx -= ax;
					vy -= ay;
					others[i].vx += ax;
					others[i].vy += ay;
				}
			}
		}

		void move() {
			vy += gravity;
			x += vx;
			y += vy;
			if (x + diameter / 2 > width) {
				x = width - diameter / 2;
				vx *= friction;
			} else if (x - diameter / 2 < 0) {
				x = diameter / 2;
				vx *= friction;
			}
			if (y + diameter / 2 > height) {
				y = height - diameter / 2;
				vy *= friction;
			} else if (y - diameter / 2 < 0) {
				y = diameter / 2;
				vy *= friction;
			}
		}

		void display() {
			fill(color);
			ellipse(x, y, diameter, diameter);
		}
	}

}
