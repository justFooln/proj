import java.util.ArrayList;
import javax.sound.midi.*;
import java.io.*;
import java.nio.file.FileSystemException;


/**
 * @author jhake
 * Responsible for all things Midi: getting, setting, low-level programming
 */
public class MidiHelper {

	// I had hoped to find many of these Midi related constants defined in a
  // Java midi file. Gave up; I had to define here.
	private final int MH_MIDI_NO_SOUND = 0;
	private final int MH_MIDI_NOTE_SOUND = 127;
	private final int MH_MIDI_CHANNEL_KEYBOARD = 1;
	private final int MH_MIDI_CHANNEL_PERCUSSION = 9;
	/*
	This constant tells the sequencer there are 4 counts (ticks) per quarter note.
	Using 4 means that each tick is an 16th note, which is the resolution of each UI
	checkbox for a sound (instrument). So the beatBoxTrack is built of 16th notes for
	each sound. By experiment, 8 checkboxes = 1 second.
	*/
	private final int MH_MIDI_4COUNTS_QUARTER_NOTE = 4;
	private final int MH_MIDI_16COUNTS_WHOLE_NOTE = 16;

	private Sequencer mhSequencer;
	private Sequence mhSequence;
	private Track mhTrack;
	// This file must be present in the default beatbox directory.
	private String mhGenMidiSoundbankFileName = "32MbGMStereo.sf2";
	private String mhGenMidiSoundBankFilePath;

	/**
	* Default Constructor
	*/
	public MidiHelper() {
	}

