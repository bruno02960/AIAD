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

import firefighting.aircraft.AircraftAgent;
import firefighting.graphics.GraphicUserInterface;
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

	// Graphic user interface
	static GraphicUserInterface gui;
	
	// Setting global world's environment conditions
	static byte seasonTypeID = (byte) random.nextInt(Config.NUM_SEASONS);
	static byte windTypeID = (byte) random.nextInt(Config.NUM_TYPE_WINDS);	
	
	
	// Creation of the world agent
    static WorldAgent worldAgent = new WorldAgent(seasonTypeID, windTypeID, System.currentTimeMillis());
	
    
    // Main method
	public static void main(String[] args) throws ControllerException {
		
		// Creation of the graphic user interface
		gui = new GraphicUserInterface(worldAgent);
		gui.getFrame().setVisible(true);
		
		Runtime rt = Runtime.instance();
		
		Profile profile = new ProfileImpl();
		ContainerController mainContainer = rt.createMainContainer(profile);
		
		try {
			mainContainer.acceptNewAgent("WorldAgent", worldAgent);
			mainContainer.getAgent("WorldAgent").start();
			
			mainContainer.acceptNewAgent("FireStation", worldAgent.getFireStationAgent());
			mainContainer.getAgent("FireStation").start();
			
			GraphicUserInterface.log("Agents and Distributed Artificial Intelligence\n");
			GraphicUserInterface.log("Project 1 - Fire Fighting\n");
			GraphicUserInterface.log("\n");
			GraphicUserInterface.log("Authors:\n");
			GraphicUserInterface.log("Bernardo Coelho Leite - up201404464@fe.up.pt;\n");
			GraphicUserInterface.log("Bruno Miguel Pinto - up201502960@fe.up.pt;\n");
			GraphicUserInterface.log("Ruben Andre Barreiro - up201808917@fe.up.pt;\n");
			
			GraphicUserInterface.log("\n\n");
		
			GraphicUserInterface.log("Starting world... Creating the environment and, all the elements and agents!!!\n");
			
			GraphicUserInterface.log("CURRENT SEASON: " + worldAgent.getSeasonType().getName() + "\n");
			GraphicUserInterface.log("TYPE OF WIND: " + worldAgent.getWindType().getName() + "\n");
			String isPossibleToOccurDroughtSituations = worldAgent.canOccurDroughtSituations() ? "yes" : "no";
			GraphicUserInterface.log("OCCURRENCE OF DROUGHTS (EXTREME DRY SITUATIONS): " + isPossibleToOccurDroughtSituations + "\n");
			
			GraphicUserInterface.log("\n\n");
			
			for(AircraftAgent aircraftAgent: worldAgent.getAircraftAgents()) {
				mainContainer.acceptNewAgent("AircraftAgent" + (int)aircraftAgent.getID(), aircraftAgent);
				mainContainer.getAgent(aircraftAgent.getLocalName()).start();
			}
		}
		catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
}