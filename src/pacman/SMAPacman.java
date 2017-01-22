package pacman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import core.Agent;
import sma.model.Position;
import sma.model.SMA;
import sma.parameter.Parameter;

public class SMAPacman extends SMA {

	private Dijkstra dijkstra;
	
	public SMAPacman(Parameter parameters, JPanel panel) {
		super(parameters);
		bindAvatar(panel);
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

	private void bindAvatar(JPanel panel) {
		for (Agent a : agentlist) {
			if (a instanceof Avatar) {
				panel.addKeyListener((Avatar) a);
			}
		}
	}
}
