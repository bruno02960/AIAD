package firefighting.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.SwingConstants;

import firefighting.utils.Config;

import javax.swing.JButton;
import javax.swing.JTextField;

public class WelcomeScreen {

	private JFrame frame;
	private JTextField gridWidth;
	private JTextField gridHeight;
	private JTextField maxWaterResources;
	private JTextField maxAircrafts;
	private JTextField maxFires;
	private JButton btnGo = new JButton("Go");

	/**
	 * Create the application.
	 */
	public WelcomeScreen() {
		frameInitialize();
		
		titleInitalize();
		
		buttonIntialize();
		
		formInitialize();
	}

	/**
	 * Initializes the form
	 */
	private void formInitialize() {
		gridWidthInitialize();
		
		gridHeightInitialize();
		
		maxWaterResourcesInitialize();
		
		maxAircraftsInitialize();
		
		maxFiresInitialize();
	}

	/**
	 * Initializes the max fires form field
	 */
	private void maxFiresInitialize() {
		maxFires = new JTextField();
		maxFires.setHorizontalAlignment(SwingConstants.CENTER);
		maxFires.setColumns(10);
		maxFires.setBounds(154, 241, 120, 20);
		frame.getContentPane().add(maxFires);
		maxFires.setText(String.valueOf(Config.NUM_MAX_FIRES));
		
		JLabel lblMaxFires = new JLabel("Max. Fires");
		lblMaxFires.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaxFires.setBounds(10, 244, 134, 14);
		frame.getContentPane().add(lblMaxFires);
	}

	/**
	 * Initializes the max aircrafts form field
	 */
	private void maxAircraftsInitialize() {
		maxAircrafts = new JTextField();
		maxAircrafts.setHorizontalAlignment(SwingConstants.CENTER);
		maxAircrafts.setColumns(10);
		maxAircrafts.setBounds(154, 210, 120, 20);
		frame.getContentPane().add(maxAircrafts);
		maxAircrafts.setText(String.valueOf(Config.NUM_MAX_AIRCRAFTS));
		
		JLabel lblMaxAircrafts = new JLabel("Num. Aircrafts");
		lblMaxAircrafts.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaxAircrafts.setBounds(10, 213, 134, 14);
		frame.getContentPane().add(lblMaxAircrafts);
	}

	/**
	 * Initializes the max water resources form field
	 */
	private void maxWaterResourcesInitialize() {
		maxWaterResources = new JTextField();
		maxWaterResources.setHorizontalAlignment(SwingConstants.CENTER);
		maxWaterResources.setColumns(10);
		maxWaterResources.setBounds(154, 179, 120, 20);
		frame.getContentPane().add(maxWaterResources);
		maxWaterResources.setText(String.valueOf(Config.NUM_MAX_WATER_RESOURCES));
		
		JLabel lblMaxWaterResources = new JLabel("Num. Water Resources");
		lblMaxWaterResources.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaxWaterResources.setBounds(10, 182, 134, 14);
		frame.getContentPane().add(lblMaxWaterResources);
	}

	/**
	 * Initializes the grid height form field
	 */
	private void gridHeightInitialize() {
		gridHeight = new JTextField();
		gridHeight.setHorizontalAlignment(SwingConstants.CENTER);
		gridHeight.setColumns(10);
		gridHeight.setBounds(154, 148, 120, 20);
		frame.getContentPane().add(gridHeight);
		gridHeight.setText(String.valueOf(Config.GRID_HEIGHT));
		
		JLabel lblGridHeight = new JLabel("Grid Height");
		lblGridHeight.setHorizontalAlignment(SwingConstants.CENTER);
		lblGridHeight.setBounds(10, 151, 134, 14);
		frame.getContentPane().add(lblGridHeight);
	}

	/**
	 * Initializes the grid width form field
	 */
	private void gridWidthInitialize() {
		gridWidth = new JTextField();
		gridWidth.setHorizontalAlignment(SwingConstants.CENTER);
		gridWidth.setBounds(154, 117, 120, 20);
		frame.getContentPane().add(gridWidth);
		gridWidth.setColumns(10);
		gridWidth.setText(String.valueOf(Config.GRID_WIDTH));
		
		JLabel lblMaxAirplanes = new JLabel("Grid Width");
		lblMaxAirplanes.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaxAirplanes.setBounds(10, 120, 134, 14);
		frame.getContentPane().add(lblMaxAirplanes);
	}

	/**
	 * Initializes the go button
	 */
	private void buttonIntialize() {
		btnGo.setBounds(97, 300, 89, 23);
		frame.getContentPane().add(btnGo);
	}

	/**
	 * Initializes the app title label
	 */
	private void titleInitalize() {
		JLabel lblAgentesInteligentesNo = new JLabel("<html><div style='text-align: center;'>Agentes Inteligentes no Combate a Inc\u00EAndios Florestais</div></html>");
		lblAgentesInteligentesNo.setBounds(0, 0, 284, 106);
		lblAgentesInteligentesNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgentesInteligentesNo.setFont(new Font("Leelawadee", Font.BOLD, 20));
		frame.getContentPane().add(lblAgentesInteligentesNo);
	}

	/**
	 * Initializes the frame
	 */
	private void frameInitialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	/**
	 * Returns the welcome screen frame
	 * @return the welcome screen frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Returns the value of the grid width field
	 * @return the value of the grid width field
	 */
	public JTextField getGridWidth() {
		return gridWidth;
	}

	/**
	 * Returns the value of the grid height field
	 * @return the value of the grid height field
	 */
	public JTextField getGridHeight() {
		return gridHeight;
	}

	/**
	 * Returns the value of the max water resources field
	 * @return the value of the max water resources field
	 */
	public JTextField getMaxWaterResources() {
		return maxWaterResources;
	}

	/**
	 * Returns the value of the max aircrafts field
	 * @return the value of the max aircrafts field
	 */
	public JTextField getMaxAircrafts() {
		return maxAircrafts;
	}

	/**
	 * Returns the value of the max fires field
	 * @return the value of the max fires field
	 */
	public JTextField getMaxFires() {
		return maxFires;
	}

	/**
	 * Returns the button go
	 * @return the button go
	 */
	public JButton getBtnGo() {
		return btnGo;
	}
}
