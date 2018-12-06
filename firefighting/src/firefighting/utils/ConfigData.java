package firefighting.utils;

public class ConfigData {
	/**
	 * Defines the world map/grid width.
	 */
	public int GRID_WIDTH;
	/**
	 * Defines the world map/grid height.
	 */
	public int GRID_HEIGHT;
	/**
	 * Defines the maximum number of filling stations that can exist.
	 */
	public int NUM_MAX_WATER_RESOURCES;
	/**
	 * Defines the maximum number of aircrafts that can exist.
	 */
	public int NUM_MAX_AIRCRAFTS;
	/**
	 * Defines the maximum number of fires that can occur.
	 */
	public int NUM_MAX_FIRES;

	public ConfigData(int gRID_WIDTH, int gRID_HEIGHT, int nUM_MAX_WATER_RESOURCES, int nUM_MAX_AIRCRAFTS,
			int nUM_MAX_FIRES) {
		GRID_WIDTH = gRID_WIDTH;
		GRID_HEIGHT = gRID_HEIGHT;
		NUM_MAX_WATER_RESOURCES = nUM_MAX_WATER_RESOURCES;
		NUM_MAX_AIRCRAFTS = nUM_MAX_AIRCRAFTS;
		NUM_MAX_FIRES = nUM_MAX_FIRES;
	}
}