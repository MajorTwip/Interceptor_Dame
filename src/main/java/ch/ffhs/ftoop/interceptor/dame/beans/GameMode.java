package ch.ffhs.ftoop.interceptor.dame.beans;

public enum GameMode {
	Singleplayer8X8(true, new int[][]{{0, 0}, {2, 0}, {4, 0}, {6, 0}, {1, 1}, {3, 1}, {5, 1}, {7, 1}}),
	Multiplayer8X8(false, new int[][]{{0, 0}, {2, 0}, {4, 0}, {6, 0}, {1, 1}, {3, 1}, {5, 1}, {7, 1}});

	private int[][] coordinates;
	private boolean singlePlayer;

	GameMode(boolean computerPlayer, int[][] coordinates) {
		this.coordinates = coordinates;
		this.singlePlayer = computerPlayer;
	}

	public int[][] getCoordinates() {
		return coordinates;
	}

	public boolean isSinglePlayer() {
		return singlePlayer;
	}
}
