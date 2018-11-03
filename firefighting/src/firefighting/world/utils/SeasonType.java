package firefighting.world.utils;

import firefighting.utils.Config;

public enum SeasonType {
	
	SPRING((byte) 0, "Spring", Config.RAIN_FACTOR_SPRING),
	SUMMER((byte) 1, "Summer", Config.RAIN_FACTOR_SUMMER),
	AUTUMN((byte) 2, "Autumn", Config.RAIN_FACTOR_AUTUMN),
	WINTER((byte) 3, "Winter", Config.RAIN_FACTOR_WINTER);

    private final byte id;
    private final String name;
    private final int rainFactor;

    private SeasonType(byte id, String name, int rainFactor) {
        this.id = id;
        this.name = name;
        this.rainFactor = rainFactor;
    }
    
    public byte getID() {
    	return this.id;
    }

	public String getName() {
		return this.name;
	}
	
	public int getRainFactor() {
		return this.rainFactor;
	}
}
