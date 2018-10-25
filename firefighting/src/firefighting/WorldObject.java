package firefighting;

import java.awt.Point;

public abstract class WorldObject {
	
	// Global Instances/Variables
	private WorldObjectType objectType;
	private WorldAgent worldAgent;
	private Point worldPosition;
	
	public WorldObject(WorldObjectType worldObjectType, Point position) {
		
		this.worldPosition = position;
		
		switch(worldObjectType) {
			case FIRE_STATION: 
				// Create FIRE_STATION code
				break;
			case FILLING_STATION: 
				// Create FILLING_STATION code
				break;
			case AIRCRAFT:
				// Create AIRCRAFT code
				break;
			case FIRE:
				// Create FIRE code
				break;
			default:
				break;
		}
	}
	
	public int getPosX() {
		return this.worldPosition.x;
	}
	
	public int getPosY() {
		return this.worldPosition.y;
	}
}
