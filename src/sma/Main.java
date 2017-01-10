package sma;

import java.io.File;
import java.io.IOException;

import javax.swing.JScrollPane;

import sma.model.SMA;
import sma.parameter.Parameter;
import sma.parameter.ParameterReader;
import view.GUIHelper;
import view.View;

public class Main {
	
	public static void main(String[] args) {
		try {
			Parameter param = new ParameterReader().getParameters(new File("res/param.txt"));
			SMA sma = new SMA(param);

			View view = new View(param);
			sma.addObserver(view);
			
			JScrollPane scrollPane = new JScrollPane(view);
			GUIHelper.showOnFrame(scrollPane,"S.M.A");
			
			sma.run();
		} catch (IOException e) {
			System.out.println("Parameters file error");
			e.printStackTrace();
		}
		
	}

}
