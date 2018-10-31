package firefighting;

import java.util.Random;

import java.awt.Point;
import jade.core.Agent;

/**
 * Class responsible for managing, updating and printing the world status.
 */
@SuppressWarnings("serial")
public class World extends Agent implements Runnable {
	
	// Global Instance Variables:
	/**
	 * The matrix/grid that represents all the positions of the world.
	 */
	private Object[][] worldMap;
	
	// Fixed agents (without movement)
	/**
	 * The Fire Station Agent in the world.
	 */
	private FireStationAgent fireStationAgent;
	
	/**
	 * The Filling Stations in the world.
	 */
	private FillingStation[] fillingStations;
	
	// Mobile agents (with movement)
	/**
	 * The Aircraft Agents in the world.
	 */
	private AircraftAgent[] aircraftAgents;
	
	// Independent agents (without movement)
	/**
	 * The current fires in the world.
	 */
	private Fire[] fires;
	
	/*
	 * The current number of filling stations in the world.
	 */
	private int currentNumFillingStations;
	
	/*
	 * The current number of aircrafts in the world.
	 */
	private int currentNumAircrafts;
	
	/*
	 * The current number of fires in the world.
	 */
	private int currentNumFires;
	
	
	public int getNumFillingStations() {
		return currentNumFillingStations;
	}
	
	public int getNumAircrafts() {
		return currentNumAircrafts;
	}
	
	public int getNumFires() {
		return currentNumFires;
	}
	
	public FireStationAgent getFireStationAgent() {
		return fireStationAgent;
	}
	
	public FillingStation[] getFillingStations() {
		return fillingStations;
	}
	
	public AircraftAgent[] getAircraftAgents() {
		return aircraftAgents;
	}
	
	// Methods:
	/**
	 * Creates the matrix/grid that represents all the positions of the world.
	 */
	public void createWorld() {
		this.worldMap = new Object[Config.GRID_WIDTH][Config.GRID_HEIGHT];
		
		this.currentNumFillingStations = 0;
		this.currentNumAircrafts = 0;
		this.currentNumFires = 0;
	}
	
	/**
	 * Returns a random coordinate X or Y.
	 * 
	 * @param axisLimit the X or Y axis' limit
	 * 
	 * @return a random coordinate X or Y
	 */
	private int generateRandomXOrY(int axisLimit) {
		Random randomObject = new Random();
		
		return randomObject.nextInt(axisLimit) + 1;
	}
	
	/**
	 * Returns a random position in the matrix/grid that represents all the positions of the world.
	 * 
	 * @return a random position in the matrix/grid that represents all the positions of the world
	 */
	private int[] generateRandomPos() {
				
		int posX = this.generateRandomXOrY(Config.GRID_WIDTH) - 1;
		int posY = this.generateRandomXOrY(Config.GRID_HEIGHT) - 1;
		
    	while(worldMap[posX][posY] != null) {
    		posX = this.generateRandomXOrY(Config.GRID_WIDTH) - 1;
    		posY = this.generateRandomXOrY(Config.GRID_HEIGHT) - 1;
    	}
    	
    	int[] pos = {posX, posY};
    	
    	return pos;
	}
	
	/**
	 * Creates the Fire Station Agent in the world.
	 */
	public void createFireStationAgent() {
		int[] fireStationPos = this.generateRandomPos();
		
		WorldObject fireStationWorldObject = new WorldObject(WorldObjectType.FIRE_STATION, new Point(fireStationPos[0], fireStationPos[1]));
		
		this.fireStationAgent = new FireStationAgent(fireStationWorldObject);
		this.worldMap[fireStationPos[0]][fireStationPos[1]] = this.fireStationAgent;
	}
	
