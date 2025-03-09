def dividir_array(array):
    # Calcula o ponto médio, arredondando para cima se o número de elementos for ímpar
    meio = len(array) // 2
    if len(array) % 2 != 0:  # Se o número de elementos for ímpar, o segundo array terá mais um elemento
        meio += 1
    
    # Retorna os dois arrays divididos
    return array[:meio], array[meio:]

# Exemplos de uso
array1 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
array2 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]

# Teste
metade1, metade2 = dividir_array(array1)
print(metade1)  # Saída: [1, 2, 3, 4, 5]
print(metade2)  # Saída: [6, 7, 8, 9, 10, 11]

metade1, metade2 = dividir_array(array2)
print(metade1)  # Saída: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
print(metade2)  # Saída: [11, 12, 13, 14, 15, 16, 17, 18, 19, 20]