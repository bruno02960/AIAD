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
 * Class with some statistics about all elements in the world and their behaviours.
 */
public class WorldMetricsStats {
	
	// 1) Global metrics:
	
	private int numFireStations;
	
	private int numWaterResources;
	
	private int numAircrafts;
	
	
	
	// 2) Countable metrics:
	
	private int numTotalFiresExtinguishedByAllAircrafts;

	private int numTotalRefillsByAllAircrafts;
		
	private int numTotalWaterRefillsByAllAircrafts;
		
	private int numTotalFuelRefillsByAllAircrafts;
	
	
	
	// 3) Time metrics:
	
	private long totalTimeToExtinguishFiresByAllAircrafts;
	
	private long totalTimeToRefillsByAllAircrafts;
		
	private long totalTimeToWaterRefillsByAllAircrafts;
		
	private long totalTimeToFuelRefillsByAllAircrafts;
	
	
	
	// 4) Average metrics:
	
	private double averageTimeToExtiguishFireByAllAircrafts;
		
	private double averageTimeToRefillByAllAircrafts;
		
	private double averageTimeToWaterRefillByAllAircrafts;
			
	private double averageTimeToFuelRefillByAllAircrafts;
	
	
	
	// 5) Messages exchanged (received/sent) metrics:
	
	private int numTotalMessagesExchangedByAllAircrafts;
		
	private int numTotalMessagesReceivedByAllAircrafts;
		
	private int numTotalMessagesSentByAllAircrafts;
		
	private int numTotalFireAlertMessagesReceivedByAllAircrafts;
	
	
	
	// 6) Environment metrics:
	
	private int numFiresGenerated;
		
	private int numTotalOccurrenceRains;
			
	// TODO - Wind Occurrences
			
	private int numTotalOccurrenceDroughtSituations;
	
	
	
	// 7) Environment effects metrics:
			
	private int numFiresIntensityIncreased;
		
	private int numFiresIntensityIncreasedByTimeout;
			
	private int numFiresIntensityDecreased;
		
	private int numFiresIntensityDecreasedByAircraftsActions;
			
	private int numFiresIntensityDecreasedByRaining;
	
	private int numWaterResourcesCapacityIncreasedByRaining;
	
	private int numWaterResourcesCapacityDecreased;
	
	private int numWaterResourcesCapacityDecreasedByAircraftsWaterTankRefills;
			
	private int numWaterResourcesCapacityDecreasedByDroughtSituationOccurences;
	
	
	
	// Constructors:
	/**
	 * 
	 */
	public WorldMetricsStats() {
		
		// 1) Global metrics:
		
		this.numFireStations = 0;
		
		this.numWaterResources = 0;
		
		this.numAircrafts = 0;
		
		
		
		// 2) Countable metrics:
		
		this.numTotalFiresExtinguishedByAllAircrafts = 0;
		
		this.numTotalRefillsByAllAircrafts = 0;
		
		this.numTotalWaterRefillsByAllAircrafts = 0;
		
		this.numTotalFuelRefillsByAllAircrafts = 0;
		
		
		
		// 3) Time metrics:
		
		this.totalTimeToExtinguishFiresByAllAircrafts = 0L;
		
		this.totalTimeToRefillsByAllAircrafts = 0L;
		
		this.totalTimeToWaterRefillsByAllAircrafts = 0L;
		
		this.totalTimeToFuelRefillsByAllAircrafts = 0L;
		
		
		
		// 4) Average metrics:
	
		this.averageTimeToExtiguishFireByAllAircrafts = 0.0;
		
		this.averageTimeToRefillByAllAircrafts = 0.0;
		
		this.averageTimeToWaterRefillByAllAircrafts = 0.0;
			
		this.averageTimeToFuelRefillByAllAircrafts = 0.0;
		
		
		
		// 5) Messages exchanged (received/sent) metrics:
		
		this.numTotalMessagesExchangedByAllAircrafts = 0;
		
		this.numTotalMessagesReceivedByAllAircrafts = 0;
		
		this.numTotalMessagesSentByAllAircrafts = 0;
		
		this.numTotalFireAlertMessagesReceivedByAllAircrafts = 0;
		
		
		
		// 6) Environment metrics:
		
		this.numFiresGenerated = 0;
	
		this.numTotalOccurrenceRains = 0;
		
		// TODO - Wind Occurrences
		
		this.numTotalOccurrenceDroughtSituations = 0;
		
		 
		
		// 7) Environment effects metrics:
		
		this.numFiresIntensityIncreased = 0;
	
		this.numFiresIntensityIncreasedByTimeout = 0;
		
		this.numFiresIntensityDecreased = 0;
		
		this.numFiresIntensityDecreasedByAircraftsActions = 0;
		
		this.numFiresIntensityDecreasedByRaining = 0;
		
		this.numWaterResourcesCapacityIncreasedByRaining = 0;
		
		this.numWaterResourcesCapacityDecreased = 0;
		
		this.numWaterResourcesCapacityDecreasedByAircraftsWaterTankRefills = 0;
		
		this.numWaterResourcesCapacityDecreasedByDroughtSituationOccurences = 0;
	}
	
	
	
