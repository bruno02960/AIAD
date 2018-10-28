package firefighting;

import java.util.Random;

/**
 * Class responsible for a Filling Station.
 */
public class FillingStation {
	
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
	private int currentStatus;
	
	// Constructors:
	/**
	 * Constructor #1 of the Filling Station.
	 * 
	 * Creates a new Filling Station, initialising its ID and its world's object.
	 * 
	 * @param the Filling Station's ID
	 * @param the Filling Station's World Object
	 */
	public FillingStation(byte id, WorldObject worldObject) {
		Random random = new Random();
		
		this.id = id;
		this.worldObject = worldObject;
		this.currentStatus = random.nextInt(Config.FILLINGSTATION_MAX_CAPACITY) + 1;
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
	private WorldObject getWorldObject() {
		return this.worldObject;
	}

	@Override
	public String toString() {
		return "F" + this.currentStatus;
	}
}
