
state = [1,3,2,0] # cada numero é a posicao da rainha naquela linha do tabuleiro

def is_valid(state, depht: int):
    for i in range(depht):
        # i é coordenada X | state[i] é Y
        for j in range(i+1, depht):
            # Checa se estao na mesma coluna
            if state[i] == state[j]:
                return False
            # Checa diagonais
            if abs(i-j) == abs(state[i] - state[j]):
                return False
    return True

def queens(n):
    current_state = [0 for i in range(n)]
    solutions = []

    def backtrack(current_state, depht):
        print(f"DEPHT: {depht}")
        if not is_valid(current_state, depht):
            return # Poda por posivcao invalida
        if depht == len(current_state):
            solutions.append(current_state.copy())
            return
        
        for i in range(n):# N é a quantidade de rainhas e o tamanho do tabuleiro (8x8, 4x4 etc)
            current_state[depht] = i
            backtrack(current_state.copy(), depht+1)
        
    backtrack(current_state=current_state, depht=0)
    print(solutions)
    print(f"Quantidade de solucoes: {len(solutions)}")


queens(12)