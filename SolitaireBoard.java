// Name: Dennis Xiang	
// USC loginid: dxiang
// CSCI455 PA2
// Spring 2015


/*
   class SolitaireBoard
   The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
   by changing NUM_FINAL_PILES, below.  There are only some values for this that result in a game that terminates.
   (See comments below next to named constant declarations for more details on this.)
 */

import java.util.Scanner;
import java.util.Random;


public class SolitaireBoard {
   
   public static final int NUM_FINAL_PILES = 9;
   // number of piles in a final configuration
   // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)
   
   public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
   // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
   // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
   // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES
   
   /**
      Representation invariant:
	
	- cardPiles array contains all card stack amounts
	- Sum of all card stack amounts in cardPiles array add up to CARD_TOTAL
	- Each stack amount is between 1 and CARD_TOTAL, inclusive
	- numStacks is the number of card stacks in play
	- numStacks is between 1 and CARD_TOTAL, inclusive
	- numStacks-1 is the last used element of the array
	- There are no "holes" in the array (no "zero" stacks between index 0 and numStacks-1 in cardPiles array)
    */
   
   private int[] cardPiles = new int[CARD_TOTAL];
   private int numStacks = 0;
   
 
   /**
     Initialize the board with the given configuration.
     PRE: SolitaireBoard.isValidConfigString(numberString)
   */
   public SolitaireBoard(String numberString) {

	   assert isValidConfigString(numberString); //ensures the input string is valid, move to client
	   Scanner sc = new Scanner(numberString);
	   int arrLoc = 0;
	   
	   while(sc.hasNext()) { //fills cardPiles array with the integers in numberString
			
			cardPiles[arrLoc] = sc.nextInt();
			arrLoc++;
			numStacks++;
			
	   }
	   
	   sc.close();
	   assert isValidSolitaireBoard();
	      
   }//end constructor
 
   
   /**
      Create a random initial configuration.
   */
   public SolitaireBoard() {
	   
	   int cardsLeft = CARD_TOTAL;
	   int arrPos = 0;
	   Random rand = new Random();
	   
	   while(cardsLeft > 0) {
		   cardPiles[arrPos] = rand.nextInt(cardsLeft)+1;
		   cardsLeft = cardsLeft - cardPiles[arrPos];
		   arrPos++;
	   }
	   
	   numStacks = arrPos;
	   assert isValidSolitaireBoard();
	   
   }//end constructor for random initial configuration
  
   
   /**
      Play one round of Bulgarian solitaire.  Updates the configuration according to the rules of Bulgarian
      solitaire: Takes one card from each pile, and puts them all together in a new pile.
    */
   public void playRound() {
	   
	   int stackCount = numStacks;
	   
	   for(int i = 0; i < numStacks; i++) { //subtracts 1 from each pile
		   cardPiles[i] += -1;
		   if(cardPiles[i] == 0) { //keeps track of current # of stacks 
			   stackCount--;
		   }
	   }
	   
	   for(int i = 0, j; i < stackCount; i++) { //shifts all stacks to the "left", filling in any gaps left from previous step
		   j = 1;
		   if(cardPiles[i] == 0) {
			   while(cardPiles[i+j] == 0) {
				   j++;
			   }
			   cardPiles[i] = cardPiles[i+j];
			   cardPiles[i+j] = 0;
		   }
	   }
	   
	   cardPiles[stackCount] = numStacks; //adds all subtracted cards to end of partially filled array
	   numStacks = stackCount + 1; //updates numStacks
	   assert isValidSolitaireBoard();
	   
   }//end playRound method
   

   /**
      Return true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
      piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order.
    */
   public boolean isDone() {
      
	   if(numStacks == NUM_FINAL_PILES) {
		   for(int count = 1; count <= NUM_FINAL_PILES; count++) {
			   if(!findNum(count)) {
				   return false;
			   }
		   }
		   return true;
	   }
	   else {
		   return false;
	   }
   }// end isDone method

   
   /**
      Returns current board configuration as a string with the format of
      a space-separated list of numbers with no leading or trailing spaces.
      The numbers represent the number of cards in each non-empty pile.
    */
   public String configString() {

	   String gameBoard = new String();
	   int arrPos = 0;
	   
	   while(arrPos < CARD_TOTAL && cardPiles[arrPos] != 0) {
		   
		   gameBoard = gameBoard + cardPiles[arrPos] + " ";
		   arrPos++;
		   
	   }
	   
      return gameBoard.substring(0, gameBoard.length()-1);   // returns substring to remove last space
   }//end configString method

   
   /**
      Returns true iff configString is a space-separated list of numbers that
      is a valid Bulgarian solitaire board assuming the card total SolitaireBoard.CARD_TOTAL
      PRE: configString must only contain numbers and whitespace
   */
   public static boolean isValidConfigString(String configString) {

	   Scanner sc = new Scanner(configString);
	   int theInt = 0;
	   int runningTotal = 0;
	   
	   while(sc.hasNext()) { //adds up all the integers
		   
		   if(sc.hasNextInt()) {
			   theInt = sc.nextInt();
			   
			   if(theInt > 0) {
				   runningTotal = runningTotal + theInt;
			   }
			   else {
				   sc.close();
				   return false;
			   }
			   
			   
		   }
		   else {
			   sc.close();
			   return false;
		   }
			   
	   }
	   
	   if(runningTotal != CARD_TOTAL){
		   sc.close();
		   return false;
	   }
	   else {
		   sc.close();
		   return true;
	   } 

   }//end isValidConfigString method


   /**
      Returns true iff the solitaire board data is in a valid state
      (See representation invariant comment for more details.)
    */
   private boolean isValidSolitaireBoard() {
	   
	   int cardCount = 0;
	   int pileCount = 0;
	   
	   //the following loop goes through the cardPiles array and makes sure each element has a value between 0 and CARD_TOTAL,
	   //counts the number of piles (elements greater and 1 and less than CARD_TOTAL, and counts the total number of cards within the piles
	   for(int pile : cardPiles) {
		   
		   if(pile < 0 || pile > CARD_TOTAL){
			   return false;
		   }
		   
		   if(pile > 0) {
			   pileCount++;
			   cardCount = cardCount + pile;
		   }
		   
	   }
	   
	   //makes sure that the number of stacks is correct
	   if(pileCount != numStacks) {
		   return false;
	   }
	   //makes sure that the total number of cards is correct
	   if(cardCount != CARD_TOTAL) {
		   return false;
	   }
	   
	   //the following loop makes sure that the first pileCount elements of the array are non-zero (empty stacks) to make sure that
	   //there are no "holes" in the array
	   for(int i = 0; i < pileCount; i++) {
		   
		   if(cardPiles[i] == 0){
			   return false;
		   }
		   
	   }
	   
	   return true;

   }//end isValidSolitaireBoard method
   

    // <add any additional private methods here>
   /**
    * Attempts to find num in cardPiles array. If found, returns true, otherwise returns false.
    * @param num
    * @return
    */
   private boolean findNum(int num) {
	   
	   for(int i = 0; i < cardPiles.length; i++) {
		   if(cardPiles[i] == num) {
			   return true;
		   }
	   }
	   
	   return false;
   }//end findNum method

}
