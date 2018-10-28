package firefighting;

import java.util.Random;
import jade.core.Agent;

/**
 * Class responsible for an Aircraft Agent and its behaviour.
 */
@SuppressWarnings("serial")
public class AircraftAgent extends Agent {
	
	// Global Instance Variables:
	/**
	 * ID of the Aircraft Agent.
	 */
	private byte id;
	
	/**
	 * World's object of the Aircraft Agent.
	 */
	private WorldObject worldObject;
	
	/**
	 * Capacity of the Aircraft Agent's tank.
	 */
	private int tankCapacity;
	
	/**
	 * Status of the Aircraft Agent's tank.
	 */
	private int tankStatus;
	
	
	// Constructors:
	/**
	 * Constructor #1 of the Aircraft Agent.
	 * 
	 * Creates a new Aircraft Agent, initialising its ID, its world's object and its tank capacity.
	 * 
	 * @param id the Aircraft Agent's ID
	 * @param worldObject the Aircraft's World Object
	 */
	AircraftAgent(byte id, WorldObject worldObject) {
		Random random = new Random();
		
		this.id = id;
		this.worldObject = worldObject;
		this.tankCapacity = random.nextInt(Config.AIRCRAFT_MAX_TANK_CAPACITY) + 1;
		this.tankStatus = 0;
	}
	
	//TODO: Remove
	public AircraftAgent() {
		
	}
	
	
	// Methods:
	/**
	 * Returns the Aircraft Agent's ID.
	 * 
	 * @return the Aircraft Agent's ID
	 */
	private byte getID() {
		return this.id;
	}
	
	/**
	 * Return the Aircraft Agent's World Object.
	 * 
	 * @return the Aircraft Agent's World Object
	 */
	private WorldObject getWorldObject() {
		return this.worldObject;
	}
	
	/**
	 * Return the Aircraft Agent's Tank Capacity.
	 * 
	 * @return the Aircraft Agent's Tank Capacity
	 */
	private int getTankCapacity() {
		return this.tankCapacity;
	}

	@Override
	public String toString() {
		return "A" + tankStatus;
	}
}
