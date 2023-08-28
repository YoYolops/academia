package sorting.divideAndConquer;

import sorting.AbstractSorting;
import util.Util;

/**
 * Quicksort is based on the divide-and-conquer paradigm. The algorithm chooses
 * a pivot element and rearranges the elements of the interval in such a way
 * that all elements lesser than the pivot go to the left part of the array and
 * all elements greater than the pivot, go to the right part of the array. Then
 * it recursively sorts the left and the right parts. Notice that if the list
 * has length == 1, it is already sorted.
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
        if(array.length <= 0 || leftIndex >= rightIndex) return;

        int pivotResultantPosition = partition(array, leftIndex, rightIndex);
        sort(array, leftIndex, pivotResultantPosition-1);
        sort(array, pivotResultantPosition+1, rightIndex);
	}

    public int partition(T[] array, int leftIndex, int rightIndex) {
        int lowerCounter = 0;
        int pivotIndex = rightIndex;

        // É preciso que a condição seja "<=" e não "<", caso contrário, um array de valores iguais jamais alteraria o lowerCounter,
        // resultando em recursão infinita
        for(int i = 0; i < rightIndex; i++) {
            if(array[i].compareTo(array[pivotIndex]) <= 0) {
                Util.swap(array, lowerCounter, i);
                lowerCounter++;
            }
        }

        Util.swap(array, pivotIndex, lowerCounter);
        return lowerCounter;
    }
}
