package aoc2023.Day_3.Part_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gear {
	// numbers stores a list of Integer[] {number, startIndex, endIndex} for each line
	private HashMap<Integer, Integer[][]> numbers;
	
	// tokens stores a list of token indexes for every line of the file
	private HashMap<Integer, Integer[]> tokens;
	
	public Integer sumOfRatios(Integer numOfLines) {
		Integer total = 0;
		
		// looping over each key in the field, tokens
		for (Integer i = 0; i < numOfLines - 1; i++) {
			Integer[] currentTokenLine = tokens.get(i);
			
			// looping over every index in the current line
			for (Integer j = 0; j < currentTokenLine.length - 1; j++) {
				Integer[] numbersAroundGear = null;
				numbersAroundGear = concatArray(numbersAroundGear, getNumbers(i - 1, currentTokenLine[j]));
				numbersAroundGear = concatArray(numbersAroundGear, getNumbers(i, currentTokenLine[j]));
				numbersAroundGear = concatArray(numbersAroundGear, getNumbers(i + 1, currentTokenLine[j]));
				
				if (numbersAroundGear.length == 2) {
					total += numbersAroundGear[0] * numbersAroundGear[1];
				}
			}
		}
		
		return total;
	}
	
	// getNumbers searches every index of the field, numbers, and returns the
	// integers which appear between the correct boundaries as the input
	// token.
	private Integer[] getNumbers(Integer lineInFile, Integer tokenIndex) {
		Integer[][] numberLine = numbers.getOrDefault(lineInFile, null);
		if (numberLine == null) {
			return null;
		}
		Integer[] gearNumbers = null;
		
		for (Integer number = 0; number < numberLine.length - 1; number++) {
			if (numberLine[number][1] <= tokenIndex && numberLine[number][2] >= tokenIndex) {
				gearNumbers = concatArray(gearNumbers, numberLine[number]);
			}
		}
		
		return gearNumbers;
	}
	
	
	/*
	// method sumOfRatios returns the total of every gear ratio
	public Integer sumOfRatios(Integer numOfLines, Integer tokenIndex) {
		Integer total = 0;
		
		// loop over every line in the modified file data
		for (Integer line = 0; line < numOfLines - 1; line++) {
			Integer[] numbersAroundGear = null;
			
			// We populate an array with all the numbers we find adjacent to the token.
			// We then check if the 
			Integer[] numbersInLine = getNumbers(line - 1, tokenIndex);
			numbersAroundGear = checkNumbers(numbersAroundGear, numbersInLine);
			
			numbersInLine = getNumbers(line , tokenIndex);
			numbersAroundGear = checkNumbers(numbersAroundGear, numbersInLine);
			
			numbersInLine = getNumbers(line + 1 , tokenIndex);
			numbersAroundGear = checkNumbers(numbersAroundGear, numbersInLine);
			
			if (numbersAroundGear.length == 2) {
				total += numbersAroundGear[0] * numbersAroundGear[1];
			}
		}
		
		return total;
	}
	
	// method checkNumbers verifies that a number was found for the current line,
	// else returns the original array.
	private Integer[] checkNumbers(Integer[] original, Integer[] numbersInLine) {
		if (numbersInLine != null) {
			return concatArray(original, numbersInLine);
		}
		
		return original;
	} */
	
	
	// method concatList concatenates two lists together
	private Integer[] concatArray(Integer[] first, Integer[] second) {
		List<Integer> newList = new ArrayList<Integer>();
		for (Integer i = 0; i < first.length - 1; i++) {
			newList.add(first[i]);
		}
		for (Integer i = 0; i < second.length - 1; i++) {
			newList.add(second[i]);
		}
		
		return (Integer[]) newList.toArray();
	}
}
