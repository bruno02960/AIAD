package firefighting.ui;

import java.awt.Color;
import java.awt.Dimension;
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
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.FlowLayout;
import javax.swing.ScrollPaneConstants;

public class GUI {

	private static WorldAgent worldAgent;
	private JFrame frame;
	private static JLabel[][] grid;
    private static JPanel panel;
    private JPanel panel_1;
    private JLabel lblCaption;
    private JPanel panel_2;
    private JScrollPane scrollPane;
    private static JLabel lblNewLabel;
    private static JTextArea textArea;

	/**
	 * Creates the graphic user interface
	 * @param worldAgent the world agent
	 */
	public GUI(WorldAgent worldAgent) {
		GUI.worldAgent = worldAgent;
		
	    panel = new JPanel();
		frame = new JFrame();
		frame.setTitle("Firefighting");
	    
	    panel_1 = new JPanel();
	    FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
	    flowLayout.setHgap(100);
	    flowLayout.setAlignment(FlowLayout.LEFT);
	    frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
	    
	    lblCaption = new JLabel("<html>Caption:<br>1) ST - Fire Station<br>2) W[c] - Filling Station, where [c] is its capacity"
	    		+ "<br>3) F[i] - Fire, where [i] is its intensity<br>4) A[t] - Aircraft, where [t] is its tank capacity</html>");
	    lblCaption.setHorizontalAlignment(SwingConstants.LEFT);
	    panel_1.add(lblCaption);
	    frame.getContentPane().add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1000, 500);
		panel.setLayout(new GridLayout(Config.GRID_HEIGHT, Config.GRID_WIDTH));
		
		panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new BorderLayout(0, 0));

        textArea = new JTextArea(10, 20); //Rows and cols to be displayed
        textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		panel_2.add(scrollPane); //We add the scroll, since the scroll already contains the textArea
		panel_2.setPreferredSize(new Dimension(600,500));

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
	
	public static void log(String text) {
		textArea.append(text);
	}
	
	/**
	 * Gets the GUI frame
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	
}
