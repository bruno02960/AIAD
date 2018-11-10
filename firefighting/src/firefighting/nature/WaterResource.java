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
 * Class responsible for a Water Resource.
 */
public class WaterResource {
	
	// Global Instance Variables:
	
	/**
	 * The ID of the water resource.
	 */
	private byte id;
	
	/**
	 * The world object of the water resource.
	 */
	private WorldObject worldObject;
	
	/**
	 * The current water quantity of the water resource.
	 */
	private int waterQuantity;
	
	
	
	// Constructors:
	
	/**
	 * Constructor #1 of the Water Resource.
	 * 
	 * Creates a new Water Resource, initialising its ID and its world object.
	 * 
	 * @param the ID of the water resource
	 * @param the world object of the water resource
	 */
	public WaterResource(byte id, WorldObject worldObject) {
		Random random = new Random();
		
		this.id = id;
		this.worldObject = worldObject;
		this.waterQuantity = random.ints(1, Config.WATER_RESOURCE_INITIAL_MIN_CAPACITY, (Config.WATER_RESOURCE_INITIAL_MAX_CAPACITY + 1)).toArray()[0];
	}

	
	
	// Methods:
	
	/**
	 * Returns the ID of the water resource.
	 * 
	 * @return the ID of the water resource
	 */
	public byte getID() {
		return this.id;
	}
	
	/**
	 * Return the world object of the water resource.
	 * 
	 * @return the the world object of the water resource
	 */
	public WorldObject getWorldObject() {
		return this.worldObject;
	}
	
	/**
	 * Returns the current water quantity of the water resource.
	 * 
	 * @return the current water quantity of the water resource
	 */
	public int getWaterQuantity() {
		return this.waterQuantity;
	}
	
	/**
	 * Returns true if the water resource have no current water quantity, or false, otherwise.
	 * 
	 * @return true if the water resource have no current water quantity, or false, otherwise
	 */
	public boolean isEmpty() {
		return this.waterQuantity == 0;
	}
	
	/**
	 * Increases the water resource's water quantity, given an increasing value.
	 * 
	 * @param incValue a given increasing value
	 */
	public void increaseQuantity(int incValue) {
		this.waterQuantity += incValue;
	}
	
	/**
	 * Increases the water resource's water quantity by one.
	 */
	public void increaseQuantity() {
		this.waterQuantity++;
	}
	
	/**
	 * Decreases the water resource's water quantity, given an decreasing value.
	 * 
	 * @param decValue a given decreasing value
	 */
	public void decreaseQuantity(int decValue) {
		if((this.waterQuantity - decValue) > 0 && !this.isEmpty())
			this.waterQuantity -= decValue;		
	}

	/**
	 * Decreases the water resource's water quantity by one.
	 */
	public void decreaseQuantity() {
		if(this.waterQuantity > 0 && !this.isEmpty())
			this.waterQuantity--;		
	}
	
	/**
	 * Returns a basic information about the water resource, most specifically its water quantity as a string.
	 */
	@Override
	public String toString() {
		return "Water Resource [" + this.waterQuantity + "]";
	}
}
