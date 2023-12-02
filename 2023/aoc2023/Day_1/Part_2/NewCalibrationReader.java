package aoc2023.Day_1.Part_2;

// cursed ceremony:
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


// Note to self: slow it down, plan out a better structure. This code is an example of 'setting pants on fire', in the Matrix.
// Committing this code is a stain on my legacy. I may get banned off GitHub permanently for this.
// NSFL 21+ Do not stare for too long. Stop reading if you experience light sensitivity or sickness.

// I may make an "amended" version of this code at a later date. I am mainly using Java for fun, here, I had to re-learn a lot
// of the specification to bring this code here...
public class NewCalibrationReader {
	private static String[] alphaDigits = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
	
	public static void main(String[] args) {
		System.out.println("Running Advent of Code Day 1, Challenge No. 2...\n----------------------------------------------");
        
        // Read the calibration document in whole (~23KB) and process each line sequentially.
        try {
        	Integer calibrationTotal = 0; 
        	Path doc = Paths.get("./aoc2023/Day_1/calibration/calibration.txt");
        	// Path doc = Paths.get("./aoc2023/Day_1/calibration/test.txt");
			List<String> lines = Files.readAllLines(doc, StandardCharsets.UTF_8);
			
			for (int i = 0; i < lines.size(); i++) {
				System.out.println("Step " + (i+1) + ".\n----------------");
				calibrationTotal += getCalibrationData(lines.get(i));
				System.out.println("New Calibration value is: " + calibrationTotal + ".\n");
			}
			
			System.out.println("Total calibration value: " + calibrationTotal);
		} catch (IOException e) {
			System.out.println(e);
			return;
		}
	}
	
	// method getCalibrationData returns the Calibration value for each line of the whole Calibration Document.
	// Each return value will consist of two digits between 11 and 99.
	// The return value is formed by taking the first and last digit within the line.
	// A 'Digit' refers to any numeric or String representation of an integer. This includes 'six' and '6'.
	// If only a single digit is found from the string, then the same digit is reused to form a double-digit number.
	
	// This method differs from Part_1 because it works by splitting strings at the first occurrence of an integer.
	// This forces one half of the string to be alphabetical and the other half is still mixed.
	// This simplifies the problem because we can immediately search for substrings or obtain the first number in the segment.
	// We can exclude middle string sequences if we can verify digits early, or we continue at Binary Search complexity. 
	private static int getCalibrationData(String line) {
		String calibrationValue = "";
		String[] splitString = line.split("(?<=\\d)");
		
		System.out.println("Input " + line + " after splitting is " + Arrays.toString(splitString) + ".");
		
		// If the line begins with a number, then we can immediately use it as the first Digit without looking too deep.
		if (splitString[0].length() == 1) {
			String firstDigit = splitString[0];
			calibrationValue = firstDigit.toString();
			
			// Check if the final element is a numeric integer.
			String finalElement = splitString[findHighestIndex(splitString)];
			if (Character.isDigit(finalElement.charAt(finalElement.length() - 1))) {
				calibrationValue = calibrationValue + finalElement.charAt(finalElement.length() - 1);
				System.out.println("Adding " + calibrationValue + " to the Calibration value.");
				return Integer.parseInt(calibrationValue);
			} else {
				// String finalElement = splitString[findHighestIndex(splitString)];
				String secondDigit = "0";
				
				// start from end of splitString and work backwards until first element. (Stop after processing the one before)
				for (int i = splitString.length - 1; i >= 0; i--) {
					if (secondDigit != "0") {
						continue;
					}
					secondDigit = lastDigitInString(splitString[i]);
					// System.out.println("Second Digit is " + secondDigit + "!");
					
					if (secondDigit != "0" && secondDigit.length() > 1) {
						// System.out.println("Entered the second digit conversion!");
						
						for (int j = 0; j < alphaDigits.length; j++) {
							if (alphaDigits[j] == secondDigit) {
								// System.out.println(alphaDigits[j] + " is equal to " + secondDigit);
								secondDigit = Integer.toString(j + 1);
							}
						}							
					}
				}
				
				if (secondDigit == "0") {
					System.out.println("Adding " + firstDigit + firstDigit + " to the calibration value.");
					return Integer.parseInt(firstDigit + firstDigit);
				}
						
				System.out.println("Adding " + firstDigit + secondDigit + " to the calibration value.");
				return Integer.parseInt(firstDigit + secondDigit);
			}
		} else {
			System.out.println("We didn't find the ints immediately");
			// The first element wasn't a numeric character so we have to parse the line for digits 
			// to see if they use words.
			String firstDigit = digitInString(splitString[0]);
			if (firstDigit != "0" && firstDigit.length() > 1) {
				for (int i = 0; i < alphaDigits.length; i++) {
					if (alphaDigits[i] == firstDigit) {
						// System.out.println(alphaDigits[i] + " is equal to " + firstDigit);
						firstDigit = Integer.toString(i + 1);
					}
				}							
			}
			
			// String finalElement = splitString[findHighestIndex(splitString)];
			String secondDigit = "0";
			
			// start from end of splitString and work backwards until first element. (Stop after processing the one before)
			for (int i = splitString.length - 1; i >= 0; i--) {
				if (secondDigit != "0") {
					continue;
				}
				secondDigit = lastDigitInString(splitString[i]);
				// System.out.println("Second Digit is " + secondDigit + "!");
				
				if (secondDigit != "0" && secondDigit.length() > 1) {
					// System.out.println("Entered the second digit conversion!");
					
					for (int j = 0; j < alphaDigits.length; j++) {
						if (alphaDigits[j] == secondDigit) {
							// System.out.println(alphaDigits[j] + " is equal to " + secondDigit);
							secondDigit = Integer.toString(j + 1);
						}
					}							
				}
			}
			
			if (secondDigit == "0") {
				System.out.println("Adding " + firstDigit + firstDigit + " to the calibration value.");
				return Integer.parseInt(firstDigit + firstDigit);
			}
					
			System.out.println("Adding " + firstDigit + secondDigit + " to the calibration value.");
			return Integer.parseInt(firstDigit + secondDigit);
		}
		
		// System.out.println("Adding 0 to the Calibration Value.");
		// return 0;
	}
	
