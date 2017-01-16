package particules;

import java.awt.Color;

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
//		if (parameters.isToric()) {
//			processAgentCollision();
//		} else {
//			if (!checkWallCollision()) {
//				processAgentCollision();
//			}
//		}
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

		if (parameters.needTrace() && this.needToFreeze) {
			System.out.println(trace());
		}
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
	
	@Override
	public String trace(){
		return "Particle;" + color + ";x=" + nextMove.getX() / parameters.getBoxSize() + ";y="
				+ nextMove.getY() / parameters.getBoxSize();
	}

}
