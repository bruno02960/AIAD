package firefighting.world.behaviours;

import firefighting.nature.WaterResource;
import firefighting.utils.Config;
import firefighting.world.WorldAgent;
import jade.core.behaviours.TickerBehaviour;


public class DroughtSituationBehaviour extends TickerBehaviour {
	
	public WorldAgent worldAgent;
	
	public DroughtSituationBehaviour(WorldAgent worldAgent, long period) {
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
				
		// Rain behaviour about all the water resources in the world
		WaterResource[] waterResources = worldAgent.getWaterResources();
/*
		// Drought situation, affecting all the water resources in the world, decreasing its capacity
		for(int wr = 0; wr < waterResources.length; wr++)
			waterResources[wr].decreasingCapacity(Config.DROUGHT_SITUATION_PENALTY);*/
	}
}