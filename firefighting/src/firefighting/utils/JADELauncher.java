package firefighting.utils;

import firefighting.world.*;

import jade.Boot;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import firefighting.AircraftAgent;

/**
 * Class responsible for running and controlling JADE execution
 */
public class JADELauncher {
    static WorldAgent worldAgent = new WorldAgent();
	
	public static void main(String[] args) throws ControllerException {		
		Runtime rt = Runtime.instance();
		
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.GUI, "true");
		ContainerController mainContainer = rt.createMainContainer(profile);
		
		try {
			
			mainContainer.acceptNewAgent("FireStation", worldAgent.getFireStationAgent());
			mainContainer.getAgent("FireStation").start();
			
			for(int i = 0; i < worldAgent.getNumAircraftsAgents(); i++) {
				mainContainer.acceptNewAgent("aircraftAgent"+i, worldAgent.getAircraftAgents()[i]);
				mainContainer.getAgent(worldAgent.getAircraftAgents()[i].getLocalName()).start();
			}

		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}