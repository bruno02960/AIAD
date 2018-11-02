package firefighting.world.behaviours;

import java.awt.Point;

import firefighting.nature.Fire;
import firefighting.utils.Config;
import firefighting.world.WorldAgent;
import firefighting.world.WorldObject;
import firefighting.world.utils.WorldObjectType;
import jade.core.behaviours.TickerBehaviour;

import firefighting.world.*;

public class GenerateFiresBehaviour extends TickerBehaviour {
	
	WorldAgent worldAgent;
	
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
		
		//System.out.println("World Agent it's generating a fire...");
		
		// It's possible to add a fire
		if(this.getWorldAgent().getCurrentNumFires() < Config.NUM_MAX_FIRES) {
		   	int[] firePos = worldAgent.generateRandomPos();
		    	
		   	WorldObject fireWorldObject = new WorldObject(WorldObjectType.FIRE, new Point(firePos[0], firePos[1]));
		    
		   	Fire[] fires = worldAgent.getCurrentFires();
		   	
		   	int currentPosFireToAdd = 0;
		   	
		   	for(currentPosFireToAdd = 0; currentPosFireToAdd < Config.NUM_MAX_FIRES; currentPosFireToAdd++)
		   		if(fires[currentPosFireToAdd] == null)
		   			break;
		   	
		   	
		   	Fire fire = new Fire((byte) currentPosFireToAdd, fireWorldObject);
		    	
		   	worldAgent.addFire(firePos[0], firePos[1], fire);
	    		
	   
	    	int fireToCreatePosInArray;

	    	for(fireToCreatePosInArray = 0; fireToCreatePosInArray < Config.NUM_MAX_FIRES; fireToCreatePosInArray++)
	    		if(fires[fireToCreatePosInArray] != null)
	    			break;
	    		
	    	if(fireToCreatePosInArray < Config.NUM_MAX_FIRES) {
		   		fires[fireToCreatePosInArray] = fire;
		    		
		   		worldAgent.incCurrentNumFires();
	    	}
	    
	    	//System.out.println("The World Agent generated the following fire:");
	    	//System.out.println(fire.toString());
	    }

	}

}