package firefighting.world.utils;

public enum WindType {
	
	VERY_WINDY((byte) 0, "Very Windy"),
	WINDY((byte) 1, "Windy"),
	NO_WIND((byte) 2, "No Wind");

    private final byte id;
    private final String name;

    private WindType(byte id, String name) {
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
