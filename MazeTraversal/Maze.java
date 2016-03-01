// Name: Dennis Xiang
// USC loginid: dxiang
// CS 455 PA3
// Spring 2015


import java.util.LinkedList;
import java.util.ListIterator;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).

   Assumptions about structure of the mazeData (given in constructor), and the
   path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate (START_SEARCH_ROW,
        START_SEARCH_COL) (constants defined below)
     -- exit loc is maze coordinate (numRows()-1, numCols()-1) 
           (methods defined below)
     -- mazeData input 2D array of 0's and 1's (see public FREE / WALL 
        constants below) where the first index indicates the row. 
        e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls

   Modified 10/23/14 by CMB
      Made START_SEARCH_ROW and START_SEARCH_COL constants (as they were
      intended to be).

 */

public class Maze {
   
   public static final int START_SEARCH_COL = 0;
   public static final int START_SEARCH_ROW = 0;

   public static final int FREE = 0;
   public static final int WALL = 1;
   
   private static final int NORTH = 0;
   private static final int SOUTH = 0;
   private static final int EAST = 0;
   private static final int WEST = 0;
   
   private int[][] mazeData;
   private LinkedList<MazeCoord> exitPath;
   private boolean searchPerformed = false;
   
   /**
      Constructs a maze.
      @param mazeData the maze to search.  See Maze class comments, above,
      for what goes in this array.
	  precondition: mazeData is not null
    */
   public Maze(int[][] mazeData) {
	   
	   assert isValidData(mazeData); //ensures mazeData isn't null
	   this.mazeData = mazeData;

   }


   /**
      Returns the number of rows in the maze
      @return number of rows
    */
   public int numRows() {
      return mazeData.length;
   }


   /**
     Returns the number of columns in the maze
     @return number of columns
   */
   public int numCols() {
      return mazeData[0].length;   // DUMMY CODE TO GET IT TO COMPILE
   } 


   /**
      Gets the maze data at loc.
      @param loc the location in maze coordinates
      @return the value at that location.  One of FREE and WALL
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
    */
   public int getValAt(MazeCoord loc) {

	   return mazeData[loc.getRow()-1][loc.getCol()-1];
   }

   
   /**
      Returns the path through the maze. First element is starting location, and
      last element is exit location.  If there was not path, or if this is called
      before search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {

      return exitPath;

   }


   /**
      Find a path through the maze if there is one.
      @return whether path was found.
    */
   public boolean search()  {  
	   
	   if(searchPerformed == false) { //makes sure search is only performed once

		   if(mazeData[0][0] == WALL){ //if the start position (0,0) is a wall, return false
			   searchPerformed = true;
			   return false;
		   } 
		   if(numRows() == 1 && numCols() == 1) { //if the maze size is 1x1
			   searchPerformed = true;
			   return true;
		   }
		   
		   LinkedList<MazeCoord> tempPath; //create temporary path

		   tempPath = new LinkedList<MazeCoord>();
		   tempPath.add(new MazeCoord(0,0));
		   if(recursiveSearch(0,1,tempPath)) { //recursive search in the east direction
			   exitPath = tempPath;
			   searchPerformed = true;
			   return true;
		   }
		   else if(recursiveSearch(1,0,tempPath)) { //recursive search in the south direction
			   searchPerformed = true;
			   exitPath = tempPath;
			   return true;
		   }
		   else{
			   searchPerformed = true; 
			   return false;
		   }
		   
	   }
	   else { 
		   if(exitPath == null) { //null exit path means no path was found
			   return false;
		   }
		   else {
			   return true;
		   }
	   }

   }

   /**
    * Performs the search recursively. Returns true if the path reaches the end, otherwise returns false
    * 
    * @param row  The row coordinate of the current space to search
    * @param col  The column coordinate of the current space to search
    * @param path  The current path thus far
    * @return  True if the current space leads to the end of the maze
    */
   private boolean recursiveSearch(int row, int col, LinkedList<MazeCoord> path) {
	   
	   if((row < 0 || row >= numRows()) || (col < 0 || col >= numCols())) { //prevents index out of bounds while searching
		   return false;
	   }
	   if(mazeData[row][col] == WALL) { //if the current space is a wall, returns false
		   return false;
	   }
	   if(isPointInPath(row, col, path)) { //if the current space is currently part of the existing path
		   return false;
	   }
	   if(row == numRows()-1 && col == numCols()-1) { //if the current space is the end, return true
		   path.add(new MazeCoord(row,col));
		   return true;
	   }
	   
	   path.add(new MazeCoord(row,col)); //once all the base conditions are passed, add the current coordinate to the current path and then search again
	   
	   if(recursiveSearch(row, col+1, path)) { //search to the east
		   return true;
	   }
	   else if(recursiveSearch(row+1, col, path)) { //search to the south
		   return true;
	   }
	   else if(recursiveSearch(row, col-1, path)) { //search to the west
		   return true;
	   }
	   else if(recursiveSearch(row-1, col, path)) { //search to the north
		   return true;
	   }
	   else { //if none of the directions provide valid search result, delete this current coordinate
		   path.removeLast();
		   return false;
	   }
	   
   }
   
   /**
    * Returns true if the given coordinate is part of the current path
    * 
    * @param row  
    * @param col
    * @param path
    * @return
    */
   private boolean isPointInPath(int row, int col, LinkedList<MazeCoord> path) {
	   
	   ListIterator<MazeCoord> iter = path.listIterator(path.size());
	   MazeCoord tempCoord;
	   
	   while(iter.hasPrevious()) {
		   tempCoord = iter.previous();
		   if(row == tempCoord.getRow() && col == tempCoord.getCol()) {
			   return true;
		   }
	   }
	   
	   return false;
	   
   }
   
   
   
   /**
    * Method returns a boolean value indicating whether or not the passed maze data is null
    * @param mazeData
    * @return true the mazeData 2D array is not null
    */
   private boolean isValidData(int[][] mazeData) {
	   if(mazeData == null) {
		   return false;
	   }
	   else { return true; }
   }
   
}
