package firefighting;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;


/**
 * Class responsible for an Aircraft Agent and its behaviour.
 */
@SuppressWarnings("serial")
public class AircraftAgent extends Agent {

	// Global Instance Variables:
	/**
	 * ID of the Aircraft Agent.
	 */
	private byte id;

	/**
	 * World's object of the Aircraft Agent.
	 */
	private WorldObject worldObject;

	/**
	 * Capacity of the Aircraft Agent's tank.
	 */
	private int tankCapacity;

	/**
	 * Status of the Aircraft Agent's tank.
	 */
	private int tankStatus;


	// Constructors:
	/**
	 * Constructor #1 of the Aircraft Agent.
	 * 
	 * Creates a new Aircraft Agent, initialising its ID, its world's object and its tank capacity.
	 * 
	 * @param id the Aircraft Agent's ID
	 * @param worldObject the Aircraft's World Object
	 */
	AircraftAgent(byte id, WorldObject worldObject) {
		Random random = new Random();

		this.id = id;
		this.worldObject = worldObject;
		this.tankCapacity = random.nextInt(Config.AIRCRAFT_MAX_TANK_CAPACITY) + 1;
		this.tankStatus = 0;
	}

	//TODO: Remove
	public AircraftAgent() {

	}


	// Methods:
	/**
	 * Returns the Aircraft Agent's ID.
	 * 
	 * @return the Aircraft Agent's ID
	 */
	private byte getID() {
		return this.id;
	}

	/**
	 * Return the Aircraft Agent's World Object.
	 * 
	 * @return the Aircraft Agent's World Object
	 */
	private WorldObject getWorldObject() {
		return this.worldObject;
	}

	/**
	 * Return the Aircraft Agent's Tank Capacity.
	 * 
	 * @return the Aircraft Agent's Tank Capacity
	 */
	private int getTankCapacity() {
		return this.tankCapacity;
	}

	@Override
	public String toString() {
		return "A" + tankStatus;
	}

	protected void setup() {
		System.out.println("Agent responder "+getLocalName()+" waiting for CFP...");
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.MatchPerformative(ACLMessage.CFP) );

		addBehaviour(new ContractNetResponder(this, template) {
			protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
				System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
				int proposal = evaluateAction();
				if (proposal > 2) {
					// We provide a proposal
					System.out.println("Agent "+getLocalName()+": Proposing "+proposal);
					ACLMessage propose = cfp.createReply();
					propose.setPerformative(ACLMessage.PROPOSE);
					propose.setContent(String.valueOf(proposal));
					return propose;
				}
				else {
					// We refuse to provide a proposal
					System.out.println("Agent "+getLocalName()+": Refuse");
					throw new RefuseException("evaluation-failed");
				}
			}

			protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
				System.out.println("Agent "+getLocalName()+": Proposal accepted");
				if (performAction()) {
					System.out.println("Agent "+getLocalName()+": Action successfully performed");
					ACLMessage inform = accept.createReply();
					inform.setPerformative(ACLMessage.INFORM);
					return inform;
				}
				else {
					System.out.println("Agent "+getLocalName()+": Action execution failed");
					throw new FailureException("unexpected-error");
				}	
			}

			protected void handleRejectProposal(ACLMessage reject) {
				System.out.println("Agent "+getLocalName()+": Proposal rejected");
			}
		} );
	}

	private int evaluateAction() {
		// Simulate an evaluation by generating a random number
		return (int) (Math.random() * 10);
	}

	private boolean performAction() {
		// Simulate action execution by generating a random number
		return (Math.random() > 0.2);
	}

	/**
	 * Calculates the path to a fire in a given location
	 * @param fireLocation location of the fire
	 * @return array list with the path of points from the airplane to the fire location
	 */
	private ArrayList<Point> distanceToFire(Point fireLocation) {
		Point s = this.worldObject.getPos();
		Point d = fireLocation;
		
		// To keep track of visited QItems. Marking blocked cells as visited
		boolean[][] visited = new boolean[Config.GRID_HEIGHT][Config.GRID_WIDTH];
		for (int i = 0; i < Config.GRID_HEIGHT; i++) {
			for (int j = 0; j < Config.GRID_WIDTH; j++) {
				if(World.getWorldMap()[i][j] == null)
					visited[i][j] = false;
				else
					visited[i][j] = true;
			}
		}
		
		// Applying BFS on matrix cells starting from source
		Queue<QItem> q = new LinkedList<QItem>();
		q.add(new QItem((int) s.getX(),(int) s.getY(),0, new ArrayList<Point>()));
		visited[(int) s.getX()][(int) s.getY()] = true;
		while (!q.isEmpty()) {
			QItem p = q.remove();
			
			// Destination found
			if (p.row == d.getX() && p.col == d.getY())
				return p.path;
			
			// Moving up
			if (p.row - 1 >= 0 && visited[p.row - 1][p.col] == false) {
				ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
				path.add(new Point(p.row - 1, p.col));
				q.add(new QItem(p.row - 1, p.col, p.dist + 1, path));
				visited[p.row - 1][p.col] = true; 
			}
			
			// Moving down
			if (p.row + 1 < Config.GRID_HEIGHT && visited[p.row + 1][p.col] == false) {
				ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
				path.add(new Point(p.row + 1, p.col));
				q.add(new QItem(p.row + 1, p.col, p.dist + 1, path));
				visited[p.row + 1][p.col] = true; 
			}
			
			// Moving left
			if (p.col - 1 >= 0 && visited[p.row][p.col - 1] == false) {
				ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
				path.add(new Point(p.row, p.col - 1));
				q.add(new QItem(p.row, p.col - 1, p.dist + 1, path));
				visited[p.row][p.col - 1] = true;
			}
			
			// Moving right
			if (p.col + 1 < Config.GRID_WIDTH && visited[p.row][p.col + 1] == false) {
				ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
				path.add(new Point(p.row, p.col + 1));
				q.add(new QItem(p.row, p.col + 1, p.dist + 1, path));
				visited[p.row][p.col + 1] = true;
			}
		}
		
		return new ArrayList<Point>();
	}

	protected void takeDown() {
		System.out.println("Agent"+getAID().getName()+"terminating.");
	}

}
