package edu.westga.cs3151.anagrams.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
/**
 * The Class AnagramFinder
 * 
 * @author CS3151
 * @version Spring 2021
 */
public class AnagramFinder {

	private List<String> wordBank;
	private List<String> currentAnagram;
	private List<String> vistedWords;
	/**
	 * Instantiates a new anagram finder
	 * 
	 * The dictionary file must be a plain text file. Each line in the file must
	 * contain exactly one word.
	 * 
	 * @param filename the name of the dictionary file used by this anagram finder
	 */
	public AnagramFinder(String filename) {
		this.wordBank = new Stack<String>();
		this.vistedWords = new ArrayList<String>();
		this.currentAnagram = new ArrayList<String>();
		try {
			File file = new File(filename);
			@SuppressWarnings("resource")
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				this.wordBank.add(data);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			throw new IllegalArgumentException("filename can not be found");
		}
		System.out.println("Word Bank Created");
	}

	/**
	 * Returns a list of anagrams that can be formed from the specified letters
	 * and that are formed from words of this anagram-finder's dictionary
	 * 
	 * @param letters the letters to be used by the anagrams
	 * @return a list of anagrams where each anagram is a list of strings
	 */
	public ArrayList<ArrayList<String>> findAnagrams(String letters) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		String[] anagrams = Arrays.copyOf(this.wordBank.toArray(), this.wordBank.size(), String[].class);
		this.determineAnagram(anagrams, letters);
		if (!this.currentAnagram.isEmpty()) {
			result.add((ArrayList<String>) this.currentAnagram);
		}
		this.currentAnagram = new ArrayList<String>();
		this.vistedWords = new ArrayList<String>();
		return result;
	}
	
	private void determineAnagram(String[] anagrams, String word){
		int median = anagrams.length / 2;
		int modLength = anagrams.length % 2;
		if (median == 0 || this.isAnagram(anagrams[median], word)) {
			if (this.isAnagram(anagrams[median], word) && this.isNotVisited(anagrams[median])) {
				this.currentAnagram.add(anagrams[median]);
				this.vistedWords.add(anagrams[median]);
			}
			
			if (median == 0) {
				return;
			}
		}
		String[] leftArray;
		String rightArray[];
		if (modLength == 0) {
			leftArray = new String[median];
			rightArray = new String[median];
			leftArray = Arrays.copyOfRange(anagrams, 0, median);
			rightArray = Arrays.copyOfRange(anagrams, median, anagrams.length);
		} else {
			leftArray = new String[median];
			rightArray = new String[median+1];
			leftArray = Arrays.copyOfRange(anagrams, 0, median);
			rightArray = Arrays.copyOfRange(anagrams, median + 1, anagrams.length);
		}
		this.determineAnagram(leftArray, word);
		this.determineAnagram(rightArray, word);
   }
	
	private boolean isAnagram(String anagram, String word) {
		char[] anagramChars = anagram.toCharArray();
		char[] wordChars = word.toLowerCase().toCharArray();
		Arrays.sort(anagramChars);
		Arrays.sort(wordChars);
		String anagramSorted = new String(anagramChars);
		String wordSorted = new String(wordChars);
		if (anagramSorted.equals(wordSorted)) {
			return true;
		}
		return false;
	}
	private boolean isNotVisited(String mutation) {
		return !this.vistedWords.contains(mutation);
	}
}
