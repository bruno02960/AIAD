package firefighting.firestation.messages;

import java.awt.Point;
import java.util.Date;

import firefighting.firestation.FireStationAgent;
import firefighting.nature.Fire;
import firefighting.world.WorldAgent;
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;

public class AlarmFireMessage extends ACLMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ACLMessage cfpMsg;
	
	private WorldAgent worldAgent;
	
	private Fire associatedFire;
	
	public AlarmFireMessage(Fire associatedFire, WorldAgent worldAgent) {
		super(ACLMessage.CFP);
		this.associatedFire = associatedFire;
		this.worldAgent = worldAgent;
		setACLMessage();
	}

	
	public ACLMessage getACLMessage() {
		return this.cfpMsg;
	}
	
	private void setACLMessage() {
		Object[] args = FireStationAgent.getAircraftAgentsNames(worldAgent.getNumAircraftsAgents());
				 
		cfpMsg = null;
				
		if (args != null && args.length > 0) {
		      
			// Fill the CFP message
			cfpMsg = new ACLMessage(ACLMessage.CFP);
			      
			// Add all the pretended receivers a
			for (int i = 0; i < args.length; ++i)  {
				if(!worldAgent.getAircraftAgents()[i].attendindFire && !worldAgent.getAircraftAgents()[i].attendindWater)
					cfpMsg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
			}
					      
			cfpMsg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
					 
			
			// We want to receive a reply in 20 seconds
			cfpMsg.setReplyByDate(new Date(System.currentTimeMillis() + 20000));
			
			Point firePos = this.associatedFire.getWorldObject().getPos();
			
			cfpMsg.setContent("FIRE INTENSITY " + this.associatedFire.getCurrentIntensity() + " POS " + (int)firePos.getX() + " " + (int)firePos.getY());
		}
	}
	
	public Fire getAssociatedFire() {
		return this.associatedFire;
	}
}
