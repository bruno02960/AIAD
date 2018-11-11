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
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentNavigableMap;

import firefighting.aircraft.behaviours.DetectEnoughFuelQuantity;
import firefighting.aircraft.behaviours.DetectEnoughWaterQuantity;
import firefighting.aircraft.utils.QItem;
import firefighting.firestation.FireStationAgent;
import firefighting.graphics.GraphicUserInterface;
import firefighting.nature.Fire;
import firefighting.nature.WaterResource;
import firefighting.utils.Config;
import firefighting.world.WorldAgent;
import firefighting.world.WorldObject;
import firefighting.world.utils.WorldObjectType;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

/**
 * Class responsible for an aircraft agent and its behaviour.
 */
public class AircraftAgent extends Agent {

	// Constants:
	
	/**
	 * The serial version UID.
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
	 * The ID of the aircraft Agent.
	 */
	private byte id;

	/**
	 * The world's object of the aircraft agent.
	 */
	private WorldObject worldObject;

	/**
	 * The perception of the aircraft agent about the world agent.
	 */
	private WorldAgent worldAgent;
	
	/**
	 * The water tank's quantity of the aircraft agent.
	 */
	private int waterTankQuantity;

	/**
	 * The fuel tank's quantity of the aircraft agent.
	 */
	private int fuelTankQuantity;
	
	/**
	 * The boolean value to keep the information about if the aircraft agent it's already busy,
	 * attending a fire or refilling its water tank in a water resource.
	 */
	private boolean busy;
	
	/**
	 * The fire that the aircraft agent is currently attending.
	 */
	private Fire currentAttendingFire;
	
	/**
	 * The current fires that the aircraft agent extinguished.
	 */
	private ArrayList<Fire> currentExtiguishedFires;
	
	/**
	 * The path of the current travel of the aircraft agent.
	 */
	private ArrayList<Point> travelPath = new ArrayList<Point>();
	
	
	
	// Constructors:
	
	/**
	 * Constructor #1 of the aircraft agent.
	 * 
	 * Creates a new aircraft agent, initialising its ID, its world object and its tank capacity.
	 * 
	 * @param id the ID of the aircraft agent
	 * @param worldObject the world object of the aircraft agent
	 */
	public AircraftAgent(byte id, WorldObject worldObject, WorldAgent worldAgent) {
		this.id = id;
		this.worldObject = worldObject;
	
		this.worldAgent = worldAgent;
		
		this.waterTankQuantity = 0;
		AircraftAgent.maxWaterTankCapacity = Config.AIRCRAFT_MAX_WATER_TANK_CAPACITY;

		this.fuelTankQuantity = Config.AIRCRAFT_MAX_INITIAL_FUEL_TANK_CAPACITY;
		AircraftAgent.maxFuelTankCapacity = Config.AIRCRAFT_MAX_FINAL_FUEL_TANK_CAPACITY;
		
		this.busy = false;
		
		this.currentAttendingFire = null;
		this.currentExtiguishedFires = new ArrayList<Fire>();
		
		this.travelPath = new ArrayList<Point>();
	}


	
	// Basic methods:
	
	/**
	 * Returns the ID of the aircraft agent.
	 * 
	 * @return the ID of the aircraft agent
	 */
	public byte getID() {
		return this.id;
	}
	
	/**
	 * Returns the world object of the aircraft agent.
	 * 
	 * @return the world object of the aircraft agent
	 */
	public WorldObject getWorldObject() {
		return this.worldObject;
	}
	
	/**
	 * Returns the perception of the aircraft agent about the world agent.
	 * 
	 * @return the perception of the aircraft agent about the world agent
	 */
	public WorldAgent getWorldAgent() {
		return this.worldAgent;
	}
	
	/**
	 * Returns the water tank's quantity of the aircraft agent.
	 * 
	 * @return the water tank's quantity of the aircraft agent
	 */
	public int getWaterTankQuantity() {
		return this.waterTankQuantity;
	}
	
	/**
	 * Increases the aircraft agent's water tank quantity by one.
	 */
	public void increaseWaterTankQuantity() {
		if(!this.haveFullWaterTank())
			this.waterTankQuantity++;
	}
	
