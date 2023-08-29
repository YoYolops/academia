package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala.
 *
 * Procure evitar desperdício de memória: AO INVÉS de alocar o array de contadores
 * com um tamanho arbitrariamente grande (por exemplo, com o maior valor de entrada possível),
 * aloque este array com o tamanho sendo o máximo inteiro presente no array a ser ordenado.
 *
 * Seu algoritmo deve assumir que o array de entrada nao possui numeros negativos,
 * ou seja, possui apenas numeros inteiros positivos e o zero.
 *
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
        if(leftIndex >= rightIndex) return;

        int highestValue = -1;
        int[] responseArray = new int[(rightIndex - leftIndex) + 1];
        for(int i = leftIndex; i <= rightIndex; i++) if(array[i] > highestValue) highestValue = array[i];
        int[] frequencyArray = new int[highestValue+1];
        for(int i = leftIndex; i <= rightIndex; i++) frequencyArray[array[i]] += 1;
        
        for(int i = 1; i < frequencyArray.length; i++) frequencyArray[i] += frequencyArray[i-1];
        
        for(int i = rightIndex; i >= leftIndex; i--) {
            // Por que tem que ter o -1 na linha 33?
            responseArray[frequencyArray[array[i]]-1] = array[i];
            frequencyArray[array[i]] -= 1;
        }
        System.out.println(Arrays.toString(responseArray));
    }
}