package aoc2023.Day_3.Part_1;

import java.io.File;
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
	}
	
	// method partTotal returns the total value of every valid part number.
	public static int partTotal() throws IOException {
		Integer total = 0;
		
    	// Path doc = Paths.get("./aoc2023/Day_3/data/data.txt");
    	Path doc = Paths.get("./aoc2023/Day_3/data/test.txt");
    	
		List<String> lines = Files.readAllLines(doc, StandardCharsets.UTF_8);
		
		lines = padFile(lines);
		
		// index over file
		// i = 1 because we padded the file on all sides
		for (Integer i = 1; i < lines.size() - 1; i++) {
			String currentLine = lines.get(i);
			// System.out.println(currentLine);
			
			// lineIndex tracks the current character at each line.
			// lineIndex begins at one because the previous element is
			// the padding.
			Integer lineIndex = 1;
			
			// right now we are iterating over a single line.
			while (lineIndex < currentLine.length() - 3) {
				// lineSegment allows us to ignore previous matches of the same number.
				String lineSegment = currentLine.substring(lineIndex);
				
				// find a number, return its start and ending index.
				Integer[] currentLineSegment = findNumber(lineSegment);
				if (currentLineSegment[0] == -1) {
					lineIndex = currentLine.length();
					continue;
				}
				
				// System.out.println("Current number is: " + currentLineSegment[0]);
				
				// segments of the surrounding lines that are between the bounds of the number.
				String previousLineSemgent = getLineSegment(
						lines.get(i - 1), 
						lineIndex + currentLineSegment[1], 
						currentLineSegment[2]);
				String nextLineSegment = getLineSegment(
						lines.get(i + 1),
						lineIndex + currentLineSegment[1],
						currentLineSegment[2]);
				
				// determine if the number is adjacent to another string
				// TODO: Think about how the currentLine influences the scores ... 
				Boolean symbolFound = new Field(currentLine.substring(lineIndex + currentLineSegment[1] - 1, lineIndex + currentLineSegment[1] + currentLineSegment[2] + 1), previousLineSemgent, nextLineSegment).adjacentToSymbol();
					
				// TODO: think about how the elements are being totalled
				if (symbolFound) {
					total += currentLineSegment[0];
				}
				
				lineIndex += currentLineSegment[0].toString().length();
			}
			
			System.out.println("Total is: " + total + "\n");
		}
		
		return total;
	}
	
	// findNumber returns the starting index and ending index of the first number in the segment.
	// Will return -1 if no more numbers exist within the segment.
	public static Integer[] findNumber(String segment) {
		Integer number = -1;
		Integer startIndex = -1;
		Integer endIndex = -1;
		
		Pattern p = Pattern.compile("\\d+");
		Matcher pMatch = p.matcher(segment);
		
		// find the first number that gets matched:
		Boolean successfulMatch = pMatch.find();
		if (!successfulMatch) {
			return new Integer[]{number, startIndex, endIndex};
		}
		
		number = Integer.parseInt(pMatch.group(0));
		
		startIndex = segment.indexOf(number.toString());
		endIndex = number.toString().length();
		
		return new Integer[] {number, startIndex, endIndex};
	}
	
	// method getLineSegment returns a substring using the start index minus one,
	// and the end index plus one.
	public static String getLineSegment(String line, Integer startIndex, Integer endIndex) {
		return line.substring(startIndex - 1, startIndex + endIndex + 1);
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
