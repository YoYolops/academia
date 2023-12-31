package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;

    public DoubleLinkedListImpl() {
        this.last = new DoubleLinkedListNode<T>();
    }

	@Override
	public void insertFirst(T element) {
        DoubleLinkedListNode<T> currentFirstCandidate = this.last;
        while(!currentFirstCandidate.isNIL()) currentFirstCandidate = (DoubleLinkedListNode) currentFirstCandidate.getNext();
        
	}

	@Override
	public void removeFirst() {

	}

	@Override
	public void removeLast() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
 