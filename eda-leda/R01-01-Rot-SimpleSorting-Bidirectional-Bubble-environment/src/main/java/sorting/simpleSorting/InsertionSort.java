package sorting.simpleSorting;

import sorting.AbstractSorting;

/**
 * As the insertion sort algorithm iterates over the array, it makes the
 * assumption that the visited positions are already sorted in ascending order,
 * which means it only needs to find the right position for the current element
 * and insert it there.
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		// TODO Auto-generated method stub
        T current;
        for(int i = leftIndex+1; i <= rightIndex; i++) {
            current = array[i];

            int count = i-1;
            while(count >= leftIndex && current.compareTo(array[count]) <= 0) {
                array[count+1] = array[count];
                count--;
            }
            // Na ultima iteração, count-- decrementa o indice uma unidade aquém de leftIndex, uma posição inválida
            array[count+1] = current;
        }
	}
}
