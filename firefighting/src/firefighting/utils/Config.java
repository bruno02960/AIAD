package firefighting.utils;

/**
 * Program configuration class.
 */
public abstract class Config {
	
	/**
	 * Defines the grid width.
	 */
	public final static int GRID_WIDTH = 7;

	/**
	 * Defines the grid height.
	 */
	public final static int GRID_HEIGHT = 8;
	
	/**
	 * Defines the maximum number of filling stations that can exist.
	 */
	public final static int NUM_MAX_FILLING_STATIONS = 8;
	
	/**
	 * Defines the maximum number of aircrafts that can exist.
	 */
	public final static int NUM_MAX_AIRCRAFTS = 3;
	
	/**
	 * Defines the maximum number of fires that can occur.
	 */
	public final static int NUM_MAX_FIRES = 8;
	
	/**
	 * Defines the maximum capacity of an aircraft agent's tank.
	 */
	public final static int AIRCRAFT_MAX_TANK_CAPACITY = 4;
	
	/**
	 * Defines the maximum capacity of a filling station.
	 */
	public final static int FILLINGSTATION_MAX_CAPACITY = 4;
	
	/**
	 * Defines the maximum intensity of a fire.
	 */
	public final static int FIRE_MAX_INTENSITY = 9;
	/**
	 * Milliseconds between aircraft moves.
	 */
	public final static int MS_BETWEEN_AIRCRAFT_MOVES = 1000;
}