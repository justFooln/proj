import java.util.ArrayList;

/**
 * @author jhake
 * Responsible for:
 * 	holding the DotComs
 * 	Reports hit, miss, bust, or end of game
 * 	Determines end-of-game - when no DotComs remain
 *
 */
public class GameBoard {
	
	/**
	 * Properties
	 */
	// Stores the list of Dot Coms still on the board. When aliveCompanies is empty the game is over.
	private ArrayList<DotCom> aliveCompanies;

	// Variable to hold the state of the user guesses;
	private StateTracker gameState;
	
	// Variable to hold the name of a dot com in case of hit or bust
	private StringBuilder dotComName;

	/**
	 * Constructors
	 */
	public GameBoard() {
		this.aliveCompanies = new ArrayList<DotCom>();
		this.gameState = new StateTracker();
		this.dotComName = new StringBuilder("");
		this.setStateMiss();
	}

	// Accessors
	/**
	 * @return true if latest call to checkUserGuess was a miss, otherwise false
	 */
	public boolean getStateMiss() {
		return (this.gameState.getStateMiss());
	}

	/**
	 * @param	hitCompany 	If a hit occurrent on last call to checkUserGuess, this contains the name of the dotCom company that was hit. Otherwise it is unchanged.
	 * @return  true 		if latest call to checkUserGuess was a hit, otherwise false.
	 */
	public boolean getStateHit(StringBuilder hitCompany) {
		boolean hit = this.gameState.getStateHit();
		if (hit) {
			hitCompany.setLength(0);
			hitCompany.append(this.dotComName);
		}
		return hit;
	}
	
	/**
	 * @return  true 	if latest call to checkUserGuess was a caused a dot com to go bust, otherwise false. 
	 * 					If true, then getStateHit will also return true and the name of the dot com to go bust.
	 */
	public boolean getStateBust(StringBuilder bustCompany) {
		boolean hit = this.gameState.getStateBust();
		if (hit) {
			bustCompany.setLength(0);
			bustCompany.append(this.dotComName);
		}
		return hit;
	}
	
	/**
	 * @return	true	if game play is ongoing - there are dot coms remaining to go bust.
	 * 			false	Game is over. if latest call to checkUserGuess resulted in the last dot com going bust. (So game is over.)
	 * 					If true, then getStateBust and getStateHit are also true, and getStateHit returns the name of the last dot com to go bust.
	 */
	public boolean getGameOn() {
		return (this.aliveCompanies.size() > 0);
	}

	// Mutators
	/**
	 * Sets the state of the game to miss
	 */
	private void setStateMiss() {
		this.gameState.setStateMiss();
	}
	
	/**
	 * Sets the state of the game to hit.
	 * @param	name	String containing the name of the company that was hit by the user guess
	 */
	private void setStateHit(String name) {
		this.gameState.setStateHit();
		this.dotComName.setLength(0);
		this.dotComName.append(name);
	}
	
	/**
	 * Sets the state of the game to bust
	 * @param	name	String containing the name of the company that went bust by the last use guess.
	 */
	private void setStateBust(String name) {
		this.gameState.setStateBust();
		this.dotComName.setLength(0);
		this.dotComName.append(name);
	}
	

	/**
	 * Initializes the game board. Must be called before the game can begin.
	 * @param numCompanies	the number of dot coms to place on the board.
	 * @param sizeOfCompany	the common size of each dot com - e.g. 3 squares on the board.
	 * @param names			list of names for the dot com. Length of this array must equal numCompanies. (One name for each company.)
	 */
	public boolean initBoard(int numCompanies, int sizeOfCompany, ArrayList<String> names) {
		
		// sanity check on inputs
		if (numCompanies == names.size()) {
			
			// Get some help from the canned code to place the dot coms on the game board.
			// Not needed by this class after initializing the game
			GameHelper placeHelper = new GameHelper();

			// initialize each of the dot coms
			for (int i=0; i<numCompanies; i++) {
				
				// create a new new dot com and add it to the game board
				this.aliveCompanies.add( new DotCom() );
				
				// set the name of the new dot com on the game board
				this.aliveCompanies.get(i).setDotComName(names.get(i));
				
				// place the dot com - note locations are in string format
				this.aliveCompanies.get(i).setDotComLoc(placeHelper.placeDotCom(sizeOfCompany));
								
			}	
		}
		printGameBoard();
		
		return this.getGameOn();
	}
	
	/** 
	 * Checks to see if a user guess is a hit, miss, or bust
	 * @param userLoc	user guess - a board location expressed as "letternumber", all lower case. E.g. "c5"
	 */
	public void checkUserGuess(String userLoc) {
		
		// Check dot coms as long as there isn't a hit.
		// Reset state to default miss
		this.setStateMiss();
		int i = 0;
		while ( ( i < this.aliveCompanies.size() ) && this.getStateMiss() ) {

			// Submit guess to dot com
			this.aliveCompanies.get(i).checkYourself(userLoc);
			
			// Was there a hit? 
			if ( this.aliveCompanies.get(i).getStateHit() ) {
				
				// set the hit state, ending the loop.
				this.setStateHit(this.aliveCompanies.get(i).getDotComName());
				
			} else {
				
				// Has this dot com gone bust - i.e. was this hit the final blow?
				if ( this.aliveCompanies.get(i).getStateBust() ) {
					
					// Set the game state to bust
					this.setStateBust(this.aliveCompanies.get(i).getDotComName());
					
					// Remove this dot com from the game board
					this.aliveCompanies.remove(i);
					
				}  else {

					// no hit and no bust, so it's a miss
					
					this.setStateMiss();
					
				}
				
			}

			// Try next dot com
			i++;

		}
			
	} // end method checkUserGuess

	/**
	 * Diagonostic routine to print out the game board. Enabled by default in initBoard().
	 */
	public void printGameBoard() {
		// Build one ArrayList containing all the dot com locations in a linear list.
		ArrayList<String> dotComLocs = new ArrayList<String>();
		ArrayList<String> tempLocs = new ArrayList<String>();
		for (DotCom company : this.aliveCompanies) {
			company.getCompanyLocation(tempLocs);
			dotComLocs.addAll(tempLocs);
		}
		System.out.println("    0  1  2  3  4  5  6");
		System.out.println("    -------------------");
		printRow("a", dotComLocs);
		printRow("b", dotComLocs);
		printRow("c", dotComLocs);
		printRow("d", dotComLocs);
		printRow("e", dotComLocs);
		printRow("f", dotComLocs);
		printRow("g", dotComLocs);
	}

	/**
	 * Diagnostic to print each row of the game board.
	 * Prints letternumber (e.g. "c5") where a company exists, space otherwise.
	 * @param rowLetter		The letter corresponding to the row to be printed out. E.g. "a"
	 * @param companyLocs	List of company locations, each location expressed as a letternumber.
	 */
	public void printRow(String rowLetter, ArrayList<String> companyLocs) {
		System.out.print(rowLetter + " | ");
		for (int i=0; i<7; i++) {
			String coord = new String(rowLetter + i);
			if (companyLocs.contains(coord)) {
				System.out.print(coord + " ");
			} else {
				System.out.print("   ");
			}
		}
		System.out.println("");
	}
	
	
	
}  // end class GameBoard


	