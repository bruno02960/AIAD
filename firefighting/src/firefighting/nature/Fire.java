package firefighting.nature;

import java.awt.Point;
import java.util.Random;

import firefighting.utils.Config;
import firefighting.world.*;

/**
 * Class responsible for a Fire.
 */
public class Fire {
	
	// Global Instance Variables:
	/**
	 * ID of the Fire.
	 */
	private byte id;
		
	/**
	 * World's object of the Fire.
	 */
	private WorldObject worldObject;

	private long timestamp;
	
	/**
	 * Current intensity of the Fire.
	 */
	private int currentIntensity;
	
	/**
	 * Original intensity of the Fire.
	 */
	private int originalIntensity;
	
	/**
	 * Probability of Fire spreading.
	 */
	private float spreadProbability;
	
	private int numSpreads;
	
	private boolean active;
	
	private boolean attended;
	
	
	// Constructors:
	/**
	 * Constructor #1 of the Fire.
	 * 
	 * Creates a Fire, initialising its ID, its world object, its current and original intensity, and also, its spread probability.
	 * 
	 * @param the Fire's ID
	 * @param the Fire's World Object
	 */
	public Fire(byte id, WorldObject worldObject) {
		Random random = new Random();
		
		this.id = id;
		this.worldObject = worldObject;
		
		this.timestamp = System.currentTimeMillis();
		
		this.currentIntensity = random.nextInt(Config.FIRE_MAX_INTENSITY) + 1;
		this.originalIntensity = currentIntensity;
		
		this.spreadProbability = random.nextFloat();
		
		this.numSpreads = 0;
		
		this.active = true;
		this.attended = false;
	}
	
	// Methods:
	/**
	 * Returns the Fire's ID.
	 * 
	 * @return the Fire's ID
	 */
	public byte getID() {
		return this.id;
	}
		
	/**
	 * Returns the Fire's World Object.
	 * 
	 * @return the Fire's World Object
	 */
	public WorldObject getWorldObject() {
		return this.worldObject;
	}
	
	/**
	 * Returns the current intensity of the Fire.
	 * 
	 * @return the current intensity of the Fire
	 */
	public int getCurrentIntensity() {
		return this.currentIntensity;
	}
	
	/**
	 * Returns the original intensity of the Fire.
	 * 
	 * @return the original intensity of the Fire
	 */
	public int getOriginalIntensity() {
		return this.originalIntensity;
	}

	/**
	 * Returns the probability of spreading of the Fire.
	 * 
	 * @return the probability of spreading of the Fire
	 */
	public float getSpreadProbability() {
		return this.spreadProbability;
	}

	public int getNumSpreads() {
		return this.numSpreads;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public boolean isAttended() {
		return this.attended;
	}
	
	//	@Override
	/*public String toString() {
		
		// TODO: Confirmar se Posicao pode ser double???
		Point pos = this.getWorldObject().getPos();
		
		int firePosX = (int) pos.getX();
		int firePosY = (int) pos.getY();
		
		String activeStatus = isActive() ? "Active" : "Not Active/Extinguished";
		String attendedStatus = isAttended() ? "Attended at this moment" : "Not attended yet";
		
		return "Fire:\n - ID: " + this.getID() + ";\n - Position: (" + firePosX + "," + firePosY + ");\n - Current Intensity: " + this.getCurrentIntensity() + ";\n - Spread Probability: " + (this.getSpreadProbability() * 100) + "%;\n - Active Status: " + activeStatus + ";\n - Attended Status: " + attendedStatus + "\n";
	}*/
	
	@Override
	public String toString() {
		return "F" + this.currentIntensity;
	}
}