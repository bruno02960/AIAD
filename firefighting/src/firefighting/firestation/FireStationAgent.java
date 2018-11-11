package firefighting.firestation;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Date;
import java.util.Vector;

import firefighting.firestation.behaviours.DetectFiresBehaviour;
import firefighting.firestation.messages.AlarmFireMessage;
import firefighting.nature.Fire;
import firefighting.world.*;

import java.util.Enumeration;

/**
 * Class responsible for a Fire Station Agent and its behaviour.
 */
@SuppressWarnings("serial")
public class FireStationAgent extends Agent {
	
	private int numAircraftsResponders;
	
	
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
	
	public WorldAgent getWorldAgent() {
		return this.worldAgent;
	}
	/**
	 * Return the Fire Station Agent's World Object.
	 * 
	 * @return the Fire Station Agent's World Object
	 */
	public WorldObject getWorldObject() {
		return this.worldObject;
	}

	public static Object[] getAircraftAgentsNames(int numAircraftAgents) {
		
		Object[] aircraftAgentNames = new Object[numAircraftAgents];
		
		// The Available Aircraft Agents
		for(int i = 0; i < numAircraftAgents; i++)
			aircraftAgentNames[i] = "AircraftAgent" + i;
		
		return aircraftAgentNames;
	}
	
	@Override
	public String toString() {
		return "ST";
	}

	protected void setup() {
		// Hello Aircrafts behaviour (just for communications debugging)
		//HelloAircraftMessage helloAircraftMsg = new HelloAircraftMessage();
		//this.addBehaviour(new HelloAircraftBehaviour(this, helloAircraftMsg.getACLMessage()));
		
		//20segundos para se ver o caso de teste (apagar 1 fogo) com calma
		this.addBehaviour(new DetectFiresBehaviour(this.getWorldAgent(), this, 5000));
	}
		
	protected void takeDown() {
		System.out.println("Agent " + this.getAID().getName() + " terminating!");
	}

	public ContractNetInitiator createContractNetInitiator() {
		// TODO Auto-generated method stub
		return null;
	}
}