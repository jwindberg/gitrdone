package com.gummyslug.opc;

import org.junit.Assert;
import org.junit.Test;

public class ConwayLifeTest {

	@Test
	public void testEqualsBooleanArrayArrayBooleanArrayArray() {
		
		OpenPixelControl opc = new OpenPixelControl("192.168.1.30", 7890, 16, 16);
		
		boolean[][] grid1 = new boolean[16][16];
		boolean[][] grid2 = new boolean[16][16];
		ConwayLife conwayLife = new ConwayLife(opc);
		
		Assert.assertTrue(conwayLife.equals(grid1, grid2));
		grid2[4][4] = true;
		Assert.assertFalse(conwayLife.equals(grid1, grid2));

		
	}

}
