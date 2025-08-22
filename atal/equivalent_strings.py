# Strings que não tiverem quantidade de caracteres em potencias de 2 nunca serão equivalentes
def is_equivalente(str1, str2):
    if str1 == str2: return True
    if len(str1) == 1 and len(str2) == 1: return False
    if len(str1)%2 != 0 or len(str2)%2 != 0: return False
    esquerda_str1, direita_str1 = str1[0 : int(len(str1)/2)], str1[int(len(str1)/2) : len(str1)]
    esquerda_str2, direita_str2 = str2[0 : int(len(str2)/2)], str2[int(len(str2)/2) : len(str2)]
    return (is_equivalente(esquerda_str1, esquerda_str2) and is_equivalente(direita_str1, direita_str2)) or (is_equivalente(esquerda_str1, direita_str2) and is_equivalente(direita_str1, esquerda_str2)) 
    
input_string1 = input().strip()
input_string2 = input().strip()

print("YES" if is_equivalente(input_string1, input_string2) else "NO")

'''
Hoje, em uma palestra sobre strings, Gerald aprendeu uma nova definição de equivalência de strings. Duas strings a e b de comprimento igual são chamadas de equivalentes em um dos dois casos:

Elas são iguais.
Se dividirmos a string a em duas metades do mesmo tamanho a1 e a2, e a string b em duas metades do mesmo tamanho b1 e b2, então uma das seguintes opções está correta:
a1 é equivalente a b1, e a2 é equivalente a b2
a1 é equivalente a b2, e a2 é equivalente a b1
Como tarefa de casa, o professor deu duas strings aos seus alunos e pediu para determinar se elas são equivalentes.

Gerald já completou essa tarefa de casa. Agora é a sua vez!

Entrada
As duas primeiras linhas da entrada contêm duas strings dadas pelo professor. Cada uma delas tem comprimento de 1 a 200 000 e consiste de letras minúsculas do alfabeto inglês. As strings têm o mesmo comprimento.

Saída
Imprima "YES" (sem as aspas), se essas duas strings forem equivalentes, e "NO" (sem as aspas) caso contrário.
'''