package pacman;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public class Defender extends Agent{

	private int life;
	public Defender(Environment environment, Parameter parameters, Position xy) {
		super(environment, parameters, xy);
		this.life = parameters.getDefenderLife();
	}

	@Override
	public void decide() {
		if(life == 0){
			this.environment.removeAgent(this);
		}
	}

	@Override
	public void update() {
		life--;
	}

	@Override
	public void agentCollisionReaction(Agent collided) {
		//Well, nothing.
	}

	@Override
	public String trace() {
		return null;
	}

}
