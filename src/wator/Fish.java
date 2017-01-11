package wator;

import java.awt.Color;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public class Fish extends Animal {
	public Fish(Environment environment, Parameter parameters, Position xy) {
		super(environment, parameters, xy);
		color = Color.GREEN;
	}

	public void checkCollisions() {

	}

	@Override
	public void decide() {
		setRandomDirection();

		if (parameters.isToric()) {
			processAgentCollision();
		} else {
//			if (!checkWallCollision()) {
//				checkAgentCollision();
//			}
		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setMature() {
		color = Color.BLUE;
	}

	@Override
	public void agentCollisionReaction(Agent collited) {
		// TODO Auto-generated method stub
	}

}