	// Methods:
	
	// 1) Global metrics:
	
	public int getNumFireStations() {
		return this.numFireStations;
	}
	
	public int getNumWaterResources() {
		return this.numWaterResources;
	}
	
	public int getNumAircrafts() {
		return this.numAircrafts;
	}
	
	
	
	// 2) Countable metrics:
		
	public int getNumTotalFiresExtinguishedByAllAircrafts() {
		return this.numTotalFiresExtinguishedByAllAircrafts;
	}
		
	public void incNumTotalFiresExtinguishedByAllAircrafts() {
		this.numTotalFiresExtinguishedByAllAircrafts++;
	}
		
	public int getNumTotalRefillsByAllAircrafts() {
		return this.numTotalRefillsByAllAircrafts;
	}
	
	public void incNumTotalRefillsByAllAircrafts() {
		this.numTotalRefillsByAllAircrafts++;
	}
	
	public int getNumTotalWaterRefillsByAllAircrafts() {
		return this.numTotalWaterRefillsByAllAircrafts;
	}
	
	public void incNumTotalWaterRefillsByAllAircrafts() {
		this.numTotalWaterRefillsByAllAircrafts++;
	}
	
	public int getNumTotalFuelRefillsByAllAircrafts() {
		return this.numTotalFuelRefillsByAllAircrafts;
	}

	public void incNumTotalFuelRefillsByAllAircrafts() {
		this.numTotalFuelRefillsByAllAircrafts++;
	}
	
	
	
	// 3) Time metrics:
	
	public long getTotalTimeToExtinguishFiresByAllAircrafts() {
		return this.totalTimeToExtinguishFiresByAllAircrafts;
	}
	
	public void incTotalTimeToExtinguishFiresByAllAircrafts() {
		this.totalTimeToExtinguishFiresByAllAircrafts++;
	}
	
	public long getTotalTimeToRefillsByAllAircrafts() {
		return this.totalTimeToRefillsByAllAircrafts;
	}
		
	public void incTotalTimeToRefillsByAllAircrafts() {
		this.totalTimeToRefillsByAllAircrafts++;
	}
		
	public long getTotalTimeToWaterRefillsByAllAircrafts() {
		return this.totalTimeToWaterRefillsByAllAircrafts;
	}
		
	public void incTotalTimeToWaterRefillsByAllAircrafts() {
		this.totalTimeToWaterRefillsByAllAircrafts++;
	}
		
	public long getTotalTimeToFuelRefillsByAllAircrafts() {
		return this.totalTimeToFuelRefillsByAllAircrafts;
	}
	
	public void incTotalTimeToFuelRefillsByAllAircrafts() {
		this.totalTimeToFuelRefillsByAllAircrafts++;
	}
		
	
	
	// 4) Average metrics:
	
	public double getAverageTimeToExtinguishFireByAllAircrafts() {
		return this.averageTimeToExtiguishFireByAllAircrafts;
	}
	
	public void incAverageTimeToExtinguishFireByAllAircrafts() {
		this.averageTimeToExtiguishFireByAllAircrafts++;
	}
	
	public double getAverageTimeToRefillByAllAircrafts() {
		return this.averageTimeToRefillByAllAircrafts;
	}
	
	public void incAverageTimeToRefillByAllAircrafts() {
		this.averageTimeToRefillByAllAircrafts++;
	}
	
	public double getAverageTimeToWaterRefillByAllAircrafts() {
		return this.averageTimeToWaterRefillByAllAircrafts;
	}
	
	public void incAverageTimeToWaterRefillByAllAircrafts() {
		this.averageTimeToWaterRefillByAllAircrafts++;
	}
	
	public double getAverageTimeToFuelRefillByAllAircrafts() {
		return this.averageTimeToFuelRefillByAllAircrafts;
	}
	
	public void incAverageTimeToFuelRefillByAllAircrafts() {
		this.averageTimeToFuelRefillByAllAircrafts++;
	}
	
	
	
	// 5) Messages exchanged (received/sent) metrics:
	
	public int getNumTotalMessagesExchangedByAllAircrafts() {
		return this.numTotalMessagesExchangedByAllAircrafts;
	}
	
