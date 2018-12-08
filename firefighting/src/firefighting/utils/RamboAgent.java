package firefighting.utils;

import java.io.IOException;
import java.util.Random;
import java.util.Set;

import firefighting.aircraft.AircraftAgent;
import firefighting.world.WorldAgent;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

@SuppressWarnings("serial")
public class RamboAgent extends Agent {
	public static ContainerController agentContainer;
    static WorldAgent worldAgent;
	
	private long init_time;
	
	/**
	 * Generates all the Fires in the world, when is possible.
	 */
	public void setup() {
		addBehaviour(new TickerBehaviour(this, 1000L) {

			@Override
			protected void onTick() {
				int extinguishedFires = 0;
				
				for(int i = 0; i < worldAgent.getAircraftAgents().length; i++) {
					extinguishedFires+=worldAgent.getAircraftAgents()[i].getAircraftMetricsStats().getNumTotalFiresExtinguishedByThisAircraft();
				}
				
				worldAgent.getWorldMetricsStats().setNumTotalFiresExtinguishedByAllAircrafts(extinguishedFires);
				
				if(extinguishedFires >= 3) {					
					long end_time = System.currentTimeMillis();
					long execution_time = end_time - init_time;
					
					AircraftAgent[] aircrafts = worldAgent.getAircraftAgents();
					
					for(int i = 0; i < aircrafts.length; i++) {
						
						for(Thread sleepingThread : aircrafts[i].getSleepingThreads().values()) {
							System.err.println("Interrupting thread = \"" + sleepingThread.getName() +  "!");
							sleepingThread.interrupt();
						}
					}
					
					killContainer();
					
					Logger.appendConfigValues(execution_time);
					
					System.out.println("Run no. " + JADELauncher.NUMBER_OF_RUNS + " finished.");

					if(JADELauncher.NUMBER_OF_RUNS == 0) {
						try {
							Logger.closeStream();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.exit(0);
					}
					else {
						JADELauncher.NUMBER_OF_RUNS--;
						instanceRun();
					}
				}				
			}

			
		});
		
		instanceRun();
	}

	private void instanceRun() {
		Random random = new Random();
		
		do {
			Config.GRID_HEIGHT = 5 + (int)(Math.random() * ((9 - 5) + 1));
	        Config.GRID_WIDTH = 6 + (int)(Math.random() * ((10 - 6) + 1));
	        Config.NUM_MAX_WATER_RESOURCES = 1 + (int)(Math.random() * ((5 - 1) + 1));
	        Config.NUM_MAX_AIRCRAFTS = 3 + (int)(Math.random() * ((7 - 3) + 1));
	        Config.NUM_MAX_FIRES = 6 + (int)(Math.random() * ((10 - 6) + 1));
		} while(Math.abs(Config.NUM_MAX_AIRCRAFTS - Config.NUM_MAX_FIRES) > 3);
		
		worldAgent = new WorldAgent();
		
		Runtime rt = Runtime.instance();
		
		Profile profile = new ProfileImpl();
		agentContainer = rt.createAgentContainer(profile);
		
		try {
			agentContainer.acceptNewAgent("WorldAgent", worldAgent);
			agentContainer.getAgent("WorldAgent").start();
			
			agentContainer.acceptNewAgent("FireStation", worldAgent.getFireStationAgent());
			agentContainer.getAgent("FireStation").start();
			
			for(int i = 0; i < worldAgent.getNumAircraftsAgents(); i++) {
				agentContainer.acceptNewAgent("AircraftAgent" + i, worldAgent.getAircraftAgents()[i]);
				agentContainer.getAgent(worldAgent.getAircraftAgents()[i].getLocalName()).start();
			}
			
			init_time = System.currentTimeMillis();
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
			agentContainer.kill();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
