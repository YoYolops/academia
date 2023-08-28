package sorting.simpleSorting;

import sorting.AbstractSorting;

/**
 * The selection sort algorithm chooses the smallest element from the array and
 * puts it in the first position. Then chooses the second smallest element and
 * stores it in the second position, and so on until the array is sorted.
 */
public class SelectionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		// TODO Auto-generated method stub
        if(leftIndex >= rightIndex) return;
        
        T current;
        T candidate = array[leftIndex+1];
		for(int i = leftIndex; i < rightIndex; i++) {
            current = array[i];

            for(int j = i+1; j <= rightIndex; j++) {
                candidate = array[j];
                if(candidate.compareTo(current) <= 0) {
                    array[j] = current;
                    current = candidate;
                }
            }

            array[i] = current;
        }
	}
}
