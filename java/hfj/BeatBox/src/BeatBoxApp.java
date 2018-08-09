import java.awt.*;
import javax.swing.*;
import java.util.*;


public class BeatBoxApp {

	/**
	 * Launch the application.
	 * After setup in main, everything is driven by the listeners in GUIHelper
	 */
	public static void main(String[] args) {
		
		// Create the beat box application
    BeatBoxApp BeatBoxApp = new BeatBoxApp();

		// Create member variables
		JFrame appFrame = new JFrame("It's Beat Box!");
		GUIHelper guiHelp = new GUIHelper();

		// Setup the UI based, in part on the instruments
		guiHelp.initializeGUI(appFrame);
        
	}

} // End BeatBoxAppWindow