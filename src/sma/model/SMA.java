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

		Random r = new Random(Parameters.seed);
		for(int i = 0; i < Parameters.nbParticles; i++){
			int index = r.nextInt(possiblePositions.size());
			agentlist.add(new Agent(environment, possiblePositions.get(index)));
			possiblePositions.remove(index);
		}
	}
	
	public void run(){
		int tick = 0;
		while(tick < Parameters.nbTicks){
			long startTime = System.currentTimeMillis();
			Collections.shuffle(agentlist); //Voir si on laisse on pas, vu que c'est pas affecté par le seed

			for(int i = 0; i < agentlist.size(); i++){
				agentlist.get(i).decide();
				//agentlist.get(i).update();
				//System.out.println("Agent " + i + "  position x=" + agentlist.get(i).getCurrentPosition().getX() + "  y=" + agentlist.get(i).getCurrentPosition().getY());
			}
			
			for(int i = 0; i < agentlist.size(); i++){
				//agentlist.get(i).decide();
				agentlist.get(i).update();
				//System.out.println("Agent " + i + "  position x=" + agentlist.get(i).getCurrentPosition().getX() + "  y=" + agentlist.get(i).getCurrentPosition().getY());
			}
			
			tick++;
			
			setChanged();
            notifyObservers();
            

			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);
			System.out.println("Tick time : " + duration);
            
            try {
				Thread.sleep(Parameters.delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
