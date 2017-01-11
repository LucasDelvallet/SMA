package wator;

import java.awt.Color;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public class Shark extends Animal {
	private int sharkStarveTime;
	
	public Shark(Environment environment, Parameter parameters, Position xy) {
		super(environment, parameters, xy);
		color = Color.PINK;
	}

	@Override
	public void decide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setMature() {
		color = Color.RED;
	}

	@Override
	public void agentCollisionReaction(Agent collited) {
		// TODO Auto-generated method stub
	}
}
