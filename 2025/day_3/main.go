package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

// Advent of Code Day 3, Part 1.
func main() {
	var joltage int64
	file, err := os.Open("input-3.txt")
	if err != nil {
		panic(err)
	}
	defer file.Close()

	lines := getLines(file)
	for _, line := range lines {
		j, err := strconv.ParseInt(findBatteries(line), 0, 0)
		if err != nil {
			log.Fatal("Tried to convert string to int64.")
		}
		joltage += j
	}

	fmt.Println("Final joltage: ", joltage)
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

func findBatteries(s string) string {
	var ja byte
	var jai int = 1000
	var jb byte
	var jbi int = 1000
	for i := range s {
		if s[i] > ja {
			jb = ja
			jbi = jai
			ja = s[i]
			jai = i
		} else if s[i] >= jb || i > jai && jbi < jai {
			jb = s[i]
			jbi = i
		}
	}
	if jai == (len(s) - 1) {
		tempj := ja
		ja = jb
		jb = tempj
	}

	return string([]byte{ja, jb})
}
