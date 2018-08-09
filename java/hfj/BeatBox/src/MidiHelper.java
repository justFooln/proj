/**
 * 
 */

import java.util.ArrayList;
import javax.sound.midi.*;
import java.io.*;


/**
 * @author jhake
 * Responsible for all things Midi: getting, setting, low-level programming
 */
//todo make methods to relate checkbox and sound arraylist indicies. checkbox->sound, sound->checkbox
public class MidiHelper {

	private final int BB_MIDI_NO_SOUND = 0;
	private final int BB_MIDI_NOTE_SOUND = 127;
	private final int BB_MIDI_CHANNEL_KEYBOARD = 1;
	private final int BB_MIDI_CHANNEL_PERCUSSION = 9;
	/*
	This constant tells the sequencer there are 4 counts (ticks) per quarter note.
	Using 4 means that each tick is a 16th note, which is the resolution of each UI
	checkbox for a sound (instrument). So the beatBoxTrack is built of 16th notes for
	each sound.
	*/
	private final int BB_MIDI_4COUNTS_QUARTER_NOTE = 4;
	private final int BB_MIDI_16COUNTS_WHOLE_NOTE = 16;

	private Sequencer beatBoxSequencer;
	private Sequence beatBoxSequence;
	private Track beatBoxTrack;
	private String bbGenMidiSoundbankFileName = "32MbGMStereo.sf2";
	private StringBuffer bbDefaultFilePath;

	/**
	* Default Constructor
	*/
	public MidiHelper() {
	}

	/**
	 * Initializes a list of sounds based on a limited set of instrumensts specific to hfj beatbox
	 *fixme Hate this initialization - the numbers should be final defined as static class members.
*/
	public MidiHelper(ArrayList<SoundMaker> soundList) {

		// Initialize default Midi sounds
		ArrayList<SoundMaker> tempSounds = new ArrayList<SoundMaker>();
		tempSounds.add(new SoundMaker(35, "Bass Drum", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(42,"Closed Hi-Hat", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(46,"Open Hi-Hat", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(38,"Acoustic Snare", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(49,"Crash Cymbal", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(39,"Hand Clap", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(50,"High Tom", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(60,"Hi Bongo", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(70,"Maracas", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(72,"Whistle", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(64,"Low Conga", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(56,"Cowbell", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(58,"Vibraslap", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(47,"Low-mid Tom", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(67,"High Agogo", BB_MIDI_16COUNTS_WHOLE_NOTE));
		tempSounds.add(new SoundMaker(63,"Open Hi Conga", BB_MIDI_16COUNTS_WHOLE_NOTE));

		// Initialize sound list
		soundList.clear();
		soundList.addAll(tempSounds);

	}

		/*
	 * The rest of this code is to drive the Midi system in response to
	 * user inputs to the UI.
	 */

	// Initializes the Midi
	public void setUpMidi() {
		try {

        // Use a synthesizer that supports the General MIDI soundbank, not the default in JRE8.
        // Thanks to Ben Hession stackoverflow.com/questions/22197856/issues-with-midi-file-in-java
        // File is available at: http://www.ntonyx.com/soft/32MbGMStereo.sf2 .

				// get an unconnected sequencer
        beatBoxSequencer = MidiSystem.getSequencer(false);
        beatBoxSequencer.open();

        // get a new synth
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();

			// Initialize the complete path to midi synthesizer soundbank file
			// Define the correct File system Path for BeatBox files.
			// I pick the user's Downloads directory. This is completely arbitrary,
			// it just has to be used consistently throughout the code
			File userHomeDirectory = new File( System.getProperty( "user.home" ));
			bbDefaultFilePath = new StringBuffer(userHomeDirectory.toString());
			bbDefaultFilePath.append( "/Downloads/" );
			bbDefaultFilePath.append( this.bbGenMidiSoundbankFileName );
			File soundSpec = new File( bbDefaultFilePath.toString() );
			Boolean canFind = soundSpec.exists();   // Just for debug to ensure the file can be found

      // Get the standard General Midi soundbank and load it
        Soundbank genMidiSoundbank = MidiSystem.getSoundbank(soundSpec);
        synth.loadAllInstruments(genMidiSoundbank);

        Receiver synthReceiver = synth.getReceiver();
        Transmitter seqTransmitter = beatBoxSequencer.getTransmitter();
        seqTransmitter.setReceiver(synthReceiver); // connect sequencer to synth

/*
			// Access the built-in Midi system
			// A hardware or software device that plays back a MIDI sequence is a Sequencer
			beatBoxSequencer = MidiSystem.getSequencer();
			beatBoxSequencer.open();
*/

			/*
			 Create the sequence based on tempo (PPQ) and ticks-per-beat
			 A MIDI sequence contains lists of time-stamped (ticks) MIDI data
			 PPQ is the tempo-based timing type, for which the resolution is expressed in pulses (ticks) per quarter note.
			 This Sequence has the timing resolution of 4 ticks per quarter note.
			*/
			beatBoxSequence = new Sequence(Sequence.PPQ,this.BB_MIDI_4COUNTS_QUARTER_NOTE);

			// Create a new, initially empty track as part of this sequence.
			beatBoxTrack = beatBoxSequence.createTrack();

      // Set the tempo in the sequencer in beats per minute = 1/2 second per beat
      beatBoxSequencer.setTempoInBPM(120);

      // Bug - improve this catch so it bubbles up to BeatBox main
		} catch(Exception e) {e.printStackTrace();}
	} // close method


	// Builds the sequence track and starts the Midi playing.
	public void buildTrackAndStart(ArrayList<SoundMaker> beatBoxSounds) {

		// kill the existing track and start a new one
		beatBoxSequence.deleteTrack(beatBoxTrack);
		beatBoxTrack = beatBoxSequence.createTrack();

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
					midiTrackList[j] = this.BB_MIDI_NO_SOUND;
				}
			} // close inner loop

			/*
			For one instrument, tracklist now contains the instrument key
			for each of the instrument's selected frequencies. Otherwise
			trackList contains NO_SOUND. Convert the trackList
			to NOTE_ON and NOTE_OFF events in beatBoxTrack
			*/
			this.makeTracks(midiTrackList);

			/*
			The sole purpose of this event is not to make sound - it's to generate an event at the same time as
			NOTE_ON for our listener. (NOTE_ON and NOTE_OFF events can't be directly monitored by our listener.)
			Listeners should be registered for this event.
			*/
			beatBoxTrack.add(makeEvent(ShortMessage.CONTROL_CHANGE,this.BB_MIDI_CHANNEL_KEYBOARD,BB_MIDI_NOTE_SOUND,0,16));

		} // close outer

		/*
			Add one last event to the entire beatBoxTrack to ensure we always go
		  a full 16 beats. This is necessary because of the pathological case when the user doesn't
		  select any checkboxes in the rightmost column.
		*/
		beatBoxTrack.add(makeEvent(ShortMessage.PROGRAM_CHANGE, this.BB_MIDI_CHANNEL_PERCUSSION,1,0,15));

		// Now
		try {
			beatBoxSequencer.setSequence(beatBoxSequence);
			beatBoxSequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			beatBoxSequencer.start();
      //fixme why is this here?  Set in setupMidi!
			beatBoxSequencer.setTempoInBPM(120);
			//fixme make exception bubble up to beatBox main
		} catch(Exception e) {e.printStackTrace();}

	} // close buildTrackAndStart method

	public void sequencerStop() {
		this.beatBoxSequencer.stop();
	}


	private void makeTracks( int[] trackList ) {

		for ( int i=0; i < trackList.length; i++ ) {

			if ( trackList[i] != this.BB_MIDI_NO_SOUND ) {
				// Add a note on event
				beatBoxTrack.add(makeEvent(ShortMessage.NOTE_ON, this.BB_MIDI_CHANNEL_PERCUSSION, trackList[i], 100, i));
				// Add a note off event
				beatBoxTrack.add(makeEvent(ShortMessage.NOTE_OFF, this.BB_MIDI_CHANNEL_PERCUSSION, trackList[i], 100, i+1));
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
