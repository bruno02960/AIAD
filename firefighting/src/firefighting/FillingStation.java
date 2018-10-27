package firefighting;

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
		this.id = id;
		this.worldObject = worldObject;
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
}
