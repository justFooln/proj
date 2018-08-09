/**
 * 
 */

import javax.sound.midi.*;

/**
 * @author jhake
 *
 */
public class SimpleMidi {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			Sequencer player = MidiSystem.getSequencer();
			player.open();
			
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			
			Track trk = seq.createTrack();
			
			ShortMessage a = new ShortMessage();
			a.setMessage(144,  1,  44,  100);
			MidiEvent noteOn = new MidiEvent(a, 1);
			trk.add(noteOn);
			
			ShortMessage b = new ShortMessage();
			b.setMessage(128, 1, 44, 100);
			MidiEvent noteOff = new MidiEvent(b, 16);
			trk.add(noteOff);
			
			player.setSequence(seq);
			
			player.start();
			System.out.println("And play...");
			
		} catch (Exception ex) {
			System.out.println("Midi exception");
		}
		
		
		
	}
		
}


