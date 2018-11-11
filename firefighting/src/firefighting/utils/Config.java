/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */
package firefighting.utils;

/**
 * Program configuration class with some predefined parameters/constants.
 */
public abstract class Config {
	
	/**
	 * Defines the time of execution of the demo of the global world's behaviour in ms (5 x 60000ms = 5mins).
	 */
	public final static long DEMO_EXECUTION_TIME = (10 * 60000);
	
	/**
	 * Defines the world map/grid width.
	 */
	public final static int GRID_WIDTH = 7;

	/**
	 * Defines the world map/grid height.
	 */
	public final static int GRID_HEIGHT = 8;
	
	/**
	 * Defines the maximum number of filling stations that can exist.
	 */
	public final static int NUM_MAX_WATER_RESOURCES = 4;
	
	/**
	 * Defines the maximum number of aircrafts that can exist.
	 */
	public final static int NUM_MAX_AIRCRAFTS = 4;
	
	/**
	 * Defines the maximum number of fires that can occur.
	 */
	public final static int NUM_MAX_FIRES = 8;
	
	/**
	 * Defines the maximum water tank's capacity of an aircraft agent.
	 */
	public final static int AIRCRAFT_MAX_WATER_TANK_CAPACITY = 6;
	
	/**
	 * Defines the initial maximum fuel tank's capacity of an aircraft agent.
	 */
	public final static int AIRCRAFT_MAX_INITIAL_FUEL_TANK_CAPACITY = (int) Math.round((2 * (Math.sqrt( Math.pow(GRID_WIDTH, 2) + Math.pow(GRID_HEIGHT, 2) ))));
	
	/**
	 * Defines the final maximum fuel tank's capacity of an aircraft agent.
	 */
	public final static int AIRCRAFT_MAX_FINAL_FUEL_TANK_CAPACITY = (GRID_WIDTH * GRID_HEIGHT) / 2;
	
	/**
	 * Defines the fuel tank's capacity factor of an aircraft agent to perform a secure travel.
	 */
	public final static int AIRCRAFT_FUEL_TANK_CAPACITY_SECURE_TRAVEL_FACTOR = 4;
	
	/**
	 * Defines the minimum capacity of a water resource.
	 */
	public final static int WATER_RESOURCE_INITIAL_MIN_CAPACITY = 20;
	
	/**
	 * Defines the maximum capacity of a water resource.
	 */
	public final static int WATER_RESOURCE_INITIAL_MAX_CAPACITY = 50;
	
	/**
	 * Defines the total number of seasons.
	 */
	public final static int NUM_SEASONS = 4;
	
	/**
	 * Defines the factor value of water provided by rain to the water resources in spring season,
	 * to calculate the amount of rain.
	 */
	public final static int RAIN_FACTOR_SPRING = 2;
	
	/**
	 * Defines the factor value of water provided by rain to the water resources in summer season,
	 * to calculate the amount of rain.
	 */
	public final static int RAIN_FACTOR_SUMMER = 1;
	
	/**
	 * Defines the factor value of water provided by rain to the water resources in autumn season,
	 * to calculate the amount of rain.
	 */
	public final static int RAIN_FACTOR_AUTUMN = 2;
	
	/**
	 * Defines the factor value of water provided by rain to the water resources in winter season,
	 * to calculate the amount of rain.
	 */
	public final static int RAIN_FACTOR_WINTER = 3;
	
	/**
	 * Defines the total number of types of wind.
	 */
	public final static int NUM_TYPE_WINDS = 4;
	
	/**
	 * Defines the penalty time that will affect the movement time of all the aircraft agents,
	 * in environments that have no wind.
	 */
	public final static long AIRCRAFT_MOVEMENT_PENALTY_TIME_NO_WIND = 0L;

	/**
	 * Defines the penalty time that will affect the movement time of all the aircraft agents,
	 * in environments that have weak wind.
	 */
	public final static long AIRCRAFT_MOVEMENT_PENALTY_TIME_WEAK_WIND = 100;

	/**
	 * Defines the penalty time that will affect the movement time of all the aircraft agents,
	 * in environments that have normal wind.
	 */
	public final static long AIRCRAFT_MOVEMENT_PENALTY_TIME_NORMAL_WIND = 250;

	/**
	 * Defines the penalty time that will affect the movement time of all the aircraft agents,
	 * in environments that have strong wind.
	 */
	public final static long AIRCRAFT_MOVEMENT_PENALTY_TIME_STRONG_WIND = 600;

	/**
	 * Defines the penalty value of water resources, during drought situations, when it happens.
	 */
	public final static int DROUGHT_OCCURRENCE_PENALTY = 2;
	
	/**
	 * Defines the maximum initial intensity of a fire.
	 */
	public final static int FIRE_MAX_INITIAL_INTENSITY = 6;
	
	/**
	 * Defines the maximum final intensity of a fire.
	 */
	public final static int FIRE_MAX_FINAL_INTENSITY = 10;
	
	/**
	 * Defines the a factor value to be used to calculate a new timeout, to verifies if the fire increases its intensity or not,
	 * verifying if the current time it's greater than the value of timeout for each number of intensity increases
	 * (30 seconds of timeout for each number of intensity increases).
	 */
	public final static long FIRE_ACTIVE_FACTOR_TIMEOUT = 30000;
	
	/**
	 * Defines the maximum fire's intensity penalty, for every time that it's active for more time than the current timeout
	 * (30 seconds of timeout for each number of intensity increases)
	 */
	public final static int FIRE_ACTIVE_INTENSITY_MAX_PENALTY = 4;
}