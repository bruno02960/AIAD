package firefighting;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import java.awt.Point;
import jade.core.Agent;

/**
 * Class responsible for updating and printing the world
 */
@SuppressWarnings("serial")
public class World extends Agent implements Runnable {

	private WorldObject[][] worldMap;
	
	// Fixed agents (without movement)
	private FireStationAgent fireStation;
	//private FillingStation[] fillingStations;
	
	// Mobile agents (with movement)
	private AircraftAgent[] aircraftAgents;
	
	// Independent agents (without movement)
	private Fire[] fires;
	
	public void createWorld() {
		this.worldMap = new WorldObject[Config.GRID_WIDTH][Config.GRID_HEIGHT];
	}
	
	private int generateRandomXOrY(int axisLimit) {
		Random randomObject = new Random();
		
		return randomObject.nextInt((axisLimit - 1) + 1) + 1;
	}
	
	private int[] generateRandomPos() {
				
		int posX = this.generateRandomXOrY(Config.GRID_WIDTH);
		int posY = this.generateRandomXOrY(Config.GRID_HEIGHT);
		
    	while(worldMap[posX][posY] != null) {
    		posX = this.generateRandomXOrY(Config.GRID_WIDTH);
    		posY = this.generateRandomXOrY(Config.GRID_HEIGHT);
    	}
    	
    	int[] pos = {posX, posY};
    	
    	return pos;
	}
	
	public void createFireStation() {
		int[] fireStationPos = this.generateRandomPos();
    		
    	// TODO - Definir a estrutura/objeto
    	//this.worldMap[fireStationPos[0]][fireStationPos[1]] = new WorldObject(WorldObjectType.FIRE, new Point(fireStationPos[0], fireStationPos[1]));
    	//this.fireStation = new WorldObject(WorldObjectType.FIRE, new Point(fireStationPos[0], fireStationPos[1]));
	}
	
	public void generateFillingStations() {
		//this.fillingStations = new FillingStation[3];
		
		for(int i = 0; i < 3; i++) {
			int[] fillingStationPos = this.generateRandomPos();
				
			// TODO - Definir a estrutura/objeto
	    	//this.worldMap[fillingStationPos[0]][fillingStationPos[1]] = new WorldObject(WorldObjectType.FILLING, new Point(fillingStationPos[0], fillingStationPos[1]));
	    	//this.fillingStations[i] = new WorldObject(WorldObjectType.FILLING, new Point(fillingStationPos[0], fillingStationPos[1]));
		}
	}
	
	public void generateAicraftAgents() {
		this.aircraftAgents = new AircraftAgent[3];
		
		for(int i = 0; i < 3; i++) {
			int[] aircraftAgentPos = this.generateRandomPos();
				
			// TODO - Definir a estrutura/objeto
	    	//this.worldMap[aircraftAgentPos[0]][aircraftAgentPos[1]] = new WorldObject(WorldObjectType.AIRCRAFT, new Point(aircraftAgentPos[0], aircraftAgentPos[1]));
	    	//this.aircraftAgents[i] = new WorldObject(WorldObjectType.AIRCRAFT, new Point(aircraftAgentPos[0], aircraftAgentPos[1]));
		}
	}
	
	
	Thread generateFires = new Thread(() -> {
		this.fires = new Fire[8];
		
	    for(;;) {
	    	// Time to wait until generate the next fire (6 seconds per fire)
	    	try {
				Thread.sleep(6000);
			}
	    	catch (InterruptedException e) {
				// Trace the InterruptedException e
				e.printStackTrace();
			}
	    	
	    	int[] firePos = this.generateRandomPos();
	    		    	
	    	//this.worldMap[firePos[0]][firePos[1]] = new WorldObject(WorldObjectType.AIRCRAFT, new Point(firePos[0], firePos[1]));
	    	//this.fires[i] = new WorldObject(WorldObjectType.AIRCRAFT, new Point(firePos[0], firePos[1]));
	    }
	});
	
	public void printWorldStatus() {
		for(int i = 0; i < Config.GRID_WIDTH * 3 + 1; i++) {
			System.out.print('-');
		}
		System.out.println("");
		for(int j = 0; j < Config.GRID_HEIGHT; j++) {
			for(int i = 0; i < Config.GRID_WIDTH; i++) {
				System.out.print("|  ");
			}
			System.out.println('|');
			for(int i = 0; i < Config.GRID_WIDTH * 3 + 1; i++) {
				System.out.print('-');
			}
			System.out.println("");
		}
	}
	
	public void startWorld() {
		this.createWorld();
		this.createFireStation();
		this.generateFillingStations();
		this.generateAicraftAgents();
		
		this.generateFires.start();
	
		//this.printWorldStatus();
	}
}