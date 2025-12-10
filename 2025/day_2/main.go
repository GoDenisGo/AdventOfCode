package main

import (
	"fmt"
	"log"
	"strconv"
	"strings"
)

// TODO: Optimise code.
func main() {
	// IMPORTANT NOTICE: If you copied this program from VCS, please copy your input text into i as a str.
	i := ""
	si := strings.Split(i, ",")
	ssi := [][]string{} // ssi is a 2-D slice of [ min max ] values.

	for _, v := range si {
		t := strings.Split(v, "-")
		ssi = append(ssi, t)
	}

	fmt.Println(checkId(ssi))
}

func checkId(s [][]string) int64 {
	var counter int64 = 0
	for _, v := range s {
		min, err := strconv.ParseInt(v[0], 0, 64)
		if err != nil {
			log.Fatalf("Tried to convert %v (type %T) to int64.", v[0], v[0])
		}
		max, err := strconv.ParseInt(v[1], 0, 64)
		if err != nil {
			log.Fatalf("Tried to convert %v (type %T) to int64.", v[1], v[1])
		}
		for i := min; i <= max; i++ {
			valid := true
			n := strconv.FormatInt(i, 10) // Conv i to string.
			// Check IDs with an even length.
			valid = compareSubstrings(n)

			if valid {
				counter += i
				fmt.Println(i)
			}

			valid = true
		}
	}

	return counter
}

func compareSubstrings(n string) bool {
	valid := true
	// We init j at 1 because we don't want to test the IDs starting with a nil string.
	for j := 1; j < len(n); j++ {
		left := n[:j]
		right := n[j:]
		/*
			We check if the sequence of digits is a valid size.
			E.g. left = "123" and right = "4" then you know the final sequence is invalid.
			Thought experiment: We could also tally the unique digits in var left.
								If there is a digit not in left then maybe we can discard
								the current number early...
		*/
		if (len(right) % len(left)) == 0 {
			// We divide right into chunks of size len(left).
			r := []string{}
			for k := 0; k < len(right); k += len(left) {
				r = append(r, right[k:k+len(left)])
			}

			for k := range r {
				if left != r[k] {
					valid = false
				}
			}

			if valid {
				return valid
			}
		}
		valid = true
	}
	return false
}
