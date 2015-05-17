package com.gummyslug.processing;

import java.util.Random;

import com.gummyslug.processing.opc.OPC;
import com.gummyslug.processing.opc.OPC.Layout;

import processing.core.PApplet;

public class SpinningTriangles extends PApplet {
	private static final Random RANDOM = new Random();

	private static final long serialVersionUID = 1L;

	private OPC opc;

	int columns = 4;
	int rows = 2;
	boolean active = true;
	float col01, col02, col03, col04;
	float columnSize = OPC.WIDTH / columns;
	float hrad = columnSize / 2;
	float focus = 110;

	Triangle[] triangles = new Triangle[columns * columns];

	public void setup() {
		size(OPC.WIDTH, OPC.HEIGHT);
		opc = new OPC(this);
		opc.setLayout(Layout.TWO);

		noStroke();
		smooth();
		colorMode(HSB, 255);
		col01 = random(255);

		for (int column = 0; column < columns; column++) {
			for (int row = 0; row < rows; row++) {
				triangles[columns * column + row] = new Triangle(column * columnSize, row * columnSize, columns
						* column + row, random(6.28f), random(0.1f));
			}
		}
	}

	public void draw() {
		background(0);
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				triangles[columns * i + j].update();
				triangles[columns * i + j].display();
			}
		}
	}

	class Triangle {
		float x, y;
		float x1, x2, x3, x4, x5, y1, y2, y3, y4, y5, d1;
		float w1, w2;
		float angle = 0;
		float angle_120 = 6.28f / 3f;
		float angle_240 = 6.28f * 2f / 3f;
		float rotationSpeed;
		int id;

		Triangle(float x, float y, int id, float angle, float rotationSpeed) {
			this.x = x;
			this.y = y;
			this.id = id;
			this.angle = angle;
			this.rotationSpeed = rotationSpeed;

		}

		void update() {

			// set colors
			col01 = (2 * id + angle) % 255;
			col02 = 255;
			col03 = 255;

			if (active) {
				rotationSpeed = rotationSpeed + rotationSpeed * random(-0.01f, 0f);
				if (Math.abs(rotationSpeed) < 0.01) {
					rotationSpeed = random(0.33f) * randomSign();
				}
				angle = (angle + rotationSpeed);

				d1 = hrad * sin(angle);

				// center

				x1 = x + hrad;
				y1 = y + hrad;

				// points
				x2 = x1 + hrad * sin(angle);
				y2 = y1 + hrad * cos(angle);
				x3 = x1 + hrad * sin(angle + angle_120);
				y3 = y1 + hrad * cos(angle + angle_120);
				x4 = x1 + hrad * sin(angle + angle_240);
				y4 = y1 + hrad * cos(angle + angle_240);

				w1 = 2 * hrad;
				w2 = d1;

			}
		}

		void display() {
			fill(col01, col02, col03);
			stroke(22, 255, 255);
			triangle(x2, y2, x3, y3, x4, y4);

		}
	}

	public void mouseClicked() {
		active = !active;
	}

	float signs[] = { -1f, 1f };

	float randomSign() {
		return signs[RANDOM.nextInt(1)];
	}

}
