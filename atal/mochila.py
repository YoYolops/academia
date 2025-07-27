
_pesos = [15,5,10,5]
_valores = [20,30,50,10]
mochila_inicial = [0,0,0,0]

def peso(mochila, pesos=_pesos):
    peso_total = 0
    for i in range(len(mochila)):
        if mochila[i] == 1:
            peso_total += pesos[i]

    return peso_total

def valor(mochila, valores=_valores):
    valor_total = 0
    for i in range(len(mochila)):
        if mochila[i] == 1:
            valor_total += valores[i]

    return valor_total

MELHOR_SOLUCAO = []
MELHOR_VALOR = 0
PESO_MAXIMO = 16
def backtrack(mochila_atual, item_atual):
    print(f"BACKTRACK LEVEL: {item_atual}")
    global MELHOR_VALOR, MELHOR_SOLUCAO, PESO_MAXIMO

    if peso(mochila_atual) > PESO_MAXIMO:
        print(f"PODA EM {mochila_atual} - PESADA")
        return #PODA
    
    if item_atual == len(mochila_atual):
        if valor(mochila_atual) > MELHOR_VALOR:
            MELHOR_VALOR = valor(mochila_atual)
            MELHOR_SOLUCAO = mochila_atual.copy()
        return
    
    mochila_atual[item_atual] = 0
    backtrack(mochila_atual.copy(), item_atual+1)
    mochila_atual[item_atual] = 1
    backtrack(mochila_atual.copy(), item_atual+1)

backtrack(mochila_inicial, 0)
print(f"Melhor Solucao: {MELHOR_SOLUCAO}")
print(f"Melhor Valor:   {MELHOR_VALOR}")
    

