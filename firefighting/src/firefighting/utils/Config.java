package firefighting;

/**
 * Program configuration class.
 */
public abstract class Config {
	
	/**
	 * Defines the grid width.
	 */
	final static int GRID_WIDTH = 7;

	/**
	 * Defines the grid height.
	 */
	final static int GRID_HEIGHT = 8;
	
	/**
	 * Defines the maximum number of filling stations that can exist.
	 */
	final static int NUM_MAX_FILLING_STATIONS = 8;
	
	/**
	 * Defines the maximum number of aircrafts that can exist.
	 */
	final static int NUM_MAX_AIRCRAFTS = 3;
	
	/**
	 * Defines the maximum number of fires that can occur.
	 */
	final static int NUM_MAX_FIRES = 8;
	
	/**
	 * Defines the maximum capacity of an aircraft agent's tank.
	 */
	final static int AIRCRAFT_MAX_TANK_CAPACITY = 4;
	
	/**
	 * Defines the maximum capacity of a filling station.
	 */
	final static int FILLINGSTATION_MAX_CAPACITY = 4;
	
	/**
	 * Defines the maximum intensity of a fire.
	 */
	final static int FIRE_MAX_INTENSITY = 9;
}