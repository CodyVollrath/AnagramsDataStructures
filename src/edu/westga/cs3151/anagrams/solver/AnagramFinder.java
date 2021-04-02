package edu.westga.cs3151.anagrams.solver;

import java.util.ArrayList;
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
	
	/**
	 * Instantiates a new anagram finder
	 * 
	 * The dictionary file must be a plain text file. Each line in the file must
	 * contain exactly one word.
	 * 
	 * @param filename the name of the dictionary file used by this anagram finder
	 */
	public AnagramFinder(String filename) {
		this.wordBank = new ArrayList<String>();
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
		List<String> values = this.determineValidAnagrams(letters);
		if (values != null) {
			this.permutationOfSentence(new LetterSubtracter(letters), new Stack<String>(), values, result);
		}
		return result;
	}
	
	private List<String> removeInvalidWords(String letters) {
		List<String> values = new ArrayList<String>();
		
		for (String word : this.wordBank) {
			for (int i = 0; i < word.length(); i++) {
				String character = String.valueOf(word.charAt(i));
				boolean areLettersContained = letters.contains(character);
				if (!areLettersContained) {
					break;
				}
				
				if (i + 1 == word.length()) {
					values.add(word);
				}
			}
		}
		return (!values.isEmpty()) ? values : null;
	}
	
	private List<String> determineValidAnagrams(String letters){
		List<String> filteredDictionary = this.removeInvalidWords(letters);
		List<String> refinedDictionary = new ArrayList<String>();
		if (filteredDictionary == null) {
			return null;
		}
		LetterSubtracter inventory = new LetterSubtracter(letters);
		for (String word : filteredDictionary) {
			LetterSubtracter anagramInventory = new LetterSubtracter(word);
			if (inventory.reduceLetters(anagramInventory) != null) {
				refinedDictionary.add(word);
			}
		}
		return refinedDictionary;
	}
	
	private void permutationOfSentence(LetterSubtracter inventory, Stack<String> anagramStack, List<String> filteredList, ArrayList<ArrayList<String>> outputList) {
		if (inventory.isEmpty()) {
			ArrayList<String> anagram = new ArrayList<String>();
			anagram.addAll(anagramStack);
			outputList.add(anagram);
			return;
		}
		
		for (String word : filteredList) {
			LetterSubtracter newInventory = inventory.reduceLetters(new LetterSubtracter(word));
			if (newInventory != null) {
				anagramStack.push(word);
				permutationOfSentence(newInventory, anagramStack, filteredList, outputList);
				anagramStack.pop();
			}
		}
	}
}
