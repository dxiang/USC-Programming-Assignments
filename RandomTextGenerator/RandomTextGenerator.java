/* 
 * Name: Dennis Xiang
 * USC loginid: dxiang
 * CS 455 PA4
 * Spring 2015
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.io.PrintWriter;

public class RandomTextGenerator {
	
	private ArrayList<String> source;
	private int numWords;
	private int prefixLength;
	
	private HashMap<Prefix,ArrayList<String>> prefixTable = new HashMap<Prefix,ArrayList<String>>();
	private boolean debugMode;

	/**
	 * Constructor method
	 * @param source: the source in arraylist format
	 * @param numWords
	 * @param prefixLength
	 * @param debugMode
	 */
	public RandomTextGenerator(ArrayList<String> source, int numWords, int prefixLength, boolean debugMode) {
		this.source = source;
		this.numWords = numWords;
		this.prefixLength = prefixLength;
		this.debugMode = debugMode;
	}//end constructor

	
	/**
	 * Creates a hashmap of all prefixes and possible next words. Randomly generates a prefix from the source based on given prefix length
	 * and uses said prefix to generate the next word. The new word is printed to the outfile and the prefix is updated to contain the new
	 * word by removing the first word of the prefix. Ensures that each line printed in the output file does not exceed 80 characters. Generates
	 * random words based on prefixes until numWords is reached.
	 * @param printer: PrintWriter object for the out put file
	 */
	public void genText(PrintWriter printer) {
		
		hashSource();
		Random rand = new Random();
		int wordCount = prefixLength;
		int charCount = 0;
		
		Prefix iniPfx = genRandPrefix(rand);
		
		printer.print(iniPfx + " ");
		charCount += iniPfx.toString().length() + 1; //keep track of number of characters per line, including whitespaces
		
		Prefix currPfx = iniPfx; //holder variables
		String nextWord;
		
		while(wordCount <= numWords) {
			
			if(prefixTable.get(currPfx) == null) { //true if prefix is the last word(s) of the source, thus there is no "next" word.
				currPfx = genRandPrefix(rand); //generate a new random prefix
			}
			
			nextWord = prefixTable.get(currPfx).get(rand.nextInt(prefixTable.get(currPfx).size())); //randomly selects a next word from the bucket of possible next words
			
			if((charCount + nextWord.length()) > 80) { //new line if next word causes current line to exceed 80 chars
				printer.println();
				charCount = 0;
			}
			
			printer.print(nextWord + " ");
			charCount += nextWord.length() + 1;
			wordCount++;
			currPfx = currPfx.advancePrefix(nextWord);
			
		}
		
	}//end method
	
	
	/**
	 * Debug version of the random text generator. Prints out debug statements during execution.
	 * @param printer
	 */
	public void genTextDebug(PrintWriter printer) {
		
		hashSource();
		Random rand = new Random(1); //debug mode random object seed set to 1
		int wordCount = prefixLength;
		int charCount = 0;
		
		Prefix iniPfx = genRandPrefix(rand);
		
		System.out.println("DEBUG: Initial prefix: " + iniPfx.getArrayList());
		printer.print(iniPfx + " ");
		charCount += iniPfx.toString().length() + 1;
		System.out.println("Character count: " + charCount);
		
		Prefix currPfx = iniPfx;
		String nextWord;
		
		while(wordCount <= numWords) {
			
			if(prefixTable.get(currPfx) == null) {
				System.out.println("DEBUG: no result for current prefix, generating a new random prefix.");
				currPfx = genRandPrefix(rand);
				System.out.println("DEBUG: new prefix: " + currPfx.getArrayList());
			}
			
			System.out.println("DEBUG: successors: " + prefixTable.get(currPfx));
			nextWord = prefixTable.get(currPfx).get(rand.nextInt(prefixTable.get(currPfx).size()));
			System.out.println("DEBUG: word generated: " + nextWord);
			
			if((charCount + nextWord.length()) > 80) {
				printer.println();
				charCount = 0;
			}
			
			printer.print(nextWord + " ");
			charCount += nextWord.length() + 1;
			System.out.println("Character count: " + charCount);
			wordCount++;
			currPfx = currPfx.advancePrefix(nextWord);
			System.out.println("DEBUG: new prefix: " + currPfx.getArrayList());
			
		}
		
	}//end method
	
	
	/**
	 * Generates a random prefix from the source using the passed random object.
	 * @param rand
	 * @return
	 */
	private Prefix genRandPrefix(Random rand) {
		
		int randIndex = rand.nextInt(source.size()-prefixLength); //ensure that the randomly generated start index leaves enough room to generate the entire prefix
		ArrayList<String> iniPfxString = new ArrayList<String>();
		for(int i = randIndex; i < (randIndex + prefixLength); i++) {
			iniPfxString.add(source.get(i));
		}
		
		return new Prefix(iniPfxString);
		
	}//end method
	
	
	/**
	 * This method stores all prefix possibilities and their corresponding next word(s) into a hash map for the given source arraylist and 
	 * prefix length. For each prefix key, multiple values can be stored via chaining within each hash bucket.
	 */
	private void hashSource() {
		
		//creates the first prefix and its next word
		ArrayList<String> iniPfxString = new ArrayList<String>();
		for(int i = 0; i < prefixLength; i++) {
			iniPfxString.add(source.get(i));
		}
		
		int sourceIndex = prefixLength;
		ArrayList<String> iniResult = new ArrayList<String>();
		iniResult.add(source.get(sourceIndex));
		Prefix initial = new Prefix(iniPfxString);
		
		prefixTable.put(initial, iniResult); //store the entry into the map
		
		Prefix currPrefix = initial; //prefix variable for upcoming loop
		ArrayList<String> currBucket; //string arraylist variable upcoming loop
		
		while(sourceIndex < source.size()-1) {
			
			currPrefix = currPrefix.advancePrefix(source.get(sourceIndex));
			sourceIndex++;
			if(prefixTable.containsKey(currPrefix)) { //if prefix key already exists, get the arraylist of values and adds a new string value to the chain
				currBucket = prefixTable.get(currPrefix);
				currBucket.add(source.get(sourceIndex));
				prefixTable.put(currPrefix, currBucket);
			}
			else {
				prefixTable.put(currPrefix, new ArrayList<String>());
				prefixTable.get(currPrefix).add(source.get(sourceIndex));
			}
			
		}
		
	}//end method
	
	
	/**
	 * FOR TESTING PURPOSES ONLY. Prints out the hashmap created by hasSource() method.
	 */
	private void printHashTable() {
		
		Iterator<Entry<Prefix, ArrayList<String>>> iter = prefixTable.entrySet().iterator();
		
		while(iter.hasNext()) {
			Entry<Prefix, ArrayList<String>> curr = iter.next();
			System.out.println(curr.getKey() + " " + curr.getValue());
		}
		
	}//end method
	
	
}//end class
