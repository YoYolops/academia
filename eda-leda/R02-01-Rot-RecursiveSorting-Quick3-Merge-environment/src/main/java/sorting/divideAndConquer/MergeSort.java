package sorting.divideAndConquer;

import java.util.ArrayList;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm. The algorithm
 * consists of recursively dividing the unsorted list in the middle, sorting
 * each sublist, and then merging them into one single sorted list. Notice that
 * if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
        // casos base, para para valores limites errados ou quando o length do array é muito pequeno
        if(rightIndex <= leftIndex) return;
        if(array.length <= 1) return;

        int pivot = (((rightIndex - leftIndex)/2)+leftIndex);
        // divide o array em dois, ordena as duas metades e faz o merge delas
        sort(array, leftIndex, pivot);
        sort(array, pivot+1, rightIndex);
        merge(array, new int[]{leftIndex, pivot}, new int[]{pivot+1, rightIndex});
	}

    public void merge(T[] array, int[] leftArrayLimits, int[] rightArrayLimits) {
        int currentLeftArrayIndex = leftArrayLimits[0]; //leftArrayLimits[0] indica o limite a esquerda do primeiro array, enquanto o indice 1 dá o limite à direita
        int currentRightArrayIndex = rightArrayLimits[0];
        ArrayList<T> tempArray = new ArrayList<T>();

        //Enquanto ainda houverem elementos em AMBOS arrays para serem mergeados
        while(currentLeftArrayIndex <= leftArrayLimits[1] && currentRightArrayIndex <= rightArrayLimits[1]) {
            // adiciono o menor deles ao array temporario e aumento o contador do array que teve seu elemento selecionado como menor
            if(array[currentLeftArrayIndex].compareTo(array[currentRightArrayIndex]) <= 0) {
                tempArray.add(array[currentLeftArrayIndex]);
                currentLeftArrayIndex++;
            } else {
                tempArray.add(array[currentRightArrayIndex]);
                currentRightArrayIndex++;
            }
        }

        // Após sair while acima, um dos subarrays pode ainda não ter tido todos os seus elementos adicionados, daí adicionamos os faltantes:
        while(currentLeftArrayIndex <= leftArrayLimits[1]) {
            tempArray.add(array[currentLeftArrayIndex]);
            currentLeftArrayIndex++;
        }

        while(currentRightArrayIndex <= rightArrayLimits[1]) {
            tempArray.add(array[currentRightArrayIndex]);
            currentRightArrayIndex++;
        }

        // Com o array temporario completo, itero sobre o array original substituindo seus elementos pelos ordenados;
        int tempCounter = 0;
        for(int i = leftArrayLimits[0]; i <= rightArrayLimits[1]; i++) {
            array[i] = tempArray.get(tempCounter);
            tempCounter++;
        }
    }
}
