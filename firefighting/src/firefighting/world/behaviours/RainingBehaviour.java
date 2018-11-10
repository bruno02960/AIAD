package firefighting.world.behaviours;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentNavigableMap;

import javax.swing.ImageIcon;

import firefighting.graphics.GraphicUserInterface;
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
	
	public void changePositionsGraphics(Color fireColor, Color waterResourceColor, boolean start) {
		// Getting all the current active fires in the world and all the water resources
		ConcurrentNavigableMap<Integer, Fire> currentFires = worldAgent.getCurrentFires();
		ArrayList<WaterResource> waterResources = worldAgent.getWaterResources();
		
		for(Fire fire: currentFires.values()) {
			
			Point waterResourcePos = fire.getWorldObject().getPos();
			ImageIcon icon;
			
			if(start)
				icon = new ImageIcon("imgs/fire-bw.png");
			else
				icon = new ImageIcon("imgs/fire.png");
			
			GraphicUserInterface.changePosColor((int) waterResourcePos.getX(), (int) waterResourcePos.getY(), fireColor, icon);
		}
		
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
				// Normal amounts of rain ([21% , 50%] of precipitation)
				rainRatio = (double)(randomObject.ints(1, 21, 51).toArray()[0]) / 100;
				break;
			// SUMMER SEASON
			case 1:
				// Small amounts of rain ([10% , 20%] of precipitation)
				rainRatio = (double)(randomObject.ints(1, 10, 21).toArray()[0]) / 100;
				break;
			// AUTUMN SEASON
			case 2:
				// Normal amounts of rain ([21% , 50%] of precipitation)
				rainRatio = (double)(randomObject.ints(1, 41, 51).toArray()[0]) / 100;
				break;
			// WINTER SEASON
			case 3:
				// Big amounts of rain ([51% , 75%] of precipitation)
				rainRatio = (double)(randomObject.ints(1, 51, 76).toArray()[0]) / 100;
				break;
			default:
				rainRatio = 0.0;
				break;
		}
		
		finalRainAmount = (int) Math.round(seasonRainFactor * rainRatio);
		
		// Getting all the current active fires in the world and all the water resources
		ConcurrentNavigableMap<Integer, Fire> currentFires = worldAgent.getCurrentFires();
		ArrayList<WaterResource> waterResources = worldAgent.getWaterResources();
		
		GraphicUserInterface.log("\n");
		GraphicUserInterface.log("START OF AN ATTEMPT OF RAIN OCCURENCE!!!\n");
		GraphicUserInterface.log("Rain [ Current Season:" + seasonType.getName() + " | Season Rain Factor:" + seasonRainFactor + " | Rain Ratio:" + rainRatio + " | Final Amount:" + finalRainAmount + " ]\n");
		GraphicUserInterface.log("Current number of active fires: " + currentFires.size() + "\n");
		GraphicUserInterface.log("\n");
		
		// Change the colours of the positions of all current active fires and water resources in the world (start of raining behaviour)
		this.changePositionsGraphics(Color.yellow, Color.blue, true);
		
		// Simulates 1s for the raining occurrence and the respectively graphics display changes' perceptions
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Raining above the current active fires in the world, decreasing their intensities
		for(Fire fire: currentFires.values()) {
			fire.decreaseIntensity(finalRainAmount);
		
			// Fire extinguished
			if(!fire.isActive())
				this.getWorldAgent().fireExtinguished(fire.getID());
		}
		
		// Raining above all the water resources in the world, increasing their capacities
		for(WaterResource waterResource: waterResources)
			waterResource.increaseQuantity(finalRainAmount);
		
		// Change the colours of the positions of all current active fires and water resources in the world (start of raining behaviour)
		this.changePositionsGraphics(Color.orange, Color.cyan, false);
		
		GraphicUserInterface.log("\n");
		GraphicUserInterface.log("END OF AN ATTEMPT OF RAIN OCCURENCE!!!\n");
		GraphicUserInterface.log("Rain [ Current Season:" + seasonType.getName() + " | Season Rain Factor:" + seasonRainFactor + " | Rain Ratio:" + rainRatio + " | Final Rain Amount:" + finalRainAmount + " ]\n");
		GraphicUserInterface.log("Current number of active fires: " + currentFires.size() + "\n");
		GraphicUserInterface.log("\n");
	}
}