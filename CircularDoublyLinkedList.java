package UnoGame;


//Node class for the CDLL. 
class Node<E>{
	
	private E element;
	private Node<E> next, prev;
	
	public Node() {
		this.element = null;
		this.next = null;
		this.prev = null;
	}
	
	public Node(E e) {
		this.element = e;
		this.next = null;
		this.prev = null;
	}
	
	public Node(E e, Node<E> n, Node<E> p) {
		this.element = e;
		this.next = n;
		this.prev = p;
	}
	
	public void setNext(Node<E> n) {
		this.next = n;
	}
	
	public Node<E> getNext(){
		return next;
	}
	
	public void setPrev(Node<E> n){
		this.prev = n;
	}
	
	public Node<E> getPrev(){
		return prev;
	}
	
	public E getElement(){
		return element;
	}
}


//CDLL class
public class CircularDoublyLinkedList<E> {

	private Node<E> head, tail;
	private int size;
	
	public CircularDoublyLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	//Adds a node at the head of the list. 
	public void addFirst(E e) {
		Node<E> n = new Node<E>(e);
		
		if(head == null) {
			n.setNext(n);
			n.setPrev(n);		
			head = n;
			tail = n;
		}
		else {
			n.setPrev(tail);
			tail.setNext(n);
			head.setPrev(n);
			n.setNext(head);
			head = n;
		}
		size++;
	}
	
	//Adds a node at the end of the list.
	public void addLast(E e) {
		Node<E> n = new Node<E>(e);
		
		if(tail == null) {
			n.setNext(n);
			n.setPrev(n);		
			head = n;
			tail = n;
		}
		else {
			n.setPrev(tail);
			tail.setNext(n);
			head.setPrev(n);
			n.setNext(head);
			tail = n;
		}
		size++;
	}
	
	//Adds a node at a specified position.
	public void add(int pos, E e) {
		if(pos == 0)
			addFirst(e);
		
		else if(pos >= size)
			addLast(e);
		
		else {
			Node<E> node1 = head;					//This part is confusing
			for(int i = 1; i < pos; i++) {
				node1 = node1.getNext();			//Traverses the list to the node before insertion point. In comments, this node will be referred to 
			}										//	as node1. The node being inserted will be newNode. The node after will be node2.
			
			Node<E> node2 = node1.getNext();		//Creates a pointer to node 2
			
			Node<E> newNode = new Node<E>(e); 		//Creates newNode 
			
			node1.setNext(newNode);					//Sets node1's next pointer to newNode
			
			newNode.setPrev(node1); 				//Sets newNode's previous pointer to node1
			
			newNode.setNext(node2);					//Sets newNode's next pointer to node2
			
			node2.setPrev(newNode); 				//Sets node2's previous pointer to newNode 
		}
		size++;
	}
	
	//Removes and returns the first element. If the list is empty it returns an empty node. If the list has a single 
	//	element, it empties the list and returns the single node.
	public Node<E> removeFirst() {
		if(size == 0)
			return new Node<E>();
		else if(size == 1) {
			Node<E> temp = head;
			head = null;
			tail = null;
			size = 0;
			return temp;
		}
		else {
			Node<E> temp = head;
			head = head.getNext();
			head.setPrev(tail);
			tail.setNext(head);
			size--;
			return temp;
		}
	}
}
