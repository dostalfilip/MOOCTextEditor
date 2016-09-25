package textgen;

import java.util.AbstractList;



/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	
	LLNode<E> headCore = new LLNode<E>();
	LLNode<E> tailCore = new LLNode<E>();
	int size;
	LLNode<E> head;
	LLNode<E> tail;

	public int getSize() {
		return size;
	}

	private void setSize(int size) {
		this.size = size;
	}

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		headCore.setNext(tailCore);
		tailCore.setPrev(headCore);	
		headCore.setPrev(null);
		tailCore.setNext(null);
		
		setSize(0);
	}
	
	
	private void headTailCheck(){
		this.head = headCore.getNext();
		this.tail = tailCore.getPrev();
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		
		
		if(element == null ){
			throw new NullPointerException();
		}
		
		
		LLNode llnode = new LLNode(element);

		
		llnode.setNext(tailCore); //head
		llnode.setPrev(tailCore.getPrev());
		
		llnode.getPrev().setNext(llnode);
		tailCore.setPrev(llnode);
		
		
		
		headTailCheck();
		setSize(getSize() + 1); 
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		LLNode<E>  actual;
		actual = tailCore.getPrev();
		//TODO
			if((getSize() <= 0 || index < 0 || index + 1 > size())){
				throw new IndexOutOfBoundsException();
			}
		
		for(int i = 0; i< getSize() - index -1; i++){
			actual = actual.getPrev();
		}

		return  (E) actual.getData();

		
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		LLNode<E>  actual = new LLNode<E>();
		actual = tailCore;
		//TODO
			if((getSize() < 0 || index < 0 || index+1 > size())){
				throw new IndexOutOfBoundsException();
			}
			
			if(element == null ){
				throw new NullPointerException();
			}
		
		for(int i = 0; i< getSize() - index; i++){
			actual = actual.getPrev();
		}
		LLNode<E>  newNode = new LLNode<E>(element);
		newNode.setNext(actual); //head
		newNode.setPrev(actual.getPrev());
		
		actual.getPrev().setNext(newNode);
		actual.setPrev(newNode);
		
		
		

		headTailCheck();
		setSize(getSize() + 1); 
	}
	/** Return the size of the list */
	public int size() 
	{

		return getSize();
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		LLNode<E> llnode = new LLNode<E>();
		//TODO
		llnode = tailCore;
	/// Ošetøení vstupù
		if((getSize() <= 0 || index < 0 || index + 1 > size())){
			throw new IndexOutOfBoundsException();
		}
		
		
		
		
	/// Set part
		for(int i = 0; i < getSize() - index; i++){
			llnode = llnode.getPrev();
		}
	/// Remove part	
		
		//Prohozeni odkazu z predchozího na dalsi
		llnode.getNext().setPrev(llnode.getPrev());
		llnode.getPrev().setNext(llnode.getNext());
		
		// vymazání vazby prvku
		llnode.setNext(null); 
		llnode.setPrev(null);
		
		setSize(getSize() - 1); 
		
		headTailCheck();
		return  (E) llnode.getData();
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		LLNode llnode = new LLNode<E>();
		//TODO
		llnode = tailCore.getPrev();
	/// Ošetøení vstupù
		if(getSize() <= 0 || index < 0 || index + 1 > size()){
			throw new IndexOutOfBoundsException();
		}
		
		if(element == null ){
			throw new NullPointerException();
		}
		
	/// Set part
		for(int i = 0; i< getSize() - index; i++){
			llnode = llnode.getPrev();
		}
		llnode.setData(element);
		
		
		return (E) llnode.getData();
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.setData(e);
		this.setPrev(null);
		this.setNext(null);
	}
	
	public LLNode() {
		this.setData(null);
		this.setPrev(null);
		this.setNext(null);
	}

	///
	///Setters and getters
	///
	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public LLNode<E> getPrev() {
		return prev;
	}

	public void setPrev(LLNode<E> prev) {
		this.prev = prev;
	}

	public LLNode<E> getNext() {
		return next;
	}

	public void setNext(LLNode<E> next) {
		this.next = next;
	}

}
