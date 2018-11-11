package firefighting.world;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import firefighting.aircraft.AircraftAgent;
import firefighting.firestation.FireStationAgent;
import firefighting.nature.WaterResource;
import firefighting.nature.Fire;
import firefighting.utils.Config;
import firefighting.world.behaviours.GenerateFiresBehaviour;
import firefighting.world.behaviours.IncreaseActiveFiresIntensityBehaviour;
import firefighting.world.behaviours.UpdateWorldStatusBehaviour;
import firefighting.world.behaviours.WeatherConditionsBehaviour;
import firefighting.world.utils.WorldObjectType;
import firefighting.world.utils.environment.SeasonType;
import firefighting.world.utils.environment.WindType;

import java.awt.Point;
import jade.core.Agent;

/**
 * Class responsible for managing, updating and printing the world status.
 */
public class WorldAgent extends Agent {
	
	// Constants:
	
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The current season type from the set {Spring, Summer, Autumn and Winter}
	 */
	private static SeasonType seasonType;	
	
	/**
	 * The current wind type from the set {Very Windy, Windy and No Wind}
	 */
	private static WindType windType;
	
	/**
	 * The timestamp of creation of the world.
	 */
	private static long timestampCreation;
	
	/**
	 * The boolean value that keeps information that allows to know if can occur periodically,
	 * in a rare way, droughts (extreme dry situations) - Can only occurs in summer season
	 */
	private static boolean droughtSituation;
	
	/**
	 * The float value that keeps the probability value of a drought (extreme dry situation) happens,
	 * if it's possible and allowed
	 * - [0%, 0%] probability interval,
	 *   of drought (extreme dry situation) happen in spring, autumn and winter seasons
	 * - a random [m%, n%] probability interval, from the set [0%, 100%],
	 *   of drought (extreme dry situation) happen in summer season
	 */
	private static float[] droughtSituationProbabilityInterval;
	
	
	
	// Global Instance Variables:
	
	/**
	 * The matrix of grid/map that represents all the positions of the world.
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
	private ArrayList<WaterResource> waterResources;
	
	// Mobile agents (with movement)
	/**
	 * The Aircraft Agents in the world.
	 */
	private ArrayList<AircraftAgent> aircraftAgents;
	
	// Independent agents (without movement)
	/**
	 * The current fires in the world.
	 */
	private ConcurrentNavigableMap<Integer, Fire> currentFires;
	
	/**
	 * The current extinguished fires in the world.
	 */
	private ArrayList<Fire> currentExtinguishedFires;
	
	/*
	 * The total number of fires generated in the world.
	 */
	private int numTotalFiresGenerated;
	
	
	
	//Constructors:
	
