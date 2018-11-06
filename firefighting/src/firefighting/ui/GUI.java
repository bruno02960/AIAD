package firefighting.ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import firefighting.aircraft.AircraftAgent;
import firefighting.firestation.FireStationAgent;
import firefighting.nature.Fire;
import firefighting.nature.WaterResource;
import firefighting.utils.Config;
import firefighting.world.WorldAgent;

public class GUI {

	private static WorldAgent worldAgent;
	private JFrame frame;
	private static JLabel[][] grid;
    private static JPanel panel;

	/**
	 * Creates the graphic user interface
	 * @param worldAgent the world agent
	 */
	public GUI(WorldAgent worldAgent) {
		GUI.worldAgent = worldAgent;
		
	    panel = new JPanel();
		frame = new JFrame();
		frame.setTitle("Firefighting");
	    frame.getContentPane().add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 500, 500);
		panel.setLayout(new GridLayout(Config.GRID_HEIGHT, Config.GRID_WIDTH));

	    grid= new JLabel[Config.GRID_WIDTH][Config.GRID_HEIGHT];
	    for (int i = 0; i < Config.GRID_HEIGHT; i++){
	        for (int j = 0; j < Config.GRID_WIDTH; j++){
	            grid[j][i] = new JLabel();
	            grid[j][i].setBorder(new LineBorder(Color.BLACK));
	            grid[j][i].setHorizontalAlignment(SwingConstants.CENTER);
	            grid[j][i].setVerticalAlignment(SwingConstants.CENTER);
	            grid[j][i].setOpaque(true);
	            
	            if (worldAgent.getWorldMap()[j][i] != null) {
	            	grid[j][i].setText(worldAgent.getWorldMap()[j][i].toString());
	            	if (worldAgent.getWorldMap()[j][i] instanceof Fire) {
	    	            grid[j][i].setBackground(Color.orange);
	            	}
	            	if (worldAgent.getWorldMap()[j][i] instanceof AircraftAgent) {
	    	            grid[j][i].setBackground(Color.yellow);
	            	}
	            	if (worldAgent.getWorldMap()[j][i] instanceof WaterResource) {
	    	            grid[j][i].setBackground(Color.cyan);
	            	}
	            	if (worldAgent.getWorldMap()[j][i] instanceof FireStationAgent) {
	    	            grid[j][i].setBackground(Color.red);
	            	}
				}
	            panel.add(grid[j][i]);
	        }
	    }
	}
	
	/**
	 * Called on tick to fill the grid with the updated positions of the objects
	 */
	public static void fillGrid() {
		for (int i = 0; i < Config.GRID_HEIGHT; i++){
	        for (int j = 0; j < Config.GRID_WIDTH; j++){
	            grid[j][i].setOpaque(true);
	            if (worldAgent.getWorldMap()[j][i] != null) {
	            	grid[j][i].setText(worldAgent.getWorldMap()[j][i].toString());
	            	if (worldAgent.getWorldMap()[j][i] instanceof Fire) {
	    	            grid[j][i].setBackground(Color.orange);
	            	}
	            	if (worldAgent.getWorldMap()[j][i] instanceof AircraftAgent) {
	    	            grid[j][i].setBackground(Color.yellow);
	            	}
	            	if (worldAgent.getWorldMap()[j][i] instanceof WaterResource) {
	    	            grid[j][i].setBackground(Color.cyan);
	            	}
	            	if (worldAgent.getWorldMap()[j][i] instanceof FireStationAgent) {
	    	            grid[j][i].setBackground(Color.red);
	            	}
				}
	            else {
	            	grid[j][i].setBackground(null);
	            	grid[j][i].setText("");
	            }
	        }
	    }
	}
	
	/**
	 * Gets the GUI frame
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	
}
