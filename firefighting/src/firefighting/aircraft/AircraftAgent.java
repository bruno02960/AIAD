package firefighting.aircraft;
/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */


import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;
import firefighting.aircraft.behaviours.DetectEnoughWaterQty;
import firefighting.aircraft.utils.QItem;
import firefighting.nature.Fire;
import firefighting.nature.WaterResource;
import firefighting.ui.GUI;
import firefighting.utils.AircraftMetricsStats;
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
	 * The water tank's quantity of the aircraft agent.
	 */
	private int waterTankQuantity;

	/**
	 * The fuel tank's quantity of the aircraft agent.
	 */
	private int fuelTankQuantity;
	
	/**
	 * TODO - The World agent (REMOVER????)
	 */
	private WorldAgent worldAgent;

	/**
	 * The boolean value to keep the information about if
	 * the aircraft agent it's already attending a fire.
	 */
	public boolean attendindFire = false;
	
	public boolean attendindWater = false;
	
	/**
	 * 
	 * TODO
	 */
	private Fire currentAttendindFire;

	/**
	 * The boolean value that keeps the information about if
	 * the aircraft agent crashed or not.
	 */
	private boolean crashed;

	/**
	 * Auxiliary variable for saving Paths
	 */
	private ArrayList<Point> auxPath = new ArrayList<Point>();

	/**
	 * The execution metrics stats of the aircraft agent.
	 */
	private AircraftMetricsStats aircraftMetricsStats;
	

	private Map<Long, Thread> sleepingThreads;
	

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
		
		this.waterTankQuantity = 0;
		AircraftAgent.maxWaterTankCapacity = randomObject.nextInt(Config.AIRCRAFT_MAX_WATER_TANK_CAPACITY) + 1;

		this.fuelTankQuantity = 0;
		AircraftAgent.maxWaterTankCapacity = randomObject.nextInt(Config.AIRCRAFT_MAX_INITIAL_FUEL_TANK_CAPACITY) + 1;
		
		this.attendindFire = false;
		this.currentAttendindFire = null;
		
		this.crashed = false;
		
		this.aircraftMetricsStats = new AircraftMetricsStats();

		this.sleepingThreads = new ConcurrentHashMap<>();
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
	 * Returns the water tank's quantity of the aircraft agent.
	 * 
	 * @return the water tank's quantity of the aircraft agent
	 */
	public int getWaterTankQuantity() {
		return this.waterTankQuantity;
	}
	
	public void increaseWaterQuantity() {
		this.waterTankQuantity++;
	}
	
	public void decreaseWaterQuantity() {
		this.waterTankQuantity--;
	}

	/**
	 * Returns true if the aircraft agent have its water tank empty and false, otherwise.
	 * 
	 * @return true if the aircraft agent have its water tank empty and false, otherwise
	 */
	public boolean haveEmptyWaterTank() {
		return this.getWaterTankQuantity() == 0;
	}

	public boolean haveFullWaterTank() {
		return this.getWaterTankQuantity() == Config.AIRCRAFT_MAX_WATER_TANK_CAPACITY;
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
	 * Returns the fuel tank's quantity of the aircraft agent.
	 * 
	 * @return the fuel tank's quantity of the aircraft agent
	 */
	public int getFuelTankQuantity() {
		return this.fuelTankQuantity;
	}

	/**
	 * Returns true if the aircraft agent have its fuel tank empty and false, otherwise.
	 * 
	 * @return true if the aircraft agent have its fuel tank empty and false, otherwise
	 */
	public boolean haveEmptyFuelTank() {
		return this.getFuelTankQuantity() == 0;
	}
	
	/**
	 * Returns true if the aircraft agent have enough fuel in the tank to fly to
	 * some destination and false, otherwise.
	 * 
	 * @return true if the aircraft agent have enough fuel in the tank to fly to
	 * 		   some destination and false, otherwise
	 */
	public boolean haveEnoughFuelToDest(int numPositions) {
		return this.getFuelTankQuantity() > numPositions;
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
	
	
	public AircraftMetricsStats getAircraftMetricsStats() {
		return this.aircraftMetricsStats;	
	}
	
	// TODO - ver daqui para baixo
	@Override
	public String toString() {
		return "A" + waterTankQuantity;
	}

	
	
	// Agent's methods:
	
	protected void setup() {
		
		//verify if has enough water
		addBehaviour(new DetectEnoughWaterQty(this, 1000));
			
		if(GUI.isActive()) {
			GUI.log("Agent responder " + getLocalName() + " waiting for CFP Messages...\n");
		}
		else {
			System.out.print("Agent responder " + getLocalName() + " waiting for CFP Messages...\n");
		}
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.MatchPerformative(ACLMessage.CFP) );

		addBehaviour(new ContractNetResponder(this, template) {
			protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
				
				if(GUI.isActive()) {
					GUI.log("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent() + "\n");
				}
				else {
					System.out.print("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent() + "\n");
				}
				int proposal = evaluateAction(cfp.getContent());
				
				if (!attendindFire && proposal < Integer.MAX_VALUE) {
					// We provide a proposal
					
					if(GUI.isActive()) {
						GUI.log("Agent "+getLocalName()+": Proposing "+proposal + "\n");
					}
					else {
						System.out.print("Agent "+getLocalName()+": Proposing "+proposal + "\n");
					}
					ACLMessage propose = cfp.createReply();
					propose.setPerformative(ACLMessage.PROPOSE);
					propose.setContent(String.valueOf(proposal));
			
					aircraftMetricsStats.incNumTotalMessagesSentByThisAircraft();
					
					return propose;
				}
				else {
					// We refuse to provide a proposal
					if(GUI.isActive()) {
						GUI.log("Agent "+getLocalName()+": Refuse\n");
					}
					else {
						System.out.print("Agent "+getLocalName()+": Refuse\n");
					}
					throw new RefuseException("evaluation-failed");
				}
			}

			protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
				if(GUI.isActive()) {
					GUI.log("Agent "+getLocalName()+": Proposal accepted\n");
				}
				else {
					System.out.print("Agent "+getLocalName()+": Proposal accepted\n");
				}
				attendindFire = true;
	
				if (performAction()) {
					if(GUI.isActive()) {
						GUI.log("Agent "+getLocalName()+": Action successfully performed\n");
					}
					else {
						System.out.print("Agent "+getLocalName()+": Action successfully performed\n");
					}
					ACLMessage inform = accept.createReply();
					inform.setPerformative(ACLMessage.INFORM);
				
					return inform;
				}
				else {
					if(GUI.isActive()) {
						GUI.log("Agent "+getLocalName()+": Action execution failed\n");
					}
					else {
						System.out.print("Agent "+getLocalName()+": Action execution failed\n");
					}
					throw new FailureException("unexpected-error");
				}	
			}

			protected void handleRejectProposal(ACLMessage reject) {
				if(GUI.isActive()) {
					GUI.log("Agent "+getLocalName()+": Proposal rejected\n");
				}
				else {
					System.out.print("Agent "+getLocalName()+": Proposal rejected\n");
				}
			}
		} );
	}
	
	public Fire getthisFire(int x, int y) {
		Fire currentFire = null ;
		
		for(int pos = 0; pos < this.worldAgent.getCurrentFires().size() ; pos++)
			if(this.worldAgent.getCurrentFires().get(pos).getWorldObject().getPos().getX() == x && this.worldAgent.getCurrentFires().get(pos).getWorldObject().getPos().getY() == y)
				currentFire = this.worldAgent.getCurrentFires().get(pos);	
		
		return currentFire;
	}

	public int evaluateAction(String message) {
	
		String[] tokens = message.split(" ");
		
		if(!tokens[0].equals("FIRE") && !tokens[1].equals("INTENSITY") && !tokens[3].equals("POS")) {
			System.err.println("Invalid alert message received!");
		}
		
		this.aircraftMetricsStats.incNumTotalFireAlertMessagesReceivedByThisAircraft();
		
		
		int totalDistanceToMake = 0;
		
		int fireIntensity = Integer.parseInt(tokens[2]);
		
		Point firePos = new Point(Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
		
		this.currentAttendindFire = getthisFire(Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
		
		ArrayList<Point> pathToFire = this.pathToFire(firePos);
		
		int distanceToFire = pathToFire.size();
		
		this.auxPath.clear();
		
		this.auxPath.addAll(this.pathToFire(firePos));

		totalDistanceToMake = distanceToFire;
			
		if(waterTankQuantity > fireIntensity/2)
			totalDistanceToMake = distanceToFire; 
		else	
			return Integer.MAX_VALUE;
		
		return totalDistanceToMake;
	}

	@SuppressWarnings("static-access")
	public boolean performAction() {
		if(this.currentAttendindFire != null) {
			this.currentAttendindFire.attended = true;
			
			long startFireAttendTravelTime = System.currentTimeMillis();
			
			// Simulate action execution by generating a random number
			for(int i = 0; i < this.auxPath.size(); i++) {
				try {
					this.sleepingThreads.put(Thread.currentThread().getId(), Thread.currentThread());
					Thread.sleep(2000);
				}
				catch (InterruptedException e) {
					//e.printStackTrace();
				}
				
				this.worldObject.setPos((int)this.auxPath.get(i).getX(), (int)this.auxPath.get(i).getY());
			}
			
			this.aircraftMetricsStats.incNumTotalTravelsByThisAircraft();
			this.aircraftMetricsStats.incTotalTimeInTravelsByThisAircraft(startFireAttendTravelTime);
			
			
			long startPutOutFireTime = System.currentTimeMillis();
			
			while(this.waterTankQuantity > 0) {
				try {
					this.sleepingThreads.put(Thread.currentThread().getId(), Thread.currentThread());
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					//e.printStackTrace();
				}
				
				// Water decrement
				this.waterTankQuantity--;
				this.currentAttendindFire.decreaseIntensity(1);
				
				if(this.currentAttendindFire.getCurrentIntensity() == 0) {
					this.worldAgent.removeFire((int)this.currentAttendindFire.getWorldObject().getPos().getX(), (int)this.currentAttendindFire.getWorldObject().getPos().getY());
					this.currentAttendindFire = null;
					
					this.aircraftMetricsStats.incNumTotalFiresExtinguishedByThisAircraft();
					this.aircraftMetricsStats.incTotalTimeToExtinguishFiresByThisAircraft(startPutOutFireTime);
					
					break;
				}
			}
			
			this.aircraftMetricsStats.incNumTotalFiresAttendedByThisAircraft();
			this.aircraftMetricsStats.incTotalTimeToAttendFiresByThisAircraft(startPutOutFireTime);

			if(this.worldAgent.getCurrentFires().contains(this.currentAttendindFire))
				this.currentAttendindFire.attended = false;
				
			this.attendindFire = false;
			this.auxPath.clear();
			
			return true;
		}
		
		this.attendindFire = false;
		
		return true;
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
        boolean[][] visited = initialiseVisitedMatrix(d);
        
        // Applying BFS on matrix cells starting from source
        Queue<QItem> q = new LinkedList<QItem>();
        q.add(new QItem((int) s.getX(),(int) s.getY(),0, new ArrayList<Point>()));
        visited[(int) s.getX()][(int) s.getY()] = true;
        while (!q.isEmpty()) {
            QItem p = q.remove();
            
            // Destination found
            if (p.row == d.getX() && p.col == d.getY()) {
                ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
                
                if(path.size() == 0) {
                    return new ArrayList<Point>();
                }
                else {
                    path.remove(path.size()-1);
                    return path;
                }
            }
            
            processCellPathToFire(visited, q, p);
        }
        
        return new ArrayList<Point>();
    }
	
	
	public ArrayList<Point> pathToNearestWaterResource() {
	  Point s = this.worldObject.getPos();

	  // To keep track of visited QItems. Marking blocked cells as visited
	  boolean[][] visited = new boolean[Config.GRID_WIDTH][Config.GRID_HEIGHT];
	  for (int i = 0; i < Config.GRID_WIDTH; i++) {
	    for (int j = 0; j < Config.GRID_HEIGHT; j++) {
	      if(worldAgent.getWorldMap()[i][j] == null || worldAgent.getWorldMap()[i][j] instanceof WaterResource)
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
	    if (worldAgent.getWorldMap()[p.row][p.col] != null && worldAgent.getWorldMap()[p.row][p.col] instanceof WaterResource) {
	      ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
	      return path;
	    }

	    processCellPathToFire(visited, q, p);
	  }

	  return new ArrayList<Point>();
	}
		
	/**
	 * Processes a new cell in the BFS process of searching a path to a given fire
	 * @param visited Visited cells
	 * @param q auxiliary queue
	 * @param p processing cell
	 */
	private void processCellPathToFire(boolean[][] visited, Queue<QItem> q, QItem p) {
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


	/**
	 * Initialises a matrix of "visited" cells which marks as visited the cells that are not null
	 * @param d destination point
	 * @return visited matrix
	 */
	private boolean[][] initialiseVisitedMatrix(Point d) {
		boolean[][] visited = new boolean[Config.GRID_WIDTH][Config.GRID_HEIGHT];
        for (int i = 0; i < Config.GRID_WIDTH; i++) {
            for (int j = 0; j < Config.GRID_HEIGHT; j++) {
                if(worldAgent.getWorldMap()[i][j] == null || (i == d.getX() && j == d.getY()))
                    visited[i][j] = false;
                else
                    visited[i][j] = true;
            }
        }
		return visited;
	}

	/**
	 * Behaviour to the aircraft agent takes in the case of take down.
	 */
	protected void takeDown() {
		if(this.isCrashed())
			System.err.println("Mayday, Mayday!!! Aircraft Agent " + getAID().getName() + " crashed and is terminating!");
		else
			System.err.println("Aircraft Agent " + getAID().getName() + " is terminating!");
	}

	public void goToNearestWaterResource() {
		
		this.attendindWater = true;
		
		ArrayList<Point> pathToNearestWaterResource = this.pathToNearestWaterResource();
		
		long startWaterRefillTravelTime = System.currentTimeMillis();
		
		
		for(int i = 0; i < pathToNearestWaterResource.size(); i++) {
			
			try {
				this.sleepingThreads.put(Thread.currentThread().getId(), Thread.currentThread());
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				//e.printStackTrace();
			}
			
			if(this.worldAgent.getWorldMap()[(int)pathToNearestWaterResource.get(i).getX()][(int)pathToNearestWaterResource.get(i).getY()] == null)
				this.worldObject.setPos((int)pathToNearestWaterResource.get(i).getX(), (int)pathToNearestWaterResource.get(i).getY());			
		}

		this.aircraftMetricsStats.incNumTotalTravelsByThisAircraft();
		this.aircraftMetricsStats.incTotalTimeInTravelsByThisAircraft(startWaterRefillTravelTime);

		
		long startWaterRefillTime = System.currentTimeMillis();
		
		while(!this.haveFullWaterTank()) {
			try {
				this.sleepingThreads.put(Thread.currentThread().getId(), Thread.currentThread());
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				//e.printStackTrace();
			}
			
			this.increaseWaterQuantity();
		}		
		
		this.aircraftMetricsStats.incNumTotalWaterRefillsByThisAircraft();
		this.aircraftMetricsStats.incTotalTimeInWaterRefillsByThisAircraft(startWaterRefillTime);
		
		this.attendindWater = false;
	}
	
	public Map<Long, Thread> getSleepingThreads() {
		return this.sleepingThreads;
	}
}