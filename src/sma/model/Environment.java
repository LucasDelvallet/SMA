package sma.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Agent;
import pacman.Winner;
import sma.parameter.Parameter;

public class Environment {
	
	private int width;
	private int height;
	private SMA sma;
	private List<Agent> agentlist;
	public Agent[][] agentsPosition;
	private Parameter parameters;
	private boolean haveWinner;
	
	public Environment(SMA sma, Parameter parameters){
		this.parameters = parameters;
		this.width = parameters.getGridSizeX()*parameters.getBoxSize();
		this.height = parameters.getGridSizeY()*parameters.getBoxSize();
		this.sma = sma;
		this.haveWinner = false;
		
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
	
	public void addWinner() {
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
		addAgent(new Winner(this, parameters, possiblePositions.get(index)));
		haveWinner = true;
	}
	
	public boolean haveWinner() {
		return haveWinner;
	}
}
