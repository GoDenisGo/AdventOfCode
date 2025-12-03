package main

/*
File CircularDoublyLinkedList.go implements a Doubly Circular Linked List.
Made for Advent of Code 2025, this is not a full implementation of the data structures and methods.
*/

/*
Type CircularDoublyLinkedList is used for simulating a virtual dial.
*/
type CircularDoublyLinkedList struct {
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
func (cl *CircularDoublyLinkedList) InsertBeginning(n *Node) {
	if cl.Head == nil {
		cl.Head = n
		cl.Tail = cl.Head
		n.Right = cl.Head
	} else {
		cl.Head.Left = n
		n.Right = cl.Head
		cl.Head = n
		cl.Tail.Right = cl.Head
		cl.Head.Left = cl.Tail
	}
	cl.Length++
}
