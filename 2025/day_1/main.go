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

	d := CircularDoublyLinkedList{Length: 100}
	for i := 99; i > -1; i-- {
		d.InsertBeginning(&Node{Index: i})
	}
	p := d.Head
	for range 50 {
		p = p.Right
	}
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
			r, err := strconv.Atoi(line[1:])
			if err != nil {
				log.Fatal(err)
			}
			p = Right(p, r)
		} else {
			// Turn the dial left.
			l, err := strconv.Atoi(line[1:])
			if err != nil {
				log.Fatal(err)
			}
			p = Left(p, l)
		}

		if p.Index == 0 {
			count++
		}
	}

	fmt.Printf("Number of times zero was pointed at: %d.\n", count)
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

func Right(p *Node, n int) *Node {
	for range n {
		p = p.Right
	}
	return p
}

func Left(p *Node, n int) *Node {
	for range n {
		p = p.Left
	}
	return p
}
