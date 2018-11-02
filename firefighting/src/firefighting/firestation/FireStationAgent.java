package firefighting.firestation;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Date;
import java.util.Vector;

import firefighting.firestation.behaviours.DetectFiresBehaviour;
import firefighting.firestation.behaviours.HelloAircraftBehaviour;
import firefighting.firestation.messages.AlarmFireMessage;
import firefighting.firestation.messages.HelloAircraftMessage;
import firefighting.nature.Fire;
import firefighting.world.*;

import java.util.Enumeration;

/**
 * Class responsible for a Fire Station Agent and its behaviour.
 */
@SuppressWarnings("serial")
public class FireStationAgent extends Agent {
	
	private int numAircraftsResponders;
	
	public FireStationAgent() {
		
	}
	
	// Global Instance Variables:
	/**
	 * World's object of the Fire Station Agent.
	 */
	private WorldAgent worldAgent;
	
	private WorldObject worldObject;
	
	
	// Constructors:
	/**
	 * Constructor #1 of the Fire Station Agent.
	 * 
	 * Creates a Fire Station Agent, initialising its world object.
	 * 
	 * @param the Fire Station Agent's World Object
	 */
	public FireStationAgent(WorldAgent worldAgent, WorldObject worldObject) {
		this.worldAgent = worldAgent;
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

	public static Object[] getAircraftAgentsNames() {
		
		// The Available Aircraft Agents
		String a = "AircraftAgent0";
		String b = "AircraftAgent1";
		String c = "AircraftAgent2";
		    
		return new Object[] {a,b,c};
	}
	
	@Override
	public String toString() {
		return "ST";
	}

	protected void setup() {
		// Hello Aircrafts behaviour (just for communications debugging)
		HelloAircraftMessage helloAircraftMsg = new HelloAircraftMessage();
		this.addBehaviour(new HelloAircraftBehaviour(this, helloAircraftMsg.getACLMessage()));
	
		
	}
		
	protected void takeDown() {
		System.out.println("Agent " + this.getAID().getName() + " terminating!");
	}
}