package adt.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o menor sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 2 < 3),
 * essa heap deixa os elementos menores no topo. Essa comparação não é feita
 * diretamente com os elementos armazenados, mas sim usando um comparator. 
 * Dessa forma, dependendo do comparator, a heap pode funcionar como uma max-heap 
 * ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é
	 * mudar apenas o comparator e mandar reordenar a heap usando esse
	 * comparator. Assim os metodos da heap não precisam saber se vai funcionar
	 * como max-heap ou min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma
	 * min-heap. OU seja, voce deve considerar que a heap usa o comparator
	 * interno e se o comparator responde compare(x,y) < 0 entao o x eh menor
	 * e sobe na heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento
	 * indexado pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento
	 * indexado pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		ArrayList<T> resp = new ArrayList<T>();
		for (int i = 0; i <= this.index; i++) {
			resp.add(this.heap[i]);
		}
		return (T[])resp.toArray(new Comparable[0]);
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode
	 * ser a raiz da heap ou de uma sub-heap. O heapify deve usar o comparator
	 * para subir os elementos na heap.
	 */
	private void heapify(int position) {
        int iLeft = left(position);
        int iRight = right(position);

        if(iLeft > index || iRight > index || position > this.index || heap[position] == null || (heap[iLeft] == null && heap[iRight] == null)) return;

        int iLargest = -1;
        if(iLeft <= this.index && comparator.compare(heap[iLeft], heap[position]) > 0) {
            iLargest = iLeft;
        } else {
            iLargest = position;
        }

        if(iRight <= this.index && comparator.compare(heap[iRight], heap[iLargest]) > 0) {
            iLargest = iRight;
        }

        if(iLargest != position) {
            T iLargestData = heap[iLargest];
            heap[iLargest] = heap[position];
            heap[position] = iLargestData;
            heapify(iLargest);
        }
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
        int newElementIndex = -1;
        for(int i = 0; i < this.heap.length; i++) {
            if(heap[i] == null) {
                heap[i] = element;
                newElementIndex = i;
                this.index++;
                break;
            } 
        }

        while(this.index > 0 && comparator.compare(heap[newElementIndex], heap[this.parent(newElementIndex)]) > 0) {
            T newElementParent = heap[this.parent(newElementIndex)];
            heap[this.parent(newElementIndex)] = element;
            heap[newElementIndex] = newElementParent;
            newElementIndex = this.parent(newElementIndex);
        }
	}

	@Override
	public void buildHeap(T[] array) {
        this.heap = array;
        this.index = array.length - 1;
        for(int i = (int) Math.floor((array.length/2)); i >= 0; i--) {
            heapify(i);
        }
	}

	@Override
	public T extractRootElement() {
        if(this.index < 0) return null;

        T lastElement = null;
        int lastElementIndex = 0;
        int counter = -1;
        for(T element : this.heap) {
            counter++;
            if(element != null) {
                lastElement = element;
                lastElementIndex = counter;
            }
        }
        
        T rootElement = this.heap[0];
        this.heap[0] = lastElement;
        this.heap[lastElementIndex] = null;
        this.index--;
        heapify(0);
        return rootElement;
	}

	@Override
	public T rootElement() {
        return this.heap[0];
	}

	@Override
	public T[] heapsort(T[] array) {
        T[] currentHeapDataClone = this.heap;
        buildHeap(array);
        ArrayList<T> sortedHeap = new ArrayList<T>();
        T extractedRoot = this.extractRootElement();
        while (extractedRoot != null) {
            sortedHeap.add(extractedRoot);
            extractedRoot = this.extractRootElement();
        }
        this.heap = currentHeapDataClone;
        return (T[]) sortedHeap.toArray(new Comparable[array.length]);
	}

	@Override
	public int size() {
        int counter = 0;
        for(T element : heap) {
            if(element == null) counter += 0;
            else counter += 1;
        }
        return counter;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}

}
