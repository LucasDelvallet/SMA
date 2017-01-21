package pacman;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public class Hunter extends Agent {

	public Hunter(Environment environment, Parameter parameters, Position xy, Dijkstra dij) {
		super(environment, parameters, xy);
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
		return null;
	}

}