	// method digitInString parses the segment and returns the first appearance of the alphabetical string.
	private static String digitInString(String segment) {
		Integer lowestIndex = -1;
		String earliestDigit = "0";
		for (int i = 0; i < alphaDigits.length; i++) {
			Integer indexOfDigit = segment.indexOf(alphaDigits[i]);

			if (indexOfDigit != -1 && lowestIndex == -1) {
				// System.out.println("Number " + alphaDigits[i] + " is found in " + segment + ".");
				lowestIndex = indexOfDigit;
				earliestDigit = alphaDigits[i];				
			} else if (indexOfDigit != -1 && indexOfDigit < lowestIndex) {
				lowestIndex = indexOfDigit;
				earliestDigit = alphaDigits[i];
				// System.out.println("Number " + alphaDigits[i] + " is NOT found in " + segment + ".");
			}
		}
		
		// System.out.println("Earliest digit after searching for alphas is: " + earliestDigit);
		
		// EDGE CASE: some strings like abc1abc1 split as abc1 / abc1 so I need to check for integers anyway...
		if (earliestDigit == "0") {
			System.out.println("Earliest digit is zero!");
			for (int i = 0; i < segment.length(); i++) {
				// System.out.println("Character at index " + i + " is: " + segment.charAt(i) + ".");
			
				if (Character.isDigit(segment.charAt(i))) {
					//System.out.println("The returned Character is " + segment.charAt(i) + ".");
					//System.out.println("The character at " + i + " is " + segment.charAt(i));
					return "" + segment.charAt(i);
				}
			}
		}
		
		//System.out.println("Earliest digit before returning from method is: " + earliestDigit);		
		return earliestDigit;
	}
	
	
	private static String lastDigitInString(String segment) {
		Integer latestIndex = -1;
		String latestDigit = "0";
		for (int i = 0; i < alphaDigits.length; i++) {
			Integer indexOfDigit = segment.indexOf(alphaDigits[i]);

			if (indexOfDigit != -1 && latestIndex == -1) {
				//System.out.println("LATEST Number " + alphaDigits[i] + " is found in " + segment + ".");
				latestIndex = indexOfDigit;
				latestDigit = alphaDigits[i];				
			} else if (indexOfDigit != -1 && indexOfDigit > latestIndex) {
				/* if (segment.indexOf(alphaDigits[i]) > getLatestIndex(latestDigit, latestIndex)) {
					
				} */
				latestIndex = indexOfDigit;
				latestDigit = alphaDigits[i];
				//System.out.println("LATEST Number " + alphaDigits[i] + " is NOT found in " + segment + ".");
			}
		}
		
		// EDGE CASE: some strings like abc1abc1 split as abc1 / abc1 so I need to check for the latest integers anyway...
		//System.out.println("THE LATEST digit is " + latestDigit + "!");
		for (int i = 0; i < segment.length(); i++) {
			//System.out.println("Character at index " + i + " is: " + segment.charAt(i) + ".");
			
			if (Character.isDigit(segment.charAt(i)) && i > latestIndex) {
				//System.out.println("The returned Character is " + segment.charAt(i) + ".");
				//System.out.println("The character at index " + i + " is " + segment.charAt(i));
				latestDigit = "" + segment.charAt(i);
			}
		}
		
		return latestDigit;
	}
	
	// method findHighestIndex calculates the 
	private static Integer findHighestIndex(String[] array) {
		return array.length - 1;
	}
}





















