package firefighting.aircraft.behaviours;

import firefighting.aircraft.AircraftAgent;
import jade.core.behaviours.TickerBehaviour;

public class DetectEmptyTankBehaviour extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	AircraftAgent aircraftAgent;
	
	public DetectEmptyTankBehaviour(AircraftAgent aircraftAgent, long period) {
		super(aircraftAgent, period);
		this.aircraftAgent = aircraftAgent;
	}
	
	public AircraftAgent getAircraftAgent() {
		return this.aircraftAgent;
	}
	

	@Override
	protected void onTick() {

		AircraftAgent aircraftAgent = this.getAircraftAgent();
		
		if(aircraftAgent.haveEmptyTank()) {
			
		}
	}

	

}