	/**
	 * Generates all the Filling Stations in the world.
	 */
	public void generateFillingStations() {
		this.fillingStations = new FillingStation[Config.NUM_MAX_FILLING_STATIONS];
		
		for(int i = 0; i < Config.NUM_MAX_FILLING_STATIONS; i++) {
			int[] fillingStationPos = this.generateRandomPos();
			
			WorldObject fillingStationWorldObject = new WorldObject(WorldObjectType.FILLING_STATION, new Point(fillingStationPos[0], fillingStationPos[1]));
			
			FillingStation fillingStation = new FillingStation((byte) this.currentNumFillingStations, fillingStationWorldObject);
			
			this.fillingStations[i] = fillingStation;
			this.worldMap[fillingStationPos[0]][fillingStationPos[1]] = fillingStation;
			
			this.currentNumFillingStations++;
		}
	}
	
	/**
	 * Generates all the Aircraft Agents in the world.
	 */
	public void generateAicraftAgents() {
		this.aircraftAgents = new AircraftAgent[Config.NUM_MAX_AIRCRAFTS];
		
		for(int i = 0; i < Config.NUM_MAX_AIRCRAFTS; i++) {
			int[] aircraftPos = this.generateRandomPos();
			
			WorldObject aircraftWorldObject = new WorldObject(WorldObjectType.AIRCRAFT, new Point(aircraftPos[0], aircraftPos[1]));
			
			AircraftAgent aircraftAgent = new AircraftAgent((byte) this.currentNumAircrafts, aircraftWorldObject);
			
			
			this.worldMap[aircraftPos[0]][aircraftPos[1]] = aircraftAgent;
			this.aircraftAgents[i] = aircraftAgent;
			
			this.currentNumAircrafts++;
		}
	}
	
	/**
	 * Generates all the Fires in the world, when is possible.
	 */
	Thread generateFires = new Thread(() -> {
		this.fires = new Fire[Config.NUM_MAX_FIRES + 1];
		
		this.currentNumFires = 0;
		
	    for(;;) {
	    	// Time to wait until generate the next fire (6 seconds per fire)
	    	try {
				Thread.sleep(6000);
			}
	    	catch (InterruptedException e) {
				// Trace the InterruptedException e
				e.printStackTrace();
			}
	    		    	
	    	if(this.currentNumFires < Config.NUM_MAX_FIRES) {
		    	int[] firePos = this.generateRandomPos();
		    	
		    	WorldObject fireWorldObject = new WorldObject(WorldObjectType.FIRE, new Point(firePos[0], firePos[1]));
		    	
		    	Fire fire = new Fire((byte) this.currentNumFires, fireWorldObject);
		    	
	    		this.worldMap[firePos[0]][firePos[1]] = fire;
	    		
	    		
	    		int fireToCreatePosInArray;

	    		for(fireToCreatePosInArray = 0; fireToCreatePosInArray < Config.NUM_MAX_FIRES; fireToCreatePosInArray++)
	    			if(this.fires[fireToCreatePosInArray] != null)
	    				break;
	    		
	    		if(fireToCreatePosInArray <= Config.NUM_MAX_FIRES) {
		    		this.fires[fireToCreatePosInArray] = fire;
		    		
		    		this.currentNumFires++;
	    		}
	    	}
	    }
	});
	
	/**
	 * Prints the current status of the world.
	 */
	public void printCurrentStatus() {
		for(int i = 0; i < Config.GRID_WIDTH * 3 + 1; i++) {
			System.out.print('-');
		}
		System.out.println("");
		for(int j = 0; j < Config.GRID_HEIGHT; j++) {
			for(int i = 0; i < Config.GRID_WIDTH; i++) {
				System.out.print("|");
				if (this.worldMap[i][j] != null) {
					System.out.print(this.worldMap[i][j]);
				}
				else {
					System.out.print("  ");
				}
			}
			System.out.println('|');
			for(int i = 0; i < Config.GRID_WIDTH * 3 + 1; i++) {
				System.out.print('-');
			}
			System.out.println("");
		}
	}
	
	/**
	 * Starts the world and all its components.
	 */
	public World() {
		this.createWorld();
		this.createFireStationAgent();
		this.generateFillingStations();
		this.generateAicraftAgents();
		
		this.generateFires.start();
	
		// TODO - Uncomment to print the status of the world
		//this.printWorldStatus();
	}
}
