package firefighting.aircraft.behaviours;

import firefighting.aircraft.AircraftAgent;
import firefighting.ui.GUI;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

@SuppressWarnings("serial")
public class HandleContractNet extends ContractNetResponder {
	
	AircraftAgent aircraftAgent;

	public HandleContractNet(AircraftAgent aircraftAgent, MessageTemplate mt) {
		super(aircraftAgent, mt);
		this.aircraftAgent = aircraftAgent;
		System.out.println("Estou aqui!!!!!!");
	}
	
	protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
		System.out.println("Agent "+aircraftAgent.getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent() + "\n");
		//System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
		int proposal = aircraftAgent.evaluateAction(cfp.getContent());
		System.out.println(aircraftAgent.attendindFire + " " + proposal);
		if (!aircraftAgent.attendindFire && proposal < Integer.MAX_VALUE) {
			// We provide a proposal
			GUI.log("Agent "+aircraftAgent.getLocalName()+": Proposing "+proposal + "\n");
			//System.out.println("Agent "+getLocalName()+": Proposing "+proposal);
			ACLMessage propose = cfp.createReply();
			propose.setPerformative(ACLMessage.PROPOSE);
			propose.setContent(String.valueOf(proposal));
			return propose;
		}
		else {
			// We refuse to provide a proposal
			GUI.log("Agent "+aircraftAgent.getLocalName()+": Refuse");
			System.out.println("Agent "+aircraftAgent.getLocalName()+": Refuse");
			throw new RefuseException("evaluation-failed");
		}
	}
	
	protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
		GUI.log("Agent "+aircraftAgent.getLocalName()+": Proposal accepted\n");
		aircraftAgent.attendindFire = true;
		//System.out.println("Agent "+getLocalName()+": Proposal accepted");
		if (aircraftAgent.performAction()) {
			GUI.log("Agent "+aircraftAgent.getLocalName()+": Action successfully performed\n");
			//System.out.println("Agent "+getLocalName()+": Action successfully performed");
			ACLMessage inform = accept.createReply();
			inform.setPerformative(ACLMessage.INFORM);
			return inform;
		}
		else {
			GUI.log("Agent "+aircraftAgent.getLocalName()+": Action execution failed\n");
			//System.out.println("Agent "+getLocalName()+": Action execution failed");
			throw new FailureException("unexpected-error");
		}	
	}
	
	
	

}
