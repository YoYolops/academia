import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler
from matplotlib.lines import Line2D
import numpy as np

database = pd.read_csv("./mall_customers.csv")

linhas_vazias = database[database.isnull().any(axis=1)]
if not linhas_vazias.empty:
    print("Linhas com dados faltantes:")
    print(linhas_vazias)
    database_cleaned = database.dropna()
else:
    print("Sem dados faltantes no dataframe")

# Verificando a presença de outlier através de box plot. Percebe-se um outlier em Annual Income:
sns.boxplot(data=database[['Age', 'Annual Income (k$)', 'Spending Score (1-100)']])
plt.show()
# Removendo o outliers e plotando grafico mais uma vez:
database = database[database['Annual Income (k$)'] <= 130]
sns.boxplot(data=database[['Age', 'Annual Income (k$)', 'Spending Score (1-100)']])
plt.show()

#Questão Teórica 1
# Definição de Aprendizado Não Supervisionado: Explicar o que é aprendizado não supervisionado e como ele difere do aprendizado supervisionado.

# Aprendizado não supervisionado é um tipo de aprendizado de máquina
# onde os dados não possuem rótulos ou categorias pré-definidas.
# O objetivo do modelo é descobrir padrões ocultos, estruturas
# ou agrupamentos dentro dos dados, sem saber previamente o que
# está “certo” ou “errado”

# As variáveis usadas para agrupamento são: Age,Annual Income (k$),Spending Score (1-100)

# No aprendizado não supervisionado, como não há um "rótulo" para guiar o modelo, o algoritmo vai se
# basear inteiramente nas variáveis fornecidas para encontrar padrões e grupos.
# Ao incluir:
# 1. Variáveis irrelevantes ou ruidosas, o algoritmo pode encontrar agrupamentos sem sentido ou distantes da realidade.
# 2. Variáveis redundantes, o modelo pode dar peso excessivo a uma informação repetida.
# 3. Variáveis significativas, o algoritmo pode formar grupos mais coerentes e úteis para análise.

variaveis_relevantes = database[['Age', 'Annual Income (k$)', 'Spending Score (1-100)']]

# Normalizando os dados:
scaler = StandardScaler()
relevantes_scaled = scaler.fit_transform(variaveis_relevantes)

# Usando o Método do Cotovelo para encontrar o número ideal de clusters
inertia = []
k_range = range(1, 11)

for k in k_range:
    kmeans = KMeans(n_clusters=k, n_init=10, random_state=42)
    kmeans.fit(relevantes_scaled)
    inertia.append(kmeans.inertia_)

plt.figure(figsize=(8, 4))
plt.plot(k_range, inertia, marker='o')
plt.title('Método do Cotovelo (Elbow Method)')
plt.xlabel('Número de clusters (k)')
plt.ylabel('Inércia (Soma dos Quadrados Internos)')
plt.xticks(k_range)
plt.grid(True)
plt.show()

# Com base no gráfico, temos que o numero ideal de clusters é 4.
kmeans = KMeans(n_clusters=4, n_init=10, random_state=42)
# Adiciona uma nova coluna cluster para dizer a que grupo aquela linha pertence (0, 1, 2 ou 3)
database['Cluster'] = kmeans.fit_predict(relevantes_scaled)

# Questão teórica 3: Explicar o funcionamento básico do algoritmo K-Means e o que significa
# "inertia" no contexto desse algoritmo.

# O K-Means é um algoritmo de agrupamento (clustering) não supervisionado que tem como
# objetivo dividir os dados em k grupos (ou clusters) a partir do cálculo de centróides.
# O algoritmo tenta minimizar a variabilidade dentro de cada grupo e maximizar a diferença entre os grupos.

# Inertia é uma métrica que o algoritmo usa para avaliar a qualidade do agrupamento.
# Ela representa a soma das distâncias quadradas de cada ponto de dados ao centroide do seu cluster.
# Ou seja, a inércia é a soma das distâncias de todos os pontos aos seus respectivos centroides.

# Analizando clusters gerados de forma gráfica:
fig = plt.figure(figsize=(10, 7))
ax = fig.add_subplot(111, projection='3d')

scatter = ax.scatter(
    database['Age'],
    database['Annual Income (k$)'],
    database['Spending Score (1-100)'],
    c=database['Cluster'],
    cmap='viridis',
    s=80
)
ax.set_title('Clusters de Clientes (3D)', fontsize=16)
ax.set_xlabel('Idade')
ax.set_ylabel('Renda Anual (k$)')
ax.set_zlabel('Pontuação de Gasto (1-100)')

legend_elements = [
    Line2D([0], [0], marker='o', color='w',
           label=f'Cluster {i}',
           markerfacecolor=scatter.cmap(scatter.norm(i)),
           markersize=10)
    for i in np.unique(database['Cluster'])
]

ax.legend(handles=legend_elements, title="Clusters")
plt.show()

# Questão teórica 4 e conclusões:

# A visualização tem como principal objetivo tornar os padrões e relações entre os dados mais evidentes.
# No caso dos clusters de clientes, as visualizações permitem identificar:
# 1. Distinção entre os grupos: 
#   O gráfico de dispersão, seja 2D ou 3D, pode mostrar claramente como os clientes estão agrupados.
#   Isso ajuda a perceber se os grupos são bem definidos ou se há sobreposição entre os clusters.

# 2. Identificação de padrões: Através da posição e da dispersão dos pontos no gráfico,
#   podemos perceber quais variáveis são mais determinantes para o agrupamento.
#   Por exemplo, se um grupo está mais concentrado em uma região do gráfico,
#   isso pode indicar que a renda ou pontuação de gastos desses clientes é similar.

# 3. Visualização de outliers: 
#   Às vezes, pontos que estão fora dos clusters podem ser facilmente
#   identificados no gráfico. Esses pontos podem ser outliers, ou seja,
#   clientes atípicos que não seguem os padrões do resto dos dados.
#   A visualização ajuda a localizar e a decidir como lidar com esses pontos.

# O que é possível inferir sobre os clientes de cada grupo?
# Cluster 0: Clientes com alta renda e baixa pontuação de gasto
# Cluster 1: Clientes de Baixa renda e baixa pontuaçao de gasto
# Cluster 2: Clientes  de alta renda com alta pontuação de gasto
# Cluster 3: Clientes de baixa renda mas alta pontuação de gasto

# Os clusters também indicam que, nesse cenário, uma maior idade pode estar associada a baixa pontuação de
# gasto, já que não existem clientes de maior idade (>45) com uma alta pontuação nesse quesito.