package firefighting.nature;

import java.util.Random;

import firefighting.utils.Config;
import firefighting.world.*;

/**
 * Class responsible for a Filling Station.
 */
public class WaterResource {
	
	// Global Instance Variables:
	/**
	 * ID of the Filling Station.
	 */
	private byte id;
	
	/**
	 * World's object of the Filling Station.
	 */
	private WorldObject worldObject;
	
	/**
	 * Filling Station current status
	 */
	private int waterCapacity;
	
	// Constructors:
	/**
	 * Constructor #1 of the Filling Station.
	 * 
	 * Creates a new Filling Station, initialising its ID and its world's object.
	 * 
	 * @param the Filling Station's ID
	 * @param the Filling Station's World Object
	 */
	public WaterResource(byte id, WorldObject worldObject) {
		Random random = new Random();
		
		this.id = id;
		this.worldObject = worldObject;
		this.waterCapacity = random.nextInt(Config.WATER_RESOURCE_INITIAL_MAX_CAPACITY) + 1;
	}

	// Methods:
	/**
	 * Returns the Filling Station's ID.
	 * 
	 * @return the Filling Station's ID
	 */
	private byte getID() {
		return this.id;
	}
	
	/**
	 * Return the Filling Station's World Object.
	 * 
	 * @return the Filling Station's World Object
	 */
	public WorldObject getWorldObject() {
		return this.worldObject;
	}
	
	public int getWaterCapacity() {
		return this.waterCapacity;
	}
	
	public void decreasingCapacity(int decreaseValue) {
		
		if((this.waterCapacity - decreaseValue) < 0) {
			this.waterCapacity = 0;
		}
		
		this.waterCapacity -= decreaseValue;
	}

	public void increasingCapacity(int increaseValue) {
		this.waterCapacity += increaseValue;
	}
	
	@Override
	public String toString() {
		return "W";
	}
}
