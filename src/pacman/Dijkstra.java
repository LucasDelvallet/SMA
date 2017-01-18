package pacman;

import sma.model.Position;

public class Dijkstra {
	private int[][] cells;
	private SMAPacman sma;
	
	public Dijkstra(int sizeX, int sizeY, SMAPacman sma) {
		this.sma = sma;
		cells = new int[sizeX][sizeY];
	}
	
	public void compute(Position avatar) {
		// TODO DIDI
	}
	
	public int getCellValue(Position p) {
		return cells[p.getX()][p.getY()];
	}
}
