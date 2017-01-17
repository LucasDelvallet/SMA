package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import core.Agent;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import particules.Particule;
import sma.model.SMA;
import sma.parameter.Parameter;

public class View extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private SMA sma;
	private Parameter parameters;
	private WatorNumberGraph watorNumberGraph;
	private WatorRatioGraph watorRatioGraph;
	private boolean displayGraph;
	
	public View(Parameter parameters, boolean displayGraph){
		this.parameters = parameters;
		this.displayGraph = displayGraph;
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(parameters.getGridSizeX()*parameters.getBoxSize(),parameters.getGridSizeY()*parameters.getBoxSize()));
	
		if(displayGraph){
			
			watorNumberGraph = new WatorNumberGraph();
			watorNumberGraph.display();
			
			watorRatioGraph = new WatorRatioGraph();
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.GRAY);
		
		if(parameters.needGrid()){
			int grid_division_x = parameters.getGridSizeX();
			int grid_division_y = parameters.getGridSizeY();
	        for (int i = 1; i < grid_division_x; i++) {
	           int x = i * (sma.getEnvironment().getWidth() / grid_division_x);
	           g2.drawLine(x, 0, x, sma.getEnvironment().getHeight());
	        }
	        for (int i = 1; i < grid_division_y; i++) {
	           int y = i * (sma.getEnvironment().getHeight() / grid_division_y);
	           g2.drawLine(0, y, sma.getEnvironment().getWidth(), y);
	        }
		}
		
		int nbShark = 0;
		int nbFish = 0;
		List<Agent> agentlist = sma.getAgentlist();
		for(int i = 0; i < agentlist.size(); i++){
			Agent agent = agentlist.get(i);
			Color c = g2.getColor();
			g2.setColor(agent.getColor());	
			g2.fillOval(agent.getCurrentPosition().getX(), agent.getCurrentPosition().getY(), parameters.getBoxSize(), parameters.getBoxSize());
			String agentType = agent.getClass().getSimpleName();
			switch(agentType){
				case "Fish":
					nbFish++;
					break;
				case "Shark":
					nbShark++;
					break;
			}
		}
		if(displayGraph){
			watorNumberGraph.update(nbShark, nbFish);
			watorRatioGraph.update(nbShark, nbFish);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		sma = (SMA)o;
		this.invalidate();
		this.repaint();
	}
	
}
