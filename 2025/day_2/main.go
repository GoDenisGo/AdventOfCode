package main

import (
	"fmt"
	"log"
	"strconv"
	"strings"
)

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

		for i := min; i < max; i++ {
			n := strconv.FormatInt(i, 10)
			/*
				Optimisation: we can ignore odd-length numbers because they would never be valid.
				Optimisation: we only have to split the numbers in half and compare them.
			*/
			if (len(n) % 2) == 0 {
				m := len(n) / 2
				left := n[:m]
				right := n[m:]
				if left == right {
					counter += i
				}
			}
		}
	}

	return counter
}
