package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
        return this.head.isNIL();
	}

	@Override
	public int size() {
        SingleLinkedListNode<T> current = this.head;
        int counter = 0;
        while(current.getData() != null) {
            current = current.getNext();
            counter++;
        }
        return counter;
	}

	@Override
	public T search(T element) {
        SingleLinkedListNode<T> current = this.head;
        while(!current.getData().equals(element)) {
            current = current.getNext();
            if(current.getNext().isNIL()) return null;
        }
        return current.getData();
	}

	@Override
	public void insert(T element) {
        SingleLinkedListNode<T> nilNode = new SingleLinkedListNode<T>();
        SingleLinkedListNode<T> currentTailNode = this.head;

        while(!currentTailNode.isNIL()) currentTailNode = currentTailNode.getNext();
        currentTailNode.setData(element);
        currentTailNode.setNext(nilNode);
	}

	@Override
	public void remove(T element) {
        if(this.isEmpty()) return;
        if(this.head.getData().equals(element)) this.head = this.head.getNext();

        SingleLinkedListNode<T> currentNode = this.head;
        while(!currentNode.getNext().isNIL()) {
            if(currentNode.getNext().getData().equals(element)) {
                currentNode.setNext(currentNode.getNext().getNext());
                break;
            }
            currentNode = currentNode.getNext();
        }
	}

	@Override
	public T[] toArray() {
        SingleLinkedListNode<T> currentNode = this.head;
        Object[] responseArray = new Object[this.size()];
        int counter = 0;

        while(!currentNode.isNIL()) {
            responseArray[counter] = currentNode.getData();
            currentNode = currentNode.getNext();
            counter++;
        }
        return (T[]) responseArray;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
