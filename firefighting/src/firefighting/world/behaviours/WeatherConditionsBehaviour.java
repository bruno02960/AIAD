package firefighting.world.behaviours;

import java.util.Random;

import firefighting.world.WorldAgent;
import firefighting.world.utils.environment.SeasonType;
import jade.core.behaviours.TickerBehaviour;

public class WeatherConditionsBehaviour extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WorldAgent worldAgent;
	
	RainingBehaviour rainingBehaviour;
	// Wind behaviour TODO
	DroughtSituationBehaviour droughtSituationBehaviour;
	
	
	public WeatherConditionsBehaviour(WorldAgent worldAgent, long period) {
		super(worldAgent, period);
		this.worldAgent = worldAgent;
	}
	
	public WorldAgent getWorldAgent() {
		return this.worldAgent;
	}
	
	public SeasonType getSeasonType() {
		return this.getWorldAgent().getSeasonType();
	}
	
	@Override
	public void onTick() {
		
		WorldAgent worldAgent = this.getWorldAgent();
		
		// Handle code of raining, wind and drought (extreme dry situations) behaviours:
		
		// 1) Handle raining's ticker behaviour
		SeasonType seasonType = this.getSeasonType();
		byte seasonTypeID = seasonType.getID();
		
		Random randomObject = new Random();
		
		int rainFrequencyTimeSec;
		long rainFrequencyTimeMs; 
		
		// Calculate a random time frequency in seconds of occurring precipitation/rain, from the global set [20s, 100s],
		// accordingly with the current season
		switch(seasonTypeID) {
			// SPRING SEASON
			case 0:
				// Normal time frequency of occurring precipitation/rain from the set [31s, 50s]
				rainFrequencyTimeSec = randomObject.ints(1, 31, 51).toArray()[0];
				break;
			// SUMMER SEASON
			case 1:
				// Normal time frequency of occurring precipitation/rain from the set [51s, 100s]
				rainFrequencyTimeSec = randomObject.ints(1, 51, 101).toArray()[0];
				break;
			// AUTUMN SEASON
			case 2:
				// Normal time frequency of occurring precipitation/rain from the set [31s, 50s]
				rainFrequencyTimeSec = randomObject.ints(1, 31, 51).toArray()[0];
				break;
			// WINTER SEASON
			case 3:
				// Normal time frequency of occurring precipitation/rain from the set [20s, 30s]
				rainFrequencyTimeSec = randomObject.ints(1, 20, 31).toArray()[0];
				break;
			default:
				rainFrequencyTimeSec = 0;
				break;
		}
		
		rainFrequencyTimeMs = (long) rainFrequencyTimeSec * 1000;
		
		this.rainingBehaviour = new RainingBehaviour(worldAgent, rainFrequencyTimeMs);
		worldAgent.addBehaviour(this.rainingBehaviour);
		
		
		// 2) Handle wind behaviour
		// TODO
				
			
		// 3) Handle drought's (extreme dry situation) ticker behaviour (if it's possible, can only occur in summer season)
		if(worldAgent.canOccurDroughtSituations()) {
			int droughtSituationFrequencyTimeSec;
			long droughtSituationFrequencyTimeMs;
			
			float probabilityOccurDroughtSituation = randomObject.nextFloat();
			
			float[] boundsDroughtSituationProbabilityInterval = worldAgent.getDroughtSituationProbabilityInterval();
			float min = boundsDroughtSituationProbabilityInterval[0];
			float max = boundsDroughtSituationProbabilityInterval[1];
			
			if((probabilityOccurDroughtSituation >= min) && (probabilityOccurDroughtSituation <= max)) {
				droughtSituationFrequencyTimeSec = randomObject.ints(1, 100, 201).toArray()[0];
				
				droughtSituationFrequencyTimeMs = (long) droughtSituationFrequencyTimeSec * 1000;
			
				this.droughtSituationBehaviour = new DroughtSituationBehaviour(worldAgent, droughtSituationFrequencyTimeMs);
				worldAgent.addBehaviour(this.droughtSituationBehaviour);
			}
		}
	}
	
	/**
	 * Destroying all the previous ticker behaviours and their respectively time counters
	 */
	public void onDone() {
		
		if(this.rainingBehaviour != null) {
			this.worldAgent.removeTimer(this.rainingBehaviour);
			this.worldAgent.removeBehaviour(this.rainingBehaviour);
		}
		
		if(this.droughtSituationBehaviour != null) {
			this.worldAgent.removeTimer(this.droughtSituationBehaviour);
			this.worldAgent.removeBehaviour(this.droughtSituationBehaviour);
		}
	}
}