package firefighting.firestation.behaviours;

import java.util.Enumeration;
import java.util.Vector;

import firefighting.firestation.FireStationAgent;
import firefighting.utils.Config;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

public class AlarmAircraftsAboutFiresBehaviour extends ContractNetInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int numAircraftResponders = Config.NUM_MAX_AIRCRAFTS;
	
	public AlarmAircraftsAboutFiresBehaviour(FireStationAgent fireStationAgent, ACLMessage alarmCFPMsg) {
		super(fireStationAgent, alarmCFPMsg);
	}
	
	protected void handleFailure(ACLMessage failure) {
		numAircraftResponders--;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void handleAllResponses(Vector responses, Vector acceptances) {
  	  
  	  	int bestProposal = -1;
  	  	ACLMessage accept = null;
  	  	
		Enumeration<ACLMessage> enumResponses = responses.elements();
  	  	
		// Initially reject all the responses/proposes from the Aircraft Agent responders/proposers
  	  	while (enumResponses.hasMoreElements()) {
  	  		ACLMessage msg = enumResponses.nextElement();
  		
  	  		if (msg.getPerformative() == ACLMessage.PROPOSE) {
  
  	  			ACLMessage reply = msg.createReply();
  	  			reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
  	  					
  	  			acceptances.addElement(reply);
  	  			
  	  			int proposal = Integer.parseInt(msg.getContent());
          
  	  			// TODO - Heuristic function
  	  			// Evaluate responses/proposes
  	  			if (proposal > bestProposal) {
  	  				bestProposal = proposal;
  	  				accept = reply;
  	  			}
  	  		}
  	  	
  	  		// Accept the proposal of the best Aircraft Agent responder/proposer
  	  		if (accept != null)
  	  			accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
  	  	}
	}
}