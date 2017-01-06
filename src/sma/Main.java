package sma;

import java.awt.*;
import sma.model.*;
import view.*;
import com.sun.media.sound.ModelAbstractChannelMixer;

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
