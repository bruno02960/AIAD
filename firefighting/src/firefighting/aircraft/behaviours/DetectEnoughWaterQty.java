/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */

package firefighting.aircraft.behaviours;

import java.awt.Point;
import java.util.ArrayList;

import firefighting.aircraft.AircraftAgent;
import firefighting.utils.Config;
import jade.core.behaviours.TickerBehaviour;

public class DetectEnoughWaterQty extends TickerBehaviour { // TODO - TickerBehaviour ou Behaviour simples????

	// Constants:
	/**
	 * The default serial version ID to the selected type.
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	// Global Instance Variables:
	
	/**
	 * The aircraft agent responsible for this behaviour.
	 */
	AircraftAgent aircraftAgent;
	
	
	
	// Constructors:
	
	/**
	 * Constructor #1 of the detection of the empty water tank by an aircraft agent.
	 * 
	 * @param aircraftAgent the aircraft agent responsible for the behaviour
	 * @param period
	 */
	public DetectEnoughWaterQty(AircraftAgent aircraftAgent, long period) {
		super(aircraftAgent, period);
	
		this.aircraftAgent = aircraftAgent;
		
		
	}
	
	
	
	// Basic methods:
	
	/**
	 * Returns the aircraft agent responsible for the behaviour.
	 * 
	 * @return the aircraft agent responsible for the behaviour
	 */
	public AircraftAgent getAircraftAgent() {
		return this.aircraftAgent;
	}
	

	
	// Behaviour methods:
	
	/**
	 * 
	 */
	@Override
	protected void onTick() {

		AircraftAgent aircraftAgent = this.getAircraftAgent();
		
		if(aircraftAgent.getWaterTankQuantity() < (Config.AIRCRAFT_MAX_WATER_TANK_CAPACITY/2) && !this.aircraftAgent.isAttendingFire() ) {
			
			this.aircraftAgent.goToNearestWaterResource();
		}
		
	}



}