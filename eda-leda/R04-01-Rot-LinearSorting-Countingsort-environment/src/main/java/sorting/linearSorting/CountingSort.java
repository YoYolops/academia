package sorting.linearSorting;

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
        // criando o array de respostas baseado no tamanho da entrada a ser ordenada
        int[] responseArray = new int[(rightIndex - leftIndex) + 1];
        // Encontrando o maior elemento no trecho pedido
        for(int i = leftIndex; i <= rightIndex; i++) if(array[i] > highestValue) highestValue = array[i];

        // Construindo o array de frequências com base no maior valor encontrado
        int[] frequencyArray = new int[highestValue+1];
        for(int i = leftIndex; i <= rightIndex; i++) frequencyArray[array[i]] += 1;
        
        // Realizando somatório para manter o algoritmo estável
        for(int i = 1; i < frequencyArray.length; i++) frequencyArray[i] += frequencyArray[i-1];
        
        // Calculando as respostas e inserindo no array resposta
        for(int i = rightIndex; i >= leftIndex; i--) {
            // Por que tem que ter o -1 na linha 33?
            responseArray[frequencyArray[array[i]]-1] = array[i];
            frequencyArray[array[i]] -= 1;
        }

        // Populando o array de entranda com os valores ordenados
        int responseArrayCounter = 0;
        for(int i = leftIndex; i <= rightIndex; i++) {
            array[i] = responseArray[responseArrayCounter];
            responseArrayCounter++;
        }
    }
}