package firefighting.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class GUI {

	private JFrame frame;
	private JTextPane textPane;

	public JTextPane getTextPane() {
		return textPane;
	}

	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		frame.setTitle("Firefighting");
		frame.getContentPane().setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(420, 11, -403, 239);
		frame.getContentPane().add(textPane);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
