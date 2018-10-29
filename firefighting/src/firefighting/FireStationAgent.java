package firefighting;

import jade.core.Agent;

/**
 * Class responsible for a Fire Station Agent and its behaviour.
 */
@SuppressWarnings("serial")
public class FireStationAgent extends Agent {
	
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
		addBehaviour(new ReceiveMessages());
	}
	
	protected void takeDown() {
		System.out.println("Agent"+getAID().getName()+"terminating.");
	}
}