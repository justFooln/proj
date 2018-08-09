import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.*;

/**
 * @author jhake
 * Responsible for set-up and UI response
 *
 */
//todo add serialization UI - save and load file dialogs
//todo restore reset button
public class GUIHelper {

	private JFrame ghAppFrame;
	private JPanel ghCenterPanel;
	private ArrayList<SoundMaker> ghSoundList = new ArrayList<>(1);
	private MidiHelper ghMidiHelp;
  private ArrayList<JCheckBox> ghCheckBoxList = new ArrayList<>(1);
  // This member holds the default path to read/write BeatBoxFiles. Without it, readwrites
  // go to whatever happens to be the default at that time. Using Downloads directory is arbitrary
  private String ghDefaultFilePath;

	/**
	 * 
	 */
	public GUIHelper( JFrame appFrame, String defaultFilePath ) {

    // Capture the application frame
    this.ghAppFrame = appFrame;

    // Capture the default path to BeatBox files
    this.ghDefaultFilePath = defaultFilePath;

    // Create the midi helper and initialize the list of sounds
	  this.ghMidiHelp = new MidiHelper(this.ghSoundList, this.ghDefaultFilePath);

    // Set up the midi interface
    this.ghMidiHelp.setUpMidi();

  }

	/**
	 * Initialize the contents of the frame.
	 */
	void initializeGUI() {

		// Set up the app frame so the app stops when the frame closes
		this.ghAppFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set the overall layout manager and borders
		BorderLayout bordLayout = new BorderLayout();
		JPanel parentPanel = new JPanel(bordLayout);
		parentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.ghAppFrame.getContentPane().add(parentPanel);

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

    // Define the RESET button and add it to the bottom box
    JButton btnReset = new JButton("Reset");
    btnReset.addActionListener( new MyResetListener() );
    southBox.add(btnReset);

    // Add the bottom panel to the parent panel
		parentPanel.add(BorderLayout.SOUTH, southBox);

		// Add soundmaker noises in the left column
		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for ( SoundMaker sound : this.ghSoundList) {
			// Get the sound maker labels - convert to StringBuffer to match types with Label
			nameBox.add( new Label( sound.getSoundMidiName() ) );
		}
		// Add the left box of names to the parent panel
		parentPanel.add(BorderLayout.WEST, nameBox);

		// Now create the grid to hold the checkboxes corresponding to sounds at time (ticks)
		int numSounds = this.ghSoundList.size();
		int numFreqs = this.ghSoundList.get(0).getMidiFreqs().size();
		GridLayout grid = new GridLayout(numSounds,numFreqs);
		grid.setVgap(1);
		grid.setHgap(2);
		this.ghCenterPanel = new JPanel(grid);
		parentPanel.add(BorderLayout.CENTER, this.ghCenterPanel);

		// Populate the grid with checkboxes
    this.SoundsToCheckboxes();

		// Add the populated center panel grid to the parent panel
		parentPanel.add(BorderLayout.CENTER, this.ghCenterPanel);

		// Now add choices to the FILE menu
    JMenuBar appMenu = new JMenuBar();
    JMenu fileMenu = new JMenu( "File" );

    // Add a file open choice
    JMenuItem fileOpenEntry = new JMenuItem( "Open BeatBox File" );
    fileOpenEntry.addActionListener( new MyFileOpenListener() );
    fileMenu.add(fileOpenEntry);

    // Add a file save choice
    JMenuItem fileSaveEntry = new JMenuItem( "Save BeatBox File" );
    fileSaveEntry.addActionListener( new MyFileSaveListener() );
    fileMenu.add( fileSaveEntry );

    // Now add the menu to the menu bar
    appMenu.add( fileMenu );
    ghAppFrame.setJMenuBar( appMenu );

    // Tamp the description of the frame, set it's size, and make it visible
    this.ghAppFrame.pack();
    this.ghAppFrame.setBounds(50,50,900,600);
    this.ghAppFrame.setVisible(true);

  } // End initializeGUI


  private void SoundsToCheckboxes(){

	  int numBoxes = this.ghCenterPanel.getComponentCount();
	  if (numBoxes > 0) {
	    this.ghCenterPanel.removeAll();
	    this.ghCheckBoxList.clear();
      numBoxes = this.ghCenterPanel.getComponentCount();
    }

	  // Over all sounds
    for ( SoundMaker sound : this.ghSoundList ) {

      // Over all frequences for each sound
      for (Boolean freqSelected : sound.getMidiFreqs() ) {

        // Create a checkbox and set the state depending on whether that frequency is selected
        JCheckBox chkBox = new JCheckBox();
        chkBox.setEnabled(true);
        chkBox.setSelected(freqSelected);

        // Add the checkbox to the panel and member checkbox list
        this.ghCheckBoxList.add(chkBox);
        this.ghCenterPanel.add(chkBox);
      }

    }

   }  // End SoundsToCheckboxes


