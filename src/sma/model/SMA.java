package sma.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class SMA extends Observable {

	private List<Agent> agentlist;
	private Environment environment;
	
	public SMA(){
		this.environment = new Environment(this);
		
		initAgent();
		environment.setAgentlist(agentlist);
	}
	
	public List<Agent> getAgentlist(){
		return agentlist;
	}
	
	public Environment getEnvironment(){
		return environment;
	}
	
	private void initAgent(){
		agentlist = new ArrayList<Agent>();
		
		List<Position> possiblePositions = new ArrayList<Position>();
		for(int i = 0; i < environment.getWidth(); i+=Parameters.boxSize){
			for(int j = 0; j < environment.getHeight(); j+=Parameters.boxSize){
				possiblePositions.add(new Position(i,j));
			}
		}

		Random rand = new Random(Parameters.seed);
		if(Parameters.seed == 0){
			rand = new Random();
		}
		for(int i = 0; i < Parameters.nbParticles; i++){
			int index = rand.nextInt(possiblePositions.size());
			agentlist.add(new Agent(environment, possiblePositions.get(index)));
			possiblePositions.remove(index);
		}
	}
	
	public void run(){
		Random rand = new Random(Parameters.seed);
		int tick = 0;
		long startTimeTotal = System.currentTimeMillis();
		while(tick < Parameters.nbTicks){
			long startTime = System.currentTimeMillis();
			switch(Parameters.scheduling){
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
			
			if(Parameters.refresh != 0 && (tick == 0 || tick % Parameters.refresh == 0)){
				setChanged();
	            notifyObservers();
			}
			
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);
			if(Parameters.trace){
				System.out.println("End of tick "+ tick+"  time : " + duration+"ms");
			}
			
            try {
				Thread.sleep(Parameters.delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long endTimeTotal = System.currentTimeMillis();
		long durationTotal = (endTimeTotal - startTimeTotal);
		System.out.println("Total time : " + durationTotal +" ms");
	}
}
