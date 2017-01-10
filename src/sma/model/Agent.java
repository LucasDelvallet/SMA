package sma.model;

import java.awt.Color;
import java.util.Random;

public class Agent {

	private static Random rand = new Random(Parameters.seed);
	private Color color;
	private Position currentPosition, nextMove;
	private boolean haveDecided, needToFreeze;
	protected Environment environment;

	public Agent(Environment environment, Position xy) {
		this.environment = environment;

		int r = rand.nextInt(200);
		int g = rand.nextInt(200);
		int b = rand.nextInt(200);
		this.color = new Color(r, g, b);

		currentPosition = xy;
		nextMove = new Position();
		haveDecided = false;
		needToFreeze = false;

		setDirection();
	}

	public void decide() {
		if (!haveDecided) {
			if (!checkWallCollision()) {
				checkAgentCollision();
			}
			haveDecided = true;
		}
	}

	public void update() {
		haveDecided = false;

		if (!needToFreeze) {
			environment.agentsPosition[currentPosition.getX()/Parameters.boxSize][currentPosition.getY()/Parameters.boxSize] = null;
			currentPosition.setX(currentPosition.getX() + nextMove.getX());
			currentPosition.setY(currentPosition.getY() + nextMove.getY());
			environment.agentsPosition[currentPosition.getX()/Parameters.boxSize][currentPosition.getY()/Parameters.boxSize] = this;
		} else {
			needToFreeze = false;
		}

		
	}

	public Position getNextMove() {
		return nextMove;
	}

	public void setNextMove(Position nextMove) {
		needToFreeze = true;
		haveDecided = true;
		this.nextMove = nextMove;
	}

	public Position getNextPosition() {
		return new Position(currentPosition.getX() + nextMove.getX(), currentPosition.getY() + nextMove.getY());
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public Color getColor() {
		return color;
	}

	/**
	 * @return true if there is a collision
	 */
	private boolean checkWallCollision() {
		// Check wall colision

		int border_x = environment.getWidth() - Parameters.boxSize;
		int border_y = environment.getHeight() - Parameters.boxSize;
		Position nextPosition = getNextPosition();
		boolean res = false;

		if (nextPosition.getX() < 0) {
			nextMove.setX(-nextMove.getX());
			res = true;
		} else if (nextPosition.getX() > border_x) {
			nextMove.setX(-nextMove.getX());
			res = true;
		}

		if (nextPosition.getY() < 0) {
			nextMove.setY(-nextMove.getY());
			res = true;
		} else if (nextPosition.getY() > border_y) {
			nextMove.setY(-nextMove.getY());
			res = true;
		}
		needToFreeze = res;
		nextPosition = getNextPosition();

		return res;
	}

	private void checkAgentCollision() {
		if (!checkCrossAgentCollision()) {
			checkOverlapAgentCollision();
		}
	}
	
	/**
	 * @return true if there is a collision
	 */
	private boolean checkCrossAgentCollision() {
		Position next = getNextPosition();
		Agent agent = null;
		
		if((agent = environment.agentsPosition[next.getX()/Parameters.boxSize][next.getY()/Parameters.boxSize]) != null) {
			if (!agent.equals(this) && agent.getCurrentPosition().equals(next)) {
				Position tmp = agent.getNextMove();
				agent.setNextMove(getNextMove());
				setNextMove(tmp);
				return true;
			}
		}
		
		return false;
	}

	/**
	 * @return true if there is a collision
	 */
	private boolean checkOverlapAgentCollision() {
		Position nextPlannedPosition = getNextPosition();

		for (Agent agent : environment.getAgents()) {
			// Si ce n'est pas lui même
			if (!agent.equals(this) && !agent.haveDecided && agent.getNextPosition().equals(nextPlannedPosition)) {
				Position tmp = agent.getNextMove();
				agent.setNextMove(getNextMove());
				setNextMove(tmp);
				return true;
			}
		}

		return false;
	}
	
	public boolean needToFreeze() {
		return needToFreeze;
	}
	
	public boolean haveDecided() {
		return haveDecided;
	}

	private void setDirection() {
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
