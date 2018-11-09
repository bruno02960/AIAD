package firefighting.world.behaviours;

import java.util.ArrayList;
import java.util.Random;

import firefighting.nature.Fire;
import firefighting.nature.WaterResource;
import firefighting.world.WorldAgent;
import firefighting.world.utils.environment.SeasonType;
import jade.core.behaviours.TickerBehaviour;


public class RainingBehaviour extends TickerBehaviour {
	
	WorldAgent worldAgent;
	
	public RainingBehaviour(WorldAgent worldAgent, long period) {
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
	
	@Override
	protected void onTick() {
		
		WorldAgent worldAgent = this.getWorldAgent();
		
		SeasonType seasonType = worldAgent.getSeasonType();
		byte seasonTypeID = seasonType.getID();
		int seasonRainFactor = seasonType.getRainFactor();
		
		Random randomObject = new Random();
		
		double rainRatio;
		int finalRainAmount;
		
		// Calculate a random ratio of increasing of rain amount, accordingly with the current season's rain factor
		switch(seasonTypeID) {
			// SPRING SEASON
			case 0:
				// Normal amounts of rain ([16% , 50%] of precipitation)
				rainRatio = (randomObject.ints(1, 16, 51).toArray()[0]) / 100;
				break;
			// SUMMER SEASON
			case 1:
				// Small amounts of rain ([0% , 15%] of precipitation)
				rainRatio = (randomObject.ints(1, 0, 16).toArray()[0]) / 100;
				break;
			// AUTUMN SEASON
			case 2:
				// Normal amounts of rain ([16% , 50%] of precipitation)
				rainRatio = (randomObject.ints(1, 16, 51).toArray()[0]) / 100;
				break;
			// WINTER SEASON
			case 3:
				// Big amounts of rain ([51% , 100%] of precipitation)
				rainRatio = (randomObject.ints(1, 51, 101).toArray()[0]) / 100;
				break;
			default:
				rainRatio = 0.0;
				break;
		}
		
		finalRainAmount = (int) Math.round(seasonRainFactor * rainRatio);
		
		
		// Rain behaviour about the current active fires in the world
		ArrayList<Fire> fires = worldAgent.getCurrentFires();
				
		// Raining above the fires, decreasing its intensity
		for(int f = 0; f < fires.size(); f++) {
			
				fires.get(f).decreaseIntensity(finalRainAmount);
				
				// Fire extinguished
				if(!fires.get(f).isActive()) {
					fires.remove(f);
				}
			
		}
		
		
		// Rain behaviour about all the water resources in the world
		WaterResource[] waterResources = worldAgent.getWaterResources();

		// Raining above the water resources, increasing its capacity
		for(int wr = 0; wr < waterResources.length; wr++)
			waterResources[wr].increasingCapacity(finalRainAmount);
	}
}