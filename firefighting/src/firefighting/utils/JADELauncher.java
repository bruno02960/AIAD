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
	/* Manual or random configuration */
	static boolean batch_run = true;
	public static int NUMBER_OF_RUNS = 19;	
	static Random random = new Random();

	// GUI related stuff
	static GUI gui;
	static WelcomeScreen welcomeScreen;
	
	// Rambo
	public static ContainerController ramboContainer;
	public static RamboAgent ramboAgent;
	    
    // Main method
	public static void main(String[] args) throws ControllerException, IOException {
		new Logger();
		
		gui = new GUI();
		
		if(batch_run) {
			batchRun();
		}
		else {
			//normalRun();
		}
	}
	
	public static void prepareRun() {
		batchRun();
	}
	
	public static void batchRun() {		
		Runtime rt = Runtime.instance();
		Profile profile = new ProfileImpl();
		ramboContainer = rt.createMainContainer(profile);
		ramboAgent = new RamboAgent();
		
		try {
			ramboContainer.acceptNewAgent("RamboAgent", ramboAgent);
			ramboContainer.getAgent("RamboAgent").start();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	/*private static void normalRun() {
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
				
				worldAgent = new WorldAgent();

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
	}*/
}