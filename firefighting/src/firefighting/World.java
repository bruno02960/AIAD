package firefighting;

import jade.core.Agent;

/**
 * Class responsible for updating and printing the world
 */
@SuppressWarnings("serial")
public class World extends Agent {

	public void print() {
		for(int i = 0; i < Config.GRID_WIDTH * 3 + 1; i++) {
			System.out.print('-');
		}
		System.out.println("");
		for(int j = 0; j < Config.GRID_HEIGHT; j++) {
			for(int i = 0; i < Config.GRID_WIDTH; i++) {
				System.out.print("|  ");
			}
			System.out.println('|');
			for(int i = 0; i < Config.GRID_WIDTH * 3 + 1; i++) {
				System.out.print('-');
			}
			System.out.println("");
		}
	}

}
