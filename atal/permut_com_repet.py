
def permutacao(permutacao_atual, valores, posicao_atual=0):
    if posicao_atual == len(valores):
        # Adicionar condicoes extras pra printar, como ser somente items não repetidos
        # (permutacao sem repeticao)
        print(permutacao_atual)
    else:
        for i in valores:
            permutacao_atual[posicao_atual] = i
            permutacao(permutacao_atual, valores, posicao_atual+1)

permutacao([0 for _ in range(3)], [1,2,3])