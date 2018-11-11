/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */

package firefighting.aircraft;

import firefighting.world.WorldObject;

/**
 * Class responsible for an crashed aircraft.
 */
public class CrashedAircraft {
	
	// Global Instance Variables:
	
	/**
	 * The ID of the aircraft Agent.
	 */
	private byte id;

	/**
	 * The world's object of the aircraft agent.
	 */
	private WorldObject worldObject;	
	
	
	// Constructors:
	
	/**
	 * Constructor #1 of the crashed aircraft.
	 * 
	 * Creates a new crashed aircraft, initialising its ID.
	 * 
	 * @param id the ID of the aircraft agent
	 * @param worldObject the world object of the aircraft agent
	 */
	public CrashedAircraft(byte id, WorldObject worldObject) {
		this.id = id;
		this.worldObject = worldObject;
	}


	
	// Basic methods:
	
	/**
	 * Returns the ID of the crashed aircraft.
	 * 
	 * @return the ID of the crashed aircraft
	 */
	public byte getID() {
		return this.id;
	}
	
	/**
	 * Returns the world object of the crashed aircraft.
	 * 
	 * @return the world object of the crashed aircraft
	 */
	public WorldObject getWorldObject() {
		return this.worldObject;
	}
	
	/**
	 * Returns a basic information about the crashed aircraft as a string.
	 */
	@Override
	public String toString() {
		return "Crashed Aircraft";
	}
}