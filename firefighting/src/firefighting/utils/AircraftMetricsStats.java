/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */
package firefighting.utils;

/**
 * Class with some statistics about the execution and behaviour of the aircraft agent.
 */
public class AircraftMetricsStats {
	
	// 1) Countable metrics:
	
	public int numTotalFiresAttendedByThisAircraft;
	
	public int numTotalFiresExtinguishedByThisAircraft;

	public int numTotalRefillsByThisAircraft;
	
	public int numTotalWaterRefillsByThisAircraft;
	
	public int numTotalFuelRefillsByThisAircraft;
	
	public int numTotalTravelsByThisAircraft;
	
	
	
	// 2) Time metrics:
	
	public long totalTimeToAttendFiresByThisAircraft;
	
	public long totalTimeToExtinguishFiresByThisAircraft;
		
	public long totalTimeToRefillsByThisAircraft;
		
	public long totalTimeToWaterRefillsByThisAircraft;
		
	public long totalTimeToFuelRefillsByThisAircraft;
	
	public long totalTimeInTravelsByThisAircraft;
	
	
	
	// 3) Messages exchanged (received/sent) metrics:
	
	public int numTotalMessagesExchangedByThisAircraft;
	
	public int numTotalMessagesReceivedByThisAircraft;
	
	public int numTotalMessagesSentByThisAircraft;
	
	public int numTotalFireAlertMessagesReceivedByThisAircraft;
	
	
	/**
	 * 
	 */
	public AircraftMetricsStats() {
		
		// 1) Countable metrics:
		
		this.numTotalFiresAttendedByThisAircraft = 0;
		
		this.numTotalFiresExtinguishedByThisAircraft = 0;
		
		this.numTotalRefillsByThisAircraft = 0;
		
		this.numTotalWaterRefillsByThisAircraft = 0;
		
		this.numTotalFuelRefillsByThisAircraft = 0;
		
		this.numTotalTravelsByThisAircraft = 0;
		
		
		
		// 2) Time metrics:
		
		this.totalTimeToAttendFiresByThisAircraft = 0L;
		
		this.totalTimeToExtinguishFiresByThisAircraft = 0L;
		
		this.totalTimeToRefillsByThisAircraft = 0L;
		
		this.totalTimeToWaterRefillsByThisAircraft = 0L;
		
		this.totalTimeToFuelRefillsByThisAircraft = 0L;
		
		this.totalTimeInTravelsByThisAircraft = 0L;
			
		
		
		// 3) Messages exchanged (received/sent) metrics:
		
		this.numTotalMessagesExchangedByThisAircraft = 0;
		
		this.numTotalMessagesReceivedByThisAircraft = 0;
		
		this.numTotalMessagesSentByThisAircraft = 0;
		
		this.numTotalFireAlertMessagesReceivedByThisAircraft = 0;
	}
	
	
	
	// Methods:
	
	// 1) Countable metrics:
	
	public int getNumTotalFiresAttendedByThisAircraft() {
		return this.numTotalFiresAttendedByThisAircraft;
	}
	
	public void incNumTotalFiresAttendedByThisAircraft() {
		this.numTotalFiresAttendedByThisAircraft++;
	}
	
	public int getNumTotalFiresExtinguishedByThisAircraft() {
		return this.numTotalFiresExtinguishedByThisAircraft;
	}
	
	public void incNumTotalFiresExtinguishedByThisAircraft() {
		this.numTotalFiresExtinguishedByThisAircraft++;
	}
	
	public int getNumTotalRefillsByThisAircraft() {
		return this.numTotalRefillsByThisAircraft;
	}
	
	public void incNumTotalRefillsByThisAircraft() {
		this.numTotalRefillsByThisAircraft++;
	}
	
	public int getNumTotalWaterRefillsByThisAircraft() {
		return this.numTotalWaterRefillsByThisAircraft;
	}
	
	public void incNumTotalWaterRefillsByThisAircraft() {
		this.numTotalWaterRefillsByThisAircraft++;
	}
	
	public int getNumTotalFuelRefillsByThisAircraft() {
		return this.numTotalFuelRefillsByThisAircraft;
	}

	public void incNumTotalFuelRefillsByThisAircraft() {
		this.numTotalFuelRefillsByThisAircraft++;
	}
	
	public int getNumTotalTravelsByThisAircraft() {
		return this.numTotalTravelsByThisAircraft;
	}

	public void incNumTotalTravelsByThisAircraft() {
		this.numTotalTravelsByThisAircraft++;
	}
	
	
	
