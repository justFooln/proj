/**
 * 
 */

/**
 * @author jhake
 *
 */
import javax.swing.*;
import java.awt.event.*;


public class SimpleGuiOneB implements ActionListener {

	JButton myButton;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Create an instance of this
		SimpleGuiOneB myGui = new SimpleGuiOneB();
		
		myGui.go();
		
	}
	
	public void go() {
		
		// Create a frame
		JFrame myFrame = new JFrame();
		
		// Create a button
		 myButton = new JButton("Click Me!");
		 
		 // Register for callback
		 myButton.addActionListener(this);
		
		// Set the program to exit when the window is closed
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add the button to the content pane in the frame
		myFrame.getContentPane().add(myButton);
		
		// Set the frame size 
		myFrame.setSize(300, 300);
		
		// Show the frame containing the button
		myFrame.setVisible(true);

	}
	
	public void actionPerformed(ActionEvent event) {
		myButton.setText("I've been clicked");
	}

}
