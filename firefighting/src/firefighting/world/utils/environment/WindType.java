/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */

package firefighting.world.utils.environment;

import firefighting.utils.Config;

/**
 * Class responsible for a type of wind.
 */
public enum WindType {
	
	// Enumeration definition:
	
	/**
	 * Possible enumerations and their parameters.
	 */
	NO_WIND((byte) 0, "No Wind", Config.AIRCRAFT_MOVEMENT_PENALTY_TIME_NO_WIND),
	WEAK_WIND((byte) 1, "Weak Wind", Config.AIRCRAFT_MOVEMENT_PENALTY_TIME_WEAK_WIND),
	NORMAL_WIND((byte) 2, "Normal Wind", Config.AIRCRAFT_MOVEMENT_PENALTY_TIME_NORMAL_WIND),
	STRONG_WIND((byte) 3, "Strong Wind", Config.AIRCRAFT_MOVEMENT_PENALTY_TIME_STRONG_WIND);
	
	
	
	// Global Instance Variables:
	
	/*
	 * The id of the type of wind.
	 */
    private final byte id;
    
    /**
     * The name of the type of wind.
     */
    private final String name;
    
    /**
     * The penalty time that will affect the movement time of all the aircraft agents,
     * accordingly with the current type of wind assigned to the world.
	 * 
	 * The penalty times associated to each type of wind are the following:
	 * - No Wind - 0ms / 0s
	 * - Weak Wind - 100ms / 0,1 s
	 * - Normal Wind - 250ms / 0,25s
	 * - Strong Wind - 600ms /0,6s
     */
    private final long penaltyAircraftMovementTime;
    
    
    
    // Constructors:
    
    /**
     * Constructor #1 of the wind type.
     * 
     * @param id the id of the type of wind
     * @param name the name of the type of wind
     * @param penaltyAircraftMovementTime the penalty time that will affect the movement time of all the aircraft agents
     */
    private WindType(byte id, String name, long penaltyAircraftMovementTime) {
        this.id = id;
        this.name = name;
        this.penaltyAircraftMovementTime = penaltyAircraftMovementTime;
    }
    
    
    
    // Basic methods:
    
    /**
     * Returns the id of the type of wind.
     * 
     * @return the id of the type of wind
     */
    public byte getID() {
    	return this.id;
    }

    /**
     * Returns the name of the type of wind.
     * 
     * @return the name of the type of wind
     */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the penalty time that will affect the movement time of all the aircraft agents,
	 * accordingly with the current type of wind assigned to the world.
	 * 
	 * The penalty times associated to each type of wind are the following:
	 * - No Wind - 0ms / 0s
	 * - Weak Wind - 100ms / 0,1 s
	 * - Normal Wind - 250ms / 0,25s
	 * - Strong Wind - 600ms /0,6s
	 * 
	 * @return the penalty time that will affect the movement time of all the aircraft agents,
	 * 		   accordingly with the current type of wind assigned to the world
	 */
	public long getPenaltyAircraftMovementTime() {
		return this.penaltyAircraftMovementTime;
	}
}
