import java.util.ArrayList;

/**
 * @author jhake
 * Responsible for all interaction with the user:
 *  get user guess
 *  find out if there is a hit based on the user guess
 *  report results to the user.
 *
 */
public class DotComBust {

	/**
	 * Holds the field of play for the game
	 */
	private GameBoard playField;
		
	/**
	 * Instantiate a new game
	 */
	public DotComBust() {
		this.playField = new GameBoard();
	}


	/**
	 * Main routine to run a game - handles all the io with the user and communicates with the game
	 * @param	numCompanies	The number of companies to put on the board. Dimension must match companyNames
	 * @param	sizeOfCompany	The number of board squares for each company. Must be less than 7, which is hard wired in GameHelper class.
	 * @param	companyNames	List of names for companies on the board. Dimension must match numCompanies - one name per company.
	 */
	public void runGame(int numCompanies, int sizeOfCompany, ArrayList<String> companyNames) {
		
		boolean gameOk = false;
		gameOk = this.playField.initBoard(numCompanies,sizeOfCompany, companyNames);
		
		if ( gameOk == true ) {
			
			// Make the iohelper
			GameHelper ioHelper = new GameHelper();

			while ( this.playField.getGameOn() ) {
				
				// get user guess using io helper
				System.out.println("");
				String userGuess = ioHelper.getUserInput("Enter a guess: ");				
				
				// compare user guess to dot com locations on game board
				this.playField.checkUserGuess(userGuess);
				
				// get the results of the guess
				StringBuilder hitName = new StringBuilder("");
				if ( this.playField.getStateHit(hitName) ) {
					// Report the hit
					System.out.println("You hit " + hitName.toString() );
				
				} else {
										
					// check to see if the company went bust
					if ( this.playField.getStateBust(hitName)) {
						System.out.println("Fatal blow for " + hitName.toString() + ", it's out of the game!");
					} else {
						// No hit or bust, must be a miss
						System.out.println("Miss! Try again...");
					}		
				}
				
			}

			// report game over
			System.out.println("All dot coms dead - game over!");
			
		} else {
			
			// cant' start the game for some reason.
			System.out.println("Cannot start game");	
		}

		
	}

}  // End DotComBust class
