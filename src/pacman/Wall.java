package pacman;

import java.awt.Color;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public class Wall extends Agent {

	public Wall(Environment environment, Parameter parameters, Position xy) {
		super(environment, parameters, xy);
		color = Color.DARK_GRAY;
	}

	@Override
	public void decide() {
		// Nothing
	}

	@Override
	public void update() {
		// Nothing
	}

	@Override
	public void agentCollisionReaction(Agent collided) {
		// Nothing
	}

	@Override
	public String trace() {
		// Nothing
		return "";
	}

}
