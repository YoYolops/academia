package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
        if(this.isFull()) return;
        int initialProbe = 0;
        int hashedIndex = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, initialProbe);

        while(this.table[hashedIndex] != null && !this.table[hashedIndex].equals(new DELETED())) {
            if(this.table[hashedIndex].equals(element)) return;
            this.COLLISIONS += 1;
            hashedIndex = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, ++initialProbe);
        }

        this.table[hashedIndex] = element;
        this.elements += 1;
	}

	@Override
	public void remove(T element) {
        if(element == null) return;
        int initialProbe = 0;
        int hashedIndex = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, initialProbe);
        while(this.table[hashedIndex] != null) {
            if(this.table[hashedIndex].equals(element)) {
                this.table[hashedIndex] = new DELETED();
                this.elements -= 1;
                return;
            }
            hashedIndex = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, ++initialProbe);
        }
	}

	@Override
	public T search(T element) {
        if(element == null) return null;
        int initialProbe = 0;
        int hashedIndex = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, initialProbe);
        
        while(this.table[hashedIndex] != null) {
            if(this.table[hashedIndex].equals(element)) return (T) this.table[hashedIndex];
            hashedIndex = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, ++initialProbe);
        }

        return null;
	}

	@Override
	public int indexOf(T element) {
        if(element == null) return -1;
        int initialProbe = 0;
        int hashedIndex = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, initialProbe);
        
        while(this.table[hashedIndex] != null) {
            if(this.table[hashedIndex].equals(element)) return hashedIndex;
            hashedIndex = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, ++initialProbe);
        }
        return -1;
	}

}
