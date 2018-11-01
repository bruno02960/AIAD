package firefighting.world.behaviours;

import firefighting.utils.Config;
import firefighting.world.WorldAgent;
import jade.core.behaviours.TickerBehaviour;

public class PrintStatusBehaviour extends TickerBehaviour {

	WorldAgent worldAgent;
	
	public PrintStatusBehaviour(WorldAgent worldAgent, long period) {
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
	
	public Object[][] getWorldMap() {
		return this.getWorldAgent().getWorldMap();
	}
	
	@Override
	protected void onTick() {
		
		Object[][] worldMap = this.getWorldMap();
		
		for(int i = 0; i < Config.GRID_WIDTH * 3 + 1; i++) {
			System.out.print('-');
		}
		
		System.out.println("");
		
		for(int j = 0; j < Config.GRID_HEIGHT; j++) {
			for(int i = 0; i < Config.GRID_WIDTH; i++) {
				System.out.print("|");
				if (worldMap[i][j] != null) {
					System.out.print(worldMap[i][j]);
				}
				else {
					System.out.print("  ");
				}
			}
		
			System.out.println('|');
			
			for(int i = 0; i < Config.GRID_WIDTH * 3 + 1; i++) {
				System.out.print('-');
			}
			
			System.out.println("");
		}
	}
}