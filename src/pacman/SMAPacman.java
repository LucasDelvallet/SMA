package pacman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import core.Agent;
import sma.model.Position;
import sma.model.SMA;
import sma.parameter.Parameter;

public class SMAPacman extends SMA implements KeyListener {

	private Dijkstra dijkstra;
	private boolean paused;
	
	public SMAPacman(Parameter parameters, JPanel panel) {
		super(parameters);
		bindAvatar(panel);
		panel.addKeyListener(this);
		paused = false;
	}

	@Override
	protected void initAgent(Parameter parameters) {
		dijkstra = new Dijkstra(parameters.getGridSizeX(), parameters.getGridSizeY(), this);
		
		agentlist = new ArrayList<Agent>();

		List<Position> possiblePositions = new ArrayList<Position>();
		for (int i = 0; i < environment.getWidth(); i += parameters.getBoxSize()) {
			for (int j = 0; j < environment.getHeight(); j += parameters.getBoxSize()) {
				possiblePositions.add(new Position(i, j));
			}
		}

		Random rand = new Random(parameters.getSeed());
		if (parameters.getSeed() == 0) {
			rand = new Random();
		}

		setAvatar(rand, possiblePositions);
		setWalls(parameters, rand, possiblePositions);
		setHunters(parameters, rand, possiblePositions);
		setDefender(rand, possiblePositions);
	}

	private void setAvatar(Random rand, List<Position> possiblePositions) {
		int index = rand.nextInt(possiblePositions.size());
		environment.addAgent(new Avatar(environment, parameters, possiblePositions.get(index), dijkstra));
		possiblePositions.remove(index);
	}

	private void setWalls(Parameter parameters, Random rand, List<Position> possiblePositions) {
		int index, nbWall;
		nbWall = ((parameters.getGridSizeX() * parameters.getGridSizeY()) * parameters.getWallsPercent()) / 100;

		for (int i = 0; i < nbWall; i++) {
			index = rand.nextInt(possiblePositions.size());
			environment.addAgent(new Wall(environment, parameters, possiblePositions.get(index)));
			possiblePositions.remove(index);
		}
	}
	
	private void setHunters(Parameter parameters, Random rand, List<Position> possiblePositions) {
		int index, nbHunter;
		nbHunter = parameters.getNbHunters();

		for (int i = 0; i < nbHunter; i++) {
			index = rand.nextInt(possiblePositions.size());
			environment.addAgent(new Hunter(environment, parameters, possiblePositions.get(index), dijkstra));
			possiblePositions.remove(index);
		}
	}
	
	private void setDefender(Random rand, List<Position> possiblePositions) {
		int index = rand.nextInt(possiblePositions.size());
		environment.addAgent(new Defender(environment, parameters, possiblePositions.get(index)));
		possiblePositions.remove(index);
	}

	private void bindAvatar(JPanel panel) {
		for (Agent a : agentlist) {
			if (a instanceof Avatar) {
				panel.addKeyListener((Avatar) a);
			}
		}
	}
	
	@Override
	protected boolean isPaused() {
		return paused;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int val = 0;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			val = parameters.getSpeedHunter() - 1;
			parameters.setSpeedHunter((val < 0)?0:val);
			break;
		case KeyEvent.VK_Z:
			parameters.setSpeedHunter(parameters.getSpeedHunter() + 1);
			break;
		case KeyEvent.VK_O:
			val = parameters.getSpeedAvatar() - 1;
			parameters.setSpeedAvatar((val < 0)?0:val);
			break;
		case KeyEvent.VK_P:
			parameters.setSpeedAvatar(parameters.getSpeedHunter() + 1);
			break;
		case KeyEvent.VK_W:
			val = parameters.getDelay() - 100;
			parameters.setDelay((val < 0)?0:val);
			break;
		case KeyEvent.VK_X:
			parameters.setDelay(parameters.getDelay() + 100);
			break;
		case KeyEvent.VK_SPACE:
			paused = !paused;
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
