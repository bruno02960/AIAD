/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */

package firefighting.nature;

import java.util.Random;

import firefighting.utils.Config;
import firefighting.world.*;

/**
 * Class responsible for a Fire.
 */
public class Fire {
	
	// Global Instance Variables:
	
	/**
	 * The ID of the fire.
	 */
	private byte id;
	
	/**
	 * The world object of the fire.
	 */
	private WorldObject worldObject;

	/**
	 * The fire's creation timestamp.
	 */
	private long creationTimestamp;
	
	/**
	 * The current intensity of the fire.
	 */
	private int currentIntensity;
	
	/**
	 * The original intensity of the fire.
	 */
	private int originalIntensity;
	
	/**
	 * The probability of the fire spreading.
	 */
	private float spreadProbability;
	
	/**
	 * The number of spreads of the fire.
	 */
	private int numSpreads;
	
	/**
	 * The number of times that the fire's intensity increases.
	 */
	private int numIntensityIncreases;
	
	/**
	 * The number of times that the fire's intensity decreases.
	 */
	private int numIntensityDecreases;
	
	/*
	 * The boolean value to keep the information that allows to know if the fire is currently active or not.
	 */
	private boolean active;
	
	/*
	 * The boolean value to keep the information that allows to know if the fire is currently being attended by some aircraft or not.
	 */
	private boolean attended;
	
	
	
	// Constructors:
	
	/**
	 * Constructor #1 of the Fire.
	 * 
	 * Creates a fire, initialising its ID, its world object, its current and original intensity, and also, its spread probability.
	 * 
	 * @param id the ID of the fire
	 * @param worldObject the world object of the fire
	 */
	public Fire(byte id, WorldObject worldObject) {
		Random random = new Random();
		
		this.id = id;
		this.worldObject = worldObject;
		
		this.creationTimestamp = System.currentTimeMillis();
		
		this.currentIntensity = random.nextInt(Config.FIRE_MAX_INITIAL_INTENSITY) + 1;
		this.originalIntensity = currentIntensity;
		
		this.spreadProbability = random.nextFloat();
		
		this.numSpreads = 0;
		this.numIntensityIncreases = 0;
		this.numIntensityDecreases = 0;
		
		this.active = true;
		this.attended = false;
	}
	
	
	// Methods:
	/**
	 * Returns the ID of the fire.
	 * 
	 * @return the ID of the fire
	 */
	public byte getID() {
		return this.id;
	}
		
	/**
	 * Returns the world object of the fire.
	 * 
	 * @return the world object of the fire
	 */
	public WorldObject getWorldObject() {
		return this.worldObject;
	}
	
	/**
	 * Returns the fire's creation timestamp.
	 * 
	 * @return the Fire's creation timestamp
	 */
	public long getTimestampCreation() {
		return this.creationTimestamp;
	}	
	
	/**
	 * Returns how long this fire is being active.
	 * 
	 * @return how long this fire is being active
	 */
	public long howLongIsActive() {
		if(this.active)
			return System.currentTimeMillis() - this.getTimestampCreation();
		else
			return -1;
	}
	
	/**
	 * Returns the current intensity of the fire.
	 * 
	 * @return the current intensity of the fire
	 */
	public int getCurrentIntensity() {
		return this.currentIntensity;
	}
	
	/**
	 * Returns the original intensity of the fire.
	 * 
	 * @return the original intensity of the fire
	 */
	public int getOriginalIntensity() {
		return this.originalIntensity;
	}

	/**
	 * Returns the probability of spreading of the fire.
	 * 
	 * @return the probability of spreading of the fire
	 */
	public float getSpreadProbability() {
		return this.spreadProbability;
	}

	/**
	 * The number of spreads of the fire.
	 * 
	 * @return the number of spreads of the fire
	 */
	public int getNumSpreads() {
		return this.numSpreads;
	}
	
	/**
	 * Increases the number of spreads of the fire.
	 */
	public void increaseNumSpreads() {
		this.numSpreads++;
	}
	
	/**
	 * Returns the number of times that the fire's intensity increases. 
	 * 
	 * @return the number of times that the fire's intensity increases
	 */
	public int getNumIntensityIncreases() {
		return this.numIntensityIncreases;
	}
	
	/**
	 * Increases the fire's intensity, given an increasing value.
	 * 
	 * @param incValue a given increasing value
	 */
	public void increaseIntensity(int incValue) {
		if((this.currentIntensity + incValue) > Config.FIRE_MAX_FINAL_INTENSITY && this.active) {
			this.currentIntensity += incValue;
			this.numIntensityIncreases++;
		}
	}
	
	/**
	 * Increases the fire's intensity by one.
	 */
	public void increaseIntensity() {
		if((this.currentIntensity + 1) > Config.FIRE_MAX_FINAL_INTENSITY && this.active) {
			this.currentIntensity++;
			this.numIntensityIncreases++;
		}
	}
	
	/**
	 * Returns the number of times that the fire's intensity increases. 
	 * 
	 * @return the number of times that the fire's intensity increases
	 */
	public int getNumIntensityDecreases() {
		return this.numIntensityDecreases;
	}
	
	/**
	 * Decreases the fire's intensity, given an decreasing value.
	 * 
	 * @param decValue a given decreasing value
	 */
	public void decreaseIntensity(int decValue) {
		if((this.currentIntensity - decValue) > 0 && this.active)
			this.currentIntensity -= decValue;
		
		if((this.currentIntensity - decValue) <= 0 && this.active)
			this.currentIntensity = 0;
		
		if(this.currentIntensity == 0)
			this.active = false;
	}
	
	/**
	 * Decreases the fire's intensity by one.
	 */
	public void decreaseIntensity() {
		if(this.currentIntensity > 0 && this.active)
			this.currentIntensity--;
		
		if(this.currentIntensity == 0)
			this.active = false;
	}
	
	/**
	 * Returns the boolean value to keep the information that allows to know if the fire is currently active or not.
	 * 
	 * @return the boolean value to keep the information that allows to know if the fire is currently active or not
	 */
	public boolean isActive() {
		return this.active;
	}
	
	/**
	 * Extinguishes this fire, setting its active state as false.
	 */
	public void extinguished() {
		this.active = false;
	}
	
	/**
	 * Returns the boolean value to keep the information that allows to know if the fire is currently being attended by some aircraft or not.
	 * 
	 * @return the boolean value to keep the information that allows to know if the fire is currently being attended by some aircraft or not
	 */
	public boolean isAttended() {
		return this.attended;
	}
	
	/**
	 * Sets the attended status value true or false, given a boolean to keep the information that
	 * allows to know if the fire is currently being attended by some aircraft or not.
	 * 
	 * @param attendedValue a given a boolean to keep the information that
	 * 						allows to know if the fire is currently being attended by some aircraft or not
	 */
	public void setAttended(boolean attendedValue) {
		this.attended = attendedValue;
	}
	
	/**
	 * Returns a basic information about the fire, most specifically its intensity as a string.
	 */
	@Override
	public String toString() {
		return "Fire [" + this.currentIntensity + "]";
	}
}