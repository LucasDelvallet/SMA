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
		breedTime = parameters.getFishBreedTime();
	}

	public void checkCollisions() {

	}

	@Override
	public void decide() {
		setRandomDirection();
		processAgentCollision();
	}

	@Override
	public void update() {
		if (!needToFreeze) {
			
			environment.agentsPosition[currentPosition.getX() / parameters.getBoxSize()][currentPosition.getY()
			                                                         					/ parameters.getBoxSize()] = null;
			
			if(isMature() && canReproduce()){
				Fish child = new Fish(environment, parameters, currentPosition);
				child.needToFreeze = true;
				environment.addAgent(child);
			}
			

			currentPosition = this.getNextPosition();
			environment.agentsPosition[currentPosition.getX() / parameters.getBoxSize()][currentPosition.getY()
					/ parameters.getBoxSize()] = this;
		} else {
			needToFreeze = false;
		}
		updateAge();
	}

	@Override
	protected void setMature() {
		color = Color.BLUE;
	}

	@Override
	public void agentCollisionReaction(Agent collided) {
		this.needToFreeze = true;
	}
	
	@Override
	public String trace(){
		return "Fish;";// TODO
	}

}