	/**
	 * Initializes a list of sounds based on a limited set of instrumensts specific to hfj beatbox
	 *fixme Hate this initialization - the midi instrument numbers should be final defined as static class members.
*/
	public MidiHelper(ArrayList<SoundMaker> soundList, String defaultFilePath ) {

	  // Initialize the complete path to midi synthesizer soundbank file
    StringBuffer tempFilePath = new StringBuffer(defaultFilePath);
    tempFilePath.append( this.mhGenMidiSoundbankFileName );
    this.mhGenMidiSoundBankFilePath = tempFilePath.toString();

		// Initialize default Midi sounds
		ArrayList<SoundMaker> tempSounds = new ArrayList<SoundMaker>();
		tempSounds.add(new SoundMaker(35, "Bass Drum", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(42,"Closed Hi-Hat", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(46,"Open Hi-Hat", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(38,"Acoustic Snare", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(49,"Crash Cymbal", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(39,"Hand Clap", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(50,"High Tom", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(60,"Hi Bongo", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(70,"Maracas", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(72,"Whistle", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(64,"Low Conga", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(56,"Cowbell", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(58,"Vibraslap", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(47,"Low-mid Tom", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(67,"High Agogo", MH_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(63,"Open Hi Conga", MH_MIDI_16COUNTS_WHOLE_NOTE));

		// Initialize sound list
		soundList.clear();
		soundList.addAll(tempSounds);

	}

	/*
  Initializes the Midi
  Use a synthesizer that supports the General MIDI soundbank, not the default in JRE8.
  Thanks to Ben Hession stackoverflow.com/questions/22197856/issues-with-midi-file-in-java
  File is available at: http://www.ntonyx.com/soft/32MbGMStereo.sf2 .
  */
  public void setUpMidi() {
		try {

				// get an unconnected sequencer
        this.mhSequencer = MidiSystem.getSequencer(false);
        this.mhSequencer.open();

        // get a new synth
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();

        // Create the File specification for Midi soundbank file
        // Path to file is system dependent and IDE dependent
        File soundSpec = new File(this.mhGenMidiSoundBankFilePath);
        Boolean canFind = soundSpec.exists();   // Just for debug to ensure the file can be found
        if ( canFind != true ) {
          throw(new FileSystemException( this.mhGenMidiSoundBankFilePath ));
        }

      // Get the standard General Midi soundbank and load it
        Soundbank genMidiSoundbank = MidiSystem.getSoundbank(soundSpec);
        synth.loadAllInstruments(genMidiSoundbank);

        Receiver synthReceiver = synth.getReceiver();
        Transmitter seqTransmitter = this.mhSequencer.getTransmitter();
        seqTransmitter.setReceiver(synthReceiver); // connect sequencer to synth

			/*
			 Create the sequence based on tempo (PPQ) and ticks-per-beat
			 A MIDI sequence contains lists of time-stamped (ticks) MIDI data
			 PPQ is the tempo-based timing type, for which the resolution is expressed in pulses (ticks) per quarter note.
			*/
			this.mhSequence = new Sequence(Sequence.PPQ,this.MH_MIDI_4COUNTS_QUARTER_NOTE);

			// Create a new, initially empty track as part of this sequence.
			this.mhTrack = this.mhSequence.createTrack();

      // Set the tempo in the sequencer in beats per minute = 1/2 second per beat
      this.mhSequencer.setTempoInBPM(120);

      // Bug - improve this catch so it bubbles up to BeatBox main
		} catch(Exception e) {e.printStackTrace();}
	} // close method


	// Builds the sequence track and starts the Midi playing.
	public void buildTrackAndStart(ArrayList<SoundMaker> beatBoxSounds) {

		// kill the existing track and start a new one
		this.mhSequence.deleteTrack(this.mhTrack);
		this.mhTrack = this.mhSequence.createTrack();

		// Create a tracklist to be used for each sound (outer loop below)
		int[] midiTrackList = null;

		// Now add the sounds at requested tempos to a track for each sound
		// Iterate over all sounds
		for ( SoundMaker sound : beatBoxSounds) {

			// Size of tracklist is determined by the number of frequencies for each instrument.
			int numFreqs = sound.getMidiFreqs().size();
			midiTrackList = new int[numFreqs];

			// Iterate over all the frequencies to build the trackList.
			// Add an instrument at tempo for each checkbox
			for (int j = 0; j < numFreqs; j++ ) {

				// Iterate over the frequencies for this sound, and add each sound to the track list.
				if ( sound.getMidiFreqs().get(j) == true ) {
					midiTrackList[j] = sound.getMidiCode();
				} else {
					midiTrackList[j] = this.MH_MIDI_NO_SOUND;
				}
			} // close inner loop

			/*
			For one instrument, tracklist now contains the instrument key
			for each of the instrument's selected frequencies. Otherwise
			trackList contains NO_SOUND. Convert the trackList
			to NOTE_ON and NOTE_OFF events in mhTrack
			*/
			this.makeTracks(midiTrackList);

			/*
			The sole purpose of this event is not to make sound - it's to generate an event at the same time as
			NOTE_ON for our listener. (NOTE_ON and NOTE_OFF events can't be directly monitored by our listener.)
			Listeners should be registered for this event.
			*/
			this.mhTrack.add(makeEvent(ShortMessage.CONTROL_CHANGE,this.MH_MIDI_CHANNEL_KEYBOARD,MH_MIDI_NOTE_SOUND,0,16));

		} // close outer

		/*
			Add one last event to the entire this.mhTrack to ensure we always go
		  a full 16 1/8th notes. This is necessary because of the pathological case when the user doesn't
		  select any checkboxes in the rightmost column.
		*/
		this.mhTrack.add(makeEvent(ShortMessage.PROGRAM_CHANGE, this.MH_MIDI_CHANNEL_PERCUSSION,1,0,15));

		// Now
		try {
			this.mhSequencer.setSequence(this.mhSequence);
			this.mhSequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			this.mhSequencer.start();
      //fixme why is this here?  Set in setupMidi!
			this.mhSequencer.setTempoInBPM(120);
			//fixme make exception bubble up to beatBox main
		} catch(Exception e) {e.printStackTrace();}

	} // close buildTrackAndStart method

  /*
  * Commands Midi to stop making sounds
  */
	public void sequencerStop() {
		this.mhSequencer.stop();
	}


	/*
	* Converts a list of instrument sounds to Midi events that can be added to this.mhTrack
	* @param trackList  a list containing either Midi instrument numbers or this.MH_MIDI_NO_SOUND
	 */
	private void makeTracks( int[] trackList ) {

		for ( int i=0; i < trackList.length; i++ ) {

			if ( trackList[i] != this.MH_MIDI_NO_SOUND ) {
				// Add a note on event
				this.mhTrack.add(makeEvent(ShortMessage.NOTE_ON, this.MH_MIDI_CHANNEL_PERCUSSION, trackList[i], 100, i));
				// Add a note off event
				this.mhTrack.add(makeEvent(ShortMessage.NOTE_OFF, this.MH_MIDI_CHANNEL_PERCUSSION, trackList[i], 100, i+1));
			}
		}
	}

	/*
	* @param
	* @param midiTimeStamp - the time-stamp for the event, in MIDI ticks
	* */
	private MidiEvent makeEvent(int midiMsg, int midiChannel, int midiSound, int midiVelocity, int midiTimeStamp) {
		MidiEvent tempEvent = null;
		try {

			// Create a messsage containing the midi instructions
			ShortMessage tempMsg = new ShortMessage();
			tempMsg.setMessage(midiMsg, midiChannel, midiSound, midiVelocity);

			// Make a midi event containing the message and timestamp ticks
			tempEvent = new MidiEvent(tempMsg, midiTimeStamp);

		} catch(Exception e) {e.printStackTrace(); }
		return tempEvent;
	}


}
