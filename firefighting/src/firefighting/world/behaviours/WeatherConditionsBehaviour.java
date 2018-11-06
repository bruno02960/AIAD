package firefighting.world.behaviours;

import java.util.Random;

import firefighting.world.WorldAgent;
import firefighting.world.utils.environment.SeasonType;
import jade.core.behaviours.CyclicBehaviour;

public class WeatherConditionsBehaviour extends CyclicBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WorldAgent worldAgent;
	
	public WeatherConditionsBehaviour(WorldAgent worldAgent) {
		super(worldAgent);
		this.worldAgent = worldAgent;
	}
	
	public WorldAgent getWorldAgent() {
		return this.worldAgent;
	}
	
	public SeasonType getSeasonType() {
		return this.getWorldAgent().getSeasonType();
	}
	
	@Override
	public void action() {
		
		WorldAgent worldAgent = this.getWorldAgent();
		
		// Handle code of raining, wind and drought (extreme dry situations) behaviours:
		
		// 1) Handle raining behaviour
		SeasonType seasonType = this.getSeasonType();
		byte seasonTypeID = seasonType.getID();
		
		Random randomObject = new Random();
		
		int rainFrequencyTimeSec;
		long rainFrequencyTimeMs; 
		
		// Calculate a random time frequency in seconds of occurring precipitation/rain, from the global set [0s, 30s],
		// accordingly with the current season
		switch(seasonTypeID) {
			// SPRING SEASON
			case 0:
				// Normal time frequency of occurring precipitation/rain from the set [6s, 14s]
				rainFrequencyTimeSec = randomObject.ints(1, 6, 15).toArray()[0];
				break;
			// SUMMER SEASON
			case 1:
				// Normal time frequency of occurring precipitation/rain from the set [15s, 20s]
				rainFrequencyTimeSec = randomObject.ints(1, 15, 21).toArray()[0];
				break;
			// AUTUMN SEASON
			case 2:
				// Normal time frequency of occurring precipitation/rain from the set [6s, 14s]
				rainFrequencyTimeSec = randomObject.ints(1, 6, 15).toArray()[0];
				break;
			// WINTER SEASON
			case 3:
				// Normal time frequency of occurring precipitation/rain from the set [1s, 5s]
				rainFrequencyTimeSec = randomObject.ints(1, 1, 6).toArray()[0];
				break;
			default:
				rainFrequencyTimeSec = 0;
				break;
		}
		
		rainFrequencyTimeMs = (long) rainFrequencyTimeSec * 1000;
		
		RainingBehaviour rainingBehaviour = new RainingBehaviour(worldAgent, rainFrequencyTimeMs);
		worldAgent.addBehaviour(rainingBehaviour);
		
		// Destroying the ticker behaviours created previously and their respectively time counters
		this.worldAgent.removeTimer(rainingBehaviour);
		this.worldAgent.removeBehaviour(rainingBehaviour);
				
		
		// 2) Handle wind behaviour
		// TODO
		
		// Destroying the ticker behaviours created previously and their respectively time counters
		//this.worldAgent.removeTimer(rainingBehaviour);
		//this.worldAgent.removeBehaviour(rainingBehaviour);
				
			
		// 3) Handle drought (extreme dry situation) behaviour (if it's possible, can only occur in summer season)
		if(worldAgent.canOccurDroughtSituations()) {
			int droughtSituationFrequencyTimeSec;
			long droughtSituationFrequencyTimeMs;
			
			float probabilityOccurDroughtSituation = randomObject.nextFloat();
			
			float[] boundsDroughtSituationProbabilityInterval = worldAgent.getDroughtSituationProbabilityInterval();
			float min = boundsDroughtSituationProbabilityInterval[0];
			float max = boundsDroughtSituationProbabilityInterval[1];
			
			if((probabilityOccurDroughtSituation >= min) && (probabilityOccurDroughtSituation <= max)) {
				droughtSituationFrequencyTimeSec = randomObject.ints(1, 10, 16).toArray()[0];
				
				droughtSituationFrequencyTimeMs = (long) droughtSituationFrequencyTimeSec * 1000;
			
				DroughtSituationBehaviour droughtSituationBehaviour = new DroughtSituationBehaviour(worldAgent, droughtSituationFrequencyTimeMs);
				worldAgent.addBehaviour(droughtSituationBehaviour);
		
				// Destroying the ticker behaviours created previously and their respectively time counters
				this.worldAgent.removeTimer(droughtSituationBehaviour);
				this.worldAgent.removeBehaviour(droughtSituationBehaviour);
			}
		}
	}
}