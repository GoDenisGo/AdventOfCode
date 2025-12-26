package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
Advent of Code Day 4, Part 1.
The TXT file was padded to include empty elements in order to simplify the problem.
This would look like:

	........
	.@@.@@.@
	........

The program then adds additional "." to both sides of every line in order to complete the padding.
This would look like:

	..........
	..@@.@@.@.
	..........

Now we start our list indexing from (1,1) in and end at len() - 1 for both x and y co-ordinates.
The start and end indexes would look like (marked with Xs):

	..........
	.X@@.@@.X.
	..........
*/
func main() {
	c := 0
	m := []string{}
	file, err := os.Open("input-4.txt")
	if err != nil {
		panic(err)
	}
	defer file.Close()

	lines := getLines(file)
	for _, line := range lines {
		m = append(m, line)
	}

	// Pad the elements to simplify operations.
	for i := range m {
		m[i] = "." + m[i] + "."
	}

	/*
		Solve puzzle.
		Variable "i" is our current column, "j" is our current row.
	*/
	for i := 1; i < (len(m) - 1); i++ {
		s := m[i]
		next := m[i+1]
		for j := 1; j < (len(s) - 1); j++ {
			if string(s[j]) == "@" {
				prev := m[i-1]
				rc := 8
				rc -= top(prev, j)
				rc -= ml(s, j)
				rc -= mr(s, j)
				rc -= bottom(next, j)

				if rc > 4 {
					c++
				}
			}
		}
	}

	fmt.Println(c)
}

func getLines(f *os.File) []string {
	var lines []string

	scanner := bufio.NewScanner(f)
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}

	if err := scanner.Err(); err != nil {
		panic(err)
	}

	return lines
}

func top(p string, i int) int {
	pos := i - 1
	c := 0
	for range 3 {
		if string(p[pos]) == "@" {
			c++
		}
		pos++
	}
	return c
}

func ml(c string, i int) int {
	if string(c[i-1]) == "@" {
		return 1
	}
	return 0
}

func mr(c string, i int) int {
	if string(c[i+1]) == "@" {
		return 1
	}
	return 0
}

func bottom(n string, i int) int {
	pos := i - 1
	c := 0
	for range 3 {
		if string(n[pos]) == "@" {
			c++
		}
		pos++
	}
	return c
}
