import javax.swing.*;
import java.io.File;


public class BeatBoxSer {


  /**
	 * Launch the application.
	 * After setup in main, everything is driven by the listeners in GUIHelper
	 */
	public static void main(String[] args) {
		
		// Create the beat box application
    BeatBoxSer BeatBoxSer = new BeatBoxSer();

    // Create the frame for the application
		JFrame appFrame = new JFrame("It's Beat Box!");

    // Define the correct File system Path for BeatBox files.
    // I pick the user's Downloads directory. This is completely arbitrary,
    // it just has to be used consistently throughout the code
    File userHomeDirectory = new File( System.getProperty( "user.home" ));
    StringBuffer bbDefaultFilePath = new StringBuffer(userHomeDirectory.toString());
    bbDefaultFilePath.append( "/Downloads/" );

    // Setup the UI based, in part on the instruments
    GUIHelper guiHelp = new GUIHelper(appFrame, bbDefaultFilePath.toString());
		guiHelp.initializeGUI();
        
	}

} // End BeatBoxSerWindow