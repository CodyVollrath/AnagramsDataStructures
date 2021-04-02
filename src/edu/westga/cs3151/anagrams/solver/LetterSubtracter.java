package edu.westga.cs3151.anagrams.solver;

/**
 * This class is used to find the difference from the char values in other words.
 * 
 * @author Cody Vollrath
 * *Note: this was inspired from an online resource: https://github.com/junqdu/AlphaBearHack/blob/master/app/src/main/java/jotaro/alphabearhack/LetterInventory.java
 */
public class LetterSubtracter{
	private int size;
	private int[] letterData;
	
	private static final int MAX_CHAR_LIMIT = 50;
	
	/**
	 * Creates a letter subtracter object
	 * @param word the word to be converted to a LetterSubtractor object
	 */
	public LetterSubtracter(String word){
		this.letterData = new int[LetterSubtracter.MAX_CHAR_LIMIT];
		word = word.toLowerCase();
		for (int i = 0; i < word.length(); i++){
			if (Character.isLetter(word.charAt(i))){
				this.letterData[word.charAt(i) - 'a']++;
				if(word.charAt(i) == 'q'){
					this.letterData['u' - 'a']++;
				}
				this.size++;
			}
		}
	}
	
	/**
	 * The size of the elements
	 * @return size
	 */
	public int size(){
		return this.size;
	}

	/**
	 * Determines if the LetterSubtractor has no element data
	 * @return true if the size is 0 false if size > 0
	 */
	public boolean isEmpty(){
		return this.size == 0;
	}
	
	/**
	 * Finds the difference between the LetterSubtracter entered and the current object
	 * @param other the other letter subtracter
	 * @return the remaining letters
	 */
	public LetterSubtracter reduceLetters(LetterSubtracter other){
		LetterSubtracter result = new LetterSubtracter("");
		for (int i = 0; i < MAX_CHAR_LIMIT; i++){
			result.letterData[i] = this.letterData[i] - other.letterData[i];
			if (result.letterData[i] < 0)
				return null;
			result.size += result.letterData[i];
		}
		return result;
	}
}
