package pacman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Agent;
import sma.model.Position;
import sma.model.SMA;
import sma.parameter.Parameter;

public class SMAPacman extends SMA {

	public SMAPacman(Parameter parameters) {
		super(parameters);
	}

	@Override
	protected void initAgent(Parameter parameters) {
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

		int index = rand.nextInt(possiblePositions.size());
//		agentlist.add(new Avatar(environment, parameters, possiblePositions.get(index)));
		possiblePositions.remove(index);
			
	}

}