	public void incNumTotalMessagesExchangedByAllAircrafts() {
		this.numTotalMessagesExchangedByAllAircrafts++;
	}
	
	public int getNumTotalMessagesReceivedByAllAircrafts() {
		return this.numTotalMessagesReceivedByAllAircrafts;
	}
	
	public void incNumTotalMessagesReceivedByAllAircrafts() {
		this.numTotalMessagesReceivedByAllAircrafts++;
	}
	
	public int getNumTotalMessagesSentByAllAircrafts() {
		return this.numTotalMessagesSentByAllAircrafts;
	}
	
	public void incNumTotalMessagesSentByAllAircrafts() {
		this.numTotalMessagesSentByAllAircrafts++;
	}
	
	public int getNumTotalFireAlertMessagesReceivedByAllAircrafts() {
		return this.numTotalFireAlertMessagesReceivedByAllAircrafts;
	}
	
	public void incNumTotalFireAlertMessagesReceivedByAllAircrafts() {
		this.numTotalFireAlertMessagesReceivedByAllAircrafts++;
	}
	
	
	
	// 6) Environment metrics:
	
	public int getNumFiresGenerated() {
		return this.numFiresGenerated;
	}
	
	public void incNumFiresGenerated() {
		this.numFiresGenerated++;
	}
	
	public int getNumTotalOccurrenceRains() {
		return this.numTotalOccurrenceRains;
	}
	
	public void incNumTotalOccurrenceRains() {
		this.numTotalOccurrenceRains++;
	}
	
	// TODO - Wind Occurrences
	
	public int getNumTotalOccurrenceDroughtSituations() {
		return this.numTotalOccurrenceDroughtSituations;
	}
			
	public void incNumTotalOccurrenceDroughtSituations() {
		this.numTotalOccurrenceDroughtSituations++;		
	}
		
		
		
	// 7) Environment effects metrics:
	
	public int getNumFiresIntensityIncreased() {
		return this.numFiresIntensityIncreased;
	}
		
	public void incNumFiresIntensityIncreased() {
		this.numFiresIntensityIncreased++;
	}
		
	public int getNumFiresIntensityIncreasedByTimeout() {
		return this.numFiresIntensityIncreasedByTimeout;
	}
	
	public void incNumFiresIntensityIncreasedByTimeout() {
		this.numFiresIntensityIncreasedByTimeout++;
	}
	
	public int getNumFiresIntensityDecreased() {
		return this.numFiresIntensityDecreased;
	}
	
	public void incNumFiresIntensityDecreased() {
		this.numFiresIntensityDecreased++;
	}
	
	public int getNumFiresIntensityDecreasedByAircraftsActions() {
		return this.numFiresIntensityDecreasedByAircraftsActions;
	}
	
	public void incNumFiresIntensityDecreasedByAircraftsActions() {
		this.numFiresIntensityDecreasedByAircraftsActions++;
	}
	
	public int getNumFiresIntensityDecreasedByRaining() {
		return this.numFiresIntensityDecreasedByRaining;
	}
	
	public void incNumFiresIntensityDecreasedByRaining() {
		this.numFiresIntensityDecreasedByRaining++;
	}
	
	public int getNumWaterResourcesCapacityIncreasedByRaining() {
		return this.numWaterResourcesCapacityIncreasedByRaining;
	}
	
	public void incNumWaterResourcesCapacityIncreasedByRaining() {
		this.numWaterResourcesCapacityIncreasedByRaining++;
	}

	public int getNumWaterResourcesCapacityDecreased() {
		return this.numWaterResourcesCapacityDecreased;
	}
	
	public void incNumWaterResourcesCapacityDecreased() {
		this.numWaterResourcesCapacityDecreased++;
	}
	
	public int getNumWaterResourcesCapacityDecreasedByAircraftsWaterTankRefill() {
		return this.numWaterResourcesCapacityDecreasedByAircraftsWaterTankRefills;
	}
	
	public void incNumWaterResourcesCapacityDecreasedByAircraftsWaterTankRefill() {
		this.numWaterResourcesCapacityDecreasedByAircraftsWaterTankRefills++;
	}
	
	public int getNumWaterResourcesCapacityDecreasedByDroughtSituationOccurences() {
		return this.numWaterResourcesCapacityDecreasedByDroughtSituationOccurences;
	}
	
	public void incNumWaterResourcesCapacityDecreasedByDroughtSituationOccurences() {
		this.numWaterResourcesCapacityDecreasedByDroughtSituationOccurences++;
	}

	public void setNumTotalFiresExtinguishedByAllAircrafts(int numTotalFiresExtinguishedByAllAircrafts) {
		this.numTotalFiresExtinguishedByAllAircrafts = numTotalFiresExtinguishedByAllAircrafts;
	}
}