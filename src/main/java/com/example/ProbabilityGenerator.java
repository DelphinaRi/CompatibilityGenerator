/*
 c2017-2023 Courtney Brown 
 * Modified by Delphina Rivas
 * 
 * Class: ProbabliityGenerator
 * 
 */

package com.example;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ProbabilityGenerator<E> {
	ArrayList<E> alphabet = new ArrayList<E>();
	// ArrayList<Int> a = new ArrayList<E>();
	ArrayList<Float> tokenCounts = new ArrayList<Float>();
	ArrayList<E> a = new ArrayList<E>();

	double tokenCount = 0;
	E tok;

	void train(ArrayList<E> data) {
		// train prob gen
		float z = 0.f;
		for (int i = 0; i < data.size(); i++) {
			// finds the index of the data element in the alpabet array
			int index = alphabet.indexOf(data.get(i));

			if (index == -1) {
				// if the element is not in the array it creates a new element in alpabet array
				// and adds a 0 to token counts
				index = alphabet.size();
				alphabet.add(data.get(i));
				tokenCounts.add(z);

			}
			// for the element in the aphabet array it adds 1 to the token counts
			float num = tokenCounts.get(index) + 1;

			tokenCounts.set(index, num);
			// a.add(data.get(i));
		}
		tokenCount += data.size();
		// return

	}

	ArrayList<E> generate(int sizeofGeneration, ArrayList<Float> data, boolean found, ArrayList<E> d) {
		ArrayList<E> newMelody = new ArrayList<E>();
		// ArrayList<E> alpha = new ArrayList<E>();

		// noramlize token count to decimals
		// add probabilities to new arraylist
		// System.out.println("running generate");
		// if found = true then the funciton will work with markov genrator
		// if found = false then it works for the prob gen
		double probability;
		int div = 0;
		for (int j = 0; j < data.size(); j++) {
			// gets the total ofo the counts if found = true
			div += data.get(j);
			// alpha.add(data.get(j));
		}

		boolean all0 = true;

		ArrayList<Double> probs = new ArrayList<Double>();
		for (int i = 0; i < data.size(); i++) {
			if (found == true) {
				// System.out.println(div + " ");
				// converts the value of data to a double and then divides it by the total of
				// the row
				float f = data.get(i);
				double p = f;
				probability = p / div;
			} else {
				probability = (double) data.get(i) / tokenCount;
			}

			if (i == 0) {
				probs.add(probability);
			} else {
				// adds probabilities together
				probs.add(probability + probs.get(i - 1));

			}
			if (data.get(i) != 0) {
				all0 = false;
			}
		}
		if (all0 == true) {
			// gets new token based on prob gen
			tok = generateToken(d); 
			return newMelody;
		}
		// determines the random value between 0 and 1 and getting the probability
		// new arraylist stores the new note for newMelody.
		double min = 0;
		double max = 1;
		// ArrayList<E> newMelody = new ArrayList<E>();
		for (int i = 0; i < sizeofGeneration; i++) {
			// gets random number
			double rand = min + (double) (Math.random() * ((max - min)));
			// System.out.println("RAND = " + rand);
			for (int j = 0; j < probs.size(); j++) {
				// if a number less than that probability it gets added to the melody array
				if (rand < probs.get(j)) {
					if (found == true && data.get(j) != 0) {
						// if found == true then it turns it to tok which gets returned for the markov
						// gen class
						tok = alphabet.get(probs.indexOf(probs.get(j)));
						// getGen(alphabet.get(probs.indexOf(probs.get(j))));
					}

					// System.out.println(i+": Rand = " + rand+ "Probability = " + probs.get(j));
					newMelody.add(alphabet.get(probs.indexOf(probs.get(j))));
					break;
				}
			}
		}
		// returns the array of the new notes
		return newMelody;
	}



	// if no speecific data is put in it calls token counts so it still works with
	// both prob gen and Markov chain generation
	ArrayList<E> generate(int sizeofGeneration) {
		return generate(sizeofGeneration, tokenCounts, false, a);
	}

	E generateToken(ArrayList<E> d) {
		//makes new prob gen for if token has no  value after it    
		ProbabilityGenerator<E> tokens = new ProbabilityGenerator<E>();
		tokens.train(d);
		ArrayList<E> t = tokens.generate(2);
		E initToken = t.get(0);

		return initToken;
	}

	E getGen() {
		// returns the 1 token that it creates
		return tok;
	}

	// nested convenience class to return two arrays from sortArrays() method
	// students do not need to use this class
	protected class SortArraysOutput {
		public ArrayList<E> symbolsListSorted;
		public ArrayList<Float> symbolsCountSorted;
	}

	// sort the symbols list and the counts list, so that we can easily print the
	// probability distribution for testing
	// symbols -- your alphabet or list of symbols (input)
	// counts -- the number of times each symbol occurs (input)
	// symbolsListSorted -- your SORTED alphabet or list of symbols (output)
	// symbolsCountSorted -- list of the number of times each symbol occurs inorder
	// of symbolsListSorted (output)
	public SortArraysOutput sortArrays(ArrayList<E> symbols, ArrayList<Float> counts) {

		SortArraysOutput sortArraysOutput = new SortArraysOutput();

		sortArraysOutput.symbolsListSorted = new ArrayList<E>(symbols);
		sortArraysOutput.symbolsCountSorted = new ArrayList<Float>();

		// sort the symbols list
		Collections.sort(sortArraysOutput.symbolsListSorted, new Comparator<E>() {
			@Override
			public int compare(E o1, E o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});

		// use the current sorted list to reference the counts and get the sorted counts
		for (int i = 0; i < sortArraysOutput.symbolsListSorted.size(); i++) {
			int index = symbols.indexOf(sortArraysOutput.symbolsListSorted.get(i));
			sortArraysOutput.symbolsCountSorted.add(counts.get(index));
		}

		return sortArraysOutput;

	}

	// Students should USE this method in your unit tests to print the probability
	// distribution
	// HINT: you can overload this function so that it uses your class variables
	// instead of taking in parameters
	// boolean is FALSE to test train() method & TRUE to test generate() method
	// symbols -- your alphabet or list of symbols (input)
	// counts -- the number of times each symbol occurs (input)
	// sumSymbols -- the count of how many tokens we have encountered (input)
	public void printProbabilityDistribution(boolean round, ArrayList<E> symbols, ArrayList<Float> counts,
			double sumSymbols) {
		// sort the arrays so that elements appear in the same order every time and it
		// is easy to test.
		SortArraysOutput sortResult = sortArrays(symbols, counts);
		ArrayList<E> symbolsListSorted = sortResult.symbolsListSorted;
		ArrayList<Float> symbolsCountSorted = sortResult.symbolsCountSorted;

		System.out.println("-----Probability Distribution-----");

		for (int i = 0; i < symbols.size(); i++) {
			if (round) {
				DecimalFormat df = new DecimalFormat("#.##");
				System.out.println("Data: " + symbolsListSorted.get(i) + " | Probability: "
						+ df.format((double) symbolsCountSorted.get(i) / sumSymbols));
			} else {
				System.out.println("Data: " + symbolsListSorted.get(i) + " | Probability: "
						+ (double) symbolsCountSorted.get(i) / sumSymbols);
			}
		}

		System.out.println("------------");
	}

	public void printProbabilityDistribution(boolean round) {
		printProbabilityDistribution(round, alphabet, tokenCounts, tokenCount);

	}
}
