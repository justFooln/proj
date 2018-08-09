import java.util.ArrayList;

/**
 * @author jhake
 * Responsible for defining dimensions of game - except size of gameboard, which is hardwired in GameHelper.
 */
public class DotComBustGame {


	/**
	 * @param args	ignored
	 */
	public static void main(String[] args) {
		
		/**
		 * Properties of the Game
		 */
		int numDotComs = 3;
		int dotComSize = 3;
		ArrayList<String> dotComNames = new ArrayList<String>();
		dotComNames.add("Pets.com");
		dotComNames.add("MyLacky.com");
		dotComNames.add("inktomi.com");

		
		// The game itself
		DotComBust game = new DotComBust();
		
		// Run the game until victory or defeat
		game.runGame(numDotComs, dotComSize, dotComNames);

	}

}
