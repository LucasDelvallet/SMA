package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import sma.model.Agent;
import sma.model.Parameters;
import sma.model.SMA;import sun.management.resources.agent;

public class View extends JPanel implements Observer{
	
	private SMA sma;
	
	public View(){
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(Parameters.gridSizeX*Parameters.boxSize,Parameters.gridSizeY*Parameters.boxSize));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.GRAY);
		
		int grid_division_x = Parameters.gridSizeX;
		int grid_division_y = Parameters.gridSizeY;
		
        for (int i = 1; i < grid_division_x; i++) {
           int x = i * (sma.getEnvironment().getWidth() / grid_division_x);
           g2.drawLine(x, 0, x, sma.getEnvironment().getHeight());
        }
        for (int i = 1; i < grid_division_y; i++) {
           int y = i * (sma.getEnvironment().getHeight() / grid_division_y);
           g2.drawLine(0, y, sma.getEnvironment().getWidth(), y);
        }
		
		List<Agent> agentlist = sma.getAgentlist();
		for(int i = 0; i < agentlist.size(); i++){
			Agent agent = agentlist.get(i);
			
			Color c = g2.getColor();
			g2.setColor(agent.getColor());	
			g2.fillOval(agent.getCurrentPosition().getX(), agent.getCurrentPosition().getY(), Parameters.boxSize, Parameters.boxSize);
		}
		
		//g2.scale(Parameters.boxSize, Parameters.boxSize);
	}

	@Override
	public void update(Observable o, Object arg) {
		sma = (SMA)o;
		this.invalidate();
		this.repaint();
	}
	
}
