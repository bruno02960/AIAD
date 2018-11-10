package firefighting.firestation.behaviours;

import java.awt.Point;
import java.util.concurrent.ConcurrentNavigableMap;

import firefighting.firestation.FireStationAgent;
import firefighting.firestation.messages.AlarmFireMessage;
import firefighting.graphics.GraphicUserInterface;
import firefighting.nature.Fire;
import firefighting.world.WorldAgent;
import jade.core.behaviours.TickerBehaviour;

public class DetectFiresBehaviour extends TickerBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	WorldAgent worldAgent;
	FireStationAgent fireStationAgent;
	
	public DetectFiresBehaviour(WorldAgent worldAgent, FireStationAgent fireStationAgent, long period) {
		super(fireStationAgent, period);
		
		this.worldAgent = worldAgent;
		this.fireStationAgent = fireStationAgent;
	}

	public WorldAgent getWorldAgent() {
		return this.worldAgent;
	}
	
	public FireStationAgent getFireStationAgent() {
		return this.fireStationAgent;
	}
	
	public ConcurrentNavigableMap<Integer, Fire> getCurrentFires() {
		return this.getWorldAgent().getCurrentFires();
	}
	

	
	@Override
	protected void onTick() {
		FireStationAgent fireStationAgent = this.getFireStationAgent();
		ConcurrentNavigableMap<Integer, Fire> fires = this.getCurrentFires();

		if(fires.size() > 0) {
			for(Fire fire: fires.values()) {
				
				// The behaviour's reaction is only valid if the fire is active and not attended by some aircraft agent yet
				if(fire.isActive() && !fire.isAttended()) {
						
					Point firePos = fire.getWorldObject().getPos();
					
					// Printing the log information about the detected fire
					GraphicUserInterface.log("\n");
					GraphicUserInterface.log("FIRE DETECTED!!! - Fire [ ID: " + (int) fire.getID()  + " Intensity: " + fire.getCurrentIntensity() + " Position: (" + (int)firePos.getX() + "," + (int)firePos.getY() + ") ]\n");
					GraphicUserInterface.log("Current number of active fires: " + this.getCurrentFires().size() + "\n");
					GraphicUserInterface.log("\n");
					
					AlarmFireMessage alarmFireMsg = new AlarmFireMessage(fire, worldAgent);	
					    
					AlarmAircraftsAboutFiresBehaviour alarmToExtinguishFire = new AlarmAircraftsAboutFiresBehaviour(fireStationAgent, alarmFireMsg.getACLMessage());
					
					fireStationAgent.addBehaviour(alarmToExtinguishFire); 
					
					// To be just one fire detection for each call of the behaviour
					break;
				}					
			}
		}
	}
}