package sma.parameter;

public class Parameter {
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
	
	public Parameter(int gridSizeX, int gridSizeY, int boxSize, int delay, int scheduling, int nbTicks, boolean grid, boolean trace, int seed, int refresh, int nbParticles, boolean toric) {
		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		this.boxSize = boxSize;
		this.delay = delay;
		this.scheduling = scheduling;
		this.nbTicks = nbTicks;
		this.grid = grid;
		this.trace = trace;
		this.seed = seed;
		this.refresh = refresh;
		this.nbParticles = nbParticles;
		this.toric = toric;
	}

	public int getGridSizeX() {
		return gridSizeX;
	}

	public int getGridSizeY() {
		return gridSizeY;
	}

	public int getBoxSize() {
		return boxSize;
	}

	public int getDelay() {
		return delay;
	}

	public int getScheduling() {
		return scheduling;
	}

	public int getNbTicks() {
		return nbTicks;
	}

	public boolean isGrid() {
		return grid;
	}

	public boolean isTrace() {
		return trace;
	}

	public int getSeed() {
		return seed;
	}

	public int getRefresh() {
		return refresh;
	}

	public int getNbParticles() {
		return nbParticles;
	}

	public boolean isToric() {
		return toric;
	}
	
	
}
