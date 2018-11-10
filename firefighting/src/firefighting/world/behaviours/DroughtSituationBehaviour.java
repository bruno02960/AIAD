package firefighting.world.behaviours;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentNavigableMap;

import javax.swing.ImageIcon;

import firefighting.graphics.GraphicUserInterface;
import firefighting.nature.Fire;
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
	
	public void changePositionsGraphics(Color waterResourceColor, boolean start) {
		// Getting all the current active fires in the world and all the water resources
		ArrayList<WaterResource> waterResources = worldAgent.getWaterResources();
	
		for(WaterResource waterResource: waterResources) {
			Point waterResourcePos = waterResource.getWorldObject().getPos();
			ImageIcon icon;
			
			if(start)
				icon = new ImageIcon("imgs/water-resource-bw.png");
			else
				icon = new ImageIcon("imgs/water-resource.png");
			
			GraphicUserInterface.changePosColor((int) waterResourcePos.getX(), (int) waterResourcePos.getY(), waterResourceColor, icon);
		}
	}
	
	
	@Override
	protected void onTick() {
		
		WorldAgent worldAgent = this.getWorldAgent();
				
		// Rain behaviour about all the water resources in the world
		ArrayList<WaterResource> waterResources = worldAgent.getWaterResources();

		GraphicUserInterface.log("\n");
		GraphicUserInterface.log("START OF AN ATTEMPT OF DROUGHT (EXTREME DRY SITUATION) OCCURENCE!!!\n");
		GraphicUserInterface.log("Drought (Extreme Dry Situation) Occurrence [ Drought Occurrence Penalty:" + Config.DROUGHT_OCCURRENCE_PENALTY + " ]\n");
		GraphicUserInterface.log("\n");
		
		// Change the colours of the positions of all current active fires and water resources in the world (start of raining behaviour)
		this.changePositionsGraphics(Color.blue, true);
		
		// Simulates 1s for the drought (extreme dry situation) occurrence and the respectively graphics display changes' perceptions
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		// Drought situation, affecting all the water resources in the world, decreasing its capacity
		for(WaterResource waterResource: waterResources)	
			waterResource.decreaseQuantity(Config.DROUGHT_OCCURRENCE_PENALTY);

		// Change the colours of the positions of all current active fires and water resources in the world (start of raining behaviour)
		this.changePositionsGraphics(Color.cyan, false);
		
		GraphicUserInterface.log("\n");
		GraphicUserInterface.log("END OF AN ATTEMPT OF DROUGHT (EXTREME DRY SITUATION) OCCURENCE!!!\n");
		GraphicUserInterface.log("Drought (Extreme Dry Situation) Occurrence [ Drought Occurrence Penalty:" + Config.DROUGHT_OCCURRENCE_PENALTY + " ]\n");
		GraphicUserInterface.log("\n");
	}
}