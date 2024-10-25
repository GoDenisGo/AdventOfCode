package aoc2023.Day_3.Part_2;

// Java import party:
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Src {
	public static void main(String[] args) {
		System.out.println("Attempting Day 3, Part 2...");
		try {
			File logs = new File("./aoc2023/Day_3/log/log.txt");
			PrintStream stream = new PrintStream(logs);
			System.setOut(stream);
			
			Integer total = totalRatio();
			System.out.println("\nTotal is: '" + total + "'.");
		} catch (IOException e) {
			System.out.println(e);
			return;
		}
	}
	
	// method totalRatio produces an object representation of the file and calculates
	// the total of every gear ratio summed together.
	public static int totalRatio() {
		Integer total = 0;
		
		return total;
	}
	
	// method processTokens puts the token indexes of every line into an Array of integers
	// and adds the array to the field of an object representation of the main file.
	public static Gear processTokens(Gear representation, File file) {
		return representation;
	}
	
	// method processNumbers populates and array of Integer arrays (each containing 
	// {number, startIndex, endIndex}) and adds the array of arrays to the field of an
	// object representation of the main file
	public static HashMap<Integer, Integer[][]> processNumbers(Gear representation, List<String> file) {
		HashMap<Integer, Integer[][]> numbersInFile = null;
		for (Integer i = 0; i < file.size() - 1; i++) {
			String currentLine = file.get(i);
			for (Integer j = 0; j < currentLine.length() - 1; j++) {
				// get numbers in one line
				// append those numbers to the list
			}
		}
		
		return numbersInFile;
	}
	
	public static Integer[][] numbersInLine(String line, Integer index) {
		Integer number = -1;
		Integer startIndex = -1;
		Integer endIndex = -1;
		String segment = line.substring(index);
		
		Pattern p = Pattern.compile("\\d+");
		Matcher pMatch = p.matcher(segment);
		
		// find the first number that gets matched:
		Boolean successfulMatch = pMatch.find();
		if (!successfulMatch) {
			return new Integer[][]{new Integer[] {number, startIndex, endIndex}};
		}
		
		number = Integer.parseInt(pMatch.group(0));
		
		startIndex = segment.indexOf(number.toString());
		endIndex = startIndex + number.toString().length() - 1;
		
		return new Integer[][]{new Integer[] {number, startIndex, endIndex}};
	}
}
