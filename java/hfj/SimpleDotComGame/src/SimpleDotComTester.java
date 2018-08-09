import java.util.ArrayList;

/**
 * Tests successful hits, misses, and game win.
 * If everything passes, there is no output
 * @author jhake
 *
 */
public class SimpleDotComTester {
		
	private SimpleDotCom testCompany = new SimpleDotCom();
	private ArrayList<String> testLoc = new ArrayList<String>();
	private int gameWidth = 0;
	private int companyWidth = 0;

	
	/**
	 * Runs suite of tests on SimpleDotCom class
	 * If everything passes, there is no output		Expected SimpleDotCom State
	 * Test Case # 		Test Case 					MISS	HIT		WIN
	 * 	1				1 miss						y		n		n
	 *  2				1 hit						n		y		n
	 *  3				multiple hits diff values	n		n		y
	 *  4				multiple hits same values	y		n		n	Note: first hit value returns HIT, thereafter MISS
	 */
	boolean testSuite(int widthOfGameBoard, int widthOfCompany, boolean verbose) {
	
		boolean gameOk = true;
		
		// Save to member vars
		this.gameWidth = widthOfGameBoard;
		this.companyWidth = widthOfCompany;

		// Test #1
		gameOk = this.runTestSequence(1, true, false, false, verbose);
		
		// Test #2
		gameOk = this.runTestSequence(2, false, true, false, verbose) && gameOk;

		// Test #3
		gameOk = this.runTestSequence(3, false, false, true, verbose) && gameOk;

		// Test #4
		gameOk = this.runTestSequence(4, true, false, false, verbose) && gameOk;
				
		return gameOk;
	}
	
	boolean runTestSequence(int testSeq, boolean expectMiss, boolean expectHit, boolean expectWin, boolean output) {

		// default to fail
		boolean testOk = false;
		
		// Verify test states
		testOk = true;
		if (!expectWin) testOk = testOk && (expectHit != expectMiss);
		if (expectWin) {
			testOk = testOk && (expectWin != expectHit);
			testOk = testOk && (expectWin != expectMiss);
		}
			
		// Conditions good for a test.
		if (testOk) {
						
			// Initialize the testCompany
			testOk = this.testCompany.initializeCompany(this.gameWidth, this.companyWidth);
			
			if (testOk) {

				// Snatch the company locations before any testing starts.
				this.testCompany.getCompanyLocation(this.testLoc);						

				// Initialize the company location guess depending on the test sequence
				switch (testSeq) {
					case 1: {
						// Reduce the guesses to just one incorrect one - out of company bounds.
						this.testLoc.clear();
						this.testLoc.add(Integer.toString(this.gameWidth+1));
						break;
					}
					case 2: {
						// Reduce the guesses to just one correct one - the first element.
						for (int i=(this.companyWidth-1); i > 0; i--) this.testLoc.remove(i);
						break;
					}
					case 3: {
						// Do nothing - this is to guess the entire company location
						break;
					}
					case 4: {
						// Copy one correct guess into all the guesses
						for (int i=1; i < (this.companyWidth); i++) this.testLoc.set(i, this.testLoc.get(0));
						break;	
					}
				}

				// Do the test case guess(es)
				for (String elementVal : testLoc) testCompany.checkYourself(elementVal);
				
				// Test the final states to be valid given the test case
				testOk = ( testCompany.getStateMiss() == expectMiss );
				testOk = testOk && ( testCompany.getStateHit() == expectHit );
				testOk = testOk && ( testCompany.getStateWin() == expectWin );
				
				// If the test failed, output to screen when requested.
				if ( !testOk && output ) 
				{
					System.out.println("SimpleDotComTester: Test #" + testSeq + " checkYourself logic failed: game width =" + gameWidth + " company width = " + companyWidth);
				}
				
			} else {
				
				// Whoops! Couldn't initialize the test for some reason.
				System.out.println("SimpleDotComTester: Test #" + testSeq + " company setup failed: game width =" + gameWidth + " company width = " + companyWidth);
				
			}			

		} else {
			
			// Testing logic inputs bad
			System.out.println("SimpleDotComTester: Test #" + testSeq + " test logic faulty: MISS=" + expectMiss + ", HIT=" + expectHit + ", WIN=" + expectWin);

		}
		
		return (testOk);	
	}
		
} // end class SimpleDotComTester

