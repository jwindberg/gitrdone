package com.gummyslug.processing.animations;

import java.util.ArrayList;
import java.util.List;

import processing.core.PConstants;

import com.gummyslug.processing.opc.AnimationApplet;

public class PlasmaGlobe extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	List<SpWalker> plasma = new ArrayList<SpWalker>();
	List<SpWalker> core = new ArrayList<SpWalker>();

	float rotX, rotY;

	public void setupInternal() {
		colorMode(HSB, 220, 100, 10, 500);
		for (int i = 0; i < 250; i++) {
			plasma.add(new SpWalker(100));
			core.add(new SpWalker(20));
		}
	}

	protected String getRenderer() {
		return PConstants.P3D;
	}

	public void draw() {
		background(0);

		pushMatrix();
		sphereDetail(12);
		translate(width / 2, height / 2, mouseY / 2);
		fill(150, 50, 200, 50);
		// noFill();
		sphere(20);
		sphere(100);
		popMatrix();
		for (int i = 0; i < plasma.size(); i++) {
			SpWalker p = (SpWalker) plasma.get(i);
			SpWalker c = (SpWalker) core.get(i);

			noFill();
			stroke(130, 100, 250, 10);
			strokeWeight(2);
			if (dist(c.x_1, c.y_1, c.z_1, p.x_1, p.y_1, p.z_1) < 85) {
				for (int r = 0; r < 20; r++) {
					float ranx = random(-10, 10);
					float rany = random(-10, 10);
					float ranz = random(-10, 10);
					// bezier (c.x_1 + ranx/2,c.y_1 + rany/2,c.z_1+
					// ranz/2,c.x_1+(p.x_1-c.x_1)/2,c.y_1+(p.y_1-c.y_1)/2,c.z_1+(p.z_1-c.z_1)/2,c.x_1+(p.x_1-c.x_1)/2,c.y_1+(p.y_1-c.y_1)/2,c.z_1+(p.z_1-c.z_1)/2,p.x_1+ranx,p.y_1+rany,p.z_1+
					// ranz);
					// ---new bezier style added by Marcos Frankowicz
					bezier(c.x_1 + ranx / 2, c.y_1 + rany / 2, c.z_1 + ranz / 2, c.x_1 + cos(p.x_1 - c.x_1) / 2, c.y_1
							- sin(p.y_1 - c.y_1) / 2, c.z_1 - sin(p.z_1 - c.z_1) / 2, c.x_1 + cos(p.x_1 - c.x_1) / 2,
							c.y_1 + (p.y_1 - c.y_1) / 2, c.z_1 + (p.z_1 - c.z_1) / 2, p.x_1 + ranx, p.y_1 + rany, p.z_1
									+ ranz);
				}

				fill(200, 80, 20, 40);
			} else
				fill(0, 0, 0, 0);

			c.Draw();
			p.Draw();
		}
	}

	class SpWalker {

		int x, y, z;
		float x_1;
		float y_1;
		float z_1;
		float x_2;
		float y_2;
		float z_2;
		int r = 100;
		float theta_1, phi_1;
		float theta_speed = 2;
		float phi_speed = 2;
		float speed = 1;
		float rot, rotX, rotY;

		SpWalker(int r) {
			this.r = r;
			rot = round(random(-2f, 1f));
			speed = random(0.5f, 2f);
			theta_1 = random(360);
			phi_1 = random(360);
		}

		void Draw() {
			// rotX += (mouseX-width/2)*0.001;
			// rotY += (mouseY-height/2)*0.0001;

			theta_1 += random(-speed, speed);
			phi_1 += random(0, speed);
			// ---added by Steven Kay ------
			float scaler = 0.000005f;
			float offset = (float) frameCount / 50.0f;
			float ang = ((float) (Math.PI * 2.0)) * noise(0 + (this.x * scaler), offset + (this.y * scaler));
			theta_1 += (float) speed * (float) Math.cos(ang);
			phi_1 += (float) speed * (float) Math.sin(ang);
			// --------------------
			pushMatrix();
			translate(width / 2, height / 2, mouseY / 2);
			if (rot >= 0)
				rotateX(radians(phi_1 * -1));
			else
				rotateX(radians(phi_1));
			rotateY(radians(theta_1));
			rotateZ(rotX);
			translate(0, 0, -r);
			// rotateZ (noise(speed));
			noStroke();
			ellipse(x, y, r / 3, r / 3);
			sphereDetail(8, 4);
			scale(1f, 1f, .5f);
			// sphere(r/6);
			x_1 = modelX(0, 0, 0);
			y_1 = modelY(0, 0, 0);
			z_1 = modelZ(0, 0, 0);

			// point(x_1 + x,y_1 + y,z_1 + z);
			popMatrix();
		}
	}

}
