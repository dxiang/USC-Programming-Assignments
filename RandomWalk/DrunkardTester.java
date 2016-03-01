/* 
Name: Dennis Xiang
USC loginid: dxiang
CS 455 PA1
Spring 2015
*/

import java.lang.Math;

/**
 * 
 * This class serves to test the Drunkard class by hard-coding various starting points and step sizes, then telling the drunkard
 * object to take multiple steps. Each step taken is tested for being valid; i.e. the step is only taken in one of the four
 * cardinal directions, and that the step size is the given step size. 
 * @author Dennis
 * 
 */
public class DrunkardTester {

    /**
    Main method for this tester class. Calls drunkardTest multiple times using different start locations and step sizes.
     */
	public static void main(String[] args) {
		
		drunkardTest(0,0,1);
		System.out.println("");
		
		drunkardTest(0,0,5);
		System.out.println("");
		
		drunkardTest(2,7,3);
		System.out.println("");

		drunkardTest(100,50,7);
		System.out.println("");
	}//end main

    /**
    Creates a Drunkard object with the given start location and step size from main and calls the takeStep() method
    of the Drunkard 10 times. The updated Drunkard object is then sent to the chkStep method to test for validity of the step.
    @param start_X the starting X coordinate for the Drunkard
    @param start_Y the starting Y coordinate for the Drunkard
    @param stepSize the step size of the Drunkard
    
     */
	private static void drunkardTest(int start_X, int start_Y, int stepSize) {
		
		ImPoint startLoc = new ImPoint(start_X, start_Y);
		Drunkard drunk1 = new Drunkard(startLoc, stepSize);
		int prev_X = start_X; //record start coordinates as "previous" location coordinates prior to calling takeStep() method of
		int prev_Y = start_Y; //the Drunkard
		
		System.out.println("-------------------Begin Test-------------------");
		System.out.println("Drunkard starts at (" + start_X + "," + start_Y + "); step size is " + stepSize);
		System.out.println("Getting starting location: " + drunk1.getCurrentLoc());
		
		
		for (int counter = 1; counter <= 10; counter++)
		{
			drunk1.takeStep();
			System.out.println("Took step to: " + drunk1.getCurrentLoc());
			chkStep(prev_X, prev_Y, drunk1.getCurrentLoc().getX(), drunk1.getCurrentLoc().getY(), stepSize); //checks validity of taken step
			prev_X = drunk1.getCurrentLoc().getX(); //re-assign "previous" coordinates
			prev_Y = drunk1.getCurrentLoc().getY();
		}//end for loop
		
		System.out.println("-------------------End Test-------------------");
		
	}//end drunkardTest method

	
    /**
    Checks that the Drunkard took a valid step by making sure that the step matches the step size and that only one direction is
    taken. 
    @param lastX the last X coordinate of the Drunkard
    @param lastY the last Y coordinate of the Drunkard
    @param newX the new X coordinate of the Drunkard
    @param newY the new Y coordinate of the Drunkard
    @param stepSize the step size of the Drunkard
    */	
	private static void chkStep(int lastX, int lastY, int newX, int newY, int stepSize){
		
		if(Math.abs(newX - lastX) == stepSize && (newY - lastY == 0)){
			System.out.println("VALID STEP - SUCCESS!");			
		}//end if
		else if(newX - lastX == 0 && Math.abs(newY - lastY) == stepSize){
			System.out.println("VALID STEP - SUCCESS!");
		}//end else if
		else{
			System.out.println("INVALID STEP - FAILED.");
		}//end else
		
	}//end chkStep method

}//end class
