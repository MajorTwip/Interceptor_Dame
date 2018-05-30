package ch.ffhs.ftoop.interceptor.dame.beans;

public enum GameMode {
	SinglePlayer8X8(true, 7, 7, new int[][]{{0, 0}, {2, 0}, {4, 0}, {6, 0}, {1, 1}, {3, 1}, {5, 1}, {7, 1}}),
	MultiPlayer8X8(false, 7, 7, new int[][]{{0, 0}, {2, 0}, {4, 0}, {6, 0}, {1, 1}, {3, 1}, {5, 1}, {7, 1}}),
	Sandbox4X4(false, 4, 4, new int[][]{});

	private int[][] coordinates;
	private boolean singlePlayer;
	private int maxX;
	private int maxY;

	GameMode(boolean computerPlayer, int maxX, int maxY, int[][] coordinates) {
		this.coordinates = coordinates;
		this.singlePlayer = computerPlayer;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public int[][] getCoordinates() {
		return coordinates;
	}

	public boolean isSinglePlayer() {
		return singlePlayer;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}
}
