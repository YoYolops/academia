package adt.bst;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		this.root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public BSTNode<T> search(T element) {
        BSTNode<T> current = this.root;
        while(!current.isEmpty() && !current.getData().equals(element)) {
            if(current.getData().compareTo(element) > 0) current = (BSTNode<T>) current.getLeft();
            else current = (BSTNode<T>) current.getRight();
        }
        return current; 
	}

	@Override
	public void insert(T element) {
        BSTNode<T> nilLeft = new BSTNode<T>();
        BSTNode<T> nilRight = new BSTNode<T>();
        BSTNode<T> current = this.root;

        while(!current.isEmpty()) {
            if(current.getData().compareTo(element) > 0) current = (BSTNode<T>) current.getLeft();
            else if(current.getData().compareTo(element) < 0) current = (BSTNode<T>) current.getRight();
            else return;
        }

        nilLeft.setParent(current);
        nilRight.setParent(current);
        current.setData(element);
        current.setLeft(nilLeft);
        current.setRight(nilRight);
	}

	@Override
    public BSTNode<T> maximum() {
        return this.maximum(this.root);
    }

	private BSTNode<T> maximum(BSTNode<T> startingNode) {
        if(startingNode.isEmpty()) return startingNode;

        BSTNode<T> current = startingNode;
        while(!current.isEmpty()) current = (BSTNode<T>) current.getRight();
        return (BSTNode<T>) current.getParent();
	}

	@Override
    public BSTNode<T> minimum() {
        return this.minimum(this.root);
    }

	private BSTNode<T> minimum(BSTNode<T> startingNode) {
        if(startingNode.isEmpty()) return startingNode;

        BSTNode<T> current = startingNode;
        while(!current.isEmpty()) current = (BSTNode<T>) current.getLeft();
        return (BSTNode<T>) current.getParent();
	}

	@Override
	public BSTNode<T> sucessor(T element) {
        BSTNode<T> elementNode = this.search(element);
        if(elementNode.isEmpty()) return null;
        if(!elementNode.getRight().isEmpty()) return this.minimum((BSTNode<T>) elementNode.getRight());
        else {
            BSTNode<T> parentNode = (BSTNode<T>) elementNode.getParent();
            while(parentNode != null && !parentNode.isEmpty() && elementNode.getData().compareTo(parentNode.getData()) > 0) {
                elementNode = parentNode;
                parentNode = (BSTNode<T>) elementNode.getParent();
            }
            return parentNode;
        }
	}

	@Override
	public BSTNode<T> predecessor(T element) {
        BSTNode<T> elementNode = this.search(element);
        if(elementNode.isEmpty()) return null;
        if(!elementNode.getLeft().isEmpty()) return this.maximum((BSTNode<T>) elementNode.getLeft());

        BSTNode<T> parentNode = (BSTNode<T>) elementNode.getParent();
        while(parentNode != null && !parentNode.isEmpty() && elementNode.getData().compareTo(parentNode.getData()) < 0) {
            elementNode = parentNode;
            parentNode = (BSTNode<T>) elementNode.getParent();
        }
        return parentNode;
	}

	@Override
	public void remove(T element) {
        BSTNode<T> nodeToRemove = this.search(element);
        if(nodeToRemove.isEmpty()) return;
        if(nodeToRemove.isLeaf()) {
            nodeToRemove.setData(null);
            nodeToRemove.setLeft(null);
            nodeToRemove.setRight(null);
            return;
        }
        // Se o nodeToRemove possui APENAS UM filho
        if(nodeToRemove.getLeft().isEmpty() && !nodeToRemove.getRight().isEmpty()) {
            if(nodeToRemove.getParent().getData().compareTo(nodeToRemove.getData()) > 0) {
                nodeToRemove.getParent().setLeft(nodeToRemove.getRight());
            } else {
                nodeToRemove.getParent().setRight(nodeToRemove.getRight());
            }
        }
        else if(!nodeToRemove.getLeft().isEmpty() && nodeToRemove.getRight().isEmpty()) {
            if(nodeToRemove.getParent().getData().compareTo(nodeToRemove.getData()) > 0) {
                nodeToRemove.getParent().setLeft(nodeToRemove.getLeft());
            } else {
                nodeToRemove.getParent().setRight(nodeToRemove.getLeft());
            }
        }

        BSTNode<T> sucessor = this.sucessor(element);
        T sucessorData = sucessor.getData();
        this.remove(sucessor.getData());
        nodeToRemove.setData(sucessorData);
	}

	@Override
	public T[] preOrder() {
        ArrayList<T> response = this.preOrder(this.root);
        Comparable[] responseArray = new Comparable[response.size()];
        for(int i = 0; i < response.size(); i++) responseArray[i] = response.get(i);
        return (T[]) responseArray; 
	}

    public ArrayList<T> preOrder(BSTNode<T> node) {
        ArrayList<T> visits = new ArrayList<T>();
        if(node.isEmpty()) return visits;

        visits.add(node.getData());
        ArrayList<T> leftVisits = preOrder((BSTNode<T>) node.getLeft());
        ArrayList<T> rightVisits = preOrder((BSTNode<T>) node.getRight());

        for(T visit : leftVisits) visits.add(visit);
        for(T visit : rightVisits) visits.add(visit);
        return visits;
    }

	@Override
	public T[] order() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] postOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
