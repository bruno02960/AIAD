package firefighting.world.utils;

public enum WorldObjectType {
	FIRE_STATION((byte) 0, "Fire Station"),
	WATER_RESOURCE((byte) 1, "Water Resource"),
	AIRCRAFT((byte) 2, "Aircraft"),
	FIRE((byte) 3, "Fire");

    private final byte id;
    private final String name;

    private WorldObjectType(byte id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public byte getID() {
    	return this.id;
    }

	public String getName() {
		return name;
	}
}