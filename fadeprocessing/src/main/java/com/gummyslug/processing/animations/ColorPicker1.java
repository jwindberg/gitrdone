package com.gummyslug.processing.animations;

import com.gummyslug.processing.opc.AnimationApplet;

public class ColorPicker1 extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	int colorPickerX, // color picker horizontal position
			colorPickerY, // color picker vertical position
			lineY, // hue line vertical position
			crossX, // saturation+brightness cross horizontal position
			crossY, // saturation+brightness cross horizontal position
			colorSelectorX = 5, // color selector button horizontal position
								// <-------------------------------------------
								// CHANGE
			colorSelectorY = 5; // color selector button vertical position
								// <-------------------------------------------
								// CHANGE

	boolean isDraggingCross = false, isDraggingLine = false, showColorPicker = false;

	int activeColor = color(100, 100, 100), interfaceColor = color(255);

	public void setupInternal() {

		smooth();

		colorMode(HSB);

		// set color picker x position to color selector + 40 and avoid it to be
		// out of screen
		colorPickerX = constrain(colorSelectorX + 40, 10, width - 340);

		// set color picker y position to color selector + 40 and avoid it to be
		// out of screen
		colorPickerY = constrain(colorSelectorY + 40, 10, height - 300);

		// set initial Line position
		lineY = colorPickerY + (int) (hue(activeColor));
		crossX = colorPickerX + (int) (saturation(activeColor));
		crossY = colorPickerY + (int) (brightness(activeColor));
	}

	public void draw() {

		background(0);

		drawColorSelector();

		if (showColorPicker) {

			drawColorPicker();
			drawLine();
			drawCross();
			drawActiveColor();
			drawValues();
			drawOK();

		}

		checkMouse();

		// set current active color
		activeColor = color(lineY - colorPickerY, crossX - colorPickerX, 255 - (crossY - colorPickerY));

	}

	void drawColorSelector() {

		stroke(interfaceColor);
		strokeWeight(1);
		fill(0);
		// rect(colorSelectorX, colorSelectorY, 20, 20);
		rect(0, 0, width-1, height-1);
		// draw color selector border at its x y position

		stroke(0);

		if (mouseX > colorSelectorX && mouseX < colorSelectorX + 20 && mouseY > colorSelectorY
				&& mouseY < colorSelectorY + 20) {
			fill(hue(activeColor), saturation(activeColor), brightness(activeColor) + 30);
		} else {
			fill(activeColor);
		}

//		 rect(colorSelectorX + 1, colorSelectorY + 1, 18, 18);
		
		rect(colorSelectorX + 1, colorSelectorY + 1, 30, 30);
//		rect(1, 1, width - 2, height - 2);

		// draw the color selector fill 1px inside the border

	}

	void drawOK() {

		if (mouseX > colorPickerX + 285 && mouseX < colorPickerX + 305 && mouseY > colorPickerY + 240
				&& mouseY < colorPickerY + 260) {
			// check if the cross is on the
			// darker color
			// optimize visibility on lighter colors
			fill(0);
		} else {// optimize visibility on darker colors

			fill(100);
		}

		text("OK", colorPickerX + 285, colorPickerY + 250);
	}

	void drawValues() {

		fill(255);
		fill(0);
		textSize(10);

		text("H: " + (int) ((lineY - colorPickerY) * 1.417647) + "°", colorPickerX + 285, colorPickerY + 100);
		text("S: " + (int) ((crossX - colorPickerX) * 0.39215 + 0.5) + "%", colorPickerX + 286, colorPickerY + 115);
		text("B: " + (int) (100 - ((crossY - colorPickerY) * 0.39215)) + "%", colorPickerX + 285, colorPickerY + 130);

		text("R: " + (int) (red(activeColor)), colorPickerX + 285, colorPickerY + 155);
		text("G: " + (int) (green(activeColor)), colorPickerX + 285, colorPickerY + 170);
		text("B: " + (int) (blue(activeColor)), colorPickerX + 285, colorPickerY + 185);

		text(hex(activeColor, 6), colorPickerX + 285, colorPickerY + 210);

	}

	void drawCross() {

		if (brightness(activeColor) < 90) {
			stroke(255);
		} else {
			stroke(0);
		}

		line(crossX - 5, crossY, crossX + 5, crossY);
		line(crossX, crossY - 5, crossX, crossY + 5);

	}

	void drawLine() {

		stroke(0);
		line(colorPickerX + 259, lineY, colorPickerX + 276, lineY);

	}

	void drawColorPicker() {

		stroke(interfaceColor);
		line(colorSelectorX + 10, colorSelectorY + 10, colorPickerX - 3, colorPickerY - 3);

		strokeWeight(1);
		fill(0);
		rect(colorPickerX - 3, colorPickerY - 3, 283, 260);

		loadPixels();

		for (int j = 0; j < 255; j++) {
			// draw a row of pixel with the same
			// brightness but progressive saturation

			for (int i = 0; i < 255; i++) {
				// draw a column of pixel with the same saturation but
				// progressive brightness
				set(colorPickerX + j, colorPickerY + i, color(lineY - colorPickerY, j, 255 - i));
			}
		}

		for (int j = 0; j < 255; j++) {
			for (int i = 0; i < 20; i++) {
				set(colorPickerX + 258 + i, colorPickerY + j, color(j, 255, 255));
			}
		}

		fill(interfaceColor);
		noStroke();
		rect(colorPickerX + 280, colorPickerY - 3, 45, 261);

	}

	void drawActiveColor() {

		fill(activeColor);
		stroke(0);
		strokeWeight(1);
		rect(colorPickerX + 282, colorPickerY - 1, 41, 80);
	}

	void checkMouse() {

		if (mousePressed) {

			if (mouseX > colorPickerX + 258 && mouseX < colorPickerX + 277 && mouseY > colorPickerY - 1
					&& mouseY < colorPickerY + 255 && !isDraggingCross) {

				lineY = mouseY;
				isDraggingLine = true;

			}

			if (mouseX > colorPickerX - 1 && mouseX < colorPickerX + 255 && mouseY > colorPickerY - 1
					&& mouseY < colorPickerY + 255 && !isDraggingLine) {

				crossX = mouseX;
				crossY = mouseY;
				isDraggingCross = true;

			}

			if (mouseX > colorSelectorX && mouseX < colorSelectorX + 20 && mouseY > colorSelectorY
					&& mouseY < colorSelectorY + 20) {
				showColorPicker = true;
			}

			if (mouseX > colorPickerX + 285 && mouseX < colorPickerX + 305 && mouseY > colorPickerY + 240
					&& mouseY < colorPickerY + 260) {
				showColorPicker = false;
			}

		} else {

			isDraggingCross = false;
			isDraggingLine = false;

		}

	}

}