	// 2) Time metrics:
	
	public long getTotalTimeToAttendFiresByThisAircraft() {
		return this.totalTimeToAttendFiresByThisAircraft;
	}
	
	public void incTotalTimeToAttendFiresByThisAircraft(long startTime) {
		long attendFireTime = System.currentTimeMillis() - startTime;
		
		this.totalTimeToAttendFiresByThisAircraft += attendFireTime;
	}
	
	public long getTotalTimeToExtinguishFiresByThisAircraft() {
		return this.totalTimeToExtinguishFiresByThisAircraft;
	}
	
	public void incTotalTimeToExtinguishFiresByThisAircraft(long startTime) {
		long extinguishFireTime = System.currentTimeMillis() - startTime;
		
		this.totalTimeToWaterRefillsByThisAircraft += extinguishFireTime;
	}
	
	public long getTotalTimeToRefillsByThisAircraft() {
		return this.totalTimeToRefillsByThisAircraft;
	}
	
	public void incTotalTimeToRefillsByThisAircraft() {
		this.totalTimeToRefillsByThisAircraft++;
	}
	
	public long getTotalTimeToWaterRefillsByThisAircraft() {
		return this.totalTimeToWaterRefillsByThisAircraft;
	}
	
	public void incTotalTimeInWaterRefillsByThisAircraft(long startTime) {
		long waterRefillTime = System.currentTimeMillis() - startTime;
		
		this.totalTimeToWaterRefillsByThisAircraft += waterRefillTime;
	}
	
	public long getTotalTimeToFuelRefillsByThisAircraft() {
		return this.totalTimeToFuelRefillsByThisAircraft;
	}
	
	public void incTotalTimeToFuelRefillsByThisAircraft() {
		this.totalTimeToFuelRefillsByThisAircraft++;
	}
	
	public long getTotalTimeInTravelsByThisAircraft() {
		return this.totalTimeInTravelsByThisAircraft;
	}
	
	public void incTotalTimeInTravelsByThisAircraft(long startTime) {
		long travelTime = System.currentTimeMillis() - startTime;
		
		this.totalTimeInTravelsByThisAircraft += travelTime;	
	}
	
	
	
	// 3) Average metrics:
	
	public double getAverageTimeToAttendFireByThisAircraft() {
		return this.getTotalTimeToAttendFiresByThisAircraft() / this.getNumTotalFiresAttendedByThisAircraft();
	}
	
	public double getAverageTimeToExtinguishFireByThisAircraft() {
		return this.getTotalTimeToExtinguishFiresByThisAircraft() / this.getNumTotalFiresExtinguishedByThisAircraft();
	}
	
	public double getAverageTimeToRefillByThisAircraft() {
		return 0;
	}
	
	public double getAverageTimeToWaterRefillByThisAircraft() {
		return this.getTotalTimeToExtinguishFiresByThisAircraft() / this.getNumTotalWaterRefillsByThisAircraft();
	}
	
	public double getAverageTimeToFuelRefillByThisAircraft() {
		return 0;
	}
	
	public double getAverageTimeInTravelsByThisAircraft() {
		return this.getTotalTimeInTravelsByThisAircraft() / this.getNumTotalTravelsByThisAircraft();
	}
	
	
	
	// 4) Messages exchanged (received/sent) metrics:
	
	public int getNumTotalMessagesExchangedByThisAircraft() {
		return this.numTotalMessagesExchangedByThisAircraft;
	}
	
	public void incNumTotalMessagesExchangedByThisAircraft() {
		this.numTotalMessagesExchangedByThisAircraft++;
	}
	
	public int getNumTotalMessagesReceivedByThisAircraft() {
		return this.numTotalMessagesReceivedByThisAircraft;
	}
	
	public void incNumTotalMessagesReceivedByThisAircraft() {
		this.numTotalMessagesReceivedByThisAircraft++;
	}
	
	public int getNumTotalMessagesSentByThisAircraft() {
		return this.numTotalMessagesSentByThisAircraft;
	}
	
	public void incNumTotalMessagesSentByThisAircraft() {
		this.numTotalMessagesSentByThisAircraft++;
	}
	
	public int getNumTotalFireAlertMessagesReceivedByThisAircraft() {
		return this.numTotalFireAlertMessagesReceivedByThisAircraft;
	}
	
	public void incNumTotalFireAlertMessagesReceivedByThisAircraft() {
		this.numTotalFireAlertMessagesReceivedByThisAircraft++;
	}
}