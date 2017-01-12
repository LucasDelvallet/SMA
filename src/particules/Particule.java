package particules;

import java.awt.Color;
import java.util.Random;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public class Particule extends Agent {

	public Particule(Environment environment, Parameter parameters, Position xy) {
		super(environment, parameters, xy);

		int r = rand.nextInt(200);
		int g = rand.nextInt(200);
		int b = rand.nextInt(200);
		color = new Color(r, g, b);

		setRandomDirection();
	}

	@Override
	public void decide() {
		if (parameters.isToric()) {
			processAgentCollision();
		} else {
			if (!checkWallCollision()) {
				processAgentCollision();
			}
		}
	}

	@Override
	public void update() {

		if (!needToFreeze) {
			environment.agentsPosition[currentPosition.getX() / parameters.getBoxSize()][currentPosition.getY()
					/ parameters.getBoxSize()] = null;
			currentPosition = this.getNextPosition();
			environment.agentsPosition[currentPosition.getX() / parameters.getBoxSize()][currentPosition.getY()
					/ parameters.getBoxSize()] = this;
		} else {
			needToFreeze = false;
		}

		if (parameters.needTrace() && this.needToFreeze) {
			System.out.println("Particles " + color + "  direction x=" + nextMove.getX() / parameters.getBoxSize() + "  y="
					+ nextMove.getY() / parameters.getBoxSize());
		}

	}

	/**
	 * @return true if there is a collision
	 */
	private boolean checkWallCollision() {
		// Check wall colision

		int border_x = environment.getWidth() - parameters.getBoxSize();
		int border_y = environment.getHeight() - parameters.getBoxSize();
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

	public boolean needToFreeze() {
		return needToFreeze;
	}

	@Override
	public void agentCollisionReaction(Agent collided) {
		Position tmp = collided.getNextMove();
		collided.setNextMove(getNextMove());
		setNextMove(tmp);
	}
	
}
