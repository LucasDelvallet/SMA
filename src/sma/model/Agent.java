package sma.model;

import java.awt.Color;
import java.util.Random;

public class Agent {

	private static Random rand = new Random(Parameters.seed);
	private Color color;
	private Position currentPosition, nextMove, nextDeterminedMove;
	private boolean mustApplyDeterminedMove;
	protected Environment environment;

	public Agent(Environment environment, Position xy) {
		this.environment = environment;
		
		int r = rand.nextInt(200);
		int g = rand.nextInt(200);
		int b = rand.nextInt(200);
		this.color = new Color(r, g, b);
		
		currentPosition = xy;
		nextMove = new Position();
		nextDeterminedMove = new Position();
		mustApplyDeterminedMove = false;
	}

	public void update() {
		environment.agentsPosition[currentPosition.getX()/Parameters.boxSize][currentPosition.getY()/Parameters.boxSize] = null;
				
		currentPosition.setX(currentPosition.getX() + nextMove.getX());
		currentPosition.setY(currentPosition.getY() + nextMove.getY());
		
		environment.agentsPosition[currentPosition.getX()/Parameters.boxSize][currentPosition.getY()/Parameters.boxSize] = this;
		
		setNextMove();
	}
	
	public Position getNextMove() {
		return nextMove;
	}
	
	public void setNextMove(Position nextMove) {
		this.nextMove = nextMove;
	}
	
	public Position getNextPosition() {
		return new Position(currentPosition.getX() + nextMove.getX(), currentPosition.getY() + nextMove.getY());
	}
	
	public Position getCurrentPosition() {
		return currentPosition;
	}
	
	public Color getColor(){
		return color;
	}

	private void checkWallCollision(){
		// Check wall colision
		
		int border_x = environment.getWidth() - Parameters.boxSize;
		int border_y = environment.getHeight() - Parameters.boxSize;
		Position nextPosition = getNextPosition();
		while(nextPosition.getX() < 0 
				|| nextPosition.getX() > border_x
				|| nextPosition.getY() < 0 
				|| nextPosition.getY() > border_y){
			
			if (nextPosition.getX() < 0) {
				nextMove.setX(0);
				nextMove.setY(-nextMove.getY());
			} else if (nextPosition.getX()  >= border_x) {
				nextMove.setX(0);
				nextMove.setY(-nextMove.getY());
			}

			if (nextPosition.getY()  < 0) {
				nextMove.setX(-nextMove.getX());
				nextMove.setY(0);
			} else if (nextPosition.getY()  >= border_y) {
				nextMove.setX(-nextMove.getX());
				nextMove.setY(0);
			}	
			nextPosition = getNextPosition();
		}
		
		
	}
	
	public void decide() {
		checkWallCollision();
		
		// Check agent collisions
		Position nextPos = getNextPosition();
		if(environment.agentsPosition[nextPos.getX()/Parameters.boxSize][nextPos.getY()/Parameters.boxSize] != null){
			Agent agentCollision = environment.agentsPosition[nextPos.getX()/Parameters.boxSize][nextPos.getY()/Parameters.boxSize];
			Position agenNextMove = agentCollision.getNextMove();
			agentCollision.setNextMove(getNextMove());
			setNextMove(agenNextMove);
		}
		
		checkWallCollision();
	}
	

	private void setNextMove() {
		switch (rand.nextInt(8)) {
		case 0:
			nextMove.setX(Parameters.boxSize);
			nextMove.setY(0);
			break;
		case 1:
			nextMove.setX(Parameters.boxSize);
			nextMove.setY(Parameters.boxSize);
			break;
		case 2:
			nextMove.setX(0);
			nextMove.setY(Parameters.boxSize);
			break;
		case 3:
			nextMove.setX(-Parameters.boxSize);
			nextMove.setY(Parameters.boxSize);
			break;
		case 4:
			nextMove.setX(-Parameters.boxSize);
			nextMove.setY(0);
			break;
		case 5:
			nextMove.setX(-Parameters.boxSize);
			nextMove.setY(-Parameters.boxSize);
			break;
		case 6:
			nextMove.setX(0);
			nextMove.setY(-Parameters.boxSize);
			break;
		case 7:
			nextMove.setX(Parameters.boxSize);
			nextMove.setY(-Parameters.boxSize);
			break;
		}
	}
}
