# Bolonha e Milano

Jogo de estrategia economica medieval em Java, construido em 8 etapas incrementais
(projeto do 4o bimestre - Desenvolvimento de Sistemas, IFSP).

## Como compilar e executar

Java 17, sem ferramenta de build externa. A partir da pasta do projeto:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
```

## Estrutura

| Pasta | Conteudo |
|---|---|
| `src/bolonhaemilano/` | Codigo-fonte Java (`Main`, `Menu`, ...) |
| `casos-de-uso/` | Diagramas de casos de uso |
| `uml/` | Diagramas de classes UML |

## Etapas

1. Estrutura inicial, Main e menu (concluida)
2. Classes Jogador e Cidade (concluida) 
3. Economia: florins, terras e graos (concluida)
4. Agricultura e populacao
5. Impostos e governo
6. Construcoes e soldados
7. Rodadas, multiplos jogadores e vitoria
8. Integracao, testes e entrega final
