package firefighting.firestation;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Date;
import java.util.Vector;

import firefighting.world.*;

import java.util.Enumeration;

/**
 * Class responsible for a Fire Station Agent and its behaviour.
 */
@SuppressWarnings("serial")
public class FireStationAgent extends Agent {
	
	private int nResponders;
	
	public FireStationAgent() {
		
	}
	
	// Global Instance Variables:
	/**
	 * World's object of the Fire Station Agent.
	 */
	private WorldObject worldObject;
	
	
	// Constructors:
	/**
	 * Constructor #1 of the Fire Station Agent.
	 * 
	 * Creates a Fire Station Agent, initialising its world object.
	 * 
	 * @param the Fire Station Agent's World Object
	 */
	public FireStationAgent(WorldObject worldObject) {
		this.worldObject = worldObject;
	}

	// Methods:
	/**
	 * Return the Fire Station Agent's World Object.
	 * 
	 * @return the Fire Station Agent's World Object
	 */
	private WorldObject getWorldObject() {
		return this.worldObject;
	}

	@Override
	public String toString() {
		return "FS";
	}

	protected void setup() {
		System.out.println("Im in! My identifier is "+getAID().getName());
		
	    // Read names of responders as arguments
	    Object[] args = getArguments();
	 
	    String a = "aircraftAgent0";
	    String b = "aircraftAgent1";
	    String c = "aircraftAgent2";
	    
	    args = new Object[] {a,b,c};
	    
	    System.out.println(args);
	    
	    if (args != null && args.length > 0) {
	      nResponders = args.length;
	      System.out.println("Trying to delegate dummy-action to one out of "+nResponders+" responders.");
	      
	      // Fill the CFP message
	      ACLMessage msg = new ACLMessage(ACLMessage.CFP);
	      for (int i = 0; i < args.length; ++i) {
	    	  msg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
	      }
	      msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
	      // We want to receive a reply in 10 secs
	      msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
	      msg.setContent("dummy-action");
	      
		
	      addBehaviour(new ContractNetInitiator(this, msg) {
	          
	          protected void handlePropose(ACLMessage propose, Vector v) {
	            System.out.println("Agent "+propose.getSender().getName()+" proposed "+propose.getContent());
	          }
	          
	          protected void handleRefuse(ACLMessage refuse) {
	            System.out.println("Agent "+refuse.getSender().getName()+" refused");
	          }
	          
	          protected void handleFailure(ACLMessage failure) {
	            if (failure.getSender().equals(myAgent.getAMS())) {
	              // FAILURE notification from the JADE runtime: the receiver
	              // does not exist
	              System.out.println("Responder does not exist");
	            }
	            else {
	              System.out.println("Agent "+failure.getSender().getName()+" failed");
	            }
	            // Immediate failure --> we will not receive a response from this agent
	            nResponders--;
	          }
	          
	       
	          protected void handleAllResponses(Vector responses, Vector acceptances) {
	            if (responses.size() < nResponders) {
	              // Some responder didn't reply within the specified timeout
	              System.out.println("Timeout expired: missing "+(nResponders - responses.size())+" responses");
	            }
	            // Evaluate proposals.
	            int bestProposal = -1;
	            AID bestProposer = null;
	            ACLMessage accept = null;
	            Enumeration e = responses.elements();
	            while (e.hasMoreElements()) {
	              ACLMessage msg = (ACLMessage) e.nextElement();
	              if (msg.getPerformative() == ACLMessage.PROPOSE) {
	                ACLMessage reply = msg.createReply();
	                reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
	                acceptances.addElement(reply);
	                int proposal = Integer.parseInt(msg.getContent());
	                if (proposal > bestProposal) {
	                  bestProposal = proposal;
	                  bestProposer = msg.getSender();
	                  accept = reply;
	                }
	              }
	            }
	            // Accept the proposal of the best proposer
	            if (accept != null) {
	              System.out.println("Accepting proposal "+bestProposal+" from responder "+bestProposer.getName());
	              accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
	            }           
	          }
	          
	          protected void handleInform(ACLMessage inform) {
	            System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
	          }
	          
	        } );
	

	    }
	    else {
	        System.out.println("No responder specified.");
	      }
	    
		    }
		
	protected void takeDown() {
		System.out.println("Agent"+getAID().getName()+"terminating.");
	}

	public ContractNetInitiator createContractNetInitiator() {
		// TODO Auto-generated method stub
		return null;
	}
}