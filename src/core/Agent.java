package core;

import java.awt.Color;
import java.util.Random;

import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public abstract class Agent {
	protected static Random rand;
	protected Position currentPosition, nextMove;
	protected boolean needToFreeze;
	protected Environment environment;
	protected Parameter parameters;
	protected Color color;
	
	public Agent(Environment environment, Parameter parameters, Position xy) {
		this.environment = environment;
		this.parameters = parameters;
		
		if (rand == null) {
			if (parameters.getSeed() == 0) {
				rand = new Random();
			} else {
				rand = new Random(parameters.getSeed());
			}
		}
		
		currentPosition = xy;
		nextMove = new Position();
		needToFreeze = false;
	}
	
	public Position getCurrentPosition() {
		return currentPosition;
	}
	
	public Position getNextMove() {
		return nextMove;
	}

	public void setNextMove(Position nextMove) {
		needToFreeze = true;
		this.nextMove = nextMove;
	}
	
	public Color getColor() {
		return color;
	}
	
	protected void setRandomDirection() {
		switch (rand.nextInt(8)) {
		case 0:
			nextMove.setX(parameters.getBoxSize());
			nextMove.setY(0);
			break;
		case 1:
			nextMove.setX(parameters.getBoxSize());
			nextMove.setY(parameters.getBoxSize());
			break;
		case 2:
			nextMove.setX(0);
			nextMove.setY(parameters.getBoxSize());
			break;
		case 3:
			nextMove.setX(-parameters.getBoxSize());
			nextMove.setY(parameters.getBoxSize());
			break;
		case 4:
			nextMove.setX(-parameters.getBoxSize());
			nextMove.setY(0);
			break;
		case 5:
			nextMove.setX(-parameters.getBoxSize());
			nextMove.setY(-parameters.getBoxSize());
			break;
		case 6:
			nextMove.setX(0);
			nextMove.setY(-parameters.getBoxSize());
			break;
		case 7:
			nextMove.setX(parameters.getBoxSize());
			nextMove.setY(-parameters.getBoxSize());
			break;
		}
	}
	
	public Position getNextPosition() {
		Position nextPosition = new Position((currentPosition.getX() + nextMove.getX()),
				currentPosition.getY() + nextMove.getY());
		if (parameters.isToric()) {
			if (nextPosition.getX() < 0) {
				nextPosition.setX((parameters.getGridSizeX() - 1) * parameters.getBoxSize());
			}
			if (nextPosition.getX() >= parameters.getGridSizeX() * parameters.getBoxSize()) {
				nextPosition.setX(0);
			}

			if (nextPosition.getY() < 0) {
				nextPosition.setY((parameters.getGridSizeY() - 1) * parameters.getBoxSize());
			}
			if (nextPosition.getY() >= parameters.getGridSizeY() * parameters.getBoxSize()) {
				nextPosition.setY(0);
			}
		}

		return nextPosition;
	}
	
	protected boolean processAgentCollision() {
		Position next = getNextPosition();
		Agent agent = null;

		if ((agent = environment.agentsPosition[next.getX() / parameters.getBoxSize()][next.getY()
				/ parameters.getBoxSize()]) != null) {
			if (!agent.equals(this) && agent.getCurrentPosition().equals(next)) {
				agentCollisionReaction(agent);
				return true;
			}
		}
		
		return false;
	}
	
	public abstract void decide();
	public abstract void update();
	public abstract void agentCollisionReaction(Agent collided);
}