package pacman;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public class Hunter extends Agent {

	private Dijkstra dijkstra;

	public Hunter(Environment environment, Parameter parameters, Position xy, Dijkstra dijkstra) {
		super(environment, parameters, xy);
		this.dijkstra = dijkstra;
		this.color = Color.RED;
		
	}

	@Override
	public void decide() {
		nextMove = dijkstra.getNextMove(this.getCurrentIndex());
		nextMove.setX(nextMove.getX()*parameters.getBoxSize());
		nextMove.setY(nextMove.getY()*parameters.getBoxSize());
		
		processAgentCollision();
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
	}

	@Override
	public void agentCollisionReaction(Agent collided) {
		if(collided.getClass().getSimpleName().equals("Avatar")){
			this.parameters.setEndOfGame();
		}else{
			this.needToFreeze = true;
		}
	}

	@Override
	public String trace() {
		return null;
	}

}
