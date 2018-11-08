package firefighting.firestation.behaviours;

import java.util.ArrayList;

import firefighting.firestation.FireStationAgent;
import firefighting.firestation.messages.AlarmFireMessage;
import firefighting.nature.Fire;
import firefighting.ui.GUI;
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
	
	public ArrayList<Fire> getCurrentFires() {
		return this.getWorldAgent().getCurrentFires();
	}
	

	
	@Override
	protected void onTick() {
		
		FireStationAgent fireStationAgent = this.getFireStationAgent();
		ArrayList<Fire> fires = this.getCurrentFires();
	/*	
		if(fires.length > 0) {
			for(int i = 0; i < Config.NUM_MAX_FIRES; i++) {
				System.out.println(fires[i]);
				if(fires[i] != null) {
					System.out.println("vai adicionar alarme");
					// The behaviour's reaction is only valid if the Fire is active and not attended by some Aircraft Agent yet
					if(fires[i].isActive() && !fires[i].isAttended()) {
						
						// Get the Fire that needs to be extinguished
						Fire fireToBeExtinguished = fires[i];
						
						AlarmFireMessage alarmFireMsg = new AlarmFireMessage(fireToBeExtinguished);	
					    
						AlarmAircraftsAboutFiresBehaviour alarmToExtinguishFire = new AlarmAircraftsAboutFiresBehaviour(fireStationAgent, alarmFireMsg.getACLMessage());
					
					    fireStationAgent.addBehaviour(alarmToExtinguishFire);
					}	
				}
			}
		}
	}
}*/

		//para ser um fogo de cada vez
		if(fires.size() > 0) {
			int i = 0;
			for(i = 0; i < fires.size(); i++) {
					// The behaviour's reaction is only valid if the Fire is active and not attended by some Aircraft Agent yet
					if(fires.get(i).isActive() && !fires.get(i).isAttended()) {
						
						GUI.log("Fire!! on index position: "+i + "\n");
						
						System.out.println("There are " + fires.size() + " fires and this is the fire on POS"+ fires.get(i).getWorldObject().getPos());
						
						// Get the Fire that needs to be extinguished
						Fire fireToBeExtinguished = fires.get(i);
						
						AlarmFireMessage alarmFireMsg = new AlarmFireMessage(fireToBeExtinguished, worldAgent);	
					    
						AlarmAircraftsAboutFiresBehaviour alarmToExtinguishFire = new AlarmAircraftsAboutFiresBehaviour(fireStationAgent, alarmFireMsg.getACLMessage());
					
					    fireStationAgent.addBehaviour(alarmToExtinguishFire); 
					    
					    break; //!!!!!!!!!!!ADICIONADO SÓ PARA FAZER PARA O PRIMEIRO INCENDIO
					}	
					
				}
			}
		}
		
	}
