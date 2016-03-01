// Name: Dennis Xiang
// USC loginid: dxiang
// CS 455 PA3
// Spring 2015

import javax.swing.JFrame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


/**
 * MazeViewer class
 * 
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 * 
 * How to call it from the command line:
 * 
 *      java MazeViewer mazeFile
 * 
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row. Each maze location is
 * either a wall (1) or free (0). Here is an example of contents of a file for
 * a 3x4 maze:
 * 
 * 3 4 
 * 0111
 * 0000
 * 1110
 * 
 * The top left is the maze entrance, and the bottom right is the maze exit.
 * 
 */


public class MazeViewer {

   public static void main(String[] args)  {

      String fileName = "";

      try {

         if (args.length < 1) {
            System.out.println("ERROR: missing file name command line argument");
         }
         else {
            fileName = args[0];

            int[][] mazeData = readMazeFile(fileName);
            
            JFrame frame = new MazeFrame(mazeData);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);
         }

      }
      catch (FileNotFoundException exc) {
         System.out.println("ERROR: File not found: " + fileName);
      }
      catch (IOException exc) {
         exc.printStackTrace();
      }
   }

   
   /**
      readMazeFile reads in and returns a maze from the file whose name is
      String given. The file format is shown in the MazeViewer class comments.
      
      @param fileName
                the name of a file to read from
      @returns 
               the array with maze contents. 0's correspond to 0's in the file
               and 1's correspond to 1's in the file. The first dimension is
               which row, and the second is which column. E.g. if the file
               started with 3 10, it would mean this array returned would have
               3 rows, and 10 columns.
      @throws FileNotFoundException
                 if there's no such file (subclass of IOException)
      @throws IOException
                 (hook given in case you want to do more error-checking.
                  that would also involve changing main to catch other 
                  exceptions)
    */
   private static int[][] readMazeFile(String fileName) throws IOException {
	   
       Scanner fileScanner = new Scanner(new File(fileName));
       String rowData;
       
       int[][] mazeArray = new int[fileScanner.nextInt()][fileScanner.nextInt()]; //takes first two integers in data file and initializes the 2D array
       fileScanner.nextLine(); //advance scanner to first row of maze data
       int rowCount = 1;
       
	   while(fileScanner.hasNextLine() && rowCount <= mazeArray.length) {
		   rowData = new String(fileScanner.nextLine()); //stores each line after the first one as a string
		   if(rowData.length() == mazeArray[0].length) { //ensures the input row data is the correct length
			   for(int i = 0; i < rowData.length(); i++) { //loops through each line and extracts each maze data point
				   mazeArray[rowCount-1][i] = Character.getNumericValue(rowData.charAt(i));
			   }
		   }
		   else {
			   System.out.println("WARNING: Maze input data row " + rowCount + " does not have length of " + mazeArray[0].length);
		   }
		   rowCount++;
	   }
	   
	   if(fileScanner.hasNextLine() || rowCount-1 != mazeArray.length) { //makes sure the number of rows of data matches the given data dimensions
		   System.out.println("WARNING: Number of maze data rows is not equal to " + mazeArray.length);
	   }
	   
	   fileScanner.close();
	   
       return mazeArray;
   }

   
}

