package firefighting.utils;

import java.util.Random;

import firefighting.world.WorldAgent;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

@SuppressWarnings("serial")
public class RamboAgent extends Agent {
	public static ContainerController mainContainer;
    static WorldAgent worldAgent;
	
	/**
	 * Generates all the Fires in the world, when is possible.
	 */
	public void setup() {
		Random random = new Random();
		
        Config.GRID_HEIGHT = 5 + (int)(Math.random() * ((9 - 5) + 1));
        Config.GRID_WIDTH = 6 + (int)(Math.random() * ((10 - 6) + 1));
        Config.NUM_MAX_WATER_RESOURCES = 1 + (int)(Math.random() * ((5 - 1) + 1));
        Config.NUM_MAX_AIRCRAFTS = 3 + (int)(Math.random() * ((7 - 3) + 1));
        Config.NUM_MAX_FIRES = 6 + (int)(Math.random() * ((10 - 6) + 1));
		
		worldAgent = new WorldAgent();
		
		Runtime rt = Runtime.instance();
		
		Profile profile = new ProfileImpl();
		mainContainer = rt.createMainContainer(profile);
		
		try {
			mainContainer.acceptNewAgent("WorldAgent", worldAgent);
			mainContainer.getAgent("WorldAgent").start();
			
			mainContainer.acceptNewAgent("FireStation", worldAgent.getFireStationAgent());
			mainContainer.getAgent("FireStation").start();
			
			for(int i = 0; i < worldAgent.getNumAircraftsAgents(); i++) {
				mainContainer.acceptNewAgent("AircraftAgent" + i, worldAgent.getAircraftAgents()[i]);
				mainContainer.getAgent(worldAgent.getAircraftAgents()[i].getLocalName()).start();
			}
		}
		catch (StaleProxyException e1) {
			e1.printStackTrace();
		} catch (ControllerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void killContainer() {
		try {
			mainContainer.kill();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
