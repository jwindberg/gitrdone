package com.gummyslug.opc;

import java.util.Arrays;

public class ConwayLife extends Pattern {

	private boolean grid[][];
	private Color color = Color.getRandom();

	private int delay = 4;
	private int delayCount = 0;

	public ConwayLife(OpenPixelControl opc) {
		super(opc);
	}

	@Override
	public void init() {
		int width = openPixelControl.getWidth();
		grid = new boolean[width][width];
		randomize(grid);
		color = Color.getRandom();
		delayCount = 0;
	}

	private void randomize(boolean[][] grid) {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid.length; y++) {
				grid[x][y] = RANDOM.nextInt(2) == 1;
			}
		}
	}

	private boolean allDead() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid.length; y++) {
				if (grid[x][y]) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void updatePattern() {
		delayCount++;
		if (delayCount > delay) {
			openPixelControl.clear();
			for (int x = 0; x < grid.length; x++) {
				for (int y = 0; y < grid.length; y++) {
					if (grid[x][y]) {
						openPixelControl.setPixel(x, y, color);
					}
				}
			}
			if (allDead()) {
				randomize(grid);
			} else {
				nextGeneration();
			}
			delayCount = 0;
		}
	}

	private void nextGeneration() {
		int width = openPixelControl.getWidth();
		boolean[][] newGrid = new boolean[width][width];

		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid.length; y++) {
				newGrid[x][y] = isAlive(grid, width, x, y);
			}
		}

		if (equals(grid, newGrid)) {
			randomize(grid);
		} else {
			grid = newGrid;
		}
	}
	
	
	protected boolean equals(boolean[][] grid1, boolean[][] grid2) {
		int width = grid1.length;
		for (int i = 0; i < width; i++) {
			if (!Arrays.equals(grid1[i], grid2[i])) {
				return false;
			}
		}
		return true;
	}

	private static final int[][] NEIGHBOURS = { { -1, -1 }, { -1, 0 }, { -1, +1 }, { 0, -1 }, { 0, +1 }, { +1, -1 },
			{ +1, 0 }, { +1, -1 } };

	static int countNeighbours(boolean[][] grid, int x, int y) {
		int cnt = 0;
		for (int[] offset : NEIGHBOURS) {
			try {
				if (grid[x + offset[1]][y + offset[0]]) {
					cnt++;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				// eat exception
			}
		}
		return cnt;
	}

	private boolean isAlive(boolean[][] grid, int width, int x, int y) {
		int numNeighbors = countNeighbours(grid, x, y);

		if (grid[x][y]) {
			if (numNeighbors < 2 || numNeighbors > 3) {
				return false;
			}
			return true;

		} else {
			return numNeighbors == 3;
		}
	}

}
