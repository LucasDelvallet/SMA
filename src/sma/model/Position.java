package sma.model;

public class Position {
	private int x, y;

	public Position() {
		this.x = 0;
		this.y = 0;
	}
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		Position other = (Position) obj;
		return (other.x == x) && (other.y == y); 
	}
	
	public boolean isOpposite(Position p) {
		return (-p.getX() == x) && (-p.getY() == y);
	}
}
