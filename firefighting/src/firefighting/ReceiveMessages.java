package firefighting;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveMessages extends Behaviour {

	@Override
	public void action() {
		
		System.out.println("ReceiveMessages Behaviour ....");
		
		ACLMessage msg = myAgent.receive();
		if(msg!=null) {
			//Message Received. Process it
			System.out.println("I received: " + msg);
		}
		else {
			block();
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;  
	}

}
