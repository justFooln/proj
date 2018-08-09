import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;
import javax.sound.midi.*;

public class WBtestAppWindow {

	// Must create member variable before it's used in main()
	JFrame appFrame = new JFrame("It's Beat Box, Bitches!");
    JPanel centerPanel; 
    ArrayList<JCheckBox> checkboxList = new ArrayList<JCheckBox>();
    
    Sequencer sequencer;
    Sequence sequence;
    Track track;

	
    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat","Acoustic Snare"};
    int[] instruments = {35,42,46,38};
	
	
	/**
	 * Launch the application.
	 * After setup in main, everything is driven by the listeners
	 */
	public static void main(String[] args) {
		
		// Create the beat box application
    WBtestAppWindow BeatBoxApp = new WBtestAppWindow();
        
    // Setup the UI
    BeatBoxApp.initializeGUI();
        
    // Setup the Midi interface
    BeatBoxApp.setUpMidi();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeGUI() {
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set the overall layout manager and borders
		BorderLayout layout = new BorderLayout();
		JPanel parentPanel = new JPanel(layout);
		parentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		appFrame.getContentPane().add(parentPanel);

		// Set buttons along bottom row
		Box southBox = new Box(BoxLayout.X_AXIS);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new MyStartListener());
		southBox.add(btnStart);         

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new MyStopListener());
		southBox.add(btnStop);         

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new MyResetListener());
		southBox.add(btnReset);         

		parentPanel.add(BorderLayout.SOUTH, southBox);

		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for (int i = 0; i < 4; i++) {
			nameBox.add(new Label(instrumentNames[i]));
		}

		parentPanel.add(BorderLayout.WEST, nameBox);


		GridLayout grid = new GridLayout(4,4);
		grid.setVgap(1);
		grid.setHgap(2);
		centerPanel = new JPanel(grid);
		parentPanel.add(BorderLayout.CENTER, centerPanel);

		for (int i = 0; i < 16; i++) {                    
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			c.setEnabled(true);
			checkboxList.add(c);
			centerPanel.add(c);            
		} // end loop

		parentPanel.add(BorderLayout.CENTER, centerPanel);

		appFrame.pack();
		appFrame.setBounds(50,50,300,300);
		appFrame.setVisible(true);

	} // End initializeGUI


	/*
	 * This code is where the action happens - literally. These functions respond to user input
	 */

	// Handles events for the start button.
	public class MyStartListener implements ActionListener {

		// Throw away the event. Build the track and start the Midi playing
		public void actionPerformed(ActionEvent a) {
			buildTrackAndStart();
		}

	} // End MyStartListener class

	// Handles events for the stop button
	public class MyStopListener implements ActionListener {

		// Throw away the event. Just stop the Midi.
		public void actionPerformed(ActionEvent a) {
			sequencer.stop();
		}

	} // End MyStopListener

	// Handles events for the reset button
	public class MyResetListener implements ActionListener {

		// Throw away the event. Just stop the Midi.
		public void actionPerformed(ActionEvent a) {
			sequencer.stop();
			// More to add here - for now just works like stop
		}

	} // End MyStopListener


	/*
	 * The rest of this code is to drive the Midi system in response to 
	 * user inputs to the UI.
	 */

	// Initializes the Midi
	public void setUpMidi() {
		try {
			// Access the built-in Midi system
			sequencer = MidiSystem.getSequencer();
			sequencer.open();

			// Set the tempo in the sequencer
			sequencer.setTempoInBPM(120);

			// Create the sequence based on tempo (PPQ) and ticks-per-beat
			sequence = new Sequence(Sequence.PPQ,4);

			// Create one track in the sequence. 
			track = sequence.createTrack();

		} catch(Exception e) {e.printStackTrace();}
	} // close method

	// Builds the sequence track and starts the Midi playing.
	public void buildTrackAndStart() {
		int[] trackList = null;

		// kill the existing track and start a new one 
		sequence.deleteTrack(track);
		track = sequence.createTrack();

		// Now add the instruments at various tempos to the track
		// Iterate over all instruments
		for (int i = 0; i < 4; i++) {

			// Set the instrument
			int key = instruments[i];   

			// Size of tracklist is determined by the number of checkboxes for each instrument.
			trackList = new int[4];

			// Iterate over all the checkboxes to build the trackList.
			// Add an instrument at tempo for each checkbox
			for (int j = 0; j < 4; j++ ) {

				// See if a checkbox is selected - index in checkboxlist by instrument
				// BUG - if every checkbox but the last one is checked, key is not added to trackList[j]
				JCheckBox jc = checkboxList.get(j + (4*i));
				if ( jc.isSelected()) {
					trackList[j] = key;
				} else {
					trackList[j] = 0;
				}                    
			} // close inner loop

			// For one instrument, tracklist now contains the instrument key
			// for each of the instrument's selected checkboxes.
			// traccklist contains Zero otherwise. 
			makeTracks(trackList);

			track.add(makeEvent(176,1,127,0,16));  
		} // close outer
		// BUG what is this track.add doing here?
		track.add(makeEvent(192,9,1,0,15));      
		try {
			sequencer.setSequence(sequence); 
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);                   
			sequencer.start();
			sequencer.setTempoInBPM(120);
		} catch(Exception e) {e.printStackTrace();}

	} // close buildTrackAndStart method


	public void makeTracks(int[] list) {        

		for (int i = 0; i < 4; i++) {

			int key = list[i];

			if (key != 0) {
				// Add a note on event
				track.add(makeEvent(144,9,key, 100, i));
				// Add a note off event
				track.add(makeEvent(128,9,key, 100, i+1));
			}
		}
	}

	public  MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);

		} catch(Exception e) {e.printStackTrace(); }
		return event;
	}


} // End WBtestAppWindow

