




def soma_subconjunto(numeros, alvo):
    escolhidos = [0 for _ in numeros]
    solucoes = [] # vai ser array de escolhidos

    def soma_escolhidos(escolhidos, numeros=numeros):
        soma = 0
        for i in range(len(numeros)):
            if escolhidos[i] == 1:
                soma += numeros[i]
        return soma


    def backtrack(valores, escolhidos, index_atual, alvo):
        print(f"NIVEL ATUAL {index_atual}")
        if soma_escolhidos(escolhidos) == alvo:
            solucoes.append(escolhidos.copy())
            print("PODA EM SUCESSO") # Poda, ja que continuar somando n ajuda
            return 

        if soma_escolhidos(escolhidos) > alvo:
            print("PODA EM FRACASSO - ALÉM DO ALVO")
            return # Poda
        
        if(index_atual == len(valores)):
            print("PODA EM FRACASSO - AQUÉM DO ALVO")
            return
        
        escolhidos[index_atual] = 0
        backtrack(valores, escolhidos.copy(), index_atual+1, alvo)
        escolhidos[index_atual] = 1
        backtrack(valores, escolhidos.copy(), index_atual+1, alvo)

    backtrack(numeros, escolhidos, 0, alvo)
    print("SOLUCOES:")
    print(solucoes)
        

#soma_subconjunto([1,2,5,6,8], 9)
soma_subconjunto([3,5,6,7], 15)