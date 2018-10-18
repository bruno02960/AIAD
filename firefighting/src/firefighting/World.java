package firefighting;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import java.awt.Point;
import jade.core.Agent;

/**
 * Class responsible for updating and printing the world
 */
@SuppressWarnings("serial")
public class World extends Agent implements Runnable {

	private Map<Point, WorldObject> worldMap;
	
	public void createWorld() {
		int maxWorldGridSize = Config.GRID_WIDTH * Config.GRID_HEIGHT;
		this.worldMap = new HashMap<Point, WorldObject>(maxWorldGridSize);
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
	    Random randomObject = new Random();

	    for(;;) {
	    	// Time to wait until generate the next fire
	    	try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	int posX = randomObject.nextInt((Config.GRID_WIDTH - 1) + 1) + 1;
	    	int posY = randomObject.nextInt((Config.GRID_HEIGHT - 1) + 1) + 1;
	    	
	    	Point firePos = new Point(posX, posY);
	    	
	    	while(worldMap.containsKey(firePos)) {
	    		posX = randomObject.nextInt((Config.GRID_WIDTH - 1) + 1) + 1;
		    	posY = randomObject.nextInt((Config.GRID_HEIGHT - 1) + 1) + 1;
		    	
		    	firePos = new Point(posX, posY);
	    	}
	    	
	    	worldMap.put(firePos, new WorldObject(WorldObjectType.FIRE, int posX, int posY))
	    }
	});
	
	
	public void startWorld() {
		this.createWorld();
		this.generateFires.start();
	}
}
