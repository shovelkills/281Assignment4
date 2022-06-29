package nz.ac.auckland.se281.a4.ds;

import java.util.NoSuchElementException;
//*******************************
//YOU SHOUD MODIFY THE SPECIFIED 
//METHODS  OF THIS CLASS
//HELPER METHODS CAN BE ADDED
//REQUIRED LIBRARIES ARE ALREADY 
//IMPORTED -- DON'T ADD MORE
//THIS CLASS ALSO HAS TO BE MADE 
//GENERIC
//*******************************

/**
 * The Linked List Class Has only one head pointer to the start node (head)
 * Nodes are indexed starting from 0. The list goes from 0 to size-1.
 *
 * @author Partha Roop
 */
public class LinkedList<T> {
	// the head of the linked list
	private Node<T> head;

	/**
	 * Constructor for LinkedList
	 */
	public LinkedList() {
		head = null;
	}

	/**
	 * This method returns a reference to a node whose position is at pos TODO:
	 * Complete this method
	 * 
	 * @param pos an integer specifying the position of the node to be located
	 * @return Node the reference to the Node at position pos
	 * @throws InvalidPositionException if position is less than 0 or greater than
	 *                                  size-1
	 * @throws NoSuchElementException   if the element does not exist in the
	 *                                  LinkedList
	 */
	private Node locateNode(int pos) throws InvalidPositionException, NoSuchElementException {
		Node<T> tempNode = this.head;
		if ((pos < 0) || (pos > (size() - 1))) {
			throw new InvalidPositionException();
		} else {
			for (int i = 0; i < pos; i++) {
				tempNode = tempNode.getNext();
			}
			if (tempNode.getNext() == null) {
				throw new NoSuchElementException();
			} else {
				return tempNode.getNext();
			}
		}
	}

	/**
	 * This method adds a node with specified data as the start node of the list
	 * TODO: Complete this method
	 *
	 * @param element a parameter, which is the value of the node to be prepended
	 */
	public void prepend(T element) {
		Node n = new Node(element);
		if (this.head == null) {
			this.head = n;
		} else {
			n.setNext(this.head);
			this.head = n;
		}

	}

	/**
	 * This method adds a node with specified data as the end node of the list TODO:
	 * Complete this method
	 *
	 * @param element a parameter, which is the value of the node to be appended
	 */

	// Note this method has been refactored using the helper methods
	// I will do this as a small ACP exercise in class
	public void append(T element) {
		Node t = new Node(element);
		Node tempNode = this.head;
		if (tempNode == null) {
			this.head = t;
		} else {
			while (tempNode.getNext() != null) {
				tempNode = tempNode.getNext();
			}
			tempNode.setNext(t);
		}
	}

	/**
	 * This method gets the value of a node at a given position TODO: Complete this
	 * method
	 *
	 * @param pos an integer, which is the position
	 * @return the value at the position pos
	 * @throws InvalidPositionException if position is less than 0 or greater than
	 *                                  size-1
	 */
	public T get(int pos) throws InvalidPositionException {
		checkPosition(pos);
		Node<T> tempNode = this.getNodePosition(pos);
		return tempNode.getValue();
	}

	/**
	 * This method adds an node at a given position in the List TODO: Complete this
	 * method
	 * 
	 * @param pos     an integer, which is the position
	 * @param element the element to insert
	 * @throws InvalidPositionException if position is less than 0 or greater than
	 *                                  size-1
	 */
	public void insert(int pos, T element) throws InvalidPositionException {
		Node n = new Node(element);
		if ((pos < 0 || (pos > size()))) {
			throw new InvalidPositionException();
		}
		Node<T> posNode = this.getNodePosition(pos);
		Node<T> prevNode = this.getPreviousNode(pos);
		if (pos == 0) {
			prepend(element);
		} else if (pos == (size())) {
			append(element);
		} else {
			n.setNext(posNode);
			prevNode.setNext(n);
		}
	}

	/**
	 * This method removes an node at a given position TODO: Complete this method
	 *
	 * @param pos an integer, which is the position
	 */
	public void remove(int pos) throws InvalidPositionException {
		checkPosition(pos);
		Node<T> posNode = this.getNodePosition(pos);
		Node<T> prevNode = this.getPreviousNode(pos);
		if (pos == 0) {
			this.head = this.head.getNext();
		} else if (pos == size() - 1) {
			prevNode.setNext(null);
		} else {
			prevNode.setNext(posNode.getNext());
		}
	}

	/**
	 * This method returns the size of the Linked list TODO: Complete this method
	 *
	 * @return the size of the list
	 */
	public int size() {
		int size = 0;
		Node<T> tempNode = this.head;
		while (tempNode != null) {
			size++;
			tempNode = tempNode.getNext();
		}
		return size;
	}

	/**
	 * This method checks if the position is valid for the linked list
	 * 
	 * @param pos the position that we are checking
	 * @throws InvalidPositionException
	 */
	private void checkPosition(int pos) throws InvalidPositionException {
		if ((pos < 0) || (pos > (size() - 1))) {
			throw new InvalidPositionException();
		}
	}

	/**
	 * Finds the Node one before the node we want to find so we can manipulate it
	 * 
	 * @param pos the position of the node that is wanted
	 * @return gets the node one before the position of the one that is wanted
	 */
	private Node getPreviousNode(int pos) {
		Node<T> tempNode = this.head;
		for (int i = 0; i < pos - 1; i++) {
			tempNode = tempNode.getNext();
		}
		return tempNode;
	}

	/**
	 * Gets the node that is in the position that is input of the value
	 * 
	 * @param pos the position of the node we want to get
	 * @return gets the node at the position
	 */
	private Node getNodePosition(int pos) {
		Node<T> tempNode = this.head;
		for (int i = 0; i < pos; i++) {
			tempNode = tempNode.getNext();
		}
		return tempNode;

	}

	/**
	 * This method is used for printing the data in the list from head till the last
	 * node
	 *
	 */
	public void print() {
		Node<T> node = head;
		while (node != null) {
			System.out.println(node);
			node = node.getNext();
		}
	}
}