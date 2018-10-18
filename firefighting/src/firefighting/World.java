package firefighting;

import java.util.Random;

import jade.core.Agent;

/**
 * Class responsible for updating and printing the world
 */
@SuppressWarnings("serial")
public class World extends Agent implements Runnable {

	private worldObject[][] worldMatrix;
	
	public void createWorld() {
		this.worldMatrix = new worldObject[Config.GRID_WIDTH][Config.GRID_HEIGHT];
		
		for(int x=0; x<Config.GRID_WIDTH ; x++)
			for(int y=0; x<Config.GRID_WIDTH ; y++)
				System.out.println(this.worldMatrix==null);
	}
	
	
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
	
	
	
	
	
	Thread generateFires = new Thread(() -> {
	    Random randomObject;

	    for(;;) {
	    	Thread.sleep(2000);
	    	int random = randomObject.nextInt((max - min) + 1) + min;
	    }
	    	
	    	
	    	
	    	
	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = randomObject.nextInt((max - min) + 1) + min;
	});
	
	
	public void startWorld() {
		this.createWorld();
		this.generateFires.start();
	}
}
