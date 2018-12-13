package firefighting.utils;

import java.util.HashMap;
import java.util.Map;

import firefighting.aircraft.AircraftAgent;
import firefighting.firestation.FireStationAgent;
import firefighting.world.WorldAgent;
import jade.core.behaviours.TickerBehaviour;

public class CalculateOccupedAircraftsPerSecond extends TickerBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorldAgent worldAgent;
	
	public int indexMetrics;
	
	public CalculateOccupedAircraftsPerSecond(WorldAgent worldAgent, long period) {
		super(worldAgent, period);
		
		this.worldAgent = worldAgent;
		this.indexMetrics = 0;
	}
	
	@Override
	protected void onTick() {
		
		int numOccupedAircraftsPerSecond = 0;
		
		AircraftAgent[] aircraftAgents = worldAgent.getAircraftAgents();
		
		for(int i = 0; i < aircraftAgents.length; i++)
			if(aircraftAgents[i].isAttendingFire())
				numOccupedAircraftsPerSecond++;
		
		this.worldAgent.numOccupedAircraftsPerSecond.put(indexMetrics, numOccupedAircraftsPerSecond);
	
		indexMetrics++;
	}
}