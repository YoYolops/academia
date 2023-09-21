package adt.queue;

public class QueueImpl<T> implements Queue<T> {

	private T[] array;
	private int tail;

	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[]) new Object[size];
		tail = -1;
	}

	@Override
	public T head() {
        if(this.isEmpty()) return null;
        return array[0];
	}

	@Override
	public boolean isEmpty() {
        return tail == -1;
	}

	@Override
	public boolean isFull() {
        return tail == (array.length - 1);
	}

	private void shiftLeft() {
        for(int i = 0; i < (array.length - 1); i++) {
            array[i] = array[i+1];
        }
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
        if(this.isFull()) throw new QueueOverflowException();
        array[++tail] = element;
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
        if(this.isEmpty()) throw new QueueUnderflowException();
        T removed = array[0];
        tail -= 1;
        this.shiftLeft();
        return removed;
	}

}
