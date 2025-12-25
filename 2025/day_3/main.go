package main

import (
	"bufio"
	"bytes"
	"fmt"
	"log"
	"os"
	"strconv"
)

// Advent of Code Day 3, Part 2.
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
	p := 0
	e := 11
	b := []byte{}
	for range 12 {
		w := s[p : len(s)-e]
		j, ji := chooseBiggest(w)
		b = append(b, j)
		p += (ji + 1)
		e--
	}

	return string(b)

}

func chooseBiggest(s string) (byte, int) {
	var j byte
	var ji int
	for i := range s {
		if j < s[i] {
			j = s[i]
			ji = i
		}

		if bytes.Equal([]byte{j}, []byte("9")) {
			return j, ji
		}
	}

	return j, ji
}
