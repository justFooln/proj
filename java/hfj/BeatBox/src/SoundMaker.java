import java.lang.StringBuffer;
import java.util.ArrayList;

/**
 * Created by jhake on 12/29/16.
 */
//todo add serialization feature
//todo how to serialize to XML output?
public class SoundMaker {

  // Holds the midi-defined integer for the instrument
  private Integer soundMidiCode = 0;

  // Holds the midi-defined string describing the instrument
  private StringBuffer  soundMidiName = new StringBuffer("null");

  // Holds the state of frequencies for this instrument
  private ArrayList<Boolean> soundMidiFreqs = new ArrayList<>(1);

  // Constructors
  public SoundMaker() {
    soundMidiFreqs.add( Boolean.FALSE );
  }

	public SoundMaker( int code, String name, int numFreqs){
		this.setMidiCode(code);
		this.setMidiName(name);
    setAndSizeMidFreqs(numFreqs);
	}

  // Accessors
  public int getMidiCode(){
    return this.soundMidiCode;
  }

  public StringBuffer getSoundMidiName(){
    return this.soundMidiName;
  }

  public ArrayList<Boolean> getMidiFreqs() { return this.soundMidiFreqs; }

  // Mutators
  public void setMidiCode(int code) {
    this.soundMidiCode = code;
  }

  public void setMidiName(String newName){
    // StringBuffer.delete() requires the second argument to be
    // the length of the String, not the index of the last character
    // in the String.
    int last = soundMidiName.length();
    this.soundMidiName.delete(0,last);
    this.soundMidiName.append(newName);
  }

  public void setMidiFreqs(ArrayList<Boolean> freqs) {
    this.soundMidiFreqs.clear();
    this.soundMidiFreqs.addAll(freqs);
  }

  public void setAndSizeMidFreqs(int numFreqs) {
    this.soundMidiFreqs.clear();
    for (int i=0; i<numFreqs; i++) { soundMidiFreqs.add( Boolean.FALSE ); }
  }

}  // Class SoundMaker
