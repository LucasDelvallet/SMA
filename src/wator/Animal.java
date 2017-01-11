package wator;

import core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public abstract class Animal extends Agent {
	protected int breedTime;
	private int age;
	
	public Animal(Environment environment, Parameter parameters, Position xy) {
		super(environment, parameters, xy);
	}
	
	protected void updateAge() {
		age++;
		if(isMature()){
			setMature();
		}
	}
	
	protected boolean isMature(){
		return age >= breedTime;
	}
	
	
	protected abstract void setMature();
}
