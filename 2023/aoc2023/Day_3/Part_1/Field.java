package aoc2023.Day_3.Part_1;

import java.util.regex.Pattern;

// class Field provides methods for finding if a symbol exists around a number.
// It is up to the implementor to process the string so the Field can produce
// accurate data.
public class Field {
	private String currentLine;
	private String previousLine;
	private String nextLine;
	
	public Field(String currentLine, String previousLine, String nextLine) {
		this.currentLine = currentLine;
		this.previousLine = previousLine;
		this.nextLine = nextLine;
	}
	
	// method adjacentToSymbol checks if a symbol was found for any of the fields of the class.
	public Boolean adjacentToSymbol() {
		String pattern = "([.]+[-*@+$=/&%]+)|([.]+\\d*[-*@+$=/&%]+)|([-*@+$=/&%]+[\\d.]*)";		
		return checkField(pattern, this.currentLine) || checkField(pattern, this.previousLine) || 
				checkField(pattern, this.nextLine);
	}
	
	// method checkField matches the pattern against the field and determines
	// if there is a match, or returns false if there wasn't a match.
	private Boolean checkField(String pattern, String field) {
		Pattern p = Pattern.compile(pattern);
		return p.matcher(field).find();
	}
}
