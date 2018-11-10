package firefighting.firestation.behaviours;

import java.util.Enumeration;
import java.util.Vector;

import firefighting.firestation.FireStationAgent;
import firefighting.graphics.GraphicUserInterface;
import firefighting.utils.Config;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

public class AlarmAircraftsAboutFiresBehaviour extends ContractNetInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int numAircraftResponders = Config.NUM_MAX_AIRCRAFTS;
	
	private ACLMessage helloAircraftCFPMsg;
	
	
	public AlarmAircraftsAboutFiresBehaviour(FireStationAgent fireStationAgent, ACLMessage helloAircraftCFPMsg) {
		super(fireStationAgent, helloAircraftCFPMsg);
		this.helloAircraftCFPMsg = helloAircraftCFPMsg;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Vector prepareCfps(ACLMessage helloAircraftCFPMsg) {
		
		GraphicUserInterface.log("\n");
		GraphicUserInterface.log("Fire station is preparing fire alarm messages about the detected fire...\n");
		GraphicUserInterface.log("\n");
		
		Vector v = new Vector();		

		v.add(this.helloAircraftCFPMsg);
		
		return v;
	}
	
	@SuppressWarnings("rawtypes")
	protected void handlePropose(ACLMessage propose, Vector acceptances) {
		GraphicUserInterface.log("Aircraft agent Responder " + propose.getSender().getName() + " answer with proposal value of " + propose.getContent() + "!\n");
    }
     
	protected void handleRefuse(ACLMessage refuse) {
		GraphicUserInterface.log("Aicraft agent " + refuse.getSender().getName() + " refused to propose!\n");
	}
      
	protected void handleFailure(ACLMessage failure) {
        
		if (failure.getSender().equals(myAgent.getAMS()))
			// FAILURE notification from the JADE runtime: the receiver doesn't exist
			System.err.println("Responder doesn't exist!"); 
		else
			System.err.println("Aircraft agent responder " + failure.getSender().getName() + " failed!");
	
		// Immediate failure --> we will not receive a response from this agent
		numAircraftResponders--;  
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void handleAllResponses(Vector responses, Vector acceptances) {
		if(responses.size() < numAircraftResponders)
			// Some responder didn't reply within the specified timeout
			System.err.println("Timeout expired: Missing " + (numAircraftResponders - responses.size()) + " responses from aircraft agents!");  
    	  
		int bestProposal = 1000;
		AID bestProposer = null;
		ACLMessage accept = null;
		
		Enumeration e = responses.elements();
    	  
		while(e.hasMoreElements()) {
    	
			ACLMessage msg = (ACLMessage) e.nextElement();
    		
			if(msg.getPerformative() == ACLMessage.PROPOSE) {
				ACLMessage reply = msg.createReply();
    			reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
    			acceptances.addElement(reply);
    			
				int proposal = Integer.parseInt(msg.getContent());
				
				// Evaluate proposals
    			if(proposal < bestProposal) {
    				bestProposal = proposal;
    				bestProposer = msg.getSender();
    				accept = reply;
    			}
			}
		}
    	  
		// Accept the proposal of the best aircraft agent proposer
		if (accept != null) {
			GraphicUserInterface.log("Accepting proposal " + bestProposal + " from aircraft agent responder " + bestProposer.getName() + "!\n");
			accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
		} 
	}
      
	protected void handleInform(ACLMessage inform) {
		GraphicUserInterface.log("Aircraft agent " + inform.getSender().getName() + " successfully performed the requested action!\n");
	}	
}