# Respostas parte 4:

# 1 - Como o tamanho da população afeta a eficiência do algoritmo?
#     O tamanho da amostra melhora a eficiência do algoritmo no que diz respeito ao número de iterações.
#     Isso se deve ao fato de que uma população mais variada gera mais indivíduos diferenciados, expandindo o espaço de busca.
#     É como se o algoritmo tivesse um campo de visão maior para procurar o indivíduo que soluciona o problema

# 2 - O que acontece se a taxa de mutação for muito alta ou muito baixa?
#     Uma taxa de mutação alta introduz imprevisibilidade no algoritmo. Em populações próximas da solução, taxas de mutação
#     alta podem sabotar indivíduos próximos do ideal, retardando o econtro de uma solução.
#     Em populações pouco variadas e/ou distantes da solução, uma alta taxa de mutação pode ajudar, aproximando indivíduos,
#     antes muito distantes, da solução alvo

# 3 - O algoritmo conseguiu encontrar a solução em todas as execuções? Se não, por que isso pode ter ocorrido?
#     O algoritmo consegue encontrar solução em quase todos os casos. Ele não encontra soluções em casos de população zero,
#     haja visto que indivíduos não brotam do nada
#     Caso a população consista de um único indivíduo, o algoritmo não conseguiria resolver, já que o cruzamento de um indivíduo com
#     ele mesmo geraria sempre um clone. Entretanto, com a introdução da possibilidade de mutação, populações pouco diversar começam a
#     se diferenciar aos poucos, econtrando a solução eventualmente


# Uma breve explicação do AG:
# O AG "OrdenadorGenetico" tenta ordenar um array numerico a partir de algoritmo genetico.
# Para isso, são instanciados indivíduos como representações de um array, com chance de mutação no ato do nascimento.
# Depois de gerados os melhores indivíduos são selecionados a partir de uma funcao aptidão.
# Depois de selecionados, os melhores cruzam entre si e geram uma nova população, que novamente é selecionada e cruza-se entre si
# Ate que um indivíduo contendo o estado desejado seja encontrado

import random

PROBABILIDADE_MUTACAO_INDIVIDUO = 0
ESTADO_INICIAL = [5,3,2,4,1,7,6,9,8]
QUANT_INDIVIDUOS = 1 # Precisa ser pelo menos 1
ESTADO_ALVO = sorted(ESTADO_INICIAL) 

def get_par_mais_proximo_da_metade(numero):
    metade = numero / 2
    arredondado = round(metade) # Arredonda para o inteiro mais próximo

    # Se for ímpar, ajusta para o número par superior mais próximo
    if arredondado % 2 != 0:
        return arredondado + 1

    return arredondado

def dividir_array_ao_meio(array):
    # caso de array com numero de elementos impar, o primeiro array contera um elemento a mais
    meio = len(array) // 2
    if len(array) % 2 != 0:
        meio += 1
    # Retorna as duas metades do array original
    return array[:meio], array[meio:]


class Individuo:
    def __init__(self, estado_inicial):
        # Aplica a mutação, trocando dois elementos aleatorios de lugar
        if random.random() <= PROBABILIDADE_MUTACAO_INDIVIDUO:
            i, j = random.sample(range(len(estado_inicial)), 2)
            estado_inicial[i], estado_inicial[j] = estado_inicial[j], estado_inicial[i]
        self.estado = estado_inicial

    def get_estado(self):
        return self.estado
    
    # Função de cruzamento que propaga genes assertivos (aqueles numeros que ja estao em posicao correta)
    # Essa é uma estratégia de elitismo
    def cruzar(self, parceiro):
        estado_alvo = sorted(self.estado)
        estado_parceiro = parceiro.get_estado()
        estado_inicial_novo_individuo = []
        elementos_usados = set()

        # Adicionar ao estado inicial do novo individuo todos os elementos que ja estao na posicao
        # correta em quaisquer das partes que se cruzam
        for i in range(len(estado_alvo)):
            if self.estado[i] == estado_alvo[i]:
                estado_inicial_novo_individuo.append(self.estado[i])
                elementos_usados.add(self.estado[i])
            elif estado_parceiro[i] == estado_alvo[i]:
                estado_inicial_novo_individuo.append(estado_parceiro[i])
                elementos_usados.add(estado_parceiro[i])
            else:
                estado_inicial_novo_individuo.append(None)
        
        # Depois sorteia os elementos que ainda faltam inserir e insere-os em posicoes aleatorias
        elementos_faltantes = [elemento for elemento in estado_alvo if elemento not in elementos_usados]

        for i in range(len(estado_inicial_novo_individuo)):
            if estado_inicial_novo_individuo[i] is None:
                escolha = random.choice(elementos_faltantes)
                estado_inicial_novo_individuo[i] = escolha
                elementos_faltantes.remove(escolha)
        
        return Individuo(estado_inicial_novo_individuo)
    
    def is_individuo_ideal(self, estado_alvo):
        return self.estado == estado_alvo


class OrdenadorGenetico:
    def __init__(self, arr_desordenado, quant_individuos):
        self.geracao_atual = 0
        self.estado_alvo = sorted(arr_desordenado)
        self.individuos = []
        for _ in range(quant_individuos):
            # Cria individuos, cada com estado inicial sendo o arr_desordenado em ordem aleatoria
            self.individuos.append(Individuo(estado_inicial=arr_desordenado))

    def get_individuos(self):
        return self.individuos
    
    def get_quantidade_individuos(self):
        return len(self.individuos)

    # recebe um individuo e devolve um numero referente à sua aptidão
    def get_aptidao(self, individuo):
        num_aptidao = 0
        for i in range(len(self.estado_alvo)):
            if individuo.get_estado()[i] == self.estado_alvo[i]:
                num_aptidao += 1
        return num_aptidao

    # Seleção por torneio
    def executar_selecao(self):
        print(f"quant individuos ANTES selecao: {self.get_quantidade_individuos()}")
        if len(self.individuos) < 4:
            return
        # um array com tamanho len(self.estado_alvo+1), onde cada espaço do array é um possível
        # numero de aptidao. Individuos com aptidao 0 ficarão na posicao 0 e assim sucetivamente
        classificacoes = [[] for _ in range(len(self.estado_alvo)+1)]

        for i in self.individuos:
            # adiciona os individuos em sua classificacao
            classificacoes[self.get_aptidao(i)].append(i)

        # Planifica o array classificacoes. Ao inves de um array de arrays de individuos,
        # temos agora um array de individuos equivalente
        classificacoes = [individuo for subarray in classificacoes for individuo in subarray]

        # individuos_selecionados = metade superior do array classificacoes (aqueles que têm mais aptidão)
        individuos_selecionados = classificacoes[get_par_mais_proximo_da_metade(len(classificacoes)):]
        self.individuos = individuos_selecionados
        print(f"quant individuos DEPOIS selecao: {self.get_quantidade_individuos()}")

    def cruzar_geracao_atual(self):
        print(f"quant individuos ANTES cruzamento: {self.get_quantidade_individuos()}")
        grupo1, grupo2 = dividir_array_ao_meio(self.individuos)
        recem_nascidos = []

        # caso grupo 2 esteja vazio, temos populacao de 1 individuo. Para possibilitar cruzamento, clonamos tal individuo
        # Introduzindo chance de mutação
        if len(grupo2) == 0:
            grupo2 = [Individuo(grupo1[0].get_estado())]

        for i in range(len(grupo2)):
            # gera dois individuos para cada cruzamento
            recem_nascidos.append(grupo2[i].cruzar(grupo1[i]))
            recem_nascidos.append(grupo1[i].cruzar(grupo2[i]))
        
        self.individuos = grupo1 + grupo2 + recem_nascidos
        self.geracao_atual += 1
        print(f"quant individuos DEPOIS cruzamento: {self.get_quantidade_individuos()}")

    def get_numero_geracao_atual(self):
        return self.geracao_atual


def main():
    finished = False
    # Definir sequência a ser trabalhada e populacao inicial
    ordenadorGenetico = OrdenadorGenetico(ESTADO_INICIAL, QUANT_INDIVIDUOS)

    while not finished:
        print(f"{ordenadorGenetico.get_numero_geracao_atual()}ª Geração")

        # Seleção a partir de funcao aptidão
        ordenadorGenetico.executar_selecao()

        # Cruza a geracao atual entre si
        # Novos nascidos tem chance PROBABILIDADE_MUTACAO_INDIVIDUO de sofrer mutação
        ordenadorGenetico.cruzar_geracao_atual()

        for individuo in ordenadorGenetico.get_individuos():
            if individuo.is_individuo_ideal(ESTADO_ALVO):
                print(f"Indivíduo ideal foi atingido: {individuo.get_estado()}")
                print(f"Quantidade de individuos na geração atual: {ordenadorGenetico.get_quantidade_individuos()}")
                finished = True
                break
        if not finished:
            print("O indivíduo ideal não foi atingido na geração atual.")
            print(f"Quantidade de individuos na geração atual: {ordenadorGenetico.get_quantidade_individuos()}")
            print()
            print("=====================================================")
            print()
        
main()