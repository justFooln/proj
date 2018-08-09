import java.util.ArrayList;

public class SimpleDotCom {
	
	// Definitions for class
	private static enum State { MISS, HIT, WIN };

	// This arraylist manages the definition of the dot com location, and whether any cells have been hit.
	// This array list is volitile, in that each time a location guess is correct, that location is removed 
	// from this list. The game ends when this list is empty.
	private ArrayList<String> locationCells  = new ArrayList<String>();
	
	// Variable to hold the state of the game;
	private SimpleDotCom.State gameState;

	//Mutators
	private void setStateMiss() {
		this.gameState = SimpleDotCom.State.MISS;
	}

	private void setStateHit() {
		this.gameState = SimpleDotCom.State.HIT;
	}
	
	private void setStateWin() {
		this.gameState = SimpleDotCom.State.WIN;
	}
	
	// Accessors
	public boolean getStateMiss() {
		return (this.gameState == SimpleDotCom.State.MISS);
	}
	
	public boolean getStateHit() {
		return (this.gameState == SimpleDotCom.State.HIT);
	}
	
	public boolean getStateWin() {
		return (this.gameState == SimpleDotCom.State.WIN);
	}
	
	// Initializes the array of locations for the company
	public boolean initializeCompany(int gameBoardSize, int companyWidth) {

		// Set the game state
		this.setStateMiss();
		
		// Reset the company location
		locationCells.clear();
		
		// Common sense checks on inputs, then set company location
		boolean setupOk = false;
		if (gameBoardSize > companyWidth) {			
			// Init the random location of the company's lowest location
			int randomLoc = (int) (Math.random() * gameBoardSize);
			// Adjust location so upper bound of game board is not exceeded
			if ( randomLoc > (gameBoardSize-companyWidth) ) randomLoc = gameBoardSize-companyWidth;
			// Adjust lowest location so lower bound is 1 or greater
			if (randomLoc < 1) randomLoc = 1;
			// Now add the company from lowest location to highest
			for (int i=0; i<companyWidth; i++) this.locationCells.add(String.valueOf(randomLoc+i));
			// Indicate all set to play
			setupOk = true;
		}
		
		return setupOk;
	}

	
	// Handles a guess about the location of the DotCom
	public void checkYourself(String guess) {
		
		// Check for a hit - is the guess in the locationCells?
		int j = this.locationCells.indexOf(guess);
		
		// Take action based on whether there is a hit.
		if (j >= 0) {
			// Hit, remove the location
			this.locationCells.remove(j);
			// Set state for hit
			this.setStateHit();
			// Set state for win if all the locations have been hit.
			if (this.locationCells.size() == 0) this.setStateWin();
		} else {
			// Miss
			this.setStateMiss();
		}

	}

	
	// Method returns the a copy of the company locations
	// Values only valid immediately after this.initializeCompany() is called
	public void getCompanyLocation(ArrayList<String> companyLoc) {
		companyLoc.clear();
		for (String elementVal : this.locationCells) {
			companyLoc.add( elementVal );
		}
	}
		

}  // end class SimpleDotCom
