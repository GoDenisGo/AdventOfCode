package aoc2023.Day_1.Part_1;

// begin ceremony:
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

// Solving the Advent of Code Day 1, Challenge No. 1. 
// Expected test output for Challenge No. 1: 105
// Testing Passed.
// Challenge Solved.

// NOTE TO SELF: Concurrency would not be an effective solution here.
// Certainly, the file can be read in parts, but the entire contents will have to be accessed.
// As such, introducing non-determinism does not produce the expected performance benefit,
// other than minimising a single large thread into many smaller threads which may complete in any order.
// The fact that the operations on the data are the same between all the threads means that the additional
// complexity is not worth the effort for processing a single file.
public class src {
	public static void main(String[] args) {
		System.out.println("Running Advent of Code Day 1, Challenge No. 1...");
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
        
        // Read the calibration document in whole (~23KB) and process each line sequentially.
        try {
        	Integer calibrationTotal = 0; 
        	Path doc = Paths.get("./aoc2023/Day_1/calibration/calibration.txt");
        	// Path doc = Paths.get("./aoc2023/Day_1/calibration/test.txt");
			List<String> lines = Files.readAllLines(doc, StandardCharsets.UTF_8);
			
			for (int i = 0; i < lines.size(); i++) {
				calibrationTotal += getCalibrationData(lines.get(i));
			}
			
			System.out.println("Total calibration value: " + calibrationTotal);
		} catch (IOException e) {
			System.out.println(e);
			return;
		}
	}
	
	// method getCalibrationData returns the Calibration value for each line of the whole Calibration Document.
	// Each return value will consist of two digits between 11 and 99.
	// A 'Digit' refers to any numeric representation of an integer, such as '6' and '7'.
	// The return value is formed by taking the first and last digit within the line.
	// If only a single digit is found from the string, then the same digit is reused to form a double-digit number.
	private static int getCalibrationData(String line) {
		String currentCalibration = "";
		
		for (int i = 0; i < line.length(); i++) {
			if (Character.isDigit(line.charAt(i))) {
				currentCalibration += line.charAt(i);
			}
		}
		
		if (currentCalibration.length() > 2) {
			// returns the first and last characters of the value
			String finalValue = "";
			finalValue = finalValue + currentCalibration.charAt(0) + currentCalibration.charAt(currentCalibration.length() - 1);
			return Integer.parseInt(finalValue, 10);
		} else if (currentCalibration.length() < 2) {
			return Integer.parseInt(currentCalibration + currentCalibration);
		}
			
		return Integer.parseInt(currentCalibration, 10);
	}
}