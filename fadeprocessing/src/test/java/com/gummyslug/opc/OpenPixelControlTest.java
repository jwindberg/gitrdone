package com.gummyslug.opc;

import org.junit.Assert;
import org.junit.Test;

public class OpenPixelControlTest {
	
	@Test
	public void testVertical() {

		OpenPixelControl opc = new OpenPixelControl("192.168.1.30", 7890, 16, 16);
		opc.writePixels();
		Color color = Color.getRGB(0, 255, 0);
		opc.setVertical(3, color);
		opc.writePixels();
		
	}
		

	// @Test
	public void blah() {
		OpenPixelControl opc = new OpenPixelControl("192.168.1.30", 7890, 16, 16);
		opc.clear();
		Color red = Color.getRGB(100, 100, 100);
		for (int i = 0; i < opc.getNumPixels(); i++) {
			opc.setPixel(i, red);
			opc.writePixels();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// @Test
	public void something() {
		OpenPixelControl opc = new OpenPixelControl("192.168.1.30", 7890, 16, 16);
		opc.clear();
		Color red = Color.getRGB(100, 100, 100);
		opc.setPixel(10, red);

	}

//	@Test
	public void testGrid() {

		OpenPixelControl opc = new OpenPixelControl("192.168.1.30", 7890, 16, 16);

		Assert.assertEquals(255, opc.getPosition(15, 15));
		Assert.assertEquals(0, opc.getPosition(0, 0));
		Assert.assertEquals(1, opc.getPosition(1, 0));
		Assert.assertEquals(15, opc.getPosition(15, 0));
		
		Assert.assertEquals(16, opc.getPosition(0, 1));
		Assert.assertEquals(17, opc.getPosition(1, 1));

	}

//	@Test
	public void testWritePixels() {
		OpenPixelControl opc = new OpenPixelControl("192.168.1.30", 7890, 16, 16);

		Color red = Color.getRGB(255, 0, 0);

		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 16; x++) {
				// opc.clear();
				opc.setPixel(x, y, red);
				opc.writePixels();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		// for (int i = 0; i < opc.getNumPixels(); i++) {
		// if (i % 2 == 0) {
		// opc.setPixel(i, red);
		// }
		// }

	}

}
