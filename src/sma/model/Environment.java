package sma.model;

import java.util.List;

public class Environment {
	
	private int width;
	private int height;
	private SMA sma;
	public List<Agent> agentlist;
	
	
	public Environment(int width, int height){
		this.width = width;
		this.height = height;
		
		//Creer les agents.
		
		//this.sma = new SMA(this, agentlist);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public List<Agent> getAgents() {
		return agentlist;
	}
	
}
