package firefighting.world.behaviours;

import java.util.Random;

import firefighting.nature.Fire;
import firefighting.utils.Config;
import firefighting.world.WorldAgent;
import jade.core.behaviours.TickerBehaviour;

public class IncreaseActiveFiresIntensityBehaviour extends TickerBehaviour {
	
	public WorldAgent worldAgent;
	
	public IncreaseActiveFiresIntensityBehaviour(WorldAgent worldAgent, long period) {
		super(worldAgent, period);

		this.worldAgent = worldAgent;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorldAgent getWorldAgent() {
		return this.worldAgent;
	}
	
	public Fire[] getCurrentFires() {
		return this.getWorldAgent().getCurrentFires();
	}
	
	@Override
	protected void onTick() {
		
		Fire[] fires = this.getCurrentFires();
		
		for(int f = 0; f < fires.length; f++) {
			Fire fire = fires[f];
			
			if(fire != null) {
				long timeoutFireIntensityIncrease = (fire.getNumIntensityIncreases() + 1) * Config.FIRE_ACTIVE_FACTOR_TIMEOUT;
				
				long activeFireTime = System.currentTimeMillis() - fire.getTimestampCreation();
				
				if(activeFireTime > timeoutFireIntensityIncrease) {
					Random randomObject = new Random();
					
					int intensityPenalty = randomObject.nextInt(Config.FIRE_ACTIVE_INTENSITY_MAX_PENALTY);
					
					fire.increaseIntensity(intensityPenalty);	
				}
			}
		}
	}
}