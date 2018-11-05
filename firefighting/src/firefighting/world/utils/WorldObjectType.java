/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */

package firefighting.world.utils;

/**
 * Class responsible for the type of a world object.
 */
public enum WorldObjectType {
	
	// Enumeration definition:
	
	/**
	 * Possible enumerations and their parameters.
	 */
	FIRE_STATION((byte) 0, "Fire Station"),
	WATER_RESOURCE((byte) 1, "Water Resource"),
	AIRCRAFT((byte) 2, "Aircraft"),
	FIRE((byte) 3, "Fire");
	
	
	
	// Global Instance Variables:
	
	/**
	 * The id of the type of world object type.
	 */
    private final byte id;
    
    /**
     * The name of the type of world object type.
     */
    private final String name;
    
    
    
    // Constructors:
    
    /**
     * Constructor #1 of the world object type.
     * 
     * @param id the id of the type of world object type
     * @param name the name of the type of world object type
     */
    private WorldObjectType(byte id, String name) {
        this.id = id;
        this.name = name;
    }
    
    
    
    // Basic methods:
    
    /**
     * Returns the id of the type of world object type.
     * 
     * @return the id of the type of world object type
     */
    public byte getID() {
    	return this.id;
    }

    /**
     * Returns the name of the type of world object type.
     * 
     * @return the name of the type of world object type
     */
	public String getName() {
		return name;
	}
}