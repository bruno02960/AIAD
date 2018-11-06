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
	
	public int numTotalFiresExtinguishedByThisAircraft;

	public int numTotalRefillsByThisAircraft;
	
	public int numTotalWaterRefillsByThisAircraft;
	
	public int numTotalFuelRefillsByThisAircraft;
	
	
	
	// 2) Time metrics:
	
	public long totalTimeToExtinguishFiresByThisAircraft;
		
	public long totalTimeToRefillsByThisAircraft;
		
	public long totalTimeToWaterRefillsByThisAircraft;
		
	public long totalTimeToFuelRefillsByThisAircraft;
	
		
		
	// 3) Average metrics:
		
	public double averageTimeToExtiguishFireByThisAircraft;
	
	public double averageTimeToRefillByThisAircraft;
	
	public double averageTimeToWaterRefillByThisAircraft;
		
	public double averageTimeToFuelRefillByThisAircraft;
	
	
	
	// 4) Messages exchanged (received/sent) metrics:
	
	public int numTotalMessagesExchangedByThisAircraft;
	
	public int numTotalMessagesReceivedByThisAircraft;
	
	public int numTotalMessagesSentByThisAircraft;
	
	public int numTotalFireAlertMessagesReceivedByThisAircraft;
	
	
	/**
	 * 
	 */
	public AircraftMetricsStats() {
		
		// 1) Countable metrics:
		
		this.numTotalFiresExtinguishedByThisAircraft = 0;
		
		this.numTotalRefillsByThisAircraft = 0;
		
		this.numTotalWaterRefillsByThisAircraft = 0;
		
		this.numTotalFuelRefillsByThisAircraft = 0;
		
		
		
		// 2) Time metrics:
		
		this.totalTimeToExtinguishFiresByThisAircraft = 0L;
		
		this.totalTimeToRefillsByThisAircraft = 0L;
		
		this.totalTimeToWaterRefillsByThisAircraft = 0L;
		
		this.totalTimeToFuelRefillsByThisAircraft = 0L;
		
		
		
		// 3) Average metrics:
	
		this.averageTimeToExtiguishFireByThisAircraft = 0.0;
		
		this.averageTimeToRefillByThisAircraft = 0.0;
		
		this.averageTimeToWaterRefillByThisAircraft = 0.0;
			
		this.averageTimeToFuelRefillByThisAircraft = 0.0;
		
		
		
		// 4) Messages exchanged (received/sent) metrics:
		
		this.numTotalMessagesExchangedByThisAircraft = 0;
		
		this.numTotalMessagesReceivedByThisAircraft = 0;
		
		this.numTotalMessagesSentByThisAircraft = 0;
		
		this.numTotalFireAlertMessagesReceivedByThisAircraft = 0;
	}
	
	
	
	// Methods:
	
	// 1) Countable metrics:
	
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
	
	
	
	// 2) Time metrics:
	
	public long getTotalTimeToExtinguishFiresByThisAircraft() {
		return this.totalTimeToExtinguishFiresByThisAircraft;
	}
	
	public void incTotalTimeToExtinguishFiresByThisAircraft() {
		this.totalTimeToExtinguishFiresByThisAircraft++;
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
	
	public void incTotalTimeToWaterRefillsByThisAircraft() {
		this.totalTimeToWaterRefillsByThisAircraft++;
	}
	
	public long getTotalTimeToFuelRefillsByThisAircraft() {
		return this.totalTimeToFuelRefillsByThisAircraft;
	}
	
	public void incTotalTimeToFuelRefillsByThisAircraft() {
		this.totalTimeToFuelRefillsByThisAircraft++;
	}
	
	
	
	// 3) Average metrics:
	
	public double getAverageTimeToExtinguishFireByThisAircraft() {
		return this.averageTimeToExtiguishFireByThisAircraft;
	}
	
	public void incAverageTimeToExtinguishFireByThisAircraft() {
		this.averageTimeToExtiguishFireByThisAircraft++;
	}
	
	public double getAverageTimeToRefillByThisAircraft() {
		return this.averageTimeToRefillByThisAircraft;
	}
	
	public void incAverageTimeToRefillByThisAircraft() {
		this.averageTimeToRefillByThisAircraft++;
	}
	
	public double getAverageTimeToWaterRefillByThisAircraft() {
		return this.averageTimeToWaterRefillByThisAircraft;
	}
	
	public void incAverageTimeToWaterRefillByThisAircraft() {
		this.averageTimeToWaterRefillByThisAircraft++;
	}
	
	public double getAverageTimeToFuelRefillByThisAircraft() {
		return this.averageTimeToFuelRefillByThisAircraft;
	}
	
	public void incAverageTimeToFuelRefillByThisAircraft() {
		this.averageTimeToFuelRefillByThisAircraft++;
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