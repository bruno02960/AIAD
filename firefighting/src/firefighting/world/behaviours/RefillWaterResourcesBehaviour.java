package firefighting.world.behaviours;

import firefighting.nature.WaterResource;
import firefighting.world.WorldAgent;
import jade.core.behaviours.TickerBehaviour;

public class RefillWaterResourcesBehaviour extends TickerBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	WorldAgent worldAgent;
	
	public RefillWaterResourcesBehaviour(WorldAgent worldAgent, long period) {
		super(worldAgent, period);
		
		this.worldAgent = worldAgent;
	}
	
	public WorldAgent getWorldAgent() {
		return this.worldAgent;
	}
	
	public WaterResource[] getCurrentFires() {
		return this.getWorldAgent().getWaterResourses();
	}

	@Override
	protected void onTick() {
		WorldAgent worldAgent = this.getWorldAgent();
		WaterResource[] waterResources = worldAgent.getWaterResourses();
		
		//for(int i = 0; i < waterResources.length; i++) {
			//if(waterResources[i].)
		//}
		
	}
	
}
