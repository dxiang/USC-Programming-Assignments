/* 
 * Name: Dennis Xiang
 * USC loginid: dxiang
 * CS 455 PA4
 * Spring 2015
 */

import java.util.ArrayList;

/**
 * Immutable class for storing information for a prefix
 * @author Dennis
 *
 */


public class Prefix {
	
	private ArrayList<String> prefixAL;
	
	/**
	 * Constructor method, takes an arraylist 
	 * @param prefixAL
	 */
	public Prefix(ArrayList<String> prefixAL) {
		this.prefixAL = prefixAL;
	}//end constructor
	
	
	/**
	 * Method for returning the arraylist containing the prefix words
	 * @return the prefix arraylist
	 */
	public ArrayList<String> getArrayList() {
		
		return prefixAL;
		
	}//end method
	
	
	/**
	 * Overrides the toString() method. Returns the arraylist elements as a string with a space between each word. 
	 */
	public String toString() {
		
		String temp = "";
		for(int i = 0; i < prefixAL.size(); i++) {
			temp = temp + prefixAL.get(i) + " ";
		}
		return temp.substring(0, temp.length()-1);
		
	}//end method
	
	
	/**
	 * Overrides the default hashCode method. Generates hashcode by adding the hash values of each word in the prefix together
	 */
	public int hashCode() {
		
		int hashVal = 0;
		
		for(String word : prefixAL) {
			hashVal = hashVal + word.hashCode();
		}
		
		return hashVal;
	}//end method
	
	
	/**
	 * Overrides the default equals method. Two prefix objects are determined to be equal if the ArrayList of words in the other object
	 * is equal that of the current object. Employs the equals() method of ArrayList objects
	 */
	public boolean equals(Object o) {
		
		if(o == null) {
			return false;
		}
		
		if(!prefixAL.equals(((Prefix)o).getArrayList())){
			return false;
		}

		return true;
		
	}//end method
	
	
	/**
	 * This method "advances" the prefix by deleting the first word in the prefix and adding the given new word to the end
	 * @param word - the new word to add to the prefix
	 * @return a new Prefix object with the updated arraylist of words
	 */
	public Prefix advancePrefix(String word) {
		
		ArrayList<String> newPfxString = new ArrayList<String>(prefixAL);
		newPfxString.remove(0);
		newPfxString.add(word);
		
		return new Prefix(newPfxString);
		
	}//end method
	

}//end class
