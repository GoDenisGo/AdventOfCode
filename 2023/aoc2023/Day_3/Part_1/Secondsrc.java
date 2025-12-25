package aoc2023.Day_3.Part_1;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//index over list{
//    use Pattern to obtain the first number in the line
//    check the adjacent elements for a symbol
//    check the above line for a symbol within the required boundaries of the number
//    check the below line for a symbol within the required boundaries of the number
//    if an adjacent symbol was found, add it to the total
//    find the next number in the line and repeat the process
//}
//finally, return the total

// Class Secondsrc is a program for solving Advent of Code, 2023, Day 3, Part 1.
// The objective is to find the sum of all numbers in a file that are next to special tokens, even diagonally.
// 
// The approach used:
// Pad the lines with '.' symbols; this negates the problem of having to handle several edge cases.
// Index each line of the file, returning every number within the lines, and adding them to the total
// if they are adjacent to a valid token.
// Once every line has been fully processed, the program outputs the final result to a log file, which was
// not committed to the repository.
public class Secondsrc {
	public static void main(String[] args) {
		System.out.println("Attempting Day 3, Part 1...");
		try {
			File logs = new File("./aoc2023/Day_3/log/log.txt");
			PrintStream stream = new PrintStream(logs);
			System.setOut(stream);
			
			Integer total = partTotal();
			System.out.println("\nTotal is: '" + total + "'.");
		} catch (IOException e) {
			System.out.println(e);
		}
		
		// Letting the user know the program has terminated.		
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		System.out.println("Program finished.\nPlease check the log file for the result.");
	}
	
	// method partTotal returns the total value of every valid part number.
	public static int partTotal() throws IOException {
		Integer total = 0;
		
    	Path doc = Paths.get("./aoc2023/Day_3/data/data.txt");
    	// Path doc = Paths.get("./aoc2023/Day_3/data/test.txt");
    	
		List<String> lines = Files.readAllLines(doc, StandardCharsets.UTF_8);
		
		lines = padFile(lines);
		
		// index over file
		// i = 1 because we padded the file on all sides
		// We only need to subtract 1 because we begin at 1 already.
		for (Integer i = 1; i < lines.size() - 1; i++) {			
			total += addParts(lines.get(i), lines.get(i - 1), lines.get(i + 1));
			
			System.out.println("Total is: " + total + "\n");
		}
		
		return total;
	}
	
	// method addParts returns the total value of each valid number within the input line.
	public static int addParts(String currentLine, String previousLine, String nextLine) {
		Integer total = 0;
		Integer lineIndex = 1; 
		
		while (lineIndex < currentLine.length() - 2) {
			// find a number and its start index
			Integer[] numberBoundaries = findNumber(currentLine, lineIndex);
			if (numberBoundaries[0] == -1) {
				lineIndex = currentLine.length();
				continue;
			}
			String numberString = numberBoundaries[0].toString();

			String currentLineSegment = currentLine.charAt(
					lineIndex + numberBoundaries[1] - 1) + 
					numberString + 
					currentLine.charAt(lineIndex + numberBoundaries[1] + numberString.length());
			String previousLineSegment = getLineSegment(
					previousLine, 
					lineIndex + numberBoundaries[1], 
					lineIndex + numberBoundaries[1] + numberString.length());
			String nextLineSegment = getLineSegment(
					nextLine,
					lineIndex + numberBoundaries[1],
					lineIndex + numberBoundaries[1] + numberString.length());
			
			Boolean symbolFound = new Field(currentLineSegment, previousLineSegment, nextLineSegment).adjacentToSymbol();
					
			if (symbolFound) {
				total += numberBoundaries[0];
			}
			
			lineIndex += numberBoundaries[1] + numberString.length();
		}
		
		return total;
	}
	
	// findNumber returns the number and its starting index,
	// from the input line. Will return -1 if no more numbers 
	// exist within the segment. Searches return only
	// the numbers after the given index.
	public static Integer[] findNumber(String line, Integer index) {
		Integer number = -1;
		Integer startIndex = -1;
		String segment = line.substring(index);
		System.out.println("The segment is: '" + line + "'.");
		
		Pattern p = Pattern.compile("\\d+");
		Matcher pMatch = p.matcher(segment);
		
		// find the first number that gets matched:
		Boolean successfulMatch = pMatch.find();
		if (!successfulMatch) {
			return new Integer[]{number, startIndex};
		}
		
		number = Integer.parseInt(pMatch.group(0));
		
		startIndex = segment.indexOf(number.toString());
		
		return new Integer[] {number, startIndex};
	}
	
	// method getLineSegment returns a substring using the start index minus one,
	// and the end index plus one.
	public static String getLineSegment(String line, Integer startIndex, Integer endIndex) {
		return line.substring(startIndex - 1, endIndex + 1);
	}
	
	// method padFile surrounds the List data with invalid symbols
	// This removes the need to check the file for bounds,
	// which would otherwise be very sophisticated to determine.
	public static List<String> padFile(List<String> file) {
		Integer fileLength = file.size();
		String pad = "";
		
		for (Integer i = 0; i < fileLength + 2; i++) {
			pad = pad + ".";
		}
		
		for (Integer i = 0; i < fileLength; i++) {
			file.set(i, "." + file.get(i) + ".");
		}
		
		file.add(0, pad);
		file.add(pad);
		
		return file;
	}
}
