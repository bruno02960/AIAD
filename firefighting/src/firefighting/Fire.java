package firefighting;

import java.util.Random;

/**
 * Class responsible for fires
 */
public class Fire {
	/**
	 * Probability of fire spreading
	 */
	private float spread_probability;
	
	/**
	 * Original intensity of the fire
	 */
	private int original_intensity;
	
	/**
	 * Current intensity of the fire
	 */
	private int current_intensity;
	
	/**
	 * Creates fire object, initializing its intensity
	 */
	private Fire() {
		Random rand = new Random();
		
		current_intensity = rand.nextInt(Config.FIRE_MAX_INTENSITY) + 1;
		original_intensity = current_intensity;
		spread_probability = rand.nextFloat();
	}
}