	/**
	 * Decreases the aircraft agent's water tank quantity by one.
	 */
	public void decreaseWaterTankQuantity() {
		if(!this.haveEmptyWaterTank())
			this.waterTankQuantity--;
	}
	
	/**
	 * Returns true if the aircraft agent have its water tank empty and false, otherwise.
	 * 
	 * @return true if the aircraft agent have its water tank empty and false, otherwise
	 */
	public boolean haveEmptyWaterTank() {
		return this.waterTankQuantity == 0;
	}

	/**
	 * Returns true if the aircraft agent have its water tank full and false, otherwise.
	 * 
	 * @return true if the aircraft agent have its water tank full and false, otherwise
	 */
	public boolean haveFullWaterTank() {
		return this.waterTankQuantity == AircraftAgent.maxWaterTankCapacity;
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
	 * Increases the aircraft agent's fuel tank quantity by one.
	 */
	public void increaseFuelTankQuantity() {
		if(!this.haveFullFuelTank())
			this.fuelTankQuantity++;
	}
	
	/**
	 * Decreases the aircraft agent's fuel tank quantity by one.
	 */
	public void decreaseFuelTankQuantity() {
		if(!this.haveEmptyFuelTank())
			this.fuelTankQuantity--;
	}

	/**
	 * Returns true if the aircraft agent have its fuel tank empty and false, otherwise.
	 * 
	 * @return true if the aircraft agent have its fuel tank empty and false, otherwise
	 */
	public boolean haveEmptyFuelTank() {
		return this.fuelTankQuantity == 0;
	}

	/**
	 * Returns true if the aircraft agent have its fuel tank full and false, otherwise.
	 * 
	 * @return true if the aircraft agent have its fuel tank full and false, otherwise
	 */
	public boolean haveFullFuelTank() {
		return this.fuelTankQuantity == this.getMaxFuelTankCapacity();
	}
	
	/**
	 * Returns the distance from a fire position to the fire station agent.
	 * 
	 * @return the distance from a fire position to the fire station agent
	 */
	public int getDistanceFromFireToFireStationAgent(Point firePos) {
		Point fireStationPos = this.getWorldAgent().getFireStationAgent().getWorldObject().getPos();
		
		return (int) (Math.abs(firePos.getX() - fireStationPos.getX()) + Math.abs(firePos.getY() - fireStationPos.getY()));
	}
	
	/**
	 * TODO
	 * 
	 * @param numStepsToThePretendedDestination
	 * @param pretendedDestination
	 * 
	 * @return
	 */
	public boolean haveEnoughFuelToTravel(int numStepsToThePretendedDestination, Point pretendedDestination) {
		Point fireStationPos = this.getWorldAgent().getFireStationAgent().getWorldObject().getPos();
		
		int numStepsFromPretendedDestinationToFireStation = (int) (Math.abs(pretendedDestination.getX() - fireStationPos.getX()) + Math.abs(pretendedDestination.getY() - fireStationPos.getY()));
		
		Random randomObject = new Random();
		
		boolean optimisticThought = randomObject.nextBoolean();
		int numTotalStepsToTravelSecure;
		
		if(optimisticThought)
			numTotalStepsToTravelSecure = Config.AIRCRAFT_FUEL_TANK_CAPACITY_SECURE_TRAVEL_FACTOR + numStepsToThePretendedDestination + numStepsFromPretendedDestinationToFireStation;
		else
			numTotalStepsToTravelSecure = numStepsToThePretendedDestination + numStepsFromPretendedDestinationToFireStation;
		
		
		GraphicUserInterface.log("\n");
		GraphicUserInterface.log("GASOLINAAA DO AIRCRAFT" + this.getID() +" - " + this.getFuelTankQuantity()+"\n");
		GraphicUserInterface.log("PASSSSOOOOOOS SEGUROS DO AIRCRAFT" + this.getID() + " - " + numTotalStepsToTravelSecure + "\n");
		GraphicUserInterface.log("\n");
		
		return this.getFuelTankQuantity() >= numTotalStepsToTravelSecure;
	}
	
	/**
	 * If the aircraft agent, at some moment, have its fuel tank empty,
	 * it suffer an accident crash and become indefinitely inactive.
	 */
	public void accidentCrash() {
		if(this.haveEmptyFuelTank()) {
			GraphicUserInterface.log("Aircraft agent responder " + getID() + ": Mayday!!! Mayday!!! I'm crashing!!! BOOOOM!!!\n\n");
		
			worldAgent.getAircraftAgents().remove((int) this.getID());
			
			Point crashPos = this.getWorldObject().getPos();
			
			WorldObject crashedWorldObject = new WorldObject(WorldObjectType.AIRCRAFT, crashPos);
			CrashedAircraft crashedAircraft = new CrashedAircraft(this.getID(), crashedWorldObject);
			
			WorldAgent worldAgent = this.getWorldAgent();
			
			worldAgent.getCrashedAircrafts().add(crashedAircraft);
			worldAgent.getWorldMap()[(int) crashPos.getX()][(int) crashPos.getY()] = crashedAircraft;			
		
			this.worldObject = null;

			this.takeDown();
			this.doDelete();
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
	 * Returns true if the aircraft agent it's already busy, at the current moment,
	 * attending a fire or refilling its water tank in a water resource,
	 * or false, otherwise.
	 * 
	 * @return true if the aircraft agent it's already busy, at the current moment,
	 * 		   attending a fire or refilling its water tank in a water resource,
	 * 		   or false, otherwise
	 */
	public boolean isBusy() {
		return this.busy;
	}
	
	/**
	 * Sets the busy status value true or false, given a boolean to keep the information that
	 * allows to know if the aircraft agent it's already busy, at the current moment,
	 * attending a fire or refilling its water tank in a water resource.
	 * 
	 * @param attendedValue a given a boolean to keep the information that
	 * 						allows to know if the aircraft agent it's already busy, at the current moment,
	 * 						attending a fire or refilling its water tank in a water resource
	 */
	public void setBusy(boolean busyValue) {
		this.busy = busyValue;
	}
	
	/**
	 * Returns the fire that the aircraft agent is currently attending.
	 * 
	 * @return the fire that the aircraft agent is currently attending
	 */
	public Fire getCurrentAttendingFire() {
		return this.currentAttendingFire;
	}
	
	/**
	 * Returns the current fires that the aircraft agent extinguished.
	 * 
	 * @return the current fires that the aircraft agent extinguished
	 */
	public ArrayList<Fire> getCurrentExtiguishedFires() {
		return this.currentExtiguishedFires;
	}
	
	/**
	 * Returns the path of the current travel of the aircraft agent.
	 * 
	 * @return the path of the current travel of the aircraft agent
	 */
	public ArrayList<Point> getTravelPath() {
		return this.travelPath;
	}
	
	/**
	 * TODO
	 * 
	 * @param message
	 * 
	 * @return
	 */
	public int evaluateActionToPutOutOfFire(String message) {
		
		String[] tokens = message.split(" ");

		boolean msgTemplateCheck1 = !tokens[0].equalsIgnoreCase("FIRE");
		boolean msgTemplateCheck2 = !tokens[1].equalsIgnoreCase("ALARM!!!");
		boolean msgTemplateCheck3 = !tokens[2].equalsIgnoreCase("-");
		boolean msgTemplateCheck4 = !tokens[3].equalsIgnoreCase("Fire");
		boolean msgTemplateCheck5 = !tokens[4].equalsIgnoreCase("[");
		boolean msgTemplateCheck6 = !tokens[5].equalsIgnoreCase("ID:");
		boolean msgTemplateCheck7 = !tokens[7].equalsIgnoreCase("Intensity:");
		boolean msgTemplateCheck8 = !tokens[9].equalsIgnoreCase("Position:");
		boolean msgTemplateCheck9 = !tokens[11].equalsIgnoreCase("]");

		if(msgTemplateCheck1 && msgTemplateCheck2 && msgTemplateCheck3 && msgTemplateCheck4 && msgTemplateCheck5 && msgTemplateCheck6 
				&& msgTemplateCheck7 && msgTemplateCheck8 && msgTemplateCheck9)
					System.err.println("Invalid fire alert message received!");
		
		// Extracting the information/parameters about the fire alert message received
		int fireIntensity = Integer.parseInt(tokens[8]);
		
		String firePosString = tokens[10];
		
		int firePosX = Integer.parseInt(firePosString.substring(1, 2));
		int firePosY = Integer.parseInt(firePosString.substring(3, 4));
		
		Point firePos = new Point(firePosX, firePosY);
		
		// Calculating the path to fire
		ArrayList<Point> pathToFire = this.pathToFire(firePos);
		
		int distanceToFire = pathToFire.size() + 1;
		
		this.travelPath.clear();
		this.travelPath.addAll(this.pathToFire(firePos));			
		
		// Returning the proposal's value
		Random random = new Random();
		
		boolean optimistThought = random.nextBoolean();
		
		if(waterTankQuantity > (fireIntensity / 2))
			if(optimistThought)
				return distanceToFire;
			else
				return distanceToFire + this.getDistanceFromFireToFireStationAgent(firePos);
		else
			return Integer.MAX_VALUE;	
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean performActionToPutOutOfFire() {
				
		ArrayList<Point> travelPathToFire = this.getTravelPath();
		
		int totalNumStepsToFire = travelPathToFire.size();
		
		GraphicUserInterface.log("ESTOOOOOOUUUUUUUUUUUU A " + totalNumStepsToFire + " PASSOOOOOOOOOS DO FOGOOOOOOOOOO");
		
		if(totalNumStepsToFire == 0) {
			if(this.currentAttendingFire == null) {
				Object[][] worldMap = this.getWorldAgent().getWorldMap();
				Point aircraftPos = this.getWorldObject().getPos();
				
				if((int)aircraftPos.getX() > 0 && (int)aircraftPos.getX() < Config.GRID_WIDTH && (int)aircraftPos.getY() > 0 && (int)aircraftPos.getY() < Config.GRID_HEIGHT) {
					if(worldMap[(int)aircraftPos.getX() + 1][(int)aircraftPos.getY()] instanceof Fire) {
						
						if(this.currentAttendingFire == null && !this.isBusy()) {
							Fire fireToAttend = (Fire) worldMap[(int)(int)aircraftPos.getX() + 1][(int)aircraftPos.getY()];
							fireToAttend.setAttended(true);
							
							this.currentAttendingFire = fireToAttend;
							this.setBusy(true);
						}
					}
					else if(worldMap[(int)aircraftPos.getX() - 1][(int)aircraftPos.getY()] instanceof Fire) {
						
						if(this.currentAttendingFire == null && !this.isBusy()) {
							Fire fireToAttend = (Fire) worldMap[(int)(int)aircraftPos.getX() - 1][(int)aircraftPos.getY()];
							fireToAttend.setAttended(true);
							
							this.currentAttendingFire = fireToAttend;
							this.setBusy(true);
						}
					}
					else if(worldMap[(int)aircraftPos.getX()][(int)aircraftPos.getY() + 1] instanceof Fire) {
						
						if(this.currentAttendingFire == null && !this.isBusy()) {
							Fire fireToAttend = (Fire) worldMap[(int)(int)aircraftPos.getX()][(int)aircraftPos.getY() + 1];
							fireToAttend.setAttended(true);
							
							this.currentAttendingFire = fireToAttend;
							this.setBusy(true);
						}
					}
					else if(worldMap[(int)aircraftPos.getX()][(int)aircraftPos.getY() - 1] instanceof Fire) {
						
						if(this.currentAttendingFire == null && !this.isBusy()) {
							Fire fireToAttend = (Fire) worldMap[(int)(int)aircraftPos.getX()][(int)aircraftPos.getY() - 1];
							fireToAttend.setAttended(true);
							
							this.currentAttendingFire = fireToAttend;
							this.setBusy(true);
						}
					}
				}
			}
				
			if(this.currentAttendingFire != null) {
				
				// Simulates the put out of fire process
				while(!this.haveEmptyWaterTank()) {
					
					// Simulates 1s for each unity of water used in the process
					try {
						Thread.sleep(1000);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					// The aircraft agent putting out of fire		
					this.decreaseWaterTankQuantity();
					this.currentAttendingFire.decreaseIntensity();
					
					if(!this.currentAttendingFire.isActive()) {
						
						byte currentAttendingFireID = this.currentAttendingFire.getID();
						
						WorldAgent worldAgent = this.getWorldAgent();
						ConcurrentNavigableMap<Integer, Fire> fires = worldAgent.getCurrentFires();
						
						if(fires.containsKey((int) currentAttendingFireID)) {
							this.getWorldAgent().getCurrentFires().get((int) currentAttendingFireID).setAttended(false);
							this.worldAgent.fireExtinguished(currentAttendingFireID);
						}
						
						this.currentAttendingFire = null;
						break;
					}
				}
			}
			
			if(this.currentAttendingFire != null) {
				byte currentAttendingFireID = this.currentAttendingFire.getID();
				
				if(this.worldAgent.getCurrentFires().containsKey((int)currentAttendingFireID))
					this.worldAgent.getCurrentFires().get((int)currentAttendingFireID).setAttended(false);
			}
			
			this.setBusy(false);
			this.travelPath.clear();
		}
		
		if(totalNumStepsToFire > 0) {
			
			Point fireToAttendPos = travelPathToFire.get(totalNumStepsToFire - 1);
			
			GraphicUserInterface.log("EUUUUUU QUEROOOO IR AO FOOOOOOOGGOOOOOOOOO MAS NAO SEI SE TENHO GASOLINA");
			
			if(this.haveEnoughFuelToTravel(totalNumStepsToFire, fireToAttendPos)) {
				Object[][] worldMap = this.worldAgent.getWorldMap();
				
				GraphicUserInterface.log("EUUUUUU VOUUUUUU AO FOOOOOOOGGOOOOOOOOO");
				
				if(worldMap[(int)fireToAttendPos.getX()][(int)fireToAttendPos.getY()] instanceof Fire) {
					
					if(this.currentAttendingFire == null && !this.isBusy()) {
						Fire fireToAttend = (Fire) worldMap[(int)fireToAttendPos.getX()][(int)fireToAttendPos.getY()];
						fireToAttend.setAttended(true);
						
						this.currentAttendingFire = fireToAttend;
						this.setBusy(true);
					}
					
					int numSteps = 0;
					
					try {
						for(Point nextStep: travelPathToFire) {
						
							if(this.currentAttendingFire == null)
								break;
							
							boolean lastStep = (numSteps + 1) == totalNumStepsToFire ? true : false;
					
							// Simulates 1s for each step made of the calculated path
							try {
								Thread.sleep(1000);
							}
							catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							if(this.worldAgent.getWorldMap()[(int)nextStep.getX()][(int)nextStep.getY()] == null)
								this.worldObject.setPos((int)nextStep.getX(), (int)nextStep.getY());			
							else
								if(!lastStep) {
									this.getTravelPath().clear();
									this.performActionToPutOutOfFire();
								}
							
							this.decreaseFuelTankQuantity();
							
							if(this.haveEmptyFuelTank())
								this.accidentCrash();
							
							numSteps++;
						}
					}
					catch (ConcurrentModificationException e) {
						GraphicUserInterface.log("Interference on the radar!!!\n\n");
						this.accidentCrash();
					}						
				}
			}
			
			if(this.currentAttendingFire != null) {
				
				// Simulates the put out of fire process
				while(!this.haveEmptyWaterTank()) {
					
					// Simulates 1s for each unity of water used in the process
					try {
						Thread.sleep(1000);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					// The aircraft agent putting out of fire		
					this.decreaseWaterTankQuantity();
					this.currentAttendingFire.decreaseIntensity();
					
					if(!this.currentAttendingFire.isActive()) {
						
						byte currentAttendingFireID = this.currentAttendingFire.getID();
						
						WorldAgent worldAgent = this.getWorldAgent();
						ConcurrentNavigableMap<Integer, Fire> fires = worldAgent.getCurrentFires();
						
						if(fires.containsKey((int) currentAttendingFireID)) {
							this.getWorldAgent().getCurrentFires().get((int) currentAttendingFireID).setAttended(false);
							this.worldAgent.fireExtinguished(currentAttendingFireID);
						}
						
						this.currentAttendingFire = null;
						break;
					}
				}
			}
			
			if(this.currentAttendingFire != null) {
				byte currentAttendingFireID = this.currentAttendingFire.getID();
				
				if(this.worldAgent.getCurrentFires().containsKey((int)currentAttendingFireID))
					this.worldAgent.getCurrentFires().get((int)currentAttendingFireID).setAttended(false);
			}
			
			this.setBusy(false);
			this.travelPath.clear();
		}
		
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
        q.add(new QItem((int) s.getX(),(int) s.getY(), 0, new ArrayList<Point>()));
        visited[(int) s.getX()][(int) s.getY()] = true;
        
        while (!q.isEmpty()) {
            QItem p = q.remove();
            
            // Destination found
            if(worldAgent.getWorldMap()[p.row][p.col] != null && worldAgent.getWorldMap()[p.row][p.col] instanceof Fire) {
                ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
                
				return path;
            }
            
            processCellPath(visited, q, p);
        }
        
        return new ArrayList<Point>();
    }
	
	
	/**
	 * Returns a matrix of "visited" cells which marks as visited the cells that are not null.
	 * 
	 * @param d destination point
	 * 
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
	 * Returns a basic information about the aircraft agent, most specifically its water tank's quantity as a string.
	 */
	@Override
	public String toString() {
		return "Aircraft [F: " + this.getFuelTankQuantity() + " | W: " + this.getWaterTankQuantity() + "]";
	}

	
	
	// Agent's methods:
	
	protected void setup() {
		
		// In every 1s, verifies if the aircraft agent have enough water in its fuel tank
		addBehaviour(new DetectEnoughFuelQuantity(this, 1000));
		
		// In every 1s, verifies if the aircraft agent have enough water in its water tank		
		addBehaviour(new DetectEnoughWaterQuantity(this, 1000));
		
		GraphicUserInterface.log("Aircraft agent responder " + this.getID() + " - " + getLocalName() + " is waiting for CFP (Call For Proposal) messages...\n");
		
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.MatchPerformative(ACLMessage.CFP));		

		addBehaviour(new ContractNetResponder(this, template) {
			
			// The serial version UID.
			private static final long serialVersionUID = 1L;

			protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
				GraphicUserInterface.log("\n");
				GraphicUserInterface.log("Aircraft agent responder " + getID() + " - " + getLocalName() + ": CFP (Call For Proposal) message received from fire station " + cfp.getSender().getName() + ".\n");
				GraphicUserInterface.log("\n");
				GraphicUserInterface.log("Action is " + cfp.getContent() + "\n");
				GraphicUserInterface.log("\n");
				
				int proposalValue = (int) evaluateActionToPutOutOfFire(cfp.getContent());
				
				if(!isBusy() && proposalValue < Integer.MAX_VALUE) {
					
					// The aircraft agent provide a proposal
					GraphicUserInterface.log("\n");
					GraphicUserInterface.log("Aircraft agent responder " + getID() + " - " + getLocalName() + ": Proposing value " + proposalValue + "!\n");
				
					ACLMessage propose = cfp.createReply();
					
					propose.setPerformative(ACLMessage.PROPOSE);
					propose.setContent(String.valueOf(proposalValue));

					return propose;
				}
				else {
					// The aircraft agent refuse to provide a proposal
					GraphicUserInterface.log("Aircraft agent responder " + getID() + " - " + getLocalName() + ": Refuse to provide a proposal!\n");
					throw new RefuseException("evaluation-failed");
				}
			}

			protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
				GraphicUserInterface.log("Aircraft Agent Responder " + getID() + " - " + getLocalName() + ": Proposal accepted!\n");
				
				// The aircraft agent's proposal was accepted by fire station
				if(performActionToPutOutOfFire()) {
					GraphicUserInterface.log("Aircraft agent responder " + getID() + " - " + getLocalName() + ": Action successfully performed!\n");
				
					ACLMessage inform = accept.createReply();
					inform.setPerformative(ACLMessage.INFORM);
					
					return inform;
				}
				else {
					GraphicUserInterface.log("Aircraft agent responder " + getID() + " - " + getLocalName() + ": Action execution failed!\n");
					throw new FailureException("unexpected-error");
				}	
			}

			@SuppressWarnings("unused")
			protected void handleRejectProposal(ACLMessage reject) {
				GraphicUserInterface.log("Aircraft agent responder " + getID() + " - " + getLocalName() + ": Proposal rejected!\n");
			}
		});
	}
	
	/**
	 * Processes a new cell in the BFS process of searching a path to a given fire TODO
	 * 
	 * @param visited Visited cells
	 * @param q auxiliary queue
	 * @param p processing cell
	 */
	private void processCellPath(boolean[][] visited, Queue<QItem> q, QItem p) {
		
		// Moving up
		if (p.row - 1 >= 0 && visited[p.row - 1][p.col] == false) {
		    @SuppressWarnings("unchecked")
			ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
		    
		    path.add(new Point(p.row - 1, p.col));
		    q.add(new QItem(p.row - 1, p.col, p.dist + 1, path));
		    
		    visited[p.row - 1][p.col] = true; 
		}
		
		// Moving down
		if (p.row + 1 < Config.GRID_WIDTH && visited[p.row + 1][p.col] == false) {
			@SuppressWarnings("unchecked")
			ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
		    
			path.add(new Point(p.row + 1, p.col));
		    q.add(new QItem(p.row + 1, p.col, p.dist + 1, path));
		    
		    visited[p.row + 1][p.col] = true; 
		}
		
		// Moving left
		if (p.col - 1 >= 0 && visited[p.row][p.col - 1] == false) {
			@SuppressWarnings("unchecked")
			ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
		    
			path.add(new Point(p.row, p.col - 1));
		    q.add(new QItem(p.row, p.col - 1, p.dist + 1, path));
		    
		    visited[p.row][p.col - 1] = true;
		}
		
		// Moving right
		if (p.col + 1 < Config.GRID_HEIGHT && visited[p.row][p.col + 1] == false) {
			@SuppressWarnings("unchecked")
			ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
		    
			path.add(new Point(p.row, p.col + 1));
		    q.add(new QItem(p.row, p.col + 1, p.dist + 1, path));
		    
		    visited[p.row][p.col + 1] = true;
		}
	}

	/**
	 * TODO
	 * @return
	 */
	public ArrayList<Point> pathToFireStationAgent() {
		Point s = this.worldObject.getPos();

		// To keep track of visited QItems. Marking blocked cells as visited
		boolean[][] visited = new boolean[Config.GRID_WIDTH][Config.GRID_HEIGHT];
	  
		for(int i = 0; i < Config.GRID_WIDTH; i++) {
			for(int j = 0; j < Config.GRID_HEIGHT; j++) {
				if(worldAgent.getWorldMap()[i][j] == null || worldAgent.getWorldMap()[i][j] instanceof FireStationAgent)
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
			if (worldAgent.getWorldMap()[p.row][p.col] != null && worldAgent.getWorldMap()[p.row][p.col] instanceof FireStationAgent) {
				@SuppressWarnings("unchecked")
				ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
			
				return path;
			}

			processCellPath(visited, q, p);
		}

		return new ArrayList<Point>();
	}

	/**
	 * TODO
	 */
	public void goRefillFuelTank() {
		
		this.setBusy(true);
		
		ArrayList<Point> pathToFireStationAgent = this.pathToFireStationAgent();
		
		int totalNumSteps = pathToFireStationAgent.size();
	
		if(totalNumSteps > 0) {
			Point fireStationAgentPos = pathToFireStationAgent.get(totalNumSteps - 1);
			
			Object[][] worldMap = this.worldAgent.getWorldMap();
			
			if(worldMap[(int)fireStationAgentPos.getX()][(int)fireStationAgentPos.getY()] instanceof FireStationAgent) {
				
				int numSteps = 0;
				
				for(Point nextStep: pathToFireStationAgent) {
					
					boolean lastStep = (numSteps + 1) == totalNumSteps ? true : false;
					
					// Simulates 1s for each step made of the calculated path
					try {
						Thread.sleep(1000);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					if(this.worldAgent.getWorldMap()[(int)nextStep.getX()][(int)nextStep.getY()] == null)
						this.worldObject.setPos((int)nextStep.getX(), (int)nextStep.getY());			
					else
						if(!lastStep)
							this.goRefillFuelTank();
					
					this.decreaseFuelTankQuantity();
					
					if(this.haveEmptyFuelTank()) {
						this.accidentCrash();
						
						break;
					}
					
					numSteps++;
				}
				
				while(!this.haveFullFuelTank()) {
					
					// Simulates 1s for each water unity filled
					try {
						Thread.sleep(1000);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					this.increaseFuelTankQuantity();
				}	
			}
		}
		
		this.setBusy(false);
	}
	
	/**
	 * TODO
	 * @return
	 */
	public ArrayList<Point> pathToNearestWaterResource() {
		Point s = this.worldObject.getPos();

		// To keep track of visited QItems. Marking blocked cells as visited
		boolean[][] visited = new boolean[Config.GRID_WIDTH][Config.GRID_HEIGHT];
	  
		for(int i = 0; i < Config.GRID_WIDTH; i++) {
			for(int j = 0; j < Config.GRID_HEIGHT; j++) {
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
				if(!((WaterResource) worldAgent.getWorldMap()[p.row][p.col]).isEmpty()) {
					@SuppressWarnings("unchecked")
					ArrayList<Point> path = (ArrayList<Point>) p.path.clone();
				
					return path;
				}
			}

			processCellPath(visited, q, p);
		}

		return new ArrayList<Point>();
	}

	/**
	 * TODO
	 */
	public void goRefillWaterTank() {
		
		this.setBusy(true);
		
		ArrayList<Point> pathToNearestWaterResource = this.pathToNearestWaterResource();
		
		int totalNumSteps = pathToNearestWaterResource.size();
	
		if(totalNumSteps > 0) {
			
			Point nearestWaterResourcePos = pathToNearestWaterResource.get(totalNumSteps - 1);

			if(this.haveEnoughFuelToTravel(totalNumSteps, nearestWaterResourcePos)) {
				Object[][] worldMap = this.worldAgent.getWorldMap();
				
				if(worldMap[(int)nearestWaterResourcePos.getX()][(int)nearestWaterResourcePos.getY()] instanceof WaterResource) {
					
					WaterResource nearestWaterResource = (WaterResource) worldMap[(int)nearestWaterResourcePos.getX()][(int)nearestWaterResourcePos.getY()];
					
					int numSteps = 0;
					
					for(Point nextStep: pathToNearestWaterResource) {
						
						boolean lastStep = (numSteps + 1) == totalNumSteps ? true : false;
						
						// Simulates 1s for each step made of the calculated path
						try {
							Thread.sleep(1000);
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						if(this.worldAgent.getWorldMap()[(int)nextStep.getX()][(int)nextStep.getY()] == null)
							this.worldObject.setPos((int)nextStep.getX(), (int)nextStep.getY());			
						else
							if(!lastStep)
								this.goRefillWaterTank();
							
						this.decreaseFuelTankQuantity();
						
						if(this.haveEmptyFuelTank())
							this.accidentCrash();
						
						numSteps++;
					}
					
					while(!this.haveFullWaterTank() && !nearestWaterResource.isEmpty()) {
						
						// Simulates 1s for each water unity filled
						try {
							Thread.sleep(1000);
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						this.increaseWaterTankQuantity();
						nearestWaterResource.decreaseQuantity();
					}	
				}
			}
		}
		
		this.setBusy(false);
	}
}