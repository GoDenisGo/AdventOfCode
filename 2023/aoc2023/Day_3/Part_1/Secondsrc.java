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

// NOTES: Day 3... you got me.
// It took me two days to solve the problem to Part 1. Now we are approaching Day 5 and I have yet
// to look at Part 2 of Day 3.
// Big lesson learned from this, and say it with me: 'PLAN. THINGS. OUT.' Okay, I did, but I didn't plan
// carefully. My first attempt, which is in another file, looks very similar to the solution in this
// file, but I didn't commit it in case I catch someone in the eyes with it; unnecessary variables
// littered throughout, no abstraction and even my mind-set, when working on the previous file, was not
// as professional as it should have been. I should have adjusted my pace before attempting the solution.
//    My evolution from this behaviour is to take more time to read the problem carefully, understand the
// restrictions, and examine my plan thoroughly. This problem felt largely overwhelming because I was unable
// to parse through the strings in a coherent manner.

// First, by introducing the Field class, I was able to immediately hide a trivial part of the challenge;
// checking if the number is adjacent to any asterisks and using this condition as a flag for adding numbers.
// The actual state within the class was not being used for more than 4 lines, maximum, so we know for sure
// that the use of a class was not required. But I still feel it was a valid mechanic to exploit because
// it makes the program easier to read; you can clearly look at the main loop and see where the check is
// being made.
//
// Second, by reading the problem requirements carefully, you can verify that your program is matching all
// the requirements. I made a mistake in my Pattern matching by not including all the possible tokens that
// the problem requires - I forgot a '#' symbol. I only found it toward the end of this session and it is
// completely possible that my previous attempts at solving this problem were closer than I assumed. This
// is a problem because you can have everything but one requirement correct, and without being careful,
// you can flip the whole project on its head - as had happened in the previous file.

// Thirdly, modularity is very important. I always make use of methods and wrappers when I can find a reason
// to do them, but without a good understanding of the scope of the project and a solid outline of how the
// program should be structured, it is very easy to write the solution in an 'irregular' way. In this final
// version of my code, I would say that more methods could be used. I chose not to add more abstraction
// because I felt like I had a good balance between repeatability and fine detail within the main loop so I
// could finally focus on reaching the desired output.

// Lastly, and this is somewhat of a repetition of what I said about planning earlier, I find it is important
// to determine how low-level my thinking should be. Java is a mature eco-system that provides many APIs, but
// there are still some minor sub-problems that do need to be taken care of. In the case of parsing the lines,
// I wrote a separate detailed plan in my notebook to conceptualise how I would attempt to find the numbers,
// index the adjacent lines from the file and repeat the process for the other numbers in succession.
// This plan came in very late, which meant I did not have an explicit focus for parsing the lines when
// working on the previous file. This meant I was immediately caught off-guard with Index-Out-Of-Range errors
// and parsing the same numbers multiple times, wasting valuable processing time (the data file is only a few
// KB in size) which could have an economic impact in an industrial setting.

// Introspection: I can feel that I have tons to learn, but I have also shown how I can persevere and reach
// a successful outcome - if I approach the problem with a critical mind-set. Being 'critical' in this 
// instance means taking extra time to assess the problem, being rigorous through my planning and taking 
// advantage of the proper engineering techniques in order to solve the problem quicker and more efficiently.
// Finally getting to solve this challenge (of which I still have another part to go) felt gratifying and
// gave me a lot of powerful skills to think about; it made my personal feelings toward the problem seem
// insignificant and immature. I would say I am fully confident I can approach harder problems if I continue
// building upon what I have learned today.

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
