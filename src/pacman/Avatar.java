package pacman;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

/**
 * https://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html
 */
public class Avatar extends Agent implements KeyListener {

	public Avatar(Environment environment, Parameter parameters, Position xy) {
		super(environment, parameters, xy);
		color = Color.GREEN;
	}
	
	@Override
	public void decide() {
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
			// TODO Remplir Didi
		} else {
			needToFreeze = false;
		}
		
	}

	@Override
	public void agentCollisionReaction(Agent collided) {
		this.needToFreeze = true;
	}

	@Override
	public String trace() {
		return ""; // TODO
	}

	class AvatarKeyboardMove extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Position direction;
		
		public AvatarKeyboardMove(Position direction) {
			this.direction = direction;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			nextMove = direction;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
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
}
