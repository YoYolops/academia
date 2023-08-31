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
        int[] responseArray = new int[(rightIndex - leftIndex) + 1];

        int lowestValue = Integer.MAX_VALUE;
        int highestValue = Integer.MIN_VALUE;
        for(int i = leftIndex; i <= rightIndex; i++) {
            if(array[i] > highestValue) highestValue = array[i];
            if(array[i] < lowestValue) lowestValue = array[i];
        }

        // Unica alteração necessária com relacao ao counting sort padrao é que o acesso ao frequency array através de indices
        // armazenados em array[i] deve ser feito pela operação: frequencyArray[ 0-(lowestValue - array[i]) ] pois já
        // que array[i] possui elementos negativos, acessar frequencyArray[array[i]] poderia resultar em algo como frequencyArray[-2]
        int[] frequencyArray = new int[(highestValue-lowestValue)+1];
        for(int i = leftIndex; i <= rightIndex; i++) frequencyArray[0-(lowestValue - array[i])] += 1;
        for(int i = 1; i < frequencyArray.length; i++) frequencyArray[i] += frequencyArray[i-1];
        for(int i = rightIndex; i >= leftIndex; i--) {
            responseArray[frequencyArray[0-(lowestValue - array[i])]-1] = array[i];
            frequencyArray[0-(lowestValue - array[i])] -= 1;
        }

        int responseArrayCounter = 0;
        for(int i = leftIndex; i <= rightIndex; i++) {
            array[i] = responseArray[responseArrayCounter];
            responseArrayCounter++;
        }
	}

}
