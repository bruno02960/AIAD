package firefighting;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendMessages extends OneShotBehaviour {

	@Override
	public void action() {
		
		System.out.println("SendMessages Behaviour!");

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID("FireStationAgent-2", AID.ISLOCALNAME));
		msg.setLanguage("English");
		msg.setOntology("Weather-forecast-ontology");
		msg.setContent("Today it’s raining");
		myAgent.send(msg);

	}



}
