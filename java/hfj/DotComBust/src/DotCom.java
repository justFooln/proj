import java.util.ArrayList;

/**
 * @author jhake
 * Defines a dot com company.
 * There are three pieces of information that define a dot com:
 * 	the name of the company
 *  the location of the company on the game board. When there are no more locations on the game board, the company ceases to exist.
 *  the state of the company - miss, hit, or bust.
 *
 */
public class DotCom {

	// Location on the Game Board
	private ArrayList<String> boardLoc;
	
	// Name of this company
	private StringBuilder companyName = new StringBuilder("");
	
	// The state of this company
	private StateTracker companyState;
		
	/**
	 * Constructor - instantiates properties and sets state to default state.
	 */
	public DotCom() {
		boardLoc = new ArrayList<String>();
		companyState = new StateTracker();
		this.companyState.setStateMiss();
	}
		
	/**
	 * Sets the location of this dot com on the game board.
	 * @param dotComLoc		List of locations in letternumber format
	 * @return				false if list of locations is empty, true otherwise. 
	 */
	public boolean setDotComLoc(ArrayList<String> dotComLoc) {
		boolean setOk = false;
		if ( !dotComLoc.isEmpty() ) {
			setOk = true;
			this.boardLoc.clear();
			for (String locVal : dotComLoc) {
				boardLoc.add( locVal );
			}
		}
		return setOk;
	}
	
	/**
	 * Sets the name of the dot com
	 * @param 	name	Any length name for the company. E.g. "Razorfish.com"
	 * @return	false	if name is empty, true otherwise.
	 */
	public boolean setDotComName(String name) {
		boolean setOk = false;
		if ( !name.isEmpty() ) {
			setOk = true;
			this.companyName.setLength(0);
			this.companyName.append(name);
		}
		return setOk;
	}
	

		
	// Accessors
	/**
	 * Inquires whether the state of this dot com is miss - the last user guess was wrong.
	 * @return	false	if last user guess included one of the company's remaining locations on the board. true otherwise.
	 */
	public boolean getStateMiss() {
		return (this.companyState.getStateMiss());
	}
	
	/**
	 * Inquires whether the state of this dot com is a hit - the last user guess was right.
	 * @return	true	if last user guess included one of the company's remaining locations on the board. false otherwise.
	 */
	public boolean getStateHit() {
		return (this.companyState.getStateHit());
	}
	
	/**
	 * Inquires whether the state of this dot com is a bust - the last user guess was not only right, but eliminated the final location for this company.
	 * @return	true	if last user guess included the last of the company's remaining locations on the board. false otherwise.
	 */
	public boolean getStateBust() {
		return (this.companyState.getStateBust());
	}

	/**
	 * Gets the name of this dot com.
	 * @return	dot com name, e.g. "Razorfish.com"
	 */
	public String getDotComName() {
		return this.companyName.toString();
	}

	/**
	 * Handles a guess about the location of the DotCom
	 * @param guess	String containing the users guess about the location of this dot com. Format of string is letternumber format, e.g. "c5".
	 */
	public void checkYourself(String guess) {

		// Check for a hit - is the guess in the boardLoc?
		int j = this.boardLoc.indexOf(guess);
		
		// Take action based on whether there is a hit.
		if (j >= 0) {
			// Hit, remove the location
			this.boardLoc.remove(j);
			// Set state for hit
			this.companyState.setStateHit();
			// Set state for bust if all the locations have been hit.
			if (this.boardLoc.size() == 0) this.companyState.setStateBust();
		} else {
			// Miss
			this.companyState.setStateMiss();
		}

	}
	
	/**
	 * Diagnostic. Returns a copy of the company locations. Return value changes as user hits are removed from the dot com's locations on the board.
	 * @param companyLoc	List to hold dot com locations. This list may be empty on return.
	 */
	public void getCompanyLocation(ArrayList<String> companyLoc) {
		companyLoc.clear();
		for (String elementVal : this.boardLoc) {
			companyLoc.add( elementVal );
		}

	}
		
	
}
