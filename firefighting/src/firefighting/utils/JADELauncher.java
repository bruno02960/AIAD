package firefighting.utils;

import java.util.Random;

import firefighting.aircraft.AircraftAgent;
import firefighting.world.*;
import firefighting.world.utils.SeasonType;
import jade.Boot;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

/**
 * Class responsible for running and controlling JADE execution
 */
public class JADELauncher {
	
	static Random random = new Random();
	
	// Setting global world's conditions
	static byte seasonTypeID = (byte) random.nextInt(Config.NUM_SEASONS);
	static byte windTypeID = (byte) random.nextInt(Config.NUM_TYPE_WINDS);	
	
    static WorldAgent worldAgent = new WorldAgent(seasonTypeID, windTypeID);
	
	public static void main(String[] args) throws ControllerException {		
		
		Runtime rt = Runtime.instance();
		
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.GUI, "true");
		ContainerController mainContainer = rt.createMainContainer(profile);
		
		try {
			
			mainContainer.acceptNewAgent("WorldAgent", worldAgent);
			mainContainer.getAgent("WorldAgent").start();
			
			mainContainer.acceptNewAgent("FireStation", worldAgent.getFireStationAgent());
			mainContainer.getAgent("FireStation").start();
			
			for(int i = 0; i < worldAgent.getNumAircraftsAgents(); i++) {
				mainContainer.acceptNewAgent("AircraftAgent"+i, worldAgent.getAircraftAgents()[i]);
				mainContainer.getAgent(worldAgent.getAircraftAgents()[i].getLocalName()).start();
			}
		}
		catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
}