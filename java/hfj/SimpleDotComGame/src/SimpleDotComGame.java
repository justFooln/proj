/**
 * A simple Java guessing game.
 * @author jhake
 */

import java.util.ArrayList;


public class SimpleDotComGame {

	/**
	 * Game to guess the location of a DotCom on the game board.
	 * @param {string[]} args ignored
	 * @param 
	 */
	public static void main(String[] args) {

		final int allowedNumOfGuesses = 10;
		int guessesUsed = 0;
		final int gameWidth = 10;
		final int companyWidth = 3;
		String userGuess = "void";
		boolean gameOn = false;
		boolean printTestResults = true;
		ArrayList<String> companyLoc  = new ArrayList<String>();
		
		if (args.length > 0) {
			switch (args[0] ) {
				case "test": {
				
					// Quick test for luck
					SimpleDotComTester tester = new SimpleDotComTester();
					// Valid game
					tester.testSuite(gameWidth, companyWidth, printTestResults);
					// Invalid game
					tester.testSuite(companyWidth,  gameWidth, printTestResults);
					// Degenerate game
					tester.testSuite(gameWidth,  1, printTestResults);
					break;
				}
				default: {
					System.out.println("SimpleDotComGame, illegal input. Use ? for parameters");
				}
			}
			
		}


		// Initialize the Gamehelper class
		GameHelper helper = new GameHelper();
		
		// Create and initialize the dot com company
		SimpleDotCom startUp = new SimpleDotCom();
		gameOn = startUp.initializeCompany(gameWidth, companyWidth);
				
		// Grab a copy of the company locations before the game begins
		if (gameOn) {
			startUp.getCompanyLocation(companyLoc);
		}

		while (gameOn == true) {

			// Get the user's guess
			System.out.println("You have " + String.valueOf(allowedNumOfGuesses - guessesUsed) + " guesses remaining.");
			userGuess = helper.getUserInput("Enter a Number: ");		
			
			// Compare it to the DotCom location
			startUp.checkYourself(userGuess);

			// Display the result of the guess to the user
			if ( startUp.getStateWin() ) {
				System.out.println("You Win! Nice job.");
				gameOn = false;
			} else {
				// Another guess used without a win.
				guessesUsed++;
				// Check for hit or miss
				if ( startUp.getStateHit() ) {
					System.out.println("Hit!");
				} else {
					if ( startUp.getStateMiss() ) {
						System.out.print("Miss! Nice Try. ");
						if (guessesUsed >= allowedNumOfGuesses) {
							System.out.println("You are out of guesses.");
							gameOn = false;
						}
					}
				}
			}
						
		}
		
		System.out.println("Game Over. Thanks for playing!");	
		System.out.print("The dot com was at: ");
		for (int i=0; i<companyLoc.size(); i++) System.out.print(companyLoc.get(i) + " ");
		System.out.println("");
		
	}
	
	
}

