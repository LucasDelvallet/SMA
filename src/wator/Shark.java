package wator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public class Shark extends Animal {
	private int sharkStarveTime;
	private boolean eatedFish;
	private static List<Position> surroundings ;
	
	public Shark(Environment environment, Parameter parameters, Position xy) {
		super(environment, parameters, xy);
		color = Color.PINK;
		sharkStarveTime = parameters.getSharkStarveTime();
		breedTime = parameters.getSharkBreedTime();
		eatedFish = false;
		
		if(surroundings == null){
			surroundings = new ArrayList<Position>();
			surroundings.add(new Position(parameters.getBoxSize(), 0));
			surroundings.add(new Position(parameters.getBoxSize(), parameters.getBoxSize()));
			surroundings.add(new Position(0,parameters.getBoxSize()));
			surroundings.add(new Position(-parameters.getBoxSize(),parameters.getBoxSize()));
			surroundings.add(new Position(-parameters.getBoxSize(),0));
			surroundings.add(new Position(-parameters.getBoxSize(),-parameters.getBoxSize()));
			surroundings.add(new Position(0,-parameters.getBoxSize()));
			surroundings.add(new Position(parameters.getBoxSize(),-parameters.getBoxSize()));
		}
	}

	@Override
	public void decide() {
		if(!watchSurroundingForFish()){
			setRandomDirection();
		}

		if (parameters.isToric()) {
			processAgentCollision();
		} else {
			//if (!checkWallCollision()) {
			//	processAgentCollision();
			//}
		}
	}
	
	private boolean watchSurroundingForFish(){
		Collections.shuffle(surroundings, rand);
		
		for(Position positionToWatch : surroundings){
			nextMove = positionToWatch;
			processAgentCollision();
			if(eatedFish){
				sharkStarveTime = parameters.getSharkStarveTime();
				eatedFish = false;
				return true;
			}
		}
		
		
		return false;
	}

	@Override
	public void update() {
		sharkStarveTime--;
		if (!needToFreeze) {
			
			environment.agentsPosition[currentPosition.getX() / parameters.getBoxSize()][currentPosition.getY()
			                                                         					/ parameters.getBoxSize()] = null;
			
			if(isMature()){
				Shark child = new Shark(environment, parameters, currentPosition);
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
		if(sharkStarveTime == 0){
			environment.removeAgent(this);
		}
	}

	@Override
	protected void setMature() {
		color = Color.RED;
	}

	@Override
	public void agentCollisionReaction(Agent collided) {
		if(collided.getClass().getSimpleName().equals("Fish")){
			environment.removeAgent(collided);
			eatedFish = true;
		}else{
			this.needToFreeze = true;
		}
	}
}
