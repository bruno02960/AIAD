package firefighting.aircraft.behaviours;

import firefighting.aircraft.AircraftAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveCFPs extends CyclicBehaviour {
	
	private AircraftAgent aircraftAgent;
	
	public ReceiveCFPs(AircraftAgent aircraftAgent) {
		this.aircraftAgent = aircraftAgent;
	}

	@Override
	public void action() {
		
		ACLMessage msg = myAgent.receive();
		
		//If CFP received...
		if(msg!= null) {
			
			MessageTemplate template = MessageTemplate.and(
					MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
					MessageTemplate.MatchPerformative(ACLMessage.CFP) );
			
			
			if(msg.getPerformative() == ACLMessage.CFP)
			{
				HandleContractNet nets = new HandleContractNet(this.aircraftAgent, template);
				this.aircraftAgent.addBehaviour(nets);
				try {
					nets.prepareResponse(msg);
				} catch (NotUnderstoodException | RefuseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
		
		}
		else
			block();
		
		
	}




}
