package pacman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import core.Agent;
import sma.model.Position;

public class Dijkstra {
	private int[][] cells;
	private Set<Position> unvisited ;
	private Set<Position> visited ;
	private SMAPacman sma;
	private List<Position> surroundings ;
	private boolean negateValue ;
	
	public Dijkstra(int sizeX, int sizeY, SMAPacman sma) {
		this.sma = sma;
		cells = new int[sizeX][sizeY];
		unvisited = new HashSet<Position>();
		visited = new HashSet<Position>();
		
		surroundings = new ArrayList<Position>();
		surroundings.add(new Position(1, 0));
		surroundings.add(new Position(1, 1));
		surroundings.add(new Position(0,1));
		surroundings.add(new Position(-1,1));
		surroundings.add(new Position(-1,0));
		surroundings.add(new Position(-1,-1));
		surroundings.add(new Position(0,-1));
		surroundings.add(new Position(1,-1));
		
		negateValue = false;
	}
	
	public int getCellValue(Position p) {
		return cells[p.getX()][p.getY()];
	}
	
	public Position getNextMove(Position p){
		
		int value = cells[p.getX()][p.getY()];
		Position nextMove = new Position(0,0);
		for(Position surrounding : surroundings){
			int x = p.getX() + surrounding.getX();
			int y = p.getY() + surrounding.getY();
			if(x >= 0 && x < cells.length && y >= 0 && y < cells[0].length){
				int nextValue = cells[x][y];
				if(nextValue < value){
					nextMove = new Position(surrounding);
					value = nextValue;
				}
			}
		}
		
		return nextMove;
	}
	
	public void compute(Position avatar, boolean negateValue) {
		Agent[][] agents = sma.getEnvironment().agentsPosition;
		
		this.negateValue = negateValue;
		unvisited.clear();
		visited.clear();
		
		Position currentCell ; 
		for(int x = 0; x < cells.length; x++){
			for(int y = 0; y < cells[x].length; y++){
				cells[x][y] =  cells.length * cells[x].length;
				if((sma.getEnvironment().agentsPosition[x][y] != null && sma.getEnvironment().agentsPosition[x][y].getClass().getSimpleName().equals("Wall"))){
					visited.add(new Position(x,y));
				}
			}
		}
		currentCell = new Position(avatar.getX(),avatar.getY());
		cells[avatar.getX()][avatar.getY()] = 0;
		visitNeighborhood(currentCell);
		
		while(unvisited.size() != 0){
			int lowestValue = cells.length * cells[0].length;
			for(Position p : unvisited){
				int pValue = cells[p.getX()][p.getY()];
				if(pValue < lowestValue){
					currentCell = p;
					lowestValue = pValue;
				}
			}
			if(lowestValue == cells.length * cells[0].length){
				break;
			}
			visitNeighborhood(currentCell);
		}
	}
	
	private void visitNeighborhood(Position currentCell){
		int currentValue = cells[currentCell.getX()][currentCell.getY()];
		
		visitNeighbor(currentCell.getX()+1, currentCell.getY(),currentValue);
		visitNeighbor(currentCell.getX()+1, currentCell.getY()+1,currentValue);
		visitNeighbor(currentCell.getX(), currentCell.getY()+1,currentValue);
		visitNeighbor(currentCell.getX()-1, currentCell.getY()+1,currentValue);
		visitNeighbor(currentCell.getX()-1, currentCell.getY(),currentValue);
		visitNeighbor(currentCell.getX()-1, currentCell.getY()-1,currentValue);
		visitNeighbor(currentCell.getX(), currentCell.getY()-1,currentValue);
		visitNeighbor(currentCell.getX()+1, currentCell.getY()-1,currentValue);

		unvisited.remove(currentCell);
		visited.add(currentCell);
	}
	
	private void visitNeighbor(int x, int y, int value){
		Position p = new Position(x,y);
		if(x >= 0 && y >= 0 && x < cells.length && y < cells[0].length && !visited.contains(p)){
			
			//if(!(sma.getEnvironment().agentsPosition[x][y] != null && sma.getEnvironment().agentsPosition[x][y].getClass().getSimpleName().equals("Wall"))){
			unvisited.add(p);
			//}
			if(negateValue){
				if(cells[x][y] < value - 1){
					cells[x][y] = value - 1;
				}
			}else{
				if(cells[x][y] > value + 1){
					cells[x][y] = value + 1;
				}
			}
			
		}
	}
	
	
}
