package sma.parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ParameterReader {
	private static final String GRIDSIZE_X = "gridSizeX";
	private static final String GRIDSIZE_Y = "gridSizeY";
	private static final String BOXSIZE = "boxSize";
	private static final String DELAY = "delay";
	private static final String SCHEDHLING = "scheduling";
	private static final String NBTICKS = "nbTicks";
	private static final String GRID = "grid";
	private static final String TRACE = "trace";
	private static final String SEED = "seed";
	private static final String REFRESH = "refresh";
	private static final String NBPARTICLES= "nbParticles";
	private static final String TORIC = "toric";
	// Default values
	private int gridSizeX = 70;
	private int gridSizeY = 70;
	private int boxSize = 7;
	private int delay = 130;
	private int scheduling = 0; //TODO 0:equitable 1:sequentiel, 2:aléatoire 
	private int nbTicks = 3000000; 
	private boolean grid = true;
	private boolean trace = false; //TODO
	private int seed = 1;
	private int refresh = 1;
	private int nbParticles = 50;
	private boolean toric = false;
	
	public Parameter getParameters(File paramFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(paramFile));
		try {
		    String line = br.readLine();
		    while (line != null) {
		        setVariable(line);
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}
		
		return new Parameter(gridSizeX, gridSizeY, boxSize, delay, scheduling, nbTicks, grid, trace, seed, refresh, nbParticles, toric);
	}
	
	private void setVariable(String line) {
		String[] param = line.split(" ");
		
		switch (param[0]) {
		case GRIDSIZE_X:
			this.gridSizeX = Integer.parseInt(param[1]);
			break;
		case GRIDSIZE_Y:
			this.gridSizeY = Integer.parseInt(param[1]);
			break;
		case BOXSIZE:
			this.gridSizeY = Integer.parseInt(param[1]);
			break;
		case DELAY:
			this.delay = Integer.parseInt(param[1]);
			break;
		case SCHEDHLING:
			this.scheduling = Integer.parseInt(param[1]);
			break;
		case NBTICKS:
			this.nbTicks = Integer.parseInt(param[1]);
			break;
		case GRID:
			this.grid = Boolean.parseBoolean(param[1]);
			break;
		case TRACE:
			this.trace = Boolean.parseBoolean(param[1]);
			break;
		case SEED:
			this.seed = Integer.parseInt(param[1]);
			break;
		case REFRESH:
			this.refresh = Integer.parseInt(param[1]);
			break;
		case NBPARTICLES:
			this.nbParticles = Integer.parseInt(param[1]);
			break;
		case TORIC:
			this.toric = Boolean.parseBoolean(param[1]);
			break;
		default:
			break;
		}
	}
	
}
