package sma.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import sma.parameter.Parameter;

public class SMA extends Observable {

	private List<Agent> agentlist;
	private Environment environment;
	private Parameter parameters;
	
	public SMA(Parameter parameters){
		this.parameters = parameters;
		this.environment = new Environment(this, parameters);
		
		initAgent(parameters);
		environment.setAgentlist(agentlist);
	}
	
	public List<Agent> getAgentlist(){
		return agentlist;
	}
	
	public Environment getEnvironment(){
		return environment;
	}
	
	private void initAgent(Parameter parameters){
		agentlist = new ArrayList<Agent>();
		
		List<Position> possiblePositions = new ArrayList<Position>();
		for(int i = 0; i < environment.getWidth(); i+=parameters.getBoxSize()){
			for(int j = 0; j < environment.getHeight(); j+=parameters.getBoxSize()){
				possiblePositions.add(new Position(i,j));
			}
		}

		Random rand = new Random(parameters.getSeed());
		if(parameters.getSeed() == 0){
			rand = new Random();
		}
		for(int i = 0; i < parameters.getNbParticles(); i++){
			int index = rand.nextInt(possiblePositions.size());
			agentlist.add(new Agent(environment, parameters, possiblePositions.get(index)));
			possiblePositions.remove(index);
		}
	}
	
	public void run(){
		Random rand = new Random(parameters.getSeed());
		int tick = 0;
		long startTimeTotal = System.currentTimeMillis();
		while(tick < parameters.getNbTicks()){
			long startTime = System.currentTimeMillis();
			switch(parameters.getScheduling()){
			case 0:
				Collections.shuffle(agentlist,rand);
			case 1:
				for(int i = 0; i < agentlist.size(); i++){
					agentlist.get(i).decide();
					agentlist.get(i).update();
				}
				break;
			case 2:
				int indexRand = rand.nextInt(agentlist.size());
				agentlist.get(indexRand).decide();
				agentlist.get(indexRand).update();
				break;
			}

			
			
			tick++;
			
			if(parameters.getRefresh()!= 0 && (tick == 0 || tick % parameters.getRefresh() == 0)){
				setChanged();
	            notifyObservers();
			}
			
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);
			if(parameters.needTrace()){
				System.out.println("End of tick "+ tick+"  time : " + duration+"ms");
			}
			
            try {
				Thread.sleep(parameters.getDelay());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long endTimeTotal = System.currentTimeMillis();
		long durationTotal = (endTimeTotal - startTimeTotal);
		System.out.println("Total time : " + durationTotal +" ms");
	}
}
