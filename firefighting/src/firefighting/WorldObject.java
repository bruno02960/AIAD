package firefighting;

public class WorldObject {
	
	// Global Instances/Variables
	private WorldObjectType objectType;
	private int[] matrixPosition;
	
	public WorldObject(WorldObjectType worldObjectType, int posX, int posY) {
		
		this.matrixPosition = new int[2];
		
		switch(worldObjectType) {
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
	
	
	
}
