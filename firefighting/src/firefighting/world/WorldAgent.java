package firefighting.world;

import java.util.Random;

import firefighting.aircraft.AircraftAgent;
import firefighting.firestation.FireStationAgent;
import firefighting.nature.WaterResource;
import firefighting.nature.Fire;
import firefighting.utils.Config;
import firefighting.world.behaviours.GenerateFiresBehaviour;
import firefighting.world.behaviours.PrintStatusBehaviour;
import firefighting.world.behaviours.RefillWaterResourcesBehaviour;
import firefighting.world.utils.SeasonType;
import firefighting.world.utils.WindType;
import firefighting.world.utils.WorldObjectType;

import java.awt.Point;
import jade.core.Agent;

/**
 * Class responsible for managing, updating and printing the world status.
 */
@SuppressWarnings("serial")
public class WorldAgent extends Agent {
	
	// Constants:
	/**
	 * The current season type from the set {Spring, Summer, Autumn and Winter}
	 */
	private static SeasonType seasonType;	
	
	/**
	 * current season type from the set {Very Windy, Windy and No Wind}
	 */
	private static WindType windType;
	
	
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
	 * The Water Resources in the world.
	 */
	private WaterResource[] waterResourses;
	
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
	 * The number of water resources in the world.
	 */
	private int numWaterResources;
	
	/*
	 * The current number of aircrafts in the world.
	 */
	private int currentNumAircrafts;
	
	/*
	 * The current number of fires in the world.
	 */
	private  int currentNumFires;
	
	
	//Constructors:
	/**
	 * 
	 */
	public WorldAgent(byte seasonTypeID, byte windTypeID) {
		
		// Sets the season type
		SeasonType seasonType;
		
		switch(seasonTypeID) {
			// SPRING SEASON
			case 0:
				seasonType = SeasonType.SPRING;
				break;
			// SUMMER SEASON
			case 1:
				seasonType = SeasonType.SUMMER;
				break;
			// AUTUMN SEASON
			case 2:
				seasonType = SeasonType.AUTUMN;
				break;
			// WINTER SEASON
			case 3:
				seasonType = SeasonType.WINTER;
				break;
			default:
				seasonType = null;
				break;
		}
		
		// Sets the wind type
		WindType windType;
		
		switch(windTypeID) {
			case 0:
				windType = WindType.VERY_WINDY;
				break;
			case 1:
				windType = WindType.WINDY;
				break;
			case 2:
				windType = WindType.NO_WIND;
				break;
			default:
				windType = null;
				break;
		}
		
		// Sets all the world's environment conditions
		WorldAgent.seasonType = seasonType;
		WorldAgent.windType = windType;
		
		// Creation of world's elements
		this.createWorld();
		this.createFireStationAgent();
		this.generateWaterResources();
		this.generateAicraftAgents();
	}
	
	
	// Methods:
	/**
	 * Returns the season type influencing the world.
	 * 
	 * @return the season type influencing the world
	 */
	public SeasonType getSeasonType() {
		return WorldAgent.seasonType;
	}
	
	/**
	 * Returns the wind type influencing the world.
	 * 
	 * @return the wind type influencing the world
	 */
	public WindType getWindType() {
		return WorldAgent.windType;
	}
	
	/**
	 * Returns the number of water resources in the world.
	 * 
	 * @return the number of water resources in the world
	 */
	public int getNumWaterResources() {
		return this.numWaterResources;
	}
	
	/**
	 * Returns the number of aircrafts the world.
	 * 
	 * @return the number of aircrafts the world.
	 */
	public int getNumAircraftsAgents() {
		return this.currentNumAircrafts;
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
	 * Returns the fire station agent in the world.
	 * 
	 * @return the fire station agent in the world
	 */
	public FireStationAgent getFireStationAgent() {
		return this.fireStationAgent;
	}
	
	/**
	 * Returns all the natural water resources in the world.
	 * 
	 * @return all the natural water resources in the world
	 */
	public WaterResource[] getWaterResourses() {
		return this.waterResourses;
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
		worldMap = new Object[Config.GRID_WIDTH][Config.GRID_HEIGHT];

		fires = new Fire[Config.NUM_MAX_FIRES];
		
		numWaterResources = 0;
		currentNumAircrafts = 0;
		currentNumFires = 0;
	}
	
	/**
	 * Returns a random coordinate X or Y.
	 * 
	 * @param axisLimit the X or Y axis' limit
	 * 
	 * @return a random coordinate X or Y
	 */
	private  int generateRandomXOrY(int axisLimit) {
		Random randomObject = new Random();
		
		return randomObject.nextInt(axisLimit) + 1;
	}
	
	/**
	 * Returns a random position in the matrix/grid that represents all the positions of the world.
	 * 
	 * @return a random position in the matrix/grid that represents all the positions of the world
	 */
	public  int[] generateRandomPos() {
				
		int posX = generateRandomXOrY(Config.GRID_WIDTH) - 1;
		int posY = generateRandomXOrY(Config.GRID_HEIGHT) - 1;
		
    	while(worldMap[posX][posY] != null) {
    		posX = generateRandomXOrY(Config.GRID_WIDTH) - 1;
    		posY = generateRandomXOrY(Config.GRID_HEIGHT) - 1;
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
		
		this.fireStationAgent = new FireStationAgent(this, fireStationWorldObject);
		this.worldMap[fireStationPos[0]][fireStationPos[1]] = this.fireStationAgent;
	}
	
	/**
	 * Generates all the natural water resources in the world.
	 */
	public void generateWaterResources() {
		this.waterResourses = new WaterResource[Config.NUM_MAX_WATER_RESOURCES];
		
		for(int i = 0; i < Config.NUM_MAX_WATER_RESOURCES; i++) {
			int[] waterResourcePos = this.generateRandomPos();
			
			WorldObject waterResourceWorldObject = new WorldObject(WorldObjectType.WATER_RESOURCE, new Point(waterResourcePos[0], waterResourcePos[1]));
			
			WaterResource waterResource = new WaterResource((byte) this.numWaterResources, waterResourceWorldObject);
			
			this.waterResourses[i] = waterResource;
			this.worldMap[waterResourcePos[0]][waterResourcePos[1]] = waterResource;
			
			this.numWaterResources++;
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
			
			AircraftAgent aircraftAgent = new AircraftAgent((byte) this.currentNumAircrafts, aircraftWorldObject, this);
			
			
			this.worldMap[aircraftPos[0]][aircraftPos[1]] = aircraftAgent;
			this.aircraftAgents[i] = aircraftAgent;
			
			this.currentNumAircrafts++;
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
		
		//addBehaviour(new GenerateFiresBehaviour(this, 6000));
		//addBehaviour(new PrintStatusBehaviour(this, 4000));
		this.addBehaviour(new RefillWaterResourcesBehaviour(this, 1000));
	}
	

	/**
	 * Returns the current world map
	 * @return current world map
	 */
	public  Object[][] getWorldMap() {
		return worldMap;
	}
}