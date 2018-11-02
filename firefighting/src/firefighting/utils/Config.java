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
	public final static int NUM_MAX_WATER_RESOURCES = 8;
	
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
	 * Defines the maximum capacity of a water resource.
	 */
	public final static int WATER_RESOURCE_MAX_CAPACITY = 8;
	
	/**
	 * Defines the total number of seasons.
	 */
	public final static int NUM_SEASONS = 4;
	
	/**
	 * Defines the amount of water provided by rain to the water resources in spring season.
	 */
	public final static int RAIN_SPRING = 2;
	
	/**
	 * Defines the amount of water provided by rain to the water resources in summer season.
	 */
	public final static int RAIN_SUMMER = 1;
	
	/**
	 * Defines the amount of water provided by rain to the water resources in autumn season.
	 */
	public final static int RAIN_AUTUMN = 2;
	
	/**
	 * Defines the amount of water provided by rain to the water resources in winter season.
	 */
	public final static int RAIN_WINTER = 4;
	
	/**
	 * Defines the total number of types of wind.
	 */
	public final static int NUM_TYPE_WINDS = 3;
	
	/**
	 * Defines the maximum intensity of a fire.
	 */
	public final static int FIRE_MAX_INTENSITY = 9;
}