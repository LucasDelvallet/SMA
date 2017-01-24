package sma.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import core.Agent;
import pacman.Defender;
import pacman.Winner;
import particules.Particule;
import sma.parameter.Parameter;

public abstract class SMA extends Observable {

	public static int tick;
	protected List<Agent> agentlist;
	protected Environment environment;
	protected Parameter parameters;
	private Random rand;

	public SMA(Parameter parameters) {
		this.parameters = parameters;
		this.environment = new Environment(this, parameters);
		
		rand = new Random(parameters.getSeed());

		initAgent(parameters);
		environment.setAgentlist(agentlist);
	}

	public List<Agent> getAgentlist() {
		return agentlist;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void run() {
		tick = 1;
		long startTimeTotal = System.currentTimeMillis();
		while (tick < parameters.getNbTicks()) {
			if (!isPaused()) {
				long startTime = System.currentTimeMillis();
				switch (parameters.getScheduling()) {
				case 0:
					Collections.shuffle(agentlist, rand);
				case 1:
					for (int i = 0; i < agentlist.size(); i++) {
						Agent agent = agentlist.get(i);
						agent.decide();
						agent.update();
					}
					break;
				case 2:
					int indexRand = rand.nextInt(agentlist.size());
					agentlist.get(indexRand).decide();
					agentlist.get(indexRand).update();
					break;
				}
				
				tick++;

				if (parameters.getRefresh() != 0 && (tick == 0 || tick % parameters.getRefresh() == 0)) {
					setChanged();
					notifyObservers();
				}

				long endTime = System.currentTimeMillis();
				long duration = (endTime - startTime);
				if (parameters.needTrace()) {
					System.out.println("End of tick " + tick + "  time : " + duration + "ms");
				}

				try {
					Thread.sleep(parameters.getDelay());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		long endTimeTotal = System.currentTimeMillis();
		long durationTotal = (endTimeTotal - startTimeTotal);
		System.out.println("Total time : " + durationTotal + " ms");
	}

	public void addAgent(Agent agent) {
		agentlist.add(agent);
	}

	public void removeAgent(Agent agent) {
		agentlist.remove(agent);
	}

	public void addDefender(){
		List<Position> possiblePositions = new ArrayList<>();
		for(int x=0; x < parameters.getGridSizeX(); x++) {
			for(int y=0; y < parameters.getGridSizeY(); y++) {
				possiblePositions.add(new Position(x*parameters.getBoxSize(), y*parameters.getBoxSize()));
			}
		}
		
		for(Agent a : agentlist) {
			possiblePositions.remove(a.getCurrentPosition());
		}
		
		
		int index = rand.nextInt(possiblePositions.size());
		environment.addAgent(new Defender(environment, parameters, possiblePositions.get(index)));
	}
	
	public void addWinner(){
		List<Position> possiblePositions = new ArrayList<>();
		for(int x=0; x < parameters.getGridSizeX(); x++) {
			for(int y=0; y < parameters.getGridSizeY(); y++) {
				possiblePositions.add(new Position(x*parameters.getBoxSize(), y*parameters.getBoxSize()));
			}
		}
		
		for(Agent a : agentlist) {
			possiblePositions.remove(a.getCurrentPosition());
		}
		
		Random rand = new Random(parameters.getSeed());
		int index = rand.nextInt(possiblePositions.size());
		environment.addAgent(new Winner(environment, parameters, possiblePositions.get(index)));
		environment.setHaveWinner();
	}
	
	protected abstract void initAgent(Parameter parameters);

	protected abstract boolean isPaused();
}
