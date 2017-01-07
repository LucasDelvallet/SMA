package sma;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import sma.model.Parameters;
import sma.model.SMA;
import view.GUIHelper;
import view.View;

public class Main {
	
	public static void main(String[] args) {
		SMA sma = new SMA();

		
		
		View view = new View();
		sma.addObserver(view);
		
		
		JScrollPane scrollPane = new JScrollPane(view);
		GUIHelper.showOnFrame(scrollPane,"S.M.A");
		
		sma.run();
	}

}
