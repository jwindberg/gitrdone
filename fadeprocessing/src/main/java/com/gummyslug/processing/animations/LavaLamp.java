package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class LavaLamp extends AnimationApplet {

	private static final long serialVersionUID = 1L;
	blob[] blobs;
	poussiere[] poussieres;

	public void setupInternal() {
		smooth();
		stroke(255, 100);
		// strokeWeight(1);
		noFill();
		blobs = new blob[10];
		for (int a = 0; a < 10; a++) {
			blobs[a] = new blob(random(-10, width + 10), random(-10, 600));
		}
		poussieres = new poussiere[20];
		for (int a = 0; a < 20; a++) {
			poussieres[a] = new poussiere();
		}
	}

	public void draw() {
		background(0);
		for (int a = 0; a < poussieres.length; a++) {
			stroke(random(160, 180), 200);
			poussieres[a].dessine();
		}
		stroke(255, 100);
		for (int a = 0; a < blobs.length; a++) {
			blobs[a].dessine();
		}
	}

	class variateur {
		float mini, vitesse, angle, val, diff;

		variateur(float mini1, float maxi1, float etapes, float depart) {
			val = mini;
			angle = depart;
			mini = mini1;
			diff = maxi1 - mini1;
			vitesse = 360.0f / etapes;
		}

		void avance() {
			angle += vitesse;
			float rad = (cos(angle / (180.0f / PI)) + 1) / 2;
			val = mini + rad * diff;
		}
	}

	class blob {
		float x, y, vX, vY;
		float[] angles;
		variateur[] rayons;
		int n;
		int couleur;
		variateur respiration;

		blob(float x, float y) {
			couleur = color(random(230, 255), random(100, 230), 0, 200);
			this.x = x;
			this.y = y;
			n = floor(random(8, 20));
			angles = new float[n];
			rayons = new variateur[n];
			float dec = 360.0f / n;
			float raydebase = random(20, 100);
			vY = -random(100f - raydebase) * 0.01f;
			vX = random(-0.03f, 0.03f);
			respiration = new variateur(-5.5f, 5.5f, 200f, random(360));
			for (int a = 0; a < n; a++) {
				angles[a] = (a * dec) / (180.0f / PI);
				rayons[a] = new variateur(raydebase + random(raydebase / 17), raydebase
						+ random(raydebase / 30, raydebase / 20), random(120, 160), random(360));
			}
		}

		void dessine() {
			float fx = 0, fy = 0, fx2 = 0, fy2 = 0, fx3 = 0, fy3 = 0;
			y += respiration.val * 0.05;
			y += vY;
			boolean rien = true;
			respiration.avance();
			x += random(-0.4f, 0.4f) + vX;
			beginShape();
			fill(couleur);
			float plus = random(-0.01f, 0.01f);
			for (int a = 0; a < n; a++) {
				angles[a] += plus;
				rayons[a].avance();
				float rad = angles[a];
				float ix = cos(rad) * (rayons[a].val + respiration.val);
				float ig = sin(rad) * (rayons[a].val - respiration.val);
				curveVertex(x + ix, y + ig);
				if ((y + ig) > 0) {
					rien = false;
				}
				if (a == 0) {
					fx = x + ix;
					fy = y + ig;
				}
				if (a == 1) {
					fx2 = x + ix;
					fy2 = y + ig;
				}
				if (a == 2) {
					fx3 = x + ix;
					fy3 = y + ig;
				}
			}
			curveVertex(fx, fy);
			curveVertex(fx2, fy2);
			curveVertex(fx3, fy3);
			endShape(CLOSE);
			if (rien == true) {
				y = 700;
				x = random(-20, 520);
			}
		}

	}

	class poussiere {
		float x, y;

		poussiere() {
			y = random(height);
			x = random(width);
		}

		void dessine() {
			y += random(0.3f, 2f);
			x += random(-1, 1);
			if (y > (width + 10)) {
				y = -10;
				x = random(width);
			}
			point(x, y);
		}
	}

}
