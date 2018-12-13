package firefighting.utils;

import java.util.HashMap;
import java.util.Map;

import firefighting.world.WorldAgent;
import jade.core.behaviours.TickerBehaviour;

public class CalculateSimultaneousFiresPerSecond extends TickerBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorldAgent worldAgent;
	
	public int indexMetrics;
	
	public CalculateSimultaneousFiresPerSecond(WorldAgent worldAgent, long period) {
		super(worldAgent, period);
		
		this.worldAgent = worldAgent;
		this.indexMetrics = 0;
	}
	
	@Override
	protected void onTick() {
		int numFires = worldAgent.getCurrentFires().size();
		this.worldAgent.numFiresPerSecond.put(indexMetrics, numFires);
		
		indexMetrics++;
	}
}