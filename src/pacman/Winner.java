package pacman;

import java.awt.Color;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public class Winner extends Agent {

	public Winner(Environment environment, Parameter parameters, Position xy) {
		super(environment, parameters, xy);
		color = Color.MAGENTA;
	}

	@Override
	public void decide() {
	}

	@Override
	public void update() {
	}

	@Override
	public void agentCollisionReaction(Agent collided) {
	}

	@Override
	public String trace() {
		return "";
	}

}
