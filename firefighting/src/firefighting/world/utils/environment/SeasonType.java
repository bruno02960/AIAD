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
 * Class responsible for a type of season.
 */
public enum SeasonType {
	
	// Enumeration definition:
	
	/**
	 * Possible enumerations and their parameters.
	 */
	SPRING((byte) 0, "Spring", Config.RAIN_FACTOR_SPRING, false),
	SUMMER((byte) 1, "Summer", Config.RAIN_FACTOR_SUMMER, true),
	AUTUMN((byte) 2, "Autumn", Config.RAIN_FACTOR_AUTUMN, false),
	WINTER((byte) 3, "Winter", Config.RAIN_FACTOR_WINTER, false);
	
	
	
	// Global Instance Variables:
	
	/*
	 * The id of the type of season.
	 */
    private final byte id;
    
    /**
     * The name of the type of season.
     */
    private final String name;
    
    /**
     * The rain factor associated to the type of season.
     */
    private final int rainFactor;

    /**
     * The boolean value that keeps the information about the possibility of 
     * droughts (extreme dry situations) occurrence, associated to the type of season.
     */
    private final boolean occurrenceOfDroughts;
    
    
    
    // Constructors:
    
    /**
     * Constructor #1 of the season type.
     * 
     * @param id the id of the type of season
     * @param name the name of the type of season
     * @param rainFactor
     * @param occurrenceOfDroughts
     */
    private SeasonType(byte id, String name, int rainFactor, boolean occurrenceOfDroughts) {
        this.id = id;
        this.name = name;
        this.rainFactor = rainFactor;
        this.occurrenceOfDroughts = occurrenceOfDroughts;
    }
    
    
    
    // Basic methods:
    
    /**
     * Returns the id of the type of season.
     * 
     * @return the id of the type of season
     */
    public byte getID() {
    	return this.id;
    }

    /**
     * Returns the name of the type of season.
     * 
     * @return the name of the type of season
     */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the rain factor associated to the type of season.
	 * 
	 * @return the rain factor associated to the type of season
	 */
	public int getRainFactor() {
		return this.rainFactor;
	}

	/**
	 * Returns the boolean value that keeps the information about the possibility of 
     * droughts (extreme dry situations) occurrence, associated to the type of season.
	 * 
	 * @return the boolean value that keeps the information about the possibility of 
     * 		   droughts (extreme dry situations) occurrence, associated to the type of season
	 */
	public boolean canOccurDroughts() {
		return this.occurrenceOfDroughts;
	}
}
