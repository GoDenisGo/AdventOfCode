package aoc2023.Day_2.Part_2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// index over file {
//     use regular expressions to get the largest number of cubes drawn for each colour
//     multiply the three numbers together to get the Power for each round.
//     repeat this for each run, add them together
// }
// finally, return the total power obtained from every round.

// Result: I passed this one on my second try. The initial attempt was because I was totalling the numbers for each of the colours across
// draws instead of looking for only the biggest number out of all the draws, per round. This resulted in an abnormally large result.
// Meanwhile, I seemed to have learnt from my experiences of yesterday when I jumped-head first into the code; this time I slowed it wayyy down.
// (Wayyy, wayyy down... hehe).
// Granted, today's problem was less tedious than Day 1, but I feel like my approach and the optimisations I came up with
// boosted my success in today's challenge, especially Part 2 where I fell back on a top-down approach. You can even witness
// a part of my planning through the use of comments just below the import ceremony. Although I did write down a note on paper just
// to visualise the problem - though it wasn't necessary.

// This time I didn't need to use a Draw instance. But I could have, and it would've worked fine, I know it.
// One thing I'm proud of is the optimisation in the method highestNumber - I managed to index through each round, 5 elements at a time.
// Spotting this was easy, and implementing it was a matter of a few keyboard presses, but knowing that my code is just a little
// more efficient provides a sense of accomplishment. Jump by 6 elements instead and that could've been a catastrophe, but I haven't tested
// it so I'll leave it up to the reader.

// Another aspect I am particularly proud of is the way I modularised my program with the totalPower method; since we are processing
// the data for each colour in the same way, I passed each colour as a parameter to the method and used it as part of the regular expressions.
// This both shrank the file size of my program (which I paid back in comments...) but it also made the main program body much more readable.

// I will try to carry this mind-set forward to the next challenges; I want to continue exploiting classes (which is Java's strong point) and
// repeat the top-down approach for planning my program structure. This way, I maintain a clear top-level view of the implementation, which is
// useful for knowing what task is next as a step toward completing the challenge.
public class src {
	public static void main(String[] args) {
		System.out.println("Attempting Day 2, Part 2...");
		try {
			Integer answer = totalPower();
			System.out.println("The total power of every game is: " + answer);
		} catch (IOException e) {
			System.out.println(e);
			return;
		}
	}
	
	public static int totalPower() throws IOException {
		Integer totalPower = 0;
		
		// read whole file
    	Path doc = Paths.get("./aoc2023/Day_2/data/data.txt");
    	// Path doc = Paths.get("./aoc2023/Day_2/data/test.txt");
    	
		List<String> lines = Files.readAllLines(doc, StandardCharsets.UTF_8);
		
		// index over file
		for (Integer i = 0; i < lines.size(); i++) {
			String currentLine = lines.get(i);
			Integer red = 0;
			Integer green = 0;
			Integer blue = 0;
			
			// use regular expressions to get the largest number of cubes drawn for each colour
			red = highestNumber(currentLine, "red");
			green = highestNumber(currentLine, "green");
			blue = highestNumber(currentLine, "blue");
			
			// get power level for each round
			totalPower += red * green * blue;		
		}
		
		return totalPower;
	}
	
	// method highestNumber searches the round for the highest value obtained by the 	
	public static int highestNumber(String round, String colour) {
		// we sanitise the round by removing the round number
		round = round.split(":")[1].trim();
		Integer largestNumber = 0;
		
		// numberAndColour gives us a Pattern, representing a string, of the number of cubes and 
		// the colour of those cubes.
		Pattern numberAndColour = Pattern.compile("(?<num>\\d+) (?<colour>" + colour + ")");
		Matcher numberAndColourMatcher = numberAndColour.matcher(round);
		
		// We can increment by 5, without the risk of missing data, because the smallest match is 
		// of the structure 'n red' where n is a single-digit number and red is the colour with
		// the shortest name.
		for (Integer i = 0; i < round.length(); i += 5) {
			Boolean successfulMatch = numberAndColourMatcher.find();
		
			if (successfulMatch) {
				Integer numberOfCubes = Integer.parseInt(numberAndColourMatcher.group("num"));
				if (numberOfCubes > largestNumber) {
					largestNumber = numberOfCubes;
				}
			}
		}
		
		return largestNumber;
	}
}
