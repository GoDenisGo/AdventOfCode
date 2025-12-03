package main

import "log"

/*
File DoublyCircularLinkedList.go implements a Doubly Circular Linked List.
Made for Advent of Code 2025, this is not a full implementation of the data structures and methods.
*/

/*
Type DoublyCircularLinkedList is used for simulating a virtual dial.
*/
type DoublyCircularLinkedList struct {
	Head   *Node
	Tail   *Node
	Length int
}

// Type Node represents its point on a virtual dial using Index as its value.
type Node struct {
	Right *Node
	Left  *Node
	Index int
}

// InsertBeginning places a Node at the start of the Circular Linked List and increments its length.
func (cl *DoublyCircularLinkedList) InsertBeginning(n *Node) {
	if cl.Head == nil {
		cl.Head = n
		cl.Tail = cl.Head
		n.Right = cl.Head
	} else {
		n.Right = cl.Head
		cl.Head = n
		cl.Tail.Right = cl.Head
	}
	cl.Length++
}

/*
Find searches for a Node whose Index is equal to i and returns it.
Crashes with log.Fatal if the Linked List is empty.
*/
func (cl *DoublyCircularLinkedList) Find(i int) *Node {
	if cl.Head == nil {
		log.Fatal("Linked list is empty!")
		return nil
	}

	if cl.Head.Index == i {
		return cl.Head
	} else {
		h := cl.Head
		for h.Right != cl.Head {
			h = h.Right
			if h.Index == i {
				return h
			}
		}
	}
}
