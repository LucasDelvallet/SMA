package sma;

import java.awt.Color;
import java.util.Random;

public class Agent {

	private Color color;
	private int x;
	private int y;
	protected Environment environment;
	
	
	public Agent(Environment environment, int x, int y){
		this.environment = environment;
		Random rand = new Random();
		this.x = x;
		this.y = y;
	}
	
	public void update(){
		
	}
	
	public void decide(){
		
	}
	
	
	
	
}
