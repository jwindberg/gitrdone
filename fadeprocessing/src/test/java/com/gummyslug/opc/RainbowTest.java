package com.gummyslug.opc;

import org.junit.Test;

public class RainbowTest {

	@Test
	public void testUpdatePattern() {
		OpenPixelControl opc = new OpenPixelControl("192.168.1.30", 7890, 16, 16);

		Rainbow rainbow = new Rainbow(opc);
		rainbow.updatePattern();
		
	}

}
