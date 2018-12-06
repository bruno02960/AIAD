package firefighting.world.behaviours;

import firefighting.ui.GUI;
import firefighting.utils.Config;
import firefighting.world.WorldAgent;
import jade.core.behaviours.TickerBehaviour;

public class UpdateStatusBehaviour extends TickerBehaviour {

	WorldAgent worldAgent;
	
	public UpdateStatusBehaviour(WorldAgent worldAgent, long period) {
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
	
	public Object[][] getWorldMap() {
		return this.getWorldAgent().getWorldMap();
	}
	
	@Override
	protected void onTick() {
		this.worldAgent.refreshWorldMapPositions();
		
		int extinguishedFires = 0;
		
		for(int i = 0; i < this.worldAgent.getAircraftAgents().length; i++) {
			extinguishedFires+=this.worldAgent.getAircraftAgents()[i].getAircraftMetricsStats().getNumTotalFiresExtinguishedByThisAircraft();
		}
		
		this.worldAgent.getWorldMetricsStats().setNumTotalFiresExtinguishedByAllAircrafts(extinguishedFires);
		
		if(extinguishedFires == 3) {
			this.worldAgent.shutDown();
			return;
		}
		
		if(GUI.isActive()) {
			GUI.fillGrid();
		}
	}
}