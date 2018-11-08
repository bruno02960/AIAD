package firefighting.nature;

import java.awt.Point;
import java.util.Random;

import firefighting.utils.Config;
import firefighting.world.*;

/**
 * Class responsible for a Fire.
 */
public class Fire {
	
	// Global Instance Variables:
	/**
	 * World's object of the Fire.
	 */
	private WorldObject worldObject;

	/**
	 * Fire's creation timestamp.
	 */
	private long creationTimestamp;
	
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
	
	private int numSpreads;
	
	private int numIntensityIncreases;
	
	private boolean active;
	
	public boolean attended;
	
	
	// Constructors:
	/**
	 * Constructor #1 of the Fire.
	 * 
	 * Creates a Fire, initialising its ID, its world object, its current and original intensity, and also, its spread probability.
	 * 
	 * @param the Fire's ID
	 * @param the Fire's World Object
	 */
	public Fire(WorldObject worldObject) {
		Random random = new Random();
		
		this.worldObject = worldObject;
		
		this.creationTimestamp = System.currentTimeMillis();
		
		this.currentIntensity = random.nextInt(Config.FIRE_MAX_INITIAL_INTENSITY) + 1;
		this.originalIntensity = currentIntensity;
		
		this.spreadProbability = random.nextFloat();
		
		this.numSpreads = 0;
		this.numIntensityIncreases = 0;
		
		this.active = true;
		this.attended = false;
	}
	
	// Methods:
	/**
	 * Returns the Fire's ID.
	 * 
	 * @return the Fire's ID
	 */

		
	/**
	 * Returns the Fire's World Object.
	 * 
	 * @return the Fire's World Object
	 */
	public WorldObject getWorldObject() {
		return this.worldObject;
	}
	
	/**
	 * Returns the Fire's creation timestamp.
	 * 
	 * @return the Fire's creation timestamp
	 */
	public long getTimestampCreation() {
		return this.creationTimestamp;
	}
	
	/**
	 * Returns the current intensity of the Fire.
	 * 
	 * @return the current intensity of the Fire
	 */
	public int getCurrentIntensity() {
		return this.currentIntensity;
	}
	
	/**
	 * Returns the original intensity of the Fire.
	 * 
	 * @return the original intensity of the Fire
	 */
	public int getOriginalIntensity() {
		return this.originalIntensity;
	}

	/**
	 * Returns the probability of spreading of the Fire.
	 * 
	 * @return the probability of spreading of the Fire
	 */
	public float getSpreadProbability() {
		return this.spreadProbability;
	}

	public int getNumSpreads() {
		return this.numSpreads;
	}
	
	public void increaseNumSpreads() {
		this.numSpreads++;
	}
	
	public int getNumIntensityIncreases() {
		return this.numIntensityIncreases;
	}
	
	public void decreaseIntensity(int decreaseValue) {
		
		this.currentIntensity = this.currentIntensity - decreaseValue;
		
		if(this.currentIntensity == 0) {
			this.active = false;
			this.attended = true;
		}

	}
	
	public void increaseIntensity(int increaseValue) {
		if((this.currentIntensity + increaseValue) > Config.FIRE_MAX_FINAL_INTENSITY) {
			this.currentIntensity += increaseValue;
			this.numIntensityIncreases++;
		}
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public boolean isAttended() {
		return this.attended;
	}
	
	
	//	@Override
	/*public String toString() {
		
		// TODO: Confirmar se Posicao pode ser double???
		Point pos = this.getWorldObject().getPos();
		
		int firePosX = (int) pos.getX();
		int firePosY = (int) pos.getY();
		
		String activeStatus = isActive() ? "Active" : "Not Active/Extinguished";
		String attendedStatus = isAttended() ? "Attended at this moment" : "Not attended yet";
		
		return "Fire:\n - ID: " + this.getID() + ";\n - Position: (" + firePosX + "," + firePosY + ");\n - Current Intensity: " + this.getCurrentIntensity() + ";\n - Spread Probability: " + (this.getSpreadProbability() * 100) + "%;\n - Active Status: " + activeStatus + ";\n - Attended Status: " + attendedStatus + "\n";
	}*/
	
	@Override
	public String toString() {
		return "F" + this.currentIntensity;
	}
}