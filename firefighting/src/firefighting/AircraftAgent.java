package firefighting;

import java.awt.Point;
import java.util.Random;
import jade.core.Agent;

/**
 * Class responsible for aircraft agents and their behaviour
 */
@SuppressWarnings("serial")
public class AircraftAgent extends Agent {
	/**
	 * Capacity of the aircraft tank
	 */
	private int tank_capacity;
	
	/**
	 * Location of aircraft
	 */
	private Point location;
	
	/**
	 * Creates a new AircraftAgent, initialising its tank capacity
	 */
	private AircraftAgent() {
		Random rand = new Random();
		
		tank_capacity = rand.nextInt(Config.AIRCRAFT_MAX_TANK_CAPACITY) + 1;
		location = new Point(rand.nextInt(Config.GRID_WIDTH),rand.nextInt(Config.GRID_HEIGHT));
	}
}
