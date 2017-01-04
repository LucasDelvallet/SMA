package sma.model;

import java.awt.Color;
import java.util.Random;

public class Agent {

	private Random rand;
	private Color color;
	private Position currentPosition, nextPosition, nextDeterminedMove;
	private boolean mustApplyDeterminedMove;
	protected Environment environment;
	
	
	public Agent(Environment environment, int x, int y){
		this.environment = environment;
		rand = new Random();
		currentPosition = new Position(x, y);
		nextPosition = new Position();
		nextDeterminedMove = new Position();
		mustApplyDeterminedMove = false;
	}
	
	public void update(){
		currentPosition = nextPosition;
		nextPosition = null;
	}
	
	public void decide(){
		if(mustApplyDeterminedMove) {
			mustApplyDeterminedMove = false;
			nextPosition.setX(currentPosition.getX() + nextDeterminedMove.getX());
			nextPosition.setY(currentPosition.getY() + nextDeterminedMove.getY());
		} else {
			setNextPosition();
		}
		
		// CheckNextPosition ici ??
		checkNextPosition();
	}
	
	private void setNextPosition() {
		switch(rand.nextInt(7)) {
		case 0:
			nextPosition.setX(nextPosition.getX() - 1);
			break;
		case 1:
			nextPosition.setX(nextPosition.getX() - 1);
			nextPosition.setY(nextPosition.getY() + 1);
			break;
		case 2:
			nextPosition.setY(nextPosition.getY() + 1);
			break;
		case 3:
			nextPosition.setX(nextPosition.getX() + 1);
			nextPosition.setY(nextPosition.getY() + 1);
			break;
		case 4:
			nextPosition.setY(nextPosition.getY() + 1);
			break;
		case 5:
			nextPosition.setX(nextPosition.getX() - 1);
			nextPosition.setY(nextPosition.getY() + 1);
			break;
		case 6:
			nextPosition.setX(currentPosition.getX() - 1);
			break;
		case 7:
			nextPosition.setX(currentPosition.getX() - 1);
			nextPosition.setY(currentPosition.getY() - 1);
			break;
		}
	}
	
	private void checkNextPosition() {
		int x = 0, y = 0;
		
		// Check wall colision
		if(nextPosition.getX() < 0) {
			mustApplyDeterminedMove = true;
			nextPosition = currentPosition;
			x = 1;
		}
		
		if(nextPosition.getX() > environment.getWidth() - 1) {
			mustApplyDeterminedMove = true;
			nextPosition = currentPosition;
			x = -1;
		}
		
		if(nextPosition.getY() < 0) {
			mustApplyDeterminedMove = true;
			nextPosition = currentPosition;
			y = 1;
		}
		
		if(nextPosition.getY() > environment.getHeight() - 1) {
			mustApplyDeterminedMove = true;
			nextPosition = currentPosition;
			y = -1;
		}
		
		// Check agent collisions
		
		nextDeterminedMove = new Position(x, y);
	}
}
