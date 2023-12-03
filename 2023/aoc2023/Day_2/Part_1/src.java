package aoc2023.Day_2.Part_1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// NOTE TO SELF: Documentation is available for all classes, become familiar with the usage and 
// the techniques given. Try to put more focus on the exact low-level steps needed to solve the task -
// there is usually a built-in or an API feature that provides them. Make use of a better plan.

// This solution was quite a bit cleaner than yesterday's program; I made use of a little abstraction
// to get the job done, such as using the Draw class to wrap some basic comparison. Agreed, it isn't a
// game changer to use a class, but without looking at Part_2 yet, I feel ready knowing that I can
// leverage the power of OOP concepts to deal with a more sophisticated problem, if I get the 
// chance to do so.

// There was an opportunity to use more abstraction - for example, the moment where I started using loops
// and control statements, but I felt the added complexity was not worth the time to solve a relatively
// trivial solution by planning deeper. There is also the fact that I haven't used Java since a year ago,
// so I am not completely familiar with the APIs, syntax and specification of the language.

// I also feel that I did not 'pay regards' to most of the problems that other students face when dealing
// with Java, specifically the static typing (which I prefer to have) and added boilerplate. I would
// assume this is because the problem is just-so significant enough that these small characteristics of 
// the language were not so problematic. I definitely notice that I keep having to parseInt, but that is
// not horrible. I would say this is a sign that I am able to perceive the overall structure and flow of
// programs better than I used to, which means I am less susceptible to confusion; in other words,
// spotting syntax errors and scanning the editor much better than I would have a year ago.

// This program reads a data.txt file and returns the sum of the successful ID's for a given round, if
// the given inputs pass the requirements.
// The program:
// - Opens the file, and reads every line of the file.
// - Each round contains multiple draws. We split each round into multiple draws and check them
//   individually.
// - If the entire round passes, we process the round number and add it to the total.
// - Once every line has been processed, we return the total and print it to the stdout.

// This entire program structure can be visualised as follows:
// index over file {
//     extract draws
//     loop over each draw
//     check if every draw for the round is valid
//     add round number for every valid round
// }
// finally, return the total

// There are some finer details:
// - Each round is split into its round number and all the draws made in each round.
// - Each draw is represented as an instance of the Draw class; this class provides a compareDraw
//   method which allows us to quickly verify whether the draws for each round meets the requirements.
// - We use Pattern Matching to obtain cube numbers, colour names and round names. These are not
//   necessarily implemented perfectly but they are kept reasonably clean.
// - The program prints each round, divided into individual Draws, to the stdout terminal. This is for
//   visualisation only and the entire round may not be printed - the program skips the round as soon
//   as an invalid Draw is found.
// - The Draw class is kept flexible by allowing the maximum-draw data to be an instance - using variable
//   assignment we can very safely compare the draws in each round to the maximum-draw, which I feel is
//   a very clean way of doing it.
// - Two file paths are provided, one as a means of testing and one is the directory for the actual
//   challenge data. I switch between the two paths using comments - this program is not intended for
//   usability!

// You will not find the data in the VCS, so here is a sample:
// "Game 1: 4 red; 5 green 6 blue; 2 red 50 green; 1 red 1 green; 7 red 8 green 5 blue"
// The data file contains exactly 100 variations of this sample structure, separated by newlines.
public class src {
	public static void main(String[] args) {
		try {
			System.out.println("Running Day 2, Part 1...");
			Integer answer = totalId();
			System.out.println("\nThe total of every successful ID is: '" + answer + "'.");
		} catch (Exception e) {
			System.out.println(e);
			return;
		}		
	}
	
	// method totalId returns the total sum of all ID's for valid games.
	public static int totalId() throws Exception {
        // Read the data document in whole (~11KB) and process each line sequentially.
        try {
        	Integer total = 0;
        	
        	// maxDraw represents the highest number of cubes that can be drawn from the bag, per colour.
        	// maxDraw is used to test the validity of each round.
        	Draw maxDraw = new Draw(12, 13, 14);
        	
        	Path doc = Paths.get("./aoc2023/Day_2/data/data.txt");
        	// Path doc = Paths.get("./aoc2023/Day_2/data/test.txt");
			List<String> lines = Files.readAllLines(doc, StandardCharsets.UTF_8);
			
			// for each line, count the draws
			for (int i = 0; i < lines.size(); i++) {
				System.out.println("\nGame " + (i + 1) + ":");
				String round = lines.get(i);
				
				// Splits the Game ID and the list of draws into two separate elements.
				String[] splitRound = round.split(":");
				
				boolean test = checkRound(maxDraw, splitRound[1]);
				
				if (test) {
					Pattern roundNumberPattern = Pattern.compile("(?<num>\\d+)");
					Matcher roundNumberMatcher = roundNumberPattern.matcher(splitRound[0]);
					Boolean successfulMatch = roundNumberMatcher.find();
					if (successfulMatch) {
						Integer roundNumber = Integer.parseInt(roundNumberMatcher.group("num"));
						total += roundNumber;
					} else {
						throw new Exception("Couldn't find round number!");
					}
				}
			}

			return total;
		} catch (IOException e) {
			System.out.println(e);
			return -1;
		}		
	}
	
	// method checkRound constructs multiple Draw instances for each round using compareDraw.
	// If any of the comparisons are false, the Round is immediately discarded.
	public static boolean checkRound(Draw maxDraw, String round) {		
		System.out.println("---------------------------------");
		
		// splits into " 4 blue, 7 red, 5 green"
		Pattern draw = Pattern.compile(";");
		
		// splits into [" 4 blue", " 7 red", " 5 green"]
		Pattern drawSegment = Pattern.compile(",");
		
		// splits into [" ", "4", " blue"]
		Pattern colour = Pattern.compile(" ");
		
		String[] drawPattern = draw.split(round);
		
		// loop over each Draw for the current round
		for (Integer i = 0; i < drawPattern.length; i++) {
			
			String[] drawSegmentPattern = drawSegment.split(drawPattern[i]);
			Integer blue = 0;
			Integer red = 0;
			Integer green = 0;

			// loop over each colour for the current Draw.
			for (Integer j = 0; j < drawSegmentPattern.length; j++) {
				String[] colourPattern = colour.split(drawSegmentPattern[j]);
				
				String colourName = colourPattern[2].trim();
				Integer colourTotal = Integer.parseInt(colourPattern[1]);
				
				// construct a Draw instance to provide the compareDraw method.
				if (colourName.equals("blue")) {
					blue = colourTotal;
				} else if (colourName.equals("red")) {
					red = colourTotal;
				} else {
					green = colourTotal;
				}				
			}
			
			Draw currentDraw = new Draw(red, green, blue);
			System.out.println(String.format("Red: %2s | Green: %2s | Blue: %2s", currentDraw.red, currentDraw.green, currentDraw.blue));
			
			if (!currentDraw.compareDraw(maxDraw)) {
				return false;
			}
		}
		
		return true;
	}
}
