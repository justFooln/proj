/**
 * @author jhake
 * This class is responsible for tracking the state of a game or dot com.
 * The class only supports 3 distinct states.
 * Case 					MISS	HIT		BUST
 * 	1 miss					y		n		n
 *  1 hit					n		y		n
 *  sequential hits 		n		y		n
 *  hit on last location	n		n		y
 * Hit or miss can occur in any order - e.g. they can alternate, but BUST only occurs when the last remaining location dot com, or last remaining dot com in the game. .
 */
public class StateTracker {
	
	/** 
	 * Define the allowed states for this class to track
	 */
	private static enum State { MISS, HIT, BUST };
	
	// Variable to hold the state of the game;
	private StateTracker.State currentState;
	
	/**
	 * Constructor initializes to "MISS"
	 */
	public StateTracker() {
		this.setStateMiss();
	}

	//Mutators
	protected void setStateMiss() {
		this.currentState = StateTracker.State.MISS;
	}

	protected void setStateHit() {
		this.currentState = StateTracker.State.HIT;
	}
	
	protected void setStateBust() {
		this.currentState = StateTracker.State.BUST;
	}
	
	// Accessors
	public boolean getStateMiss() {
		return (this.currentState == StateTracker.State.MISS);
	}
	
	public boolean getStateHit() {
		return (this.currentState == StateTracker.State.HIT);
	}
	
	public boolean getStateBust() {
		return (this.currentState == StateTracker.State.BUST);
	}


}
