package aoc2023.Day_2.Part_1;

// Class Draw represents a single draw of the cubes. Each round has multiple Draws.
// This class serves as a discrete representation for each draw, and it offers a 
// comparison to other Draws.

// Usage:
// - Define an instance, 'newMaxDraw' to store the maximum Draw parameters.
// - Define another instance, 'newDraw' to store the current Draw parameters.
// - Compare newDraw to newMaxDraw; if any of the members in newDraw are higher
//   than the members of newMaxDraw, then the entire round can be discarded.
// - If the newDraw is valid, move on to the next draw.
public class Draw {
	public Integer red;
	public Integer green;
	public Integer blue;
	
	public Draw(Integer red, Integer green, Integer blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	// method compareDraw compares each attribute to the maxDraw attributes.
	// Returns false if any attribute is larger than the maxDraw,
	// otherwise it returns true.
	public boolean compareDraw(Draw maxDraw) {
		if (this.red > maxDraw.red || this.green > maxDraw.green || this.blue > maxDraw.blue) {
			return false;
		}
		
		return true;
	}
}