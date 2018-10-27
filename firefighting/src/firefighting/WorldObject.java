package firefighting;

import java.awt.Point;

/**
 * Class responsible for a World's Object and its behaviour.
 */
public class WorldObject {
	
	// Global Instance Variables:
	/**
	 * The type of the world's object.
	 */
	private WorldObjectType worldObjectType;
	
	/**
	 * The point that represents the position of the world's object.
	 */
	private Point worldPosition;
	
	
	// Constructors:
	/**
	 * Constructor #1 of the World's Object.
	 * 
	 * Creates a new World's Object, initialising its type and its position.
	 * 
	 * @param worldObjectType the type of the world's object
	 * @param position the point that represents the position of the world's object
	 */
	public WorldObject(WorldObjectType worldObjectType, Point position) {
		this.worldObjectType = worldObjectType;
		this.worldPosition = position;
	}
	
	
	// Methods:
	/**
	 * Returns the type of the world's object.
	 * 
	 * @return the type of the world's object
	 */
	private WorldObjectType getWorldObjectType() {
		return this.worldObjectType;
	}
	
	/**
	 * Returns the coordinate X of the point that represents the position of the world's object.
	 * 
	 * @return the coordinate X of the point that represents the position of the world's object
	 */
	private int getPosX() {
		return this.worldPosition.x;
	}
	
	/**
	 * Returns the coordinate Y of the point that represents the position of the world's object.
	 * 
	 * @return the coordinate Y of the point that represents the position of the world's object
	 */
	private int getPosY() {
		return this.worldPosition.y;
	}
}
