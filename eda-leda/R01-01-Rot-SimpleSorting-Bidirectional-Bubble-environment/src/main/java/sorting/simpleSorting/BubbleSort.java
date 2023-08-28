package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * The bubble sort algorithm iterates over the array multiple times, pushing big
 * elements to the right by swapping adjacent elements, until the array is
 * sorted.
 */
public class BubbleSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Not Implemented yet!");
		for(int j = 0; j < array.length; j++) {
			for(int i = 0; i < array.length-j; i++) {
				if(i+1 < array.length && array[i].compareTo(array[i+1]) > 0) {
					Util.swap(array, i, i+1);
				}
			}
		}
	}
}
