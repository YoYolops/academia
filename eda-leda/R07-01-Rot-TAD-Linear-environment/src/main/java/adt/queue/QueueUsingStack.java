package adt.queue;

import adt.stack.Stack;
import adt.stack.StackImpl;
import adt.stack.StackOverflowException;
import adt.stack.StackUnderflowException;

public class QueueUsingStack<T> implements Queue<T> {

	private Stack<T> stack1;
	private Stack<T> stack2;

	public QueueUsingStack(int size) {
		stack1 = new StackImpl<T>(size);
		stack2 = new StackImpl<T>(size);
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
        try { this.stack1.push(element); }
        catch(StackOverflowException e) { throw new QueueOverflowException(); }
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
        T returnValue;
        while(!stack1.isEmpty()) {
            try {
                T removed = stack1.pop();
                stack2.push(removed);
            } 
            catch(StackUnderflowException e) { throw new QueueUnderflowException(); }
            catch(StackOverflowException e) { throw new QueueUnderflowException(); }
        }

        try {
            returnValue = stack2.pop();
        } catch(StackUnderflowException e) { throw new QueueUnderflowException(); }

        while(!stack2.isEmpty()) {
            try {
                T removed = stack2.pop();
                stack1.push(removed);
            }
            catch(StackUnderflowException e) { throw new QueueUnderflowException(); }
            catch(StackOverflowException e) { throw new QueueUnderflowException(); }
        }

        return returnValue;
	}

	@Override
	public T head() {
        try {
            if(stack1.isEmpty()) return null;

            T response;
            while(!stack1.isEmpty()) {
                T removed = stack1.pop();
                stack2.push(removed);
            }

            response = stack2.pop();
            stack1.push(response);

            while(!stack2.isEmpty()) {
                T removed = stack2.pop();
                stack1.push(removed);
            }

            return response;
        }
        catch(StackUnderflowException e) { return null; }
        catch(StackOverflowException e) { return null; }
	}

	@Override
	public boolean isEmpty() {
        return stack1.isEmpty();
	}

	@Override
	public boolean isFull() {
		return stack1.isFull();
	}

}
