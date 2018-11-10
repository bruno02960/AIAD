/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */

package firefighting.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
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

public class GraphicUserInterface {

	// Global Instance Variables:
	
	/**
	 * The world agent associated to the graphic user interface.
	 */
	private static WorldAgent worldAgent;
	
	/**
	 * The frame associated to the graphic user interface.
	 */
	private JFrame frame;
	
	/**
	 * The grid that represents the current status of the world.
	 */
	private static JLabel[][] grid;
    
	/**
	 * The text area associated to the graphic user interface.
	 */
	private static JTextArea textArea;

	
	
	// Constructors:
	
	/**
	 * Constructor #1 of the GUI.
	 * 
	 * Creates the graphic user interface.
	 * 
	 * @param worldAgent the world agent associated to the graphic user interface
	 */
	public GraphicUserInterface(WorldAgent worldAgent) {
		GraphicUserInterface.worldAgent = worldAgent;
		
		JPanel panel = new JPanel();
		JPanel panel_1 = new JPanel();
		JPanel panel_2 = new JPanel();

		frameInitialize(panel, panel_1, panel_2);
	    
	    FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
	    flowLayout.setHgap(100);
	    flowLayout.setAlignment(FlowLayout.LEFT);
	    
	    captionInitialize(panel, panel_1);

        textArea = new JTextArea(10, 20);
        textArea.setEditable(false);

        scrollPaneInitialize(panel_2);
	    gridInitialize(worldAgent, panel);
	}
	
	
	
	// Methods:
	
	/**
	 * Initialises the grid that represents the current status of the world.
	 * 
	 * @param worldAgent the world agent associated to the graphic user interface
	 * @param panel main the panel associated to the graphic user interface
	 */
	private void gridInitialize(WorldAgent worldAgent, JPanel panel) {
		grid= new JLabel[Config.GRID_WIDTH][Config.GRID_HEIGHT];
	    for (int i = 0; i < Config.GRID_HEIGHT; i++){
	        for (int j = 0; j < Config.GRID_WIDTH; j++){
	            grid[j][i] = new JLabel();
	            grid[j][i].setBorder(new LineBorder(Color.BLACK));
	            grid[j][i].setHorizontalAlignment(SwingConstants.CENTER);
	            grid[j][i].setVerticalAlignment(SwingConstants.CENTER);
	            grid[j][i].setOpaque(true);
	            
	            setCell(worldAgent.getWorldMap()[j][i], grid[j][i]);
	            panel.add(grid[j][i]);
	        }
	    }
	}

	/**
	 * Initialises the caption area associated to the graphic user interface.
	 * 
	 * @param mainPanel main panel associated to the graphic user interface
	 * @param captionPanel caption panel associated to the graphic user interface
	 */
	private void captionInitialize(JPanel mainPanel, JPanel captionPanel) {
		JLabel labelCaption = new JLabel("<html>Caption:<br>1) Fire Station<br>2) Water Resource [c], where c is its water quantity"
	    		+ "<br>3) Fire [i], where i is its current intensity<br>4) Aircraft [F: f | W: w], where f is its fuel tank quantity and w is its water quantity</html>");
	    labelCaption.setHorizontalAlignment(SwingConstants.LEFT);
	    captionPanel.add(labelCaption);
		mainPanel.setLayout(new GridLayout(Config.GRID_HEIGHT, Config.GRID_WIDTH));
	}

	/**
	 * Initialises the frame associated to the graphic user interface.
	 * 
	 * @param mainPanel main panel associated to the graphic user interface
	 * @param captionPanel caption panel associated to the graphic user interface
	 * @param infoPanel info panel associated to the graphic user interface
	 */
	private void frameInitialize(JPanel mainPanel, JPanel captionPanel, JPanel infoPanel) {
		frame = new JFrame();
		frame.setTitle("Firefighting");
	    frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setBounds(100, 100, 1800, 1000);
	    frame.getContentPane().add(captionPanel, BorderLayout.SOUTH);
		frame.getContentPane().add(infoPanel, BorderLayout.EAST);
	}

	/**
	 * Initialises the info scroll's panel associated to the graphic user interface.
	 * 
	 * @param infoPanel info panel associated to the graphic user interface
	 */
	private void scrollPaneInitialize(JPanel infoPanel) {
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		// Uncomment do deactivate the horizontal scroll bar
		//scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		infoPanel.setLayout(new BorderLayout(0, 0));
		infoPanel.add(scrollPane); //We add the scroll, since the scroll already contains the textArea
		infoPanel.setPreferredSize(new Dimension(800,500));
	}
	
	/**
	 * Sets a given cell of the graphic user interface's grid.
	 * 
	 * @param worldCell cell of the world's map being processed
	 * @param gridCell cell of the world's grid being processed
	 */
	private static void setCell(Object worldCell, JLabel gridCell) {
		if (worldCell != null) {
			
			gridCell.setText(worldCell.toString());
        	gridCell.setHorizontalTextPosition(JLabel.CENTER);
    		gridCell.setVerticalTextPosition(JLabel.BOTTOM);
    		
        	if (worldCell instanceof Fire) {
        		gridCell.setBackground(Color.orange);
        		gridCell.setIcon(new ImageIcon("imgs/fire.png"));
        	}
      
        	if (worldCell instanceof AircraftAgent) {
        		gridCell.setBackground(Color.green);
        		gridCell.setIcon(new ImageIcon("imgs/aircraft.png"));
        	}
        	
        	if (worldCell instanceof WaterResource) {
        		gridCell.setBackground(Color.cyan);
        		gridCell.setIcon(new ImageIcon("imgs/water-resource.png"));
        	}
      
        	if (worldCell instanceof FireStationAgent) {
        		gridCell.setBackground(Color.gray);
        		gridCell.setIcon(new ImageIcon("imgs/fire-station.png"));
        	}
		}
        else {
        	gridCell.setBackground(null);
        	gridCell.setIcon(null);
        	gridCell.setText("");
        }
	}
	
	/**
	 * TODO
	 * 
	 * @param posX
	 * @param posY
	 * @param color
	 * @param icon
	 */
	public static void changePosColor(int posX, int posY, Color color, ImageIcon icon) {
        grid[posX][posY].setOpaque(true);
        grid[posX][posY].setBackground(color);
        grid[posX][posY].setIcon(icon);
	}
	
	/**
	 * Fills the grid with the updated positions of the objects on the world's map/grid.
	 */
	public static void fillGrid() {
		for (int i = 0; i < Config.GRID_HEIGHT; i++){
	        for (int j = 0; j < Config.GRID_WIDTH; j++){
	            grid[j][i].setOpaque(true);
	            setCell(worldAgent.getWorldMap()[j][i], grid[j][i]);
	        }
	    }
	}
	
	/**
	 * Logs a message to appear in the graphic user interface.
	 * 
	 * @param text a message to appear in the graphic user interface
	 */
	public static void log(String text) {
		textArea.append(text);
	}
	
	/**
	 * Returns the graphic user interface's frame.
	 * 
	 * @return the graphic user interface's frame
	 */
	public JFrame getFrame() {
		return this.frame;
	}		
}