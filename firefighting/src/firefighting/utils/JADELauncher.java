/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */

package firefighting.utils;

import java.util.Random;

import javax.swing.JFrame;

import firefighting.ui.GUI;
import firefighting.world.*;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

/**
 * Class responsible for running and controlling JADE execution.
 */
public class JADELauncher {	
	static Random random = new Random();

	/* GUI related stuff */
	static GUI gui = new GUI();
	/* ----------------- */
	
	// Setting global world's environment conditions
	static byte seasonTypeID = (byte) random.nextInt(Config.NUM_SEASONS);
	static byte windTypeID = (byte) random.nextInt(Config.NUM_TYPE_WINDS);	
	
	
	// Creation of the world agent
    static WorldAgent worldAgent = new WorldAgent(seasonTypeID, windTypeID);
	
    
    // Main method
	public static void main(String[] args) throws ControllerException {
		/* GUI related stuff */
		GUI window = new GUI();
		window.getFrame().setVisible(true);
		/* ----------------- */
		
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
				mainContainer.acceptNewAgent("AircraftAgent" + i, worldAgent.getAircraftAgents()[i]);
				mainContainer.getAgent(worldAgent.getAircraftAgents()[i].getLocalName()).start();
			}
		}
		catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
}