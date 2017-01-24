package pacman;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.model.SMA;
import sma.parameter.Parameter;

/**
 * https://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html
 */
public class Avatar extends Agent implements KeyListener {

	private Dijkstra dijkstra;
	private int invicibility;
	private int defendersEated;

	public Avatar(Environment environment, Parameter parameters, Position xy, Dijkstra dijkstra) {
		super(environment, parameters, xy);
		setDefaultColor();
		this.dijkstra = dijkstra;
		invicibility = 0;
		defendersEated = 0;
	}

	@Override
	public void decide() {
		if (SMA.tick % parameters.getSpeedAvatar() == 0) {
			processAgentCollision();
		} else {
			this.needToFreeze = true;
		}
	}

	@Override
	public void update() {
		if (!needToFreeze) {
			environment.agentsPosition[currentPosition.getX() / parameters.getBoxSize()][currentPosition.getY()
					/ parameters.getBoxSize()] = null;
			currentPosition = this.getNextPosition();
			environment.agentsPosition[currentPosition.getX() / parameters.getBoxSize()][currentPosition.getY()
					/ parameters.getBoxSize()] = this;

			dijkstra.compute(this.getCurrentIndex(), invicibility > 0);

			if (defendersEated >= 4 && !environment.haveWinner()) {
				environment.addWinner();
			}
		} else {
			needToFreeze = false;
		}
		if (invicibility <= 0) {
			setDefaultColor();
		}
		if (SMA.tick % parameters.getDefenderLife() == 0) {
			environment.addDefender();
		}
		invicibility--;
	}

	@Override
	protected boolean processWallCollision() {
		int border_x = environment.getWidth() - parameters.getBoxSize();
		int border_y = environment.getHeight() - parameters.getBoxSize();
		Position nextPosition = getNextPosition();
		boolean res = false;

		if (nextPosition.getX() < 0) {
			res = true;
		} else if (nextPosition.getX() > border_x) {
			res = true;
		}

		if (nextPosition.getY() < 0) {
			res = true;
		} else if (nextPosition.getY() > border_y) {
			res = true;
		}

		needToFreeze = res;

		return res;
	}

	@Override
	public void agentCollisionReaction(Agent collided) {
		if (collided.getClass().getSimpleName().equals("Defender")) {
			environment.removeAgent(collided);
			invicibility = parameters.getDefenderLife();
			defendersEated++;
			setInvicibleColor();
		} else if (collided.getClass().getSimpleName().equals("Hunter")) {
			if (invicibility > 0) {
				environment.removeAgent(collided);
			}
		} else if(collided.getClass().getSimpleName().equals("Winner")) {
			parameters.setEndOfGame();
		} else {
			this.needToFreeze = true;
		}
	}

	@Override
	public String trace() {
		return ""; // TODO
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_NUMPAD8:
			nextMove = new Position(0, -parameters.getBoxSize());
			break;
		case KeyEvent.VK_NUMPAD9:
			nextMove = new Position(parameters.getBoxSize(), -parameters.getBoxSize());
			break;
		case KeyEvent.VK_NUMPAD6:
			nextMove = new Position(parameters.getBoxSize(), 0);
			break;
		case KeyEvent.VK_NUMPAD3:
			nextMove = new Position(parameters.getBoxSize(), parameters.getBoxSize());
			break;
		case KeyEvent.VK_NUMPAD2:
			nextMove = new Position(0, parameters.getBoxSize());
			break;
		case KeyEvent.VK_NUMPAD1:
			nextMove = new Position(-parameters.getBoxSize(), parameters.getBoxSize());
			break;
		case KeyEvent.VK_NUMPAD4:
			nextMove = new Position(-parameters.getBoxSize(), 0);
			break;
		case KeyEvent.VK_NUMPAD7:
			nextMove = new Position(-parameters.getBoxSize(), -parameters.getBoxSize());
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Nothing
	}

	private void setDefaultColor() {
		color = Color.GREEN;
	}

	private void setInvicibleColor() {
		color = Color.CYAN;
	}
}
