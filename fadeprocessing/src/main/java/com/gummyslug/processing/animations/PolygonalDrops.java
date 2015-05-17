package com.gummyslug.processing.animations;

import java.util.ArrayList;

import com.gummyslug.processing.opc.AnimationApplet;

public class PolygonalDrops extends AnimationApplet {

	int DROP_RADIUS = 20;
	int SPEED = 2;
	int ALPHA_SPEED = 2;
	int MAX_NUMBER_OF_SHAPES = 3;
	int MAX_NUMBER_OF_VERTEX = 12;
	float TIME_TO_CREATE_DROPS = 2;
	int CENTER_X;
	int CENTER_Y;
	int numberOfVertex;
	int currentShape;
	int circleRadius;
	int startTime;

	ArrayList<Drop> drops;

	public void setupInternal() {
		smooth();
		drops = new ArrayList<Drop>();
		numberOfVertex = 12;
		currentShape = 0;
		circleRadius = width / 8;
		CENTER_X = width / 2;
		CENTER_Y = height / 2;
		startTime = millis();
		createDrops();
	}

	public void draw() {
		background(0);

		translate(CENTER_X, CENTER_Y);

		for (int i = 0; i < drops.size(); i++) {
			drops.get(i).update();
			drops.get(i).render();
		}

		for (int i = 0; i < drops.size(); i++)
			if (drops.get(i).finished())
				drops.remove(i);

		int ellapsedTime = (millis() - startTime) / 1000;
		if (ellapsedTime > TIME_TO_CREATE_DROPS) {
			startTime = millis();
			createDrops();
		}
	}

	void createDrops() {
		float deltaAngle = radians(360.0f / numberOfVertex);
		float angle = 0;

		for (int i = 0; i < numberOfVertex; i++) {
			float x = circleRadius * cos(angle);
			float y = circleRadius * sin(angle);
			drops.add(new Drop(currentShape, DROP_RADIUS, x, y, SPEED, ALPHA_SPEED));
			angle += deltaAngle;
		}
	}

	public void keyPressed() {
		if (key == 's' || key == 'S')
			currentShape = (currentShape + 1) % MAX_NUMBER_OF_SHAPES;

		if (key == 'n' || key == 'N') {
			numberOfVertex++;
			if (numberOfVertex > MAX_NUMBER_OF_VERTEX)
				numberOfVertex = 3;
		}
	}

	class Drop {
		// Note about shapes:
		// 0 => Circle, defaultShape
		// 1 => Rectangle
		// 2 => Triangle

		int shape;
		float px, py;
		float radius;
		float radiusSpeed, fillAlphaSpeed, strokeAlphaSpeed;
		float red, green, blue, fillAlpha, strokeAlpha;

		public Drop(int _shape, float _radius, float _px, float _py, float _radiusSpeed, float alphaSpeed) {
			shape = _shape;
			radius = _radius;
			px = _px;
			py = _py;
			radiusSpeed = _radiusSpeed;
			fillAlphaSpeed = alphaSpeed;
			strokeAlphaSpeed = 0.7F * alphaSpeed;
			fillAlpha = 255;
			strokeAlpha = 255;
			red = random(0, 255);
			green = random(0, 255);
			blue = random(0, 255);
		}

		public boolean finished() {
			return strokeAlpha < 0;
		}

		public void update() {
			radius += radiusSpeed;
			fillAlpha -= fillAlphaSpeed;
			strokeAlpha -= strokeAlphaSpeed;
		}

		public void render() {
			stroke(red, green, blue, strokeAlpha);
			strokeWeight(3);
			fill(red, green, blue, fillAlpha);

			float cos30 = radians(30);

			// I hate magic numbers, but that will have to do for now
			switch (shape) {
			// CIRCLE
			case 0:
				ellipse(px, py, radius, radius);
				break;

			// RECTANGLE
			case 1:
				rectMode(CENTER);
				rect(px, py, radius, radius);
				break;

			// TRIANGLE
			// Note: the vertices of the equilateral triangle are calculated
			// using some relations between
			// the apothem position and the circle on which the triangle is
			// inscribed.
			// Reference:
			// http://www.vitutor.com/geometry/plane/equilateral_triangle.html
			case 2:
				triangle(px + radius * cos30, py + radius * 0.5F, px, py - radius * 0.5F, px - radius * cos30, py
						+ radius * 0.5F);
				break;

			default:
				println("BUG IN CLASS Drop: Drop Mode should never reach default case");
				break;
			}
		}
	}

}
