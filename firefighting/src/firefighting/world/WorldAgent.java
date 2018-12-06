package firefighting.world;

import java.util.ArrayList;
import java.util.Random;

import firefighting.aircraft.AircraftAgent;
import firefighting.firestation.FireStationAgent;
import firefighting.nature.WaterResource;
import firefighting.nature.Fire;
import firefighting.utils.AircraftMetricsStats;
import firefighting.utils.Config;
import firefighting.utils.JADELauncher;
import firefighting.utils.Logger;
import firefighting.utils.WorldMetricsStats;
import firefighting.world.behaviours.GenerateFiresBehaviour;
import firefighting.world.behaviours.IncreaseActiveFiresIntensityBehaviour;
import firefighting.world.behaviours.UpdateStatusBehaviour;
import firefighting.world.behaviours.WeatherConditionsBehaviour;
import firefighting.world.utils.WorldObjectType;
import firefighting.world.utils.environment.SeasonType;
import firefighting.world.utils.environment.WindType;

import java.awt.Point;
import java.awt.event.ActionListener;

import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.ShutdownPlatform;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;

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
	 * The current wind type from the set {Very Windy, Windy and No Wind}
	 */
	private static WindType windType;
	
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
	private  FireStationAgent fireStationAgent;
	
	/**
	 * The Water Resources in the world.
	 */
	private WaterResource[] waterResources;
	
	// Mobile agents (with movement)
	/**
	 * The Aircraft Agents in the world.
	 */
	private  AircraftAgent[] aircraftAgents;
	
	// Independent agents (without movement)
	/**
	 * The current fires in the world.
	 */
	//private  Fire[] fires;
	private ArrayList<Fire> fires;
	
	/*
	 * The number of water resources in the world.
	 */
	private int numWaterResources;
	
	/*
	 * The current number of aircrafts in the world.
	 */
	private  int currentNumAircrafts;
	
	/*
	 * The current number of fires in the world.
	 */
	private  int currentNumFires;
	
	private  ActionListener actionListener;
	
	private WorldMetricsStats worldMetricsStats;
	
	
	//Constructors:
	/**
	 * 
	 */
	public WorldAgent() {
		
		/* TODO: What's this line code doing?*/
		this.actionListener = actionListener;
		
		// Creation of world's elements
		this.createWorld();
		this.createFireStationAgent();
		this.generateWaterResources();
		this.generateAicraftAgents();
		
		this.worldMetricsStats = new WorldMetricsStats();
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
		return this.getCurrentFires().size();
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
	public WaterResource[] getWaterResources() {
		return this.waterResources;
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
	public ArrayList<Fire> getCurrentFires() {
		return this.fires;
	}
	
	
	// Methods:
	/**
	 * Creates the matrix/grid that represents all the positions of the world.
	 */
	public void createWorld() {
		worldMap = new Object[Config.GRID_WIDTH][Config.GRID_HEIGHT];

		//fires = new Fire[Config.NUM_MAX_FIRES];
		fires = new ArrayList<Fire>();
		
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
		this.waterResources = new WaterResource[Config.NUM_MAX_WATER_RESOURCES];
		
		for(int i = 0; i < Config.NUM_MAX_WATER_RESOURCES; i++) {
			int[] waterResourcePos = this.generateRandomPos();
			
			WorldObject waterResourceWorldObject = new WorldObject(WorldObjectType.WATER_RESOURCE, new Point(waterResourcePos[0], waterResourcePos[1]));
			
			WaterResource waterResource = new WaterResource((byte) this.numWaterResources, waterResourceWorldObject);
			
			this.waterResources[i] = waterResource;
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
	
	public void removeFire(int firePosX, int firePosY) {
		
		ArrayList<Fire> fires = this.getCurrentFires();
		
		for(int f = 0; f < fires.size(); f++) {
			
				WorldObject fire = fires.get(f).getWorldObject();
				if(fire.getPosX() == firePosX && fire.getPosY() == firePosY) {
					fires.remove(f);
					break;
				}
			
		}
		
		this.worldMap[firePosX][firePosY] = null;
	}
	
	public void refreshWorldMapPositions() {
		Object[][] tmpWorldMap = new Object[Config.GRID_WIDTH][Config.GRID_HEIGHT];
		
		// 1) Switching/refreshing the fire station's position in the world map/grid 
		FireStationAgent fireStationAgent = this.getFireStationAgent();
		WorldObject fireStationWorldObject = fireStationAgent.getWorldObject();
		
		tmpWorldMap[fireStationWorldObject.getPosX()][fireStationWorldObject.getPosY()] = fireStationAgent;
		
		// 2) Switching/refreshing the water resources' positions in the world map/grid 
		WaterResource[] waterResources = this.getWaterResources();
		
		for(int wr = 0; wr < waterResources.length; wr++) {
			WaterResource waterResource = waterResources[wr];
			WorldObject waterResourceWorldObject = waterResource.getWorldObject();
			
			tmpWorldMap[waterResourceWorldObject.getPosX()][waterResourceWorldObject.getPosY()] = waterResource;
		}
				
		// 3) Switching/refreshing the aircraft agents' positions in the world map/grid 
		AircraftAgent[] aircraftAgents = this.getAircraftAgents();
				
		for(int aa = 0; aa < aircraftAgents.length; aa++) {
			AircraftAgent aircraftAgent = aircraftAgents[aa];
			WorldObject aircraftWorldObject = aircraftAgent.getWorldObject();
					
			tmpWorldMap[aircraftWorldObject.getPosX()][aircraftWorldObject.getPosY()] = aircraftAgent;
		}
		
		// 4) Switching/refreshing the fires' positions in the world map/grid 
		ArrayList<Fire> fires = this.getCurrentFires();
				
		for(int f = 0; f < fires.size(); f++) {	
		
				Fire fire = fires.get(f);
				WorldObject fireWorldObject = fire.getWorldObject();
				
				tmpWorldMap[fireWorldObject.getPosX()][fireWorldObject.getPosY()] = fire;
			
		}
				
		// 5) Switching/refreshing the world maps/grids objects
		this.worldMap = null;
		this.worldMap = tmpWorldMap;
		tmpWorldMap = null;
	}
	/**
	 * Generates all the Fires in the world, when is possible.
	 */
	public void setup() {
		
		this.addBehaviour(new GenerateFiresBehaviour(this, 8000));
		this.addBehaviour(new UpdateStatusBehaviour(this, 1000));
	}
	
	

	/**
	 * Returns the current world map
	 * @return current world map
	 */
	public  Object[][] getWorldMap() {
		return worldMap;
	}
	
	
	public WorldMetricsStats getWorldMetricsStats() {
		return this.worldMetricsStats;	
	}


	/**
	 * Shuts JADE down
	 */
	/*public void shutDown() {
	}*/
	
	public void shutDown() {		
		/*ACLMessage shutdownMessage = new ACLMessage(ACLMessage.REQUEST);
        Codec codec = new SLCodec();
        this.getContentManager().registerLanguage(codec);
        this.getContentManager().registerOntology(JADEManagementOntology.getInstance());
        shutdownMessage.addReceiver(this.getAMS());
        shutdownMessage.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
        shutdownMessage.setOntology(JADEManagementOntology.getInstance().getName());
        try {
			this.getContentManager().fillContent(shutdownMessage,new Action(this.getAID(), new ShutdownPlatform()));
		} catch (CodecException | OntologyException e) {
			//e.printStackTrace();
		}
        this.send(shutdownMessage);*/
		
		JADELauncher.prepareRun();
	}
}