package sma.model;

import java.util.List;

public class Environment {
	
	private int width;
	private int height;
	private SMA sma;
	private List<Agent> agentlist;
	
	
	public Environment(SMA sma){
		this.width = Parameters.gridSizeX*Parameters.boxSize;
		this.height = Parameters.gridSizeY*Parameters.boxSize;
		this.sma = sma;
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
	
	public void setAgentlist(List<Agent> agentlist){
		this.agentlist = agentlist;
	}
}
