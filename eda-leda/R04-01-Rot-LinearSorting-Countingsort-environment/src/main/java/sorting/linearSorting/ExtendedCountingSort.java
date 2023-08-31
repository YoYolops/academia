package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos:
 * - Alocar o tamanho minimo possivel para o array de contadores (C)
 * - Ser capaz de ordenar arrays contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
        if(leftIndex >= rightIndex) return;

        int lowestValue = Integer.MAX_VALUE;
        int highestValue = Integer.MIN_VALUE;
        for(int i = leftIndex; i <= rightIndex; i++) {
            if(array[i] > highestValue) highestValue = array[i];
            if(array[i] < lowestValue) lowestValue = array[i];
        }

        int[] frequencyArray = new int[(highestValue-lowestValue)+1];
        for(int i = leftIndex; i <= rightIndex; i++) frequencyArray[0-(lowestValue - array[i])] += 1;
        for(int i = 1; i < frequencyArray.length; i++) frequencyArray[i] += frequencyArray[i-1];
        
	}

}
