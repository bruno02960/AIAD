package firefighting;

import java.util.Random;

/**
 * Class responsible for a Fire.
 */
public class Fire {
	
	// Global Instance Variables:
	/**
	 * ID of the Fire.
	 */
	private byte id;
		
	/**
	 * World's object of the Fire.
	 */
	private WorldObject worldObject;

	/**
	 * Current intensity of the Fire.
	 */
	private int currentIntensity;
	
	/**
	 * Original intensity of the Fire.
	 */
	private int originalIntensity;
	
	/**
	 * Probability of Fire spreading.
	 */
	private float spreadProbability;
	
	
	// Constructors:
	/**
	 * Constructor #1 of the Fire.
	 * 
	 * Creates a Fire, initialising its ID, its world object, its current and original intensity, and also, its spread probability.
	 * 
	 * @param the Fire's ID
	 * @param the Fire's World Object
	 */
	public Fire(byte id, WorldObject worldObject) {
		Random random = new Random();
		
		this.id = id;
		this.worldObject = worldObject;
		
		this.currentIntensity = random.nextInt(Config.FIRE_MAX_INTENSITY) + 1;
		this.originalIntensity = currentIntensity;
		
		this.spreadProbability = random.nextFloat();
	}
	
	// Methods:
	/**
	 * Returns the Fire's ID.
	 * 
	 * @return the Fire's ID
	 */
	private byte getID() {
		return this.id;
	}
		
	/**
	 * Returns the Fire's World Object.
	 * 
	 * @return the Fire's World Object
	 */
	private WorldObject getWorldObject() {
		return this.worldObject;
	}
	
	/**
	 * Returns the current intensity of the Fire.
	 * 
	 * @return the current intensity of the Fire
	 */
	private int getCurrentIntensity() {
		return this.currentIntensity;
	}
	
	/**
	 * Returns the original intensity of the Fire.
	 * 
	 * @return the original intensity of the Fire
	 */
	private int getOriginalIntensity() {
		return this.originalIntensity;
	}

	/**
	 * Returns the probability of spreading of the Fire.
	 * 
	 * @return the probability of spreading of the Fire
	 */
	private float getSpreadProbability() {
		return this.spreadProbability;
	}

	@Override
	public String toString() {
		return "f" + this.currentIntensity;
	}
}