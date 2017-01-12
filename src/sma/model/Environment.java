package sma.model;

import java.util.List;

import core.Agent;
import sma.parameter.Parameter;

public class Environment {
	
	private int width;
	private int height;
	private SMA sma;
	private List<Agent> agentlist;
	public Agent[][] agentsPosition;
	private Parameter parameters;
	
	public Environment(SMA sma, Parameter parameters){
		this.parameters = parameters;
		this.width = parameters.getGridSizeX()*parameters.getBoxSize();
		this.height = parameters.getGridSizeY()*parameters.getBoxSize();
		this.sma = sma;
		
		agentsPosition = new Agent[parameters.getGridSizeX()][parameters.getGridSizeY()];
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
		
		for(Agent agent : agentlist){
			agentsPosition[agent.getCurrentPosition().getX()/parameters.getBoxSize()][agent.getCurrentPosition().getY()/parameters.getBoxSize()] = agent;
		}
	}
	
	public void addAgent(Agent agent){
		agentsPosition[agent.getCurrentPosition().getX() / parameters.getBoxSize()]
						[agent.getCurrentPosition().getY() / parameters.getBoxSize()] = agent;
	
		sma.addAgent(agent);
	}
	
	public void removeAgent(Agent agent){
		agentsPosition[agent.getCurrentPosition().getX() / parameters.getBoxSize()]
						[agent.getCurrentPosition().getY() / parameters.getBoxSize()] = null;
	
		sma.removeAgent(agent);
	
	}
}
