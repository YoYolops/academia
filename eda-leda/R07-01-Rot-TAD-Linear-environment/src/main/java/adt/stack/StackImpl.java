package adt.stack;

public class StackImpl<T> implements Stack<T> {

	private T[] array;
	private int top;

	@SuppressWarnings("unchecked")
	public StackImpl(int size) {
		array = (T[]) new Object[size];
		top = -1;
	}

	@Override
	public T top() {
        if(top == -1) return null;
        return array[top];
	}

	@Override
	public boolean isEmpty() {
        return top == -1;
	}

	@Override
	public boolean isFull() {
        return top == (array.length - 1);
	}

	@Override
	public void push(T element) throws StackOverflowException {
        if(element == null) return;
        if(this.isFull()) throw new StackOverflowException();
        this.array[++top] = element;
	}

	@Override
	public T pop() throws StackUnderflowException {
        if(top == -1) throw new StackUnderflowException();
        T removed = array[top];
        array[top] = null;
        top -= 1;
        return removed;
	}

}
