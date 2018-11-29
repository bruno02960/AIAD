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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import firefighting.ui.GUI;
import firefighting.ui.WelcomeScreen;
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

	// GUI related stuff
	static GUI gui;
	static WelcomeScreen welcomeScreen;
	
	// Setting global world's environment conditions
	static byte seasonTypeID = (byte) random.nextInt(Config.NUM_SEASONS);
	static byte windTypeID = (byte) random.nextInt(Config.NUM_TYPE_WINDS);	
	
	
	// Creation of the world agent
    static WorldAgent worldAgent;
    
    // Main method
	public static void main(String[] args) throws ControllerException, IOException {
		new Logger();
		
		// GUI related stuff
		welcomeScreen = new WelcomeScreen();
		welcomeScreen.getFrame().setVisible(true);
		welcomeScreen.getBtnGo().addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        Config.GRID_HEIGHT = Integer.parseInt(welcomeScreen.getGridHeight().getText());
		        Config.GRID_WIDTH = Integer.parseInt(welcomeScreen.getGridWidth().getText());
		        Config.NUM_MAX_WATER_RESOURCES = Integer.parseInt(welcomeScreen.getMaxWaterResources().getText());
		        Config.NUM_MAX_AIRCRAFTS = Integer.parseInt(welcomeScreen.getMaxAircrafts().getText());
		        Config.NUM_MAX_FIRES = Integer.parseInt(welcomeScreen.getMaxFires().getText());
		        
				welcomeScreen.getFrame().setVisible(false);
				
				worldAgent = new WorldAgent(seasonTypeID, windTypeID);

				gui = new GUI(worldAgent);
				gui.getFrame().setVisible(true);
				
				Runtime rt = Runtime.instance();
				
				Profile profile = new ProfileImpl();
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
				catch (StaleProxyException e1) {
					e1.printStackTrace();
				} catch (ControllerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
	}
}