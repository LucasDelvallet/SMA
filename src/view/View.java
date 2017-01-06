package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import sma.model.Agent;
import sma.model.Parameters;
import sma.model.SMA;import sun.management.resources.agent;

public class View extends JPanel implements Observer{
	
	private SMA sma;
	
	public void paint(Graphics g) {
		super.paint(g);
		
		List<Agent> agentlist = sma.getAgentlist();
		for(int i = 0; i < agentlist.size(); i++){
			Agent agent = agentlist.get(i);
			
			Color c = g.getColor();
			g.setColor(agent.getColor());	
			g.fillOval(agent.getCurrentPosition().getX(), agent.getCurrentPosition().getY(), Parameters.boxSize+1, Parameters.boxSize+1);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		sma = (SMA)o;
		this.invalidate();
		this.repaint();
	}
	
}
