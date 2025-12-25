package main

import (
	"bufio"
	"fmt"
	"os"
)

// Advent of Code Day 4, Part 1.
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
				rc -= tl(prev, j)
				rc -= tm(prev, j)
				rc -= tr(prev, j)
				rc -= ml(s, j)
				rc -= mr(s, j)
				rc -= bl(next, j)
				rc -= bm(next, j)
				rc -= br(next, j)

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

func tl(p string, i int) int {
	if string(p[i-1]) == "@" {
		return 1
	}
	return 0
}

func tm(p string, i int) int {
	if string(p[i]) == "@" {
		return 1
	}
	return 0
}

func tr(p string, i int) int {
	if string(p[i+1]) == "@" {
		return 1
	}
	return 0
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

func bl(n string, i int) int {
	if string(n[i-1]) == "@" {
		return 1
	}
	return 0
}

func bm(n string, i int) int {
	if string(n[i]) == "@" {
		return 1
	}
	return 0
}

func br(n string, i int) int {
	if string(n[i+1]) == "@" {
		return 1
	}
	return 0
}
