package com.gummyslug.processing.animations;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import processing.core.PImage;

import com.gummyslug.processing.opc.AnimationApplet;

public class Camera extends AnimationApplet {

	private static final long serialVersionUID = 1L;

	private OpenCVFrameGrabber grabber;

	public void setupInternal() {
		frameRate(30);
		grabber = initializeGrabber();
	}

	private OpenCVFrameGrabber initializeGrabber() {
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(1);
		try {
			grabber.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grabber;
	}

	public void draw() {
		background(0);
		IplImage frame = grabFrame();
		if (frame != null) {
			PImage pImage = convert((frame.getBufferedImage()));
			if (pImage != null) {
				image(pImage, (width - getWidth()) / 2, (height - getHeight()) / 2, getWidth(), getHeight());
			}
		}
	}

	protected IplImage grabFrame() {
		try {
			return grabber.grab();
		} catch (Exception e) {
			return null;
		}
	}

	protected PImage convert(BufferedImage bufferedImage) {
		if (bufferedImage != null) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "jpg", baos);
				byte[] bytes = baos.toByteArray();
				Image awtImage = Toolkit.getDefaultToolkit().createImage(bytes);
				return flip(loadImageMT(awtImage));
				// return loadImageMT(awtImage);
			} catch (IOException e) {
				// silence
			}
		}
		return null;
	}

	public PImage flip(PImage image) {
		PImage reverse = new PImage(image.width, image.height);
		for (int i = 0; i < image.width; i++) {
			for (int j = 0; j < image.height; j++) {
				reverse.set(image.width - 1 - i, j, image.get(i, j));
			}
		}
		return reverse;
	}

}