	/**
	 * TODO 
	 */
	public WorldAgent(byte seasonTypeID, byte windTypeID, long timestamp) {
		
		// Sets the type of season
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
		
		// Sets the type of wind
		WindType windType;
		
		switch(windTypeID) {
			// NO WIND
			case 0:
				windType = WindType.NO_WIND;
				break;
			// WEAK WIND
			case 1:
				windType = WindType.WEAK_WIND;
				break;
			// NORMAL WIND
			case 2:
				windType = WindType.NORMAL_WIND;
				break;
			// STRONG WIND
			case 3:
				windType = WindType.STRONG_WIND;
				break;
			default:
				windType = null;
				break;
		}
		
		// Sets all the world's environment conditions
		WorldAgent.seasonType = seasonType;
		WorldAgent.windType = windType; // TODO: REVER
		WorldAgent.timestampCreation = timestamp;
		
		Random randomObject = new Random();
		
		// Verifies the current season type first. If the current season type defined previously was the summer,
		// generates a random boolean value (true or false) to keep the information that allows to know
		// if, at some moment, can occur droughts (extreme dry situations), that will affect the global behaviour
		// of the world and its weather conditions
		if(this.getSeasonType().getID() == SeasonType.SUMMER.getID())
			WorldAgent.droughtSituation = randomObject.nextBoolean();
		else
			WorldAgent.droughtSituation = false;
		
		if(this.canOccurDroughtSituations()) {
			float bound1 = randomObject.nextFloat();
			float bound2 = randomObject.nextFloat();
			
			if(bound1 != bound2) {
				float max = bound2 > bound1 ? bound2 : bound1;
				float min = bound2 < bound1 ? bound2 : bound1;
				
				WorldAgent.droughtSituationProbabilityInterval = new float[]{min,max};
			}
			else
				WorldAgent.droughtSituationProbabilityInterval = new float[]{bound1,bound2};
		}
		else
			// Never occurs droughts (extreme dry situations)
			WorldAgent.droughtSituationProbabilityInterval = new float[]{0.0f,0.0f};
		
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
	 * Returns the boolean value that keeps the information that allows to know if can occur periodically,
	 * in a rare way, droughts (extreme dry situations) - Can only occurs in summer season.
	 * 
	 * @return the boolean value that keeps the information that allows to know if can occur periodically,
	 * 		   in a rare way, droughts (extreme dry situations) - Can only occurs in summer season
	 */
	public boolean canOccurDroughtSituations() {
		return WorldAgent.droughtSituation;
	}
	
	/**
	 * Returns the float array that keeps the possible probability interval of occurring periodically,
	 * in a rare way, droughts (extreme dry situations) - Can only occurs in summer season.
	 * 
	 * @return the boolean value that keep information that allows to know if can occur periodically,
	 * 		   in a rare way, droughts (extreme dry situations) - Can only occurs in summer season
	 */
	public float[] getDroughtSituationProbabilityInterval() {
		return WorldAgent.droughtSituationProbabilityInterval;
	}
	
	/**
	 * Returns the timestamp of creation of the world.
	 * 
	 * @return the timestamp of creation of the world
	 */
	public long getTimestampCreation() {
		return WorldAgent.timestampCreation;
	}
	
	/**
	 * Returns true if the demo execution has already ended, or false, otherwise.
	 * 
	 * @return true if the demo execution has already ended, or false, otherwise
	 */
	public boolean hasDemoExecutionEnded() {
		
		long currentTimeOfDemoExecution = System.currentTimeMillis() - this.getTimestampCreation();
		
		return currentTimeOfDemoExecution >= Config.DEMO_EXECUTION_TIME;
	}
	
	/**
	 * Returns the number of water resources in the world.
	 * 
	 * @return the number of water resources in the world
	 */
	public int getNumWaterResources() {
		return this.waterResources.size();
	}
	
	/**
	 * Returns the number of aircrafts the world.
	 * 
	 * @return the number of aircrafts the world.
	 */
	public int getNumAircraftsAgents() {
		return this.aircraftAgents.size();
	}
	
	/**
	 * Returns the current number of fires in the world.
	 * 
	 * @return the current number of fires in the world
	 */
	public int getNumCurrentFires() {
		return this.currentFires.size();
	}
	
	/**
	 * Returns the total number of fires generated in the world.
	 * 
	 * @return the total number of fires generated in the world
	 */
	public int getNumTotalFiresGenerated() {
		return this.numTotalFiresGenerated;
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
	public ArrayList<WaterResource> getWaterResources() {
		return this.waterResources;
	}
	
	/**
	 * Returns all the aircraft agents in the world.
	 * 
	 * @return all the aircraft agents in the world
	 */
	public ArrayList<AircraftAgent> getAircraftAgents() {
		return this.aircraftAgents;
	}
	
	/**
	 * Returns all the current fires in the world.
	 * 
	 * @return all the current fires in the world
	 */
	public ConcurrentNavigableMap<Integer, Fire> getCurrentFires() {
		return this.currentFires;
	}
	
	/**
	 * Returns all the current extinguished fires in the world.
	 * 
	 * @return all the current extinguished fires in the world
	 */
	public ArrayList<Fire> getCurrentExtinguishedFires() {
		return this.currentExtinguishedFires;
	}
	
	
	
	// Methods:
	/**
	 * Creates the matrix/grid that represents all the positions of the world.
	 */
	public void createWorld() {
		worldMap = new Object[Config.GRID_WIDTH][Config.GRID_HEIGHT];

		this.currentFires = new ConcurrentSkipListMap<Integer, Fire>();
		
		this.currentExtinguishedFires = new ArrayList<Fire>();
		
		this.numTotalFiresGenerated = 0;
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
		this.waterResources = new ArrayList<WaterResource>();
		
		for(int i = 0; i < Config.NUM_MAX_WATER_RESOURCES; i++) {
			int[] waterResourcePos = this.generateRandomPos();
			
			WorldObject waterResourceWorldObject = new WorldObject(WorldObjectType.WATER_RESOURCE, new Point(waterResourcePos[0], waterResourcePos[1]));
			
			WaterResource waterResource = new WaterResource((byte) this.getNumWaterResources(), waterResourceWorldObject);
			
			this.waterResources.add(waterResource);
			this.worldMap[waterResourcePos[0]][waterResourcePos[1]] = waterResource;
		}
	}
	
	/**
	 * Generates all the Aircraft Agents in the world.
	 */
	public void generateAicraftAgents() {
		this.aircraftAgents = new ArrayList<AircraftAgent>();
		
		for(int i = 0; i < Config.NUM_MAX_AIRCRAFTS; i++) {
			int[] aircraftPos = this.generateRandomPos();
			
			WorldObject aircraftWorldObject = new WorldObject(WorldObjectType.AIRCRAFT, new Point(aircraftPos[0], aircraftPos[1]));
			
			AircraftAgent aircraftAgent = new AircraftAgent((byte) this.getNumAircraftsAgents(), aircraftWorldObject, this);
			
			this.aircraftAgents.add(aircraftAgent);
			this.worldMap[aircraftPos[0]][aircraftPos[1]] = aircraftAgent;
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
		this.currentFires.put((int)fire.getID(), fire);
		this.worldMap[firePosX][firePosY] = fire;
		
		this.numTotalFiresGenerated++;
	}
	
	/**
	 * TODO
	 * 
	 * @param fireExtinguishedID
	 */
	public void fireExtinguished(byte fireExtinguishedID) {
		Fire fireExtiguished = this.currentFires.remove((int) fireExtinguishedID);
		this.currentExtinguishedFires.add(fireExtiguished);
	}
	
	/**
	 * Refreshes all the world objects' positions in the current world's status grid/map.
	 */
	public void refreshWorldMapPositions() {
		Object[][] tmpWorldMap = new Object[Config.GRID_WIDTH][Config.GRID_HEIGHT];
		
		// 1) Switching/refreshing the fire station's position in the world map/grid 
		FireStationAgent fireStationAgent = this.getFireStationAgent();
		WorldObject fireStationWorldObject = fireStationAgent.getWorldObject();
		
		tmpWorldMap[fireStationWorldObject.getPosX()][fireStationWorldObject.getPosY()] = fireStationAgent;
		
		// 2) Switching/refreshing the water resources' positions in the world map/grid 
		ArrayList<WaterResource> waterResources = this.getWaterResources();
		
		for(WaterResource waterResource: waterResources) {
			WorldObject waterResourceWorldObject = waterResource.getWorldObject();
			tmpWorldMap[waterResourceWorldObject.getPosX()][waterResourceWorldObject.getPosY()] = waterResource;
		}
						
		// 3) Switching/refreshing the aircraft agents' positions in the world map/grid 
		ArrayList<AircraftAgent> aircraftAgents = this.getAircraftAgents();
				
		for(AircraftAgent aircraftAgent: aircraftAgents) {
			WorldObject aircraftWorldObject = aircraftAgent.getWorldObject();
			tmpWorldMap[aircraftWorldObject.getPosX()][aircraftWorldObject.getPosY()] = aircraftAgent;
		}
		
		// 4) Switching/refreshing the fires' positions in the world map/grid 
		ConcurrentNavigableMap<Integer, Fire> fires = this.getCurrentFires();
				
		for(Fire fire: fires.values()) {	
			WorldObject fireWorldObject = fire.getWorldObject();		
			tmpWorldMap[fireWorldObject.getPosX()][fireWorldObject.getPosY()] = fire;	
		}
				
		// 5) Switching/refreshing the world maps/grids objects
		this.worldMap = null;
		this.worldMap = tmpWorldMap;
		tmpWorldMap = null;
	}
	
	/**
	 * Handle all the behaviours of the world and its respectively elements.
	 */
	public void setup() {
		this.addBehaviour(new GenerateFiresBehaviour(this, 6000));
		this.addBehaviour(new UpdateWorldStatusBehaviour(this, 1000));
		this.addBehaviour(new IncreaseActiveFiresIntensityBehaviour(this, 10000));
		this.addBehaviour(new WeatherConditionsBehaviour(this, 30000));
	}
	
	/**
	 * Returns the current world's status map/grid.
	 * 
	 * @return current world's status map/grid
	 */
	public Object[][] getWorldMap() {
		return worldMap;
	}
}