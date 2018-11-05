/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */

package firefighting.aircraft;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;
import firefighting.aircraft.utils.QItem;
import firefighting.utils.Config;
import firefighting.world.*;


/**
 * Class responsible for an aircraft agent and its behaviour.
 */
public class AircraftAgent extends Agent {

	// Constants:
	
	/**
	 * The default serial version ID to the selected type.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The maximum water tank's capacity of the aircraft agent.
	 */
	private static int maxWaterTankCapacity;
	
	/**
	 * The maximum fuel tank's capacity of the aircraft agent.
	 */
	private static int maxFuelTankCapacity;
	
	
	
	// Global Instance Variables:
	
	/**
	 * The id of the aircraft Agent.
	 */
	private byte id;

	/**
	 * The world's object of the aircraft agent.
	 */
	private WorldObject worldObject;

	/**
	 * The water tank's capacity of the aircraft agent.
	 */
	private int waterTankCapacity;

	/**
	 * The fuel tank's capacity of the aircraft agent.
	 */
	private int fuelTankCapacity;
	
	/**
	 * TODO - The World agent (REMOVER????)
	 */
	private WorldAgent worldAgent;

	/**
	 * The boolean value to keep the information about if
	 * the aircraft agent it's already attending a fire.
	 */
	private boolean attendindFire;

	/**
	 * The boolean value that keeps the information about if
	 * the aircraft agent crashed or not.
	 */
	private boolean crashed;

	/**
	 * Auxiliary variable for saving Paths
	 */
	private ArrayList<Point> auxPath = new  ArrayList<Point>();
	
	

	// Constructors:
	
	/**
	 * Constructor #1 of the aircraft agent.
	 * 
	 * Creates a new aircraft agent, initialising its id, its world's object and its tank capacity.
	 * 
	 * @param id the aircraft agent's id
	 * @param worldObject the aircraft agent's world object
	 */
	public AircraftAgent(byte id, WorldObject worldObject, WorldAgent worldAgent) {
		this.id = id;
		this.worldObject = worldObject;
	
		this.worldAgent = worldAgent;
		
		Random randomObject = new Random();
		
		this.waterTankCapacity = 0;
		AircraftAgent.maxWaterTankCapacity = randomObject.nextInt(Config.AIRCRAFT_MAX_WATER_TANK_CAPACITY) + 1;

		this.fuelTankCapacity = 0;
		AircraftAgent.maxWaterTankCapacity = randomObject.nextInt(Config.AIRCRAFT_MAX_INITIAL_FUEL_TANK_CAPACITY) + 1;
		
		this.attendindFire = false;
		
		this.crashed = false;
	}


	
	// Basic methods:
	
	/**
	 * Returns the aircraft agent's id.
	 * 
	 * @return the aircraft agent's id
	 */
	private byte getID() {
		return this.id;
	}

	/**
	 * Returns the aircraft agent's world object.
	 * 
	 * @return the aircraft agent's world object
	 */
	public WorldObject getWorldObject() {
		return this.worldObject;
	}
	
	/**
	 * Returns the water tank's capacity of the aircraft agent.
	 * 
	 * @return the water tank's capacity of the aircraft agent
	 */
	public int getWaterTankCapacity() {
		return this.waterTankCapacity;
	}

	/**
	 * Returns true if the aircraft agent have its water tank empty and false, otherwise.
	 * 
	 * @return true if the aircraft agent have its water tank empty and false, otherwise
	 */
	public boolean haveEmptyWaterTank() {
		return this.getWaterTankCapacity() == 0;
	}

	/**
	 * Returns the maximum water tank's capacity of the aircraft agent.
	 * 
	 * @return the maximum water tank's capacity of the aircraft agent
	 */
	public int getMaxWaterTankCapacity() {
		return AircraftAgent.maxWaterTankCapacity;
	}
	
	/**
	 * Returns the fuel tank's capacity of the aircraft agent.
	 * 
	 * @return the fuel tank's capacity of the aircraft agent
	 */
	public int getFuelTankCapacity() {
		return this.fuelTankCapacity;
	}

	/**
	 * Returns true if the aircraft agent have its fuel tank empty and false, otherwise.
	 * 
	 * @return true if the aircraft agent have its fuel tank empty and false, otherwise
	 */
	public boolean haveEmptyFuelTank() {
		return this.getFuelTankCapacity() == 0;
	}
	
	/**
	 * Returns true if the aircraft agent have enough fuel in the tank to fly to
	 * some destination and false, otherwise.
	 * 
	 * @return true if the aircraft agent have enough fuel in the tank to fly to
	 * 		   some destination and false, otherwise
	 */
	public boolean haveEnoughFuelToDest(int numPositions) {
		return this.getFuelTankCapacity() > numPositions;
	}
	
	/**
	 * If the aircraft agent, at some moment, have its fuel tank empty,
	 * it suffer an accident crash and become indefinitely inactive.
	 */
	public void accidentCrash() {
		if(this.haveEmptyWaterTank()) {
			this.crashed = true;
			this.takeDown();
		}
	}

	/**
	 * Returns the maximum fuel tank's capacity of the aircraft agent.
	 * 
	 * @return the maximum fuel tank's capacity of the aircraft agent
	 */
	public int getMaxFuelTankCapacity() {
		return AircraftAgent.maxFuelTankCapacity;
	}
	
	/**
	 * Returns true if the aircraft agent is attending some fire,
	 * at the current moment.
	 * 
	 * @return true if the aircraft agent is attending some fire,
	 * 		   at the current moment
	 */
	public boolean isAttendingFire() {
		return this.attendindFire;
	}
	
	/**
	 * Sets the aircraft agent as attending some fire,
	 * at the current moment.
	 */
	public void attendFire() {
		this.attendindFire = true;
	}
	
	/**
	 * Sets the aircraft agent as not attending any fire,
	 * at the current moment.
	 */
	public void finishAttendingFire() {
		this.attendindFire = false;
	}
	
	/**
	 * Returns the boolean value that keeps the information about if
	 * the aircraft agent crashed or not.
	 * 
	 * @return the boolean value that keeps the information about if
	 * 		   the aircraft agent crashed or not
	 */
	public boolean isCrashed() {
		return this.crashed;
	}
	
	
	// TODO - ver daqui para baixo
	@Override
	public String toString() {
		return "A" + waterTankCapacity;
	}

	
	
	// Agent's methods:
	
	protected void setup() {
		System.out.println("Agent responder " + getLocalName() + " waiting for CFP Messages...");
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.MatchPerformative(ACLMessage.CFP) );

		addBehaviour(new ContractNetResponder(this, template) {
			protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
				System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
				int proposal = evaluateAction(cfp.getContent());
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

	private int evaluateAction(String message) {
	
		String[] tokens = message.split(" ");
		
		if(!tokens[0].equals("FIRE") && !tokens[1].equals("POS")) {
			System.err.println("Invalid alert message received!");
		}
		
		Point point = new Point(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
		
		this.auxPath.addAll(this.pathToFire(point));
		
		//return this.auxPath.size();
		
		return (int) (Math.random() * 10);
		
	}

	private boolean performAction() {
		
		// Simulate action execution by generating a random number
		
		for(int i = 0; i < this.auxPath.size(); i++) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(this.worldAgent.getWorldMap()[(int)this.auxPath.get(i).getX()][(int)this.auxPath.get(i).getY()] != null) {
				Point point = auxPath.get(auxPath.size()-1);
				this.auxPath.clear();
				this.auxPath.addAll(this.pathToFire(point));
			}
			
			this.worldObject.setPos((int)this.auxPath.get(i).getX(), (int)this.auxPath.get(i).getY());
				
		}

		return (Math.random() > 0.2);
	}

	/**
	 * Returns/Calculates the path to a fire in a given location,
	 * in an array list with the path of points from the aircraft agent to the fire location.
	 * 
	 * @param fireLocation location of the fire
	 * 
	 * @return the path to a fire in a given location,
	 * 		   in an array list with the path of points from the aircraft agent to
	 * 		   the fire location.
	 * 
	 */
@SuppressWarnings("unchecked")
	private ArrayList<Point> pathToFire(Point fireLocation) {
        Point s = this.worldObject.getPos();
        Point d = fireLocation;
        
        // To keep track of visited QItems. Marking blocked cells as visited
        boolean[][] visited = new boolean[Config.GRID_WIDTH][Config.GRID_HEIGHT];
        for (int i = 0; i < Config.GRID_WIDTH; i++) {
            for (int j = 0; j < Config.GRID_HEIGHT; j++) {
                if(worldAgent.getWorldMap()[i][j] == null || (i == d.getX() && j == d.getY()))
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
            if (p.row == d.getX() && p.col == d.getY()) {
                ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
                path.remove(path.size()-1);
                return path;
            }
            
            // Moving up
            if (p.row - 1 >= 0 && visited[p.row - 1][p.col] == false) {
                ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
                path.add(new Point(p.row - 1, p.col));
                q.add(new QItem(p.row - 1, p.col, p.dist + 1, path));
                visited[p.row - 1][p.col] = true; 
            }
            
            // Moving down
            if (p.row + 1 < Config.GRID_WIDTH && visited[p.row + 1][p.col] == false) {
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
            if (p.col + 1 < Config.GRID_HEIGHT && visited[p.row][p.col + 1] == false) {
                ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
                path.add(new Point(p.row, p.col + 1));
                q.add(new QItem(p.row, p.col + 1, p.dist + 1, path));
                visited[p.row][p.col + 1] = true;
            }
        }
        
        return new ArrayList<Point>();
    }


	/**
	 * Behaviour to the aircraft agent takes in the case of take down.
	 */
	protected void takeDown() {
		if(this.isCrashed())
			System.out.println("Mayday, Mayday!!! Aircraft Agent " + getAID().getName() + " crashed and is terminating!");
		else
			System.out.println("Aircraft Agent " + getAID().getName() + " is terminating!");
	}
}