package firefighting.world.utils;

import firefighting.utils.Config;

public enum SeasonType {
	
	SPRING((byte) 0, "Spring", Config.RAIN_SPRING),
	SUMMER((byte) 1, "Summer", Config.RAIN_SUMMER),
	AUTUMN((byte) 2, "Autumn", Config.RAIN_AUTUMN),
	WINTER((byte) 3, "Winter", Config.RAIN_WINTER);

    private final byte id;
    private final String name;
    private final int amountRain;

    private SeasonType(byte id, String name, int amountRain) {
        this.id = id;
        this.name = name;
        this.amountRain = amountRain;
    }
    
    public byte getID() {
    	return this.id;
    }

	public String getName() {
		return this.name;
	}
	
	public int getAmountRain() {
		return this.amountRain;
	}
}
