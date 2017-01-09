package sma.model;

import java.util.List;

public class Environment {
	
	private int width;
	private int height;
	private SMA sma;
	private List<Agent> agentlist;
	//public Agent[][] agentsPosition;
	
	public Environment(SMA sma){
		this.width = Parameters.gridSizeX*Parameters.boxSize;
		this.height = Parameters.gridSizeY*Parameters.boxSize;
		this.sma = sma;
		
		//agentsPosition = new Agent[Parameters.gridSizeX][Parameters.gridSizeY];
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
		
//		for(Agent agent : agentlist){
//			agentsPosition[agent.getCurrentPosition().getX()/Parameters.boxSize][agent.getCurrentPosition().getY()/Parameters.boxSize] = agent;
//		}
	}
}
