/**
 * 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author jhake
 * Responsible for set-up and UI response
 *
 */
//todo add serialization UI - save and load file dialogs
//todo restore reset button
public class GUIHelper {

	JFrame bbAppFrame;
	private JPanel bbCenterPanel;
	private ArrayList<SoundMaker> bbSoundList = new ArrayList<>(1);
	MidiHelper bbMidiHelp;
  ArrayList<JCheckBox> bbCheckBoxList = new ArrayList<>(1);

	/**
	 * 
	 */
	public GUIHelper() {

    // Create the midi helper and initialize the list of sounds
	  this.bbMidiHelp = new MidiHelper(this.bbSoundList);

    // Set up the midi interface
    bbMidiHelp.setUpMidi();

  }

	/**
	 * Initialize the contents of the frame.
	 */
	void initializeGUI(JFrame appFrame) {

		// Capture the application frame
		this.bbAppFrame = appFrame;

		// Set up so the app stops when the frame closes
		this.bbAppFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set the overall layout manager and borders
		BorderLayout bordLayout = new BorderLayout();
		JPanel parentPanel = new JPanel(bordLayout);
		parentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.bbAppFrame.getContentPane().add(parentPanel);

		// Set buttons along bottom row
		// Define the box to hold the buttons
		Box southBox = new Box(BoxLayout.X_AXIS);

		// Define the START button and add it to the bottom box
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener( new MyStartListener() );
		southBox.add(btnStart);

		// Define the STOP button and add it to the bottom box
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener( new MyStopListener() );
		southBox.add(btnStop);

		// Add the bottom panel to the parent panel
		parentPanel.add(BorderLayout.SOUTH, southBox);

		// Add soundmaker noises in the left column
		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for ( SoundMaker sound : this.bbSoundList) {
			// Get the sound maker labels - convert to StringBuffer to match types with Label
			nameBox.add( new Label( new String( sound.getSoundMidiName() ) ) );
		}
		// Add the left box of names to the parent panel
		parentPanel.add(BorderLayout.WEST, nameBox);


		// Now create the grid to hold the checkboxes corresponding to sounds at time (ticks)
		int numSounds = this.bbSoundList.size();
		int numFreqs = this.bbSoundList.get(0).getMidiFreqs().size();
		GridLayout grid = new GridLayout(numSounds,numFreqs);
		grid.setVgap(1);
		grid.setHgap(2);
		this.bbCenterPanel = new JPanel(grid);
		parentPanel.add(BorderLayout.CENTER, this.bbCenterPanel);

		// Populate the grid with checkboxes
		// Over all sounds
		for ( SoundMaker sound : this.bbSoundList ) {

			// Over all frequences for each sound
			for (Boolean freqSelected : sound.getMidiFreqs() ) {

				// Create a checkbox and set the state depending on whether that frequency is selected
				JCheckBox chkBox = new JCheckBox();
				chkBox.setSelected(freqSelected);

				// Enable the checkbox and add it to the panel and member checkbox list
        bbCheckBoxList.add(chkBox);
				chkBox.setEnabled(true);
				this.bbCenterPanel.add(chkBox);
			}

		}
		// Add the populated center panel grid to the parent panel
		parentPanel.add(BorderLayout.CENTER, this.bbCenterPanel);

		// Tamp the description of the frame, set it's size, and make it visible
		this.bbAppFrame.pack();
		this.bbAppFrame.setBounds(50,50,900,600);
		this.bbAppFrame.setVisible(true);

	} // End initializeGUI

		/*
	 * This code is where the action happens - literally. These functions respond to user input
	 */

	// Handles events for the start button.
	public class MyStartListener implements ActionListener {

		// Throw away the event. Build the track and start the Midi playing
		public void actionPerformed(ActionEvent a) {
			// Get the states of the check boxes and transfer them to the frequencies for each sound
			// Over all sounds
			for ( int i=0; i < bbSoundList.size(); i++ ) {

				// Over all frequences for each sound
				int numFreqs = bbSoundList.get(i).getMidiFreqs().size();
				ArrayList<Boolean> tempFreqs = new ArrayList<>( numFreqs );
				for ( int j = 0; j < numFreqs; j++ ) {
				  int indexIntoChkBoxArray = (i * numFreqs);
					JCheckBox chkBox = (JCheckBox) (JCheckBox) bbCheckBoxList.get( j + indexIntoChkBoxArray );
					Boolean chkBoxState = chkBox.isSelected();
					tempFreqs.add( chkBoxState );
				}
				bbSoundList.get(i).setMidiFreqs( tempFreqs );
			}
			bbMidiHelp.buildTrackAndStart(bbSoundList);
		} // End ActionPerformed
	} // End MyStartListener class

	// Handles events for the stop button
	public class MyStopListener implements ActionListener {

		// Throw away the event. Just stop the Midi.
		public void actionPerformed(ActionEvent a) {
			bbMidiHelp.sequencerStop();
		}

	} // End MyStopListener


}  // End GUIHelper
