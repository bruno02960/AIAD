package firefighting.world.behaviours;

import java.awt.Point;
import java.util.concurrent.ConcurrentNavigableMap;

import firefighting.nature.Fire;
import firefighting.utils.Config;
import firefighting.world.WorldAgent;
import firefighting.world.WorldObject;
import firefighting.world.utils.WorldObjectType;
import jade.core.behaviours.TickerBehaviour;

public class GenerateFiresBehaviour extends TickerBehaviour {
	
	public WorldAgent worldAgent;
	
	public GenerateFiresBehaviour(WorldAgent worldAgent, long period) {
		super(worldAgent, period);
		this.worldAgent = worldAgent;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorldAgent getWorldAgent() {
		return this.worldAgent;
	}
	
	@Override
	protected void onTick() {
		
		WorldAgent worldAgent = this.getWorldAgent();
		
		// It's possible to add a fire
		if(this.getWorldAgent().getNumCurrentFires() < Config.NUM_MAX_FIRES) {
		   	
			int[] firePos = worldAgent.generateRandomPos();
		    	
		   	WorldObject fireWorldObject = new WorldObject(WorldObjectType.FIRE, new Point(firePos[0], firePos[1]));
		    
		   	ConcurrentNavigableMap<Integer, Fire> fires = worldAgent.getCurrentFires();

		   	Fire fire = new Fire((byte) worldAgent.getNumTotalFiresGenerated(), fireWorldObject);
		    
		   	//in map
		   	worldAgent.addFire(firePos[0], firePos[1], fire);
	   
		   	// TODO
		   	fires.put((int) fire.getID(), fire);
	    }
	}
}