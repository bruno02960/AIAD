package firefighting.world;

import java.util.Random;

import firefighting.AircraftAgent;
import firefighting.FillingStation;
import firefighting.Fire;
import firefighting.firestation.FireStationAgent;
import firefighting.utils.Config;
import firefighting.world.behaviours.GenerateFiresBehaviour;
import firefighting.world.behaviours.PrintStatusBehaviour;
import firefighting.world.utils.WorldObjectType;

import java.awt.Point;
import jade.core.Agent;

/**
 * Class responsible for managing, updating and printing the world status.
 */
@SuppressWarnings("serial")
public class WorldAgent extends Agent {
	
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
	private int currentNumAircraftsAgents;
	
	/*
	 * The current number of fires in the world.
	 */
	private int currentNumFires;
	
	
	//Constructors:
	/**
	 * 
	 */
	public WorldAgent() {
		this.createWorld();
		this.createFireStationAgent();
		this.generateFillingStations();
		this.generateAicraftAgents();
	}
	
	
	// Methods:
	/**
	 * Returns the number of filling stations in the world.
	 * 
	 * @return the number of filling stations in the world
	 */
	public int getNumFillingStations() {
		return this.currentNumFillingStations;
	}
	
	/**
	 * Returns the number of aircrafts the world.
	 * 
	 * @return the number of aircrafts the world.
	 */
	public int getNumAircraftsAgents() {
		return this.currentNumAircraftsAgents;
	}
	
	/**
	 * Returns the current number of fires in the world.
	 * 
	 * @return the current number of fires in the world
	 */
	public int getCurrentNumFires() {
		return this.currentNumFires;
	}
	
	/**
	 * Increases the current number of fires in the world.
	 */
	public void incCurrentNumFires() {
		this.currentNumFires++;
	}
	
	/**
	 * Decreases the current number of fires in the world.
	 */
	public void decCurrentNumFires() {
		this.currentNumFires--;
	}
	
	/**
	 * Returns the current world's map/grid.
	 * 
	 * @return the current world's map/grid
	 */
	public Object[][] getWorldMap() {
		return this.worldMap;
	}
	
	/**
	 * Returns the fire station agent in the world.
	 * 
	 * @return the fire station agent in the world
	 */
	public FireStationAgent getFireStationAgent() {
		return this.fireStationAgent;
	}
	
	/**
	 * Returns all the filling stations in the world.
	 * 
	 * @return all the filling stations in the world
	 */
	public FillingStation[] getFillingStations() {
		return this.fillingStations;
	}
	
	/**
	 * Returns all the aircraft agents in the world.
	 * 
	 * @return all the aircraft agents in the world
	 */
	public AircraftAgent[] getAircraftAgents() {
		return this.aircraftAgents;
	}
	
	/**
	 * Returns all the current fires in the world.
	 * 
	 * @return all the current fires in the world
	 */
	public Fire[] getCurrentFires() {
		return this.fires;
	}
	
	
	// Methods:
	/**
	 * Creates the matrix/grid that represents all the positions of the world.
	 */
	public void createWorld() {
		this.worldMap = new Object[Config.GRID_WIDTH][Config.GRID_HEIGHT];

		this.fires = new Fire[Config.NUM_MAX_FIRES + 1];
		
		this.currentNumFillingStations = 0;
		this.currentNumAircraftsAgents = 0;
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
	public int[] generateRandomPos() {
				
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
			
			AircraftAgent aircraftAgent = new AircraftAgent((byte) this.currentNumAircraftsAgents, aircraftWorldObject, this);
			
			
			this.worldMap[aircraftPos[0]][aircraftPos[1]] = aircraftAgent;
			this.aircraftAgents[i] = aircraftAgent;
			
			this.currentNumAircraftsAgents++;
		}
	}
	
	/**
	 * Adds a fire to some available position in the world, if it's possible.
	 * 
	 * @param firePosX coordinate X of the world's map/grid
	 * @param firePosY coordinate Y of the world's map/grid
	 * @param fire the fire object to add
	 */
	public void addFire(int firePosX, int firePosY, Fire fire) {
		this.worldMap[firePosX][firePosY] = fire;
	}
	
	/**
	 * Generates all the Fires in the world, when is possible.
	 */
	public void setup() {
		addBehaviour(new GenerateFiresBehaviour(this, 6000));
		addBehaviour(new PrintStatusBehaviour(this, 500));
	}
}
