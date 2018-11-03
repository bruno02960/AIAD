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
	
	public final static void clearConsole()
	{
	    try
	    {
	        final String os = System.getProperty("os.name");

	        if (os.contains("Windows"))
	        {
	            Runtime.getRuntime().exec("cls");
	        }
	        else
	        {
	            Runtime.getRuntime().exec("clear");
	        }
	    }
	    catch (final Exception e)
	    {
	        //  Handle any exceptions.
	    }
	}
	
	@Override
	protected void onTick() {
		
		// Clean the console
		PrintStatusBehaviour.clearConsole();
		
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
	
		System.err.println();
		System.out.println();
		
		System.out.println("Caption:");
		System.out.println("1) ST - Fire Station | 2) W[c] - Filling Station, where [c] is its capacity");
		System.out.println("3) F[i] - Fire, where [i] is its intensity | 4) A[t] - Aircraft, where [t] is its tank capacity");
	
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}
}