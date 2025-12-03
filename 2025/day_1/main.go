package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

// Package main represents Day 1 of AOC, 2025.
// File input-1.txt contains a list of dial instructions.
// The dial turns R or L by a certain amount based on the list.
// The dial only has numbers 0 - 99, going below 0 wraps back to 99 and vice-versa.
// We need to count the amount of time the dial hits 0.
//
// If you want to run this code yourself, download the input file for Day 1 Part 1 and rename it to input-1.txt.
func main() {
	fmt.Println("AOC 2025 - Day 1!")
	dial := 50
	count := 0

	file, err := os.Open("input-1.txt")
	if err != nil {
		panic(err)
	}
	defer file.Close()

	lines := getLines(file)
	for _, line := range lines {
		if string(line[0]) == "R" {
			// Turn the dial right.
			num, err := strconv.Atoi(line[1:])
			if err != nil {
				log.Fatal(err)
			}
			dial += num
		} else {
			// Turn the dial left.
			num, err := strconv.Atoi(line[1:])
			if err != nil {
				log.Fatal(err)
			}
			dial -= num
		}

		// Var "dial" could be outside the range [0,99] which isn't legal in this puzzle.
		dial = Over(dial)
		dial = Under(dial)

		if dial > 99 || dial < 0 {
			log.Fatalf("Dial value is %v. Dial should not exceed 0 <= dial <= 99 ", dial)
		}

		if dial == 0 {
			count += 1
		}
	}

	fmt.Println(count)
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

// Over subtracts 100 from n until 0 <= n <= 99.
func Over(n int) int {
	if n > 99 {
		n = Over(n - 100)
	}
	return n
}

// Under adds 100 to n until 0 <= n <= 99.
func Under(n int) int {
	if n < 0 {
		n = Under(n + 100)
	}
	return n
}
