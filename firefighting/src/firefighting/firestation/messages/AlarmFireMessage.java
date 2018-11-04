package firefighting.firestation.messages;

import java.util.Date;

import firefighting.firestation.FireStationAgent;
import firefighting.nature.Fire;
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;

public class AlarmFireMessage extends ACLMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ACLMessage cfpMsg;
	
	private Fire associatedFire;
	
	public AlarmFireMessage(Fire associatedFire) {
		super(ACLMessage.CFP);
		this.associatedFire = associatedFire;
		setACLMessage();
	}

	
	public ACLMessage getACLMessage() {
		return this.cfpMsg;
	}
	
	private void setACLMessage() {
		Object[] args = FireStationAgent.getAircraftAgentsNames();
				 
		cfpMsg = null;
				
		if (args != null && args.length > 0) {
		      
			// Fill the CFP message
			cfpMsg = new ACLMessage(ACLMessage.CFP);
			      
			// Add all the pretended receivers a
			for (int i = 0; i < args.length; ++i)  
				cfpMsg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
					      
			cfpMsg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
					 
			
			// We want to receive a reply in 10 seconds
			cfpMsg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			
			cfpMsg.setContent("FIRE POS " + (int)this.associatedFire.getWorldObject().getPos().getX() + " " + (int)this.associatedFire.getWorldObject().getPos().getY());
		}
	}
	
	public Fire getAssociatedFire() {
		return this.associatedFire;
	}
}
