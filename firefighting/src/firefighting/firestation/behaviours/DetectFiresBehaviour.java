package firefighting.firestation.behaviours;

import java.awt.Point;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.util.stream.Stream;

import firefighting.Fire;
import firefighting.firestation.FireStationAgent;
import firefighting.utils.Config;
import firefighting.world.WorldAgent;
import firefighting.world.WorldObject;
import firefighting.world.utils.WorldObjectType;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

public class DetectFiresBehaviour extends TickerBehaviour {
	
	WorldAgent worldAgent;
	FireStationAgent fireStationAgent;
	
	public DetectFiresBehaviour(WorldAgent worldAgent, FireStationAgent fireStationAgent, long period) {
		super(fireStationAgent, period);
		
		this.worldAgent = worldAgent;
		this.fireStationAgent = fireStationAgent;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		
		for(int i = 0; i < Config.NUM_MAX_FIRES; i++) {
			if(fires[i].isActive() && !fires[i].isAttended()) {
				//ACLMessage acl = new ACLMessage
				//this.getAgent().send(acl);
				
				
				// Read names of responders as arguments
			    //Object[] args = getArguments();
			 
			    String a = "AircraftAgent0";
			    String b = "AircraftAgent1";
			    String c = "AircraftAgent2";
			    
			    Object[] args = new Object[] {a,b,c};
			    int nResponders = 0;
			    
			    System.out.println(args);
			    
			    if (args != null && args.length > 0) {
			    	nResponders = args.length;
			    	System.out.println("Trying to delegate dummy-action to one out of " + nResponders + " responders.");
			      
			    	// Fill the CFP message
			    	ACLMessage msg = new ACLMessage(ACLMessage.CFP);
			      
			    	for (int j = 0; j < args.length; ++j) {
			    		msg.addReceiver(new AID((String) args[j], AID.ISLOCALNAME));
			    	}
			      
			    	msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			      
			    	// We want to receive a reply in 10 secs
			      
			    	msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			      
			    	msg.setContent("dummy-action");
			      
			    	ContractNetInitiator h = this.getFireStationAgent().createContractNetInitiator();
			    }
			}	
		}
	}
}