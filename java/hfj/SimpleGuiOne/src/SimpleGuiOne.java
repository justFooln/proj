/**
 * 
 */
import javax.swing.*;

/**
 * @author jhake
 *
 */
public class SimpleGuiOne {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create a frame
		JFrame myFrame = new JFrame();
		
		// Create a button
		JButton myButton = new JButton("Click Me!");
		
		// Set the program to exit when the window is closed
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add the button to the content pane in the frame
		myFrame.getContentPane().add(myButton);
		
		// Set the frame size 
		myFrame.setSize(300, 300);
		
		// Show the frame containing the button
		myFrame.setVisible(true);
	}

}
