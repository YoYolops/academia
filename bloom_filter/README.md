[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/KJ3Rtvqc)
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=19776436&assignment_repo_type=AssignmentRepo)
# Laboratório: Análise de Desempenho e Comportamento de Filtros de Bloom

📌 O Objetivo desssa atividade é explorar empiricamente o comportamento de Filtros de Bloom em relação a falsos positivos e comparar o desempenho de diferentes estratégias de hash em cenários práticos.

⚙️ Código Base : As atividades dessa atividade podem utilizar como base o exemplo visto em sala de aula
 - 📄 [hashing_bloom_filter.ipynb](./hashing_bloom_filter)


Itens a serem entregues : 

- [ ] [Atividade 1: Análise de Falsos Positivos](#-atividade-1-análise-de-falsos-positivos)
- [ ] [Atividade 2: Análise de Desempenho Temporal](#-atividade-2-análise-de-desempenho-temporal)

**O que devo entregar ?**

> Apenas o notebook é suficeinte!

## 🔍 Atividade 1: Análise de Falsos Positivos

**Objetivo** : Determinar experimentalmente quando um Filtro de Bloom começa a apresentar falsos positivos para diferentes estratégias de hash.
    
### Descrição da Atividade
Com os parâmetros fixos m=20 e k=4 (conforme apresentado no notebook), você deverá:

1. **Implementar um experimento** que:
   - Crie três filtros de Bloom, um para cada estratégia de hash (MD5/SHA1, djb2/sdbm, xxhash)
   - Adicione elementos incrementalmente (use inteiros de 1 a n)
   - Registre quando ocorre o primeiro falso positivo
   Dica : Modifique o código para detectar a colisão

2. **Análise a ser realizada**:
   - Para cada estratégia, identifique com quantos elementos inseridos começam a surgir falsos positivos
   - Calcule a taxa de ocupação do filtro quando isso ocorre (número de bits setados / tamanho total)
   - Compare o comportamento entre as três estratégias

3. **Visualização**:
   - Crie um gráfico de linha mostrando a evolução da taxa de falsos positivos vs. número de elementos inseridos
   - Use cores diferentes para cada estratégia de hash
   - Marque no gráfico o ponto onde cada estratégia atinge 1% de falsos positivos

4. **Questões** para Reflexão
   1. Nos seus experiementos, qual estratégia apresentou melhor distribuição (demorou mais para ter falsos positivos)?
   2. A taxa de ocupação do filtro quando surgem os primeiros falsos positivos é similar entre as estratégias?

---

## 🔍 Atividade 2: Análise de Desempenho Temporal

**Objetivo** Comparar o tempo de execução das diferentes estratégias de hash em um cenário mais realista.

### 🔍 Descrição da Atividade

1. **Configuração do experimento**:
   - m = 512K posições
   - dois niveis de k
      - $k_1$ = 128 funções hash
      - $k_2$ = 768 funções hash
   - Teste com 100, 1000 e 5000 elementos

2. **Metodologia**:
   - Para cada combinação (estratégia de hash, numero de funções hash ($k$) e número de elementos), 
   - Como entrada utilize inteiros sequenciais como elementos (1 a n) (em um for)
   - Meça separadamente:
     - Tempo total de inserção de todos os elementos
     - Tempo médio por inserção
     - Tempo de consulta (teste 1000 consultas aleatórias após todas as inserções)

3. **Sugestões Visualizações**:
   - **Gráfico de barras agrupadas**: Tempo total de inserção por estratégia e número de elementos
   - **Gráfico de linha**: Tempo médio por inserção vs. número de elementos (uma linha por estratégia)
   <!-- - **Tabela comparativa**: Mostrando speedup relativo (quanto uma estratégia é mais rápida que outra) -->

### Análises Esperada

1. **Compare a complexidade computacional**:
   - MD5/SHA1: funções criptográficas, computacionalmente intensivas
   - djb2/sdbm: funções simples com operações bit-a-bit
   - xxhash: otimizada para velocidade mantendo boa distribuição

2. **Discuta o impacto dos diferentes k ($k_1$ e $k_1$)**:
   - Com tantas funções hash ($k_1$ e $k_1$), qual estratégia escala melhor?
   - Discuta e compare o desempenho de $k_1$ e $k_1$
   - O overhead de gerar 768 hashes é linear com o esperado?


3. **Análise de trade-offs**:
   - Velocidade vs. qualidade de distribuição
   - Como a escolha da estratégia afetaria um sistema real com bilhões de itens?



<!-- ### Questões para Conclusão
1. Qual estratégia você recomendaria para o problema original da Amazon? Justifique.
2. Como o parâmetro k=768 afeta a escolha da estratégia?
3. Existem cenários onde uma estratégia "mais lenta" seria preferível? -->
