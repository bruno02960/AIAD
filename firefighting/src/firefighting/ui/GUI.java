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
import firefighting.utils.AircraftMetricsStats;
import firefighting.utils.Config;
import firefighting.world.WorldAgent;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class GUI {
	
	private static boolean active;

	private static WorldAgent worldAgent;
	private JFrame mainFrame;
	private static JLabel[][] grid;
    private static JTextArea textArea;
    private JPanel panel_3;
    private JPanel panel_4;

    public GUI() {
    	active = false;
    }
    
	/**
	 * Creates the graphic user interface
	 * @param worldAgent the world agent
	 */
	public GUI(WorldAgent worldAgent) {
		GUI.worldAgent = worldAgent;
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 794, 642);
		panel_3 = new JPanel();
		panel_3.setBounds(9, 660, 1095, 93);
		panel_4 = new JPanel();
		panel_4.setBounds(810, 10, 762, 642);

		frameInitialize(panel, panel_3, panel_4);
	    
	    captionInitialize(panel, panel_3);

        textArea = new JTextArea(10, 20);
        textArea.setEditable(false);

        scrollPaneInitialize(panel_4);
	    gridInitialize(worldAgent, panel);
	    
	    JButton btnNewButton = new JButton("Stop");
	    btnNewButton.setBounds(1430, 680, 141, 72);
	    mainFrame.getContentPane().add(btnNewButton);
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		AircraftAgent[] aircraftAgents = worldAgent.getAircraftAgents();
	    		
	    		for(int i = 0; i < aircraftAgents.length; i++) {
	    			AircraftMetricsStats aircraftMetricsStats = aircraftAgents[i].getAircraftMetricsStats();
	    			
	    			GUI.log("\n");
	    			GUI.log("\n");
	    			GUI.log("Aircraft Agent " + i + " Stats:" + "\n");
	    			GUI.log("- Number of Total Fires Attended: " + aircraftMetricsStats.getNumTotalFiresAttendedByThisAircraft() + "\n");
	    			GUI.log("- Number of Total Fires Extinguished: " + aircraftMetricsStats.getNumTotalFiresExtinguishedByThisAircraft() + "\n");
	    			GUI.log("- Number of Total Water Refills: " + aircraftMetricsStats.getNumTotalWaterRefillsByThisAircraft() + "\n");
	    			GUI.log("- Number of Total Travels: " + aircraftMetricsStats.getNumTotalTravelsByThisAircraft() + "\n");
	    			GUI.log("\n");
	    			GUI.log("- Number of Total Time to Attend Fires: " + (double) aircraftMetricsStats.getTotalTimeToAttendFiresByThisAircraft()/1000 + "s\n");
	    			GUI.log("- Number of Total Time to Water Refills: " + (double) aircraftMetricsStats.getTotalTimeToWaterRefillsByThisAircraft()/1000 + "s\n");
	    			GUI.log("- Number of Total Time in Travels: " + (double) aircraftMetricsStats.getTotalTimeInTravelsByThisAircraft()/1000 + "s\n");
	    			GUI.log("\n");
	    			GUI.log("- Number of Total Fire Alerts Received: " + aircraftMetricsStats.getNumTotalFireAlertMessagesReceivedByThisAircraft() + "\n");
	    			GUI.log("- Number of Total Proposals Sent: " + aircraftMetricsStats.getNumTotalMessagesSentByThisAircraft() + "\n");
	    			GUI.log("\n");
	    			GUI.log("\n");
	    		}
	    		
	    		worldAgent.shutDown();
	    		btnNewButton.setVisible(false);
	    	}
	    });
	    btnNewButton.setAlignmentY(0.0f);
	}

	/**
	 * Initializes the grid
	 * @param worldAgent world agent
	 * @param panel main panel
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
	 * Initialises the capiton area
	 * @param panel main panel
	 * @param panel_1 captionPanel
	 */
	private void captionInitialize(JPanel panel, JPanel panel_1) {
		panel_3.setLayout(new BorderLayout(1000, 1000));
		JLabel lblCaption = new JLabel("<html>Caption:<br>1) ST - Fire Station<br>2) W - Filling Station"
	    		+ "<br>3) F[i] - Fire, where [i] is its intensity<br>4) A[t] - Aircraft, where [t] is its tank capacity</html>");
		lblCaption.setHorizontalAlignment(SwingConstants.LEFT);
	    panel_1.add(lblCaption, BorderLayout.WEST);
		panel.setLayout(new GridLayout(Config.GRID_HEIGHT, Config.GRID_WIDTH));
	}

	/**
	 * Initialises the frame
	 * @param panel main panel
	 * @param panel_1 caption panel
	 * @param panel_2 info panel
	 */
	private void frameInitialize(JPanel panel, JPanel panel_1, JPanel panel_2) {
		mainFrame = new JFrame();
		mainFrame.setTitle("Firefighting");
	    mainFrame.getContentPane().setLayout(null);
	    mainFrame.getContentPane().add(panel);
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		mainFrame.setBounds(100, 100, 1600, 800);
	    mainFrame.getContentPane().add(panel_1);
		mainFrame.getContentPane().add(panel_2);
	}

	/**
	 * Initalizes the info scroll panel
	 * @param panel_2 info panel
	 */
	private void scrollPaneInitialize(JPanel panel_2) {
		panel_4.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel_2.add(scrollPane); //We add the scroll, since the scroll already contains the textArea
		panel_2.setPreferredSize(new Dimension(800,500));
	}
	
	/**
	 * Sets a given cell of the GUI grid
	 * @param worldCell cell of the worldMap being processed
	 * @param gridCell cell of the grid being processed
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
	 * Called on tick to fill the grid with the updated positions of the objects
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
	 * Logs a message in the GUI
	 * @param text Message to log
	 */
	public static void log(String text) {
		textArea.append(text);
	}
	
	/**
	 * Gets the GUI frame
	 * @return the frame
	 */
	public JFrame getFrame() {
		return mainFrame;
	}

	public static boolean isActive() {
		return active;
	}
	
	
}
