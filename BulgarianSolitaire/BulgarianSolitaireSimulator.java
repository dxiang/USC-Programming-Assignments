// Name: Dennis Xiang	
// USC loginid: dxiang
// CSCI455 PA2
// Spring 2015

/**
 <add main program comment here>
 */

import java.util.Scanner;

/**
 * Runs a bulgarian solitaire simulator using the SolitaireBoard class. Takes program arguments for user input for start configuration
 * and for user input for round progression.
 * @author Dennis
 *
 */
public class BulgarianSolitaireSimulator {
	
	/**
	 * Main method - obtains user input for initial board configuration and initializes the SolitaireBoard object
	 * @param args
	 */
	public static void main(String[] args) {

		boolean singleStep = false;
		boolean userConfig = false;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-u")) {
				userConfig = true;
			} else if (args[i].equals("-s")) {
				singleStep = true;
			}
		}

		System.out.println("***************************************");
		System.out.println("**  Welcome to Bulgarian Solitaire!  **");
		System.out.println("***************************************");

		SolitaireBoard gameBoard;
		Scanner inputSc = new Scanner(System.in);

		
		if (userConfig == true) {

			System.out.println("Number of total cards is " + SolitaireBoard.CARD_TOTAL);
			System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");
			System.out.println("Please enter a space-separated list of positive integers followed by newline:");

			String inputStr = inputSc.nextLine();
			
			while (!SolitaireBoard.isValidConfigString(inputStr)) {

				System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be 45");
				System.out.println("Please enter a space-separated list of positive integers followed by newline:");
				inputStr = inputSc.nextLine();

			}

			gameBoard = new SolitaireBoard(inputStr);

		} 
		else {
			System.out.println("Generating a random solitaire board...");
			gameBoard = new SolitaireBoard();
		}

		playGame(gameBoard, inputSc, singleStep);
		System.out.println("DONE!");

	}//end main method
	

	/**
	 * Plays game by playing rounds until the SolitaireBoard reaches the end configuration
	 * @param gameBoard
	 * @param inputSc
	 * @param singleStep
	 */
	private static void playGame(SolitaireBoard gameBoard, Scanner inputSc, boolean singleStep) {

		int roundCount = 1;
		
		System.out.println("Initial configuration: " + gameBoard.configString());
		while(!gameBoard.isDone()){
			gameBoard.playRound();
			System.out.println("[" + roundCount + "] Current configuration: " + gameBoard.configString());
			if(singleStep) {
				System.out.println("<Type return to continue>");
				inputSc.nextLine();
			}
			roundCount++;
		}
	}//end playGame method


}//end class
