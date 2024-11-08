# Problema de Leitores e Escritores em Java

Este projeto implementa o problema clássico de leitores e escritores utilizando threads em Java. Ele visa ilustrar a importância das soluções de sincronização para gerenciar o acesso simultâneo a um recurso compartilhado.

## Descrição

O programa lê um arquivo de texto (`bd/bd.txt`) que contém uma palavra por linha, armazenando cada palavra em uma estrutura de dados em memória. Essa estrutura de dados torna-se uma região crítica, sendo acessada por dois tipos de threads:

- **Leitores**: Leem palavras na estrutura de dados em posições aleatórias.
- **Escritores**: Modificam palavras na estrutura de dados em posições aleatórias, substituindo-as por `"MODIFICADO"`.

O programa possui duas versões de controle de acesso à região crítica:

1. **Com controle de prioridade para leitores**: Escritores só entram quando não há leitores ativos.
2. **Sem controle específico**: Qualquer thread (leitor ou escritor) pode acessar a região crítica sem distinção.

A execução é repetida com diferentes proporções de leitores e escritores, variando de 0 leitores e 100 escritores a 100 leitores e 0 escritores, em passos de 1. Cada proporção é rodada 50 vezes, e o tempo médio de execução é registrado para comparação entre as duas versões.

## Estrutura do Projeto

- **Database.java**: Implementa a estrutura de dados compartilhada (região crítica) e os mecanismos de sincronização.
- **GenericThread.java**: Classe base para threads leitoras e escritoras.
- **Reader.java**: Classe que implementa o comportamento das threads leitoras.
- **Writer.java**: Classe que implementa o comportamento das threads escritoras.
- **ReaderWriterSimulator.java**: Classe principal, que inicializa o sistema, cria as threads e realiza a medição de tempo.
- **bd/**: Pasta contendo o arquivo de texto (`bd.txt`) com as palavras que serão carregadas na memória.
- **README.md**: Este arquivo com as instruções para execução.

## Pré-requisitos

- **Java**: Certifique-se de que o JDK (Java Development Kit) 11 ou superior está instalado.
- **bd/bd.txt**: Arquivo de texto com as palavras que serão carregadas na memória.

## Como Executar o Programa

1. **Compilar o código**:
   No terminal, na pasta principal do projeto (onde estão os arquivos `.java`), execute o comando:
   ```bash
   javac ReaderWriterSimulator.java

2. **Executar o programa: Após a compilação, execute o programa com**:

   ```bash
   java ReaderWriterSimulator

## Resultados: 
   O programa irá executar 50 vezes cada proporção de leitores e escritores para ambas as versões (com e sem controle de acesso). Os tempos médios para cada configuração serão exibidos no console e podem ser utilizados para análises comparativas.

## Saída Esperada: 

Proporção Leitores: 50, Escritores: 50
Com controle de acesso: Média de 100ms
Sem controle de acesso: Média de 200ms
...
