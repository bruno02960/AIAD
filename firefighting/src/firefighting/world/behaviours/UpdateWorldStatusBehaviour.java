package firefighting.world.behaviours;

import firefighting.graphics.GraphicUserInterface;
import firefighting.world.WorldAgent;
import jade.core.behaviours.TickerBehaviour;

public class UpdateWorldStatusBehaviour extends TickerBehaviour {

	WorldAgent worldAgent;
	
	public UpdateWorldStatusBehaviour(WorldAgent worldAgent, long period) {
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
		GraphicUserInterface.fillGrid();
		
		if(this.worldAgent.hasDemoExecutionEnded()) {
			GraphicUserInterface.log("\n\n\n");
			GraphicUserInterface.log("SIMULATION ENDED!!!\n\n");
			GraphicUserInterface.log("The simulation results are presented below...\n");
			GraphicUserInterface.log("\n\n\n");
			
			// Print all the log info
			// TODO
			
			this.worldAgent.doDelete();
		}
	}
}