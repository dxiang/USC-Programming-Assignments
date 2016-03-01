/*
Name: Dennis Xiang
USC loginid: dxiang
CS 455 PA1
Spring 2015

The Drunkard class defines an object that simulates a drunkard; it is given a starting location as an ImPoint object and a step size, 
and is capable of taking a step of size stepSize in a random cardinal direction. The drunkard object can also return its location 
as an ImPoint object.
*/

import java.util.Random;

/**
   Drunkard class
       Represents a "drunkard" doing a random walk on a grid.
*/

public class Drunkard {

	private ImPoint currentLoc;
	private int stepSize;
	private Random rnd = new Random();
	
    /**
       Creates drunkard with given starting location and distance
       to move in a single step.
       @param startLoc starting location of drunkard
       @param theStepSize size of one step in the random walk
    */
    public Drunkard(ImPoint startLoc, int theStepSize) {
    	
    	currentLoc = startLoc;
    	stepSize = theStepSize;
    	
    }// end Drunkard constructor


    /**
       Takes a step of length step-size (see constructor) in one of
       the four compass directions.  Changes the current location of the
       drunkard.
    */
    public void takeStep() {
    	
    	int direction = rnd.nextInt(4);
 
    	switch(direction)
    	{
    		case 0: currentLoc = currentLoc.translate(0, stepSize); break; //north
    		case 1: currentLoc = currentLoc.translate(stepSize,0); break; //east
    		case 2: currentLoc = currentLoc.translate(0, -stepSize); break; //south
    		case 3: currentLoc = currentLoc.translate(-stepSize,0); break; //west
    	}//end switch

    }//end takeStep() method


    /**
       gets the current location of the drunkard.
       @return an ImPoint object representing drunkard's current location
    */
    public ImPoint getCurrentLoc() {
    	
    	return currentLoc;
    	
    }//end getCurrentLoc() method

}//end Drunkard class
