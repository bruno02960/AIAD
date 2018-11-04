package firefighting.world;

import firefighting.world.utils.*;

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

	public void setPos(int x , int y) {
		this.worldPosition.x = x;
		this.worldPosition.y = y;
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
	int getPosX() {
		return this.worldPosition.x;
	}
	
	/**
	 * Returns the coordinate Y of the point that represents the position of the world's object.
	 * 
	 * @return the coordinate Y of the point that represents the position of the world's object
	 */
	int getPosY() {
		return this.worldPosition.y;
	}

	/**
	 * Returns the coordinates of the position of the world's object.
	 * 
	 * @return the coordinates of the position of the world's object
	 */
	public Point getPos() {
		return this.worldPosition;
	}
}
