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
		currentPosition.setX(currentPosition.getX() + nextMove.getX());
		currentPosition.setY(currentPosition.getY() + nextMove.getY());

		if (mustApplyDeterminedMove) {
			mustApplyDeterminedMove = false;
			nextMove = nextDeterminedMove;
		} else {
			setNextMove();
		}
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

	public void decide() {
		int x = 0, y = 0;

		// Check wall colision
		if (nextMove.getX() + currentPosition.getX()  < 0) {
			mustApplyDeterminedMove = true;
			x = 1;
		} else if (nextMove.getX() + currentPosition.getX()  > environment.getWidth() - 1) {
			mustApplyDeterminedMove = true;
			x = -1;
		}

		if (nextMove.getY() + currentPosition.getY()  < 0) {
			mustApplyDeterminedMove = true;
			y = 1;
		} else if (nextMove.getY() + currentPosition.getY()  > environment.getHeight() - 1) {
			mustApplyDeterminedMove = true;
			y = -1;
		}

		// Check agent collisions
		for(Agent agent : environment.getAgents()) {
			if(!agent.equals(this)) {
				if(getNextPosition().equals(agent.getNextPosition())) {
					Position agenNextMove = agent.getNextMove();
					agent.setNextMove(getNextMove());
					setNextMove(agenNextMove);
				}
			}
		}

		nextDeterminedMove = new Position(x, y);
	}

	private void setNextMove() {
		switch (rand.nextInt(8)) {
		case 0:
			nextMove.setX(1);
			nextMove.setY(0);
			break;
		case 1:
			nextMove.setX(1);
			nextMove.setY(1);
			break;
		case 2:
			nextMove.setX(0);
			nextMove.setY(1);
			break;
		case 3:
			nextMove.setX(-1);
			nextMove.setY(1);
			break;
		case 4:
			nextMove.setX(-1);
			nextMove.setY(0);
			break;
		case 5:
			nextMove.setX(-1);
			nextMove.setY(-1);
			break;
		case 6:
			nextMove.setX(0);
			nextMove.setY(-1);
			break;
		case 7:
			nextMove.setX(1);
			nextMove.setY(-1);
			break;
		}
	}
}
