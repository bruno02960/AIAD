package firefighting.firestation.behaviours;

import firefighting.firestation.FireStationAgent;
import firefighting.firestation.messages.AlarmFireMessage;
import firefighting.nature.Fire;
import firefighting.utils.Config;
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
	
	public Fire[] getCurrentFires() {
		return this.getWorldAgent().getCurrentFires();
	}
	
	@Override
	protected void onTick() {
		
		FireStationAgent fireStationAgent = this.getFireStationAgent();
		Fire[] fires = this.getCurrentFires();
		
		if(fires != null) {
			for(int i = 0; i < Config.NUM_MAX_FIRES; i++) {
				
				if(fires[i] != null) {
					
					// The behaviour's reaction is only valid if the Fire is active and not attended by some Aircraft Agent yet
					if(fires[i].isActive() && !fires[i].isAttended()) {
						
						// Get the Fire that needs to be extinguished
						Fire fireToBeExtinguished = fires[i];
						
						AlarmFireMessage alarmFireMsg = new AlarmFireMessage(fireToBeExtinguished);	
					    
						AlarmAircraftsAboutFiresBehaviour alarmToExtinguishFire = new AlarmAircraftsAboutFiresBehaviour(fireStationAgent, alarmFireMsg);
					    						
					    fireStationAgent.addBehaviour(alarmToExtinguishFire);
					}	
				}
			}
		}
	}
}