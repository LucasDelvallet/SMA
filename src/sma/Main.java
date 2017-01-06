package sma;

import java.awt.Color;
import java.awt.Dimension;

import sma.model.Parameters;
import sma.model.SMA;
import view.GUIHelper;
import view.View;

public class Main {
	
	public static void main(String[] args) {
		SMA sma = new SMA();

		View jc = new View();
		jc.setBackground(Color.WHITE);
		jc.setPreferredSize(new Dimension(Parameters.gridSizeX,Parameters.gridSizeY));
		sma.addObserver(jc);
		GUIHelper.showOnFrame(jc,"S.M.A");
		
		sma.run();
	}

}