  private void CheckboxesToSounds() {
    // Over all sounds
    for ( int i=0; i < this.ghSoundList.size(); i++ ) {

      // Over all frequences for each sound
      int numFreqs = this.ghSoundList.get(i).getMidiFreqs().size();
      ArrayList<Boolean> tempFreqs = new ArrayList<>( numFreqs );
      for ( int j = 0; j < numFreqs; j++ ) {
        int indexIntoChkBoxArray = (i * numFreqs);
        JCheckBox chkBox = this.ghCheckBoxList.get( j + indexIntoChkBoxArray );
        Boolean chkBoxState = chkBox.isSelected();
        tempFreqs.add( chkBoxState );
      }
      this.ghSoundList.get(i).setMidiFreqs( tempFreqs );
    }
  }  // End CheckboxesToSounds

		/*
	 * This code is where the action happens - literally. These functions respond to user input
	 */

	// Handles events for the start button.
	public class MyStartListener implements ActionListener {

		// Throw away the event. Build the track and start the Midi playing
		public void actionPerformed(ActionEvent a) {
			// Get the states of the check boxes and transfer them to the frequencies for each sound
      // check box state -> sound list
      CheckboxesToSounds();

			ghMidiHelp.buildTrackAndStart(ghSoundList);
		} // End ActionPerformed

	} // End MyStartListener class


  // Handles events for the stop button
  public class MyStopListener implements ActionListener {

    // Throw away the event. Just stop the Midi.
    public void actionPerformed(ActionEvent a) {
      ghMidiHelp.sequencerStop();
    } // End ActionPerformed

  } // End MyStopListener


  // Handles events for the reset button
  public class MyResetListener implements ActionListener {

    // Throw away the event. Just reset the sounds and checkbox list.
    // Then refresh the UI.
    public void actionPerformed(ActionEvent a) {

      // Reset all the sounds to have no frequencies selected
      for (SoundMaker sound : ghSoundList) {
        int numFreqs = sound.getMidiFreqs().size();
        sound.setAndSizeMidFreqs( numFreqs );
      }

      // Now make the UI display the unchecked boxes
      refreshCheckboxUI();

    } // End ActionPerformed

  } // End MyResetListener


  // Handles events for the File:Open menu
  public class MyFileOpenListener implements ActionListener {

    // Throw away the event. Just save a BeatBox sound file.
    public void actionPerformed(ActionEvent a) {

      // Set up a chooser dialog to show the users files and directories
      JFileChooser opnFlChsr = new JFileChooser( ghDefaultFilePath );
      opnFlChsr.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

      // Get the file name from the user
      int results = opnFlChsr.showOpenDialog( ghAppFrame );

      // Save if the user accepted
      if (results == JFileChooser.APPROVE_OPTION) {
        LoadFile( opnFlChsr.getSelectedFile() );
        refreshCheckboxUI();
      }

    } // End ActionPerformed

  } // End MyFileOpenListener


  // Handles events for the File:Save menu
 public class MyFileSaveListener implements ActionListener {

    // Throw away the event. Just open a BeatBox sound file.
    public void actionPerformed(ActionEvent a) {

      // Set up a chooser dialog to show the users files and directories
      JFileChooser saveFileChooser = new JFileChooser( ghDefaultFilePath );
      saveFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

      // Get the file name from the user
      int results = saveFileChooser.showSaveDialog( ghAppFrame );

      // Save if the user accepted
      if (results == JFileChooser.APPROVE_OPTION) {
        SaveFile( saveFileChooser.getSelectedFile() );
      }

    } // End ActionPerformed

  } // End MyFileOpenListener


  // Saves Sounds as serialized objects to a Java File
  // Java appears to write object files FIFO
  private void SaveFile(File fileToSave) {

	  try {

      // Setup the stream to the file
      ObjectOutputStream serialStream = new ObjectOutputStream( new FileOutputStream( fileToSave ));

      // Now write the number of sounds to the file - needed for loading a file
      // because ObjectInputStream.readObject() lacks a clean way to detect EOF without error
      int numSounds = this.ghSoundList.size();
      serialStream.writeInt( numSounds );

      // Iterate over the list of sounds, serializing each one to the File
     for ( SoundMaker sound : this.ghSoundList) {
        serialStream.writeObject( sound );
      }

      // Close the output stream
      serialStream.close();

    } catch ( Exception ex ) {
	    ex.printStackTrace();
    }
  }  // End SaveFile

  // Loads Sounds as serialized objects from a Java File
  // Java appears to write object files FIFO, expect read FIFO
  private void LoadFile(File fileToLoad) {

    try {

      // Setup the stream to the file
      ObjectInputStream serialStream = new ObjectInputStream( new FileInputStream( fileToLoad ));

      // Read the number of objects in the file
      int numSounds = serialStream.readInt();

      // Iterate over the sounds in the file, adding them to ghSoundList
      this.ghSoundList.clear();
      for ( int i=0; i < numSounds; i++) {
        SoundMaker sound = (SoundMaker) serialStream.readObject();
        this.ghSoundList.add(sound);
      }

      // Close the input stream
      serialStream.close();

    } catch ( Exception ex ) {
      ex.printStackTrace();
    }
  }  // End LoadFile


  private void refreshCheckboxUI() {
    SoundsToCheckboxes();
    this.ghCenterPanel.revalidate();
    this.ghCenterPanel.repaint();
  } // End refreshCheckboxUI


}  // End GUIHelper
