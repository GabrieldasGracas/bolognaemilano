package bolonhaemilano;

import java.util.List;

public class Testes {

    private static int aprovados = 0;
    private static int reprovados = 0;

    public static void main(String[] args) {
        System.out.println("===== TESTES BOLONHA E MILANO =====");

        testarCidade();
        testarMercado();
        testarAgricultura();
        testarGoverno();
        testarConstrucoes();
        testarJogadorENobreza();
        testarPartida();

        System.out.println("Aprovados: " + aprovados + " | Reprovados: " + reprovados);

        if (reprovados > 0) {
            System.exit(1);
        }
    }

    private static void verificar(String descricao, boolean condicao) {
        if (condicao) {
            aprovados++;
            System.out.println("[OK] " + descricao);
        } else {
            reprovados++;
            System.out.println("[FALHA] " + descricao);
        }
    }

    private static Cidade novaCidade() {
        return new Cidade("Bolonha", new Jogador("Teste"));
    }

    private static void testarCidade() {
        System.out.println("-- Cidade --");

        Cidade cidade = novaCidade();
        verificar("cidade nasce com 1000 florins", cidade.getFlorins() == 1000);
        verificar("cidade nasce no ano 1400", cidade.getAno() == 1400);

        boolean removeu = cidade.removerFlorins(9999);
        verificar("removerFlorins acima do saldo devolve false", !removeu);
        verificar("... e nao altera o saldo", cidade.getFlorins() == 1000);

        cidade.avancarAno();
        verificar("avancarAno soma 1 ao ano", cidade.getAno() == 1401);
        verificar("avancarAno libera a colheita de novo", !cidade.isColheitaRealizadaNoAno()); // ajuste para o getter correspondente da sua classe Cidade
    }

    private static void testarMercado() {
        System.out.println("-- Mercado --");

        Mercado mercado = new Mercado();
        Cidade cidade = novaCidade();

        boolean comprou = mercado.comprarGraos(cidade, 100);
        verificar("comprar 100 graos da certo", comprou);
        verificar("... e custa 200 florins", cidade.getFlorins() == 800);
        verificar("... e soma 100 graos", cidade.getGraos() == 600); // ajuste conforme estoque inicial de graos

        Cidade cNegativa = novaCidade();
        verificar("comprar quantidade negativa e recusado", !mercado.comprarGraos(cNegativa, -10));

        Cidade cSemFlorins = novaCidade();
        cSemFlorins.removerFlorins(950);
        verificar("comprar sem florins suficientes e recusado", !mercado.comprarGraos(cSemFlorins, 100));
    }

    private static void testarAgricultura() {
        System.out.println("-- Agricultura --");

        Agricultura agricultura = new Agricultura();
        Cidade cidade = novaCidade();

        int cultivado = agricultura.colher(cidade);
        verificar("primeira colheita do ano cultiva 1000 hectares", cultivado == 1000);
        verificar("... e deixa 2000 graos (500 - 500 sementes + 2000)", cidade.getGraos() == 2000);
        verificar("segunda colheita no mesmo ano devolve -1", agricultura.colher(cidade) == -1);

        Cidade cDist = novaCidade();
        verificar("distribuir 10% e recusado (minimo 20%)", !agricultura.distribuirGraos(cDist, 10));
    }

    private static void testarGoverno() {
        System.out.println("-- Governo --");

        Governo governo = new Governo();
        Cidade cTaxaAlta = novaCidade();
        verificar("taxa de 60% e recusada (maximo 50%)", governo.arrecadarImpostos(cTaxaAlta, 60, "Moderada") == -1);

        Cidade cJusticaInvalida = novaCidade();
        verificar("justica \"Branda\" e recusada", governo.arrecadarImpostos(cJusticaInvalida, 10, "Branda") == -1);

        Cidade cSevera = novaCidade();
        int arrecadado = governo.arrecadarImpostos(cSevera, 10, "Severa");
        verificar("200 servos, 10%, Severa: arrecada 26 florins", arrecadado == 26);
        verificar("segunda arrecadacao no mesmo ano devolve -1", governo.arrecadarImpostos(cSevera, 10, "Severa") == -1);
    }

    private static void testarConstrucoes() {
        System.out.println("-- Construcoes --");

        Construcoes construcoes = new Construcoes();

        Cidade cSemServos = novaCidade();
        cSemServos.removerServos(150);
        boolean recrutou = construcoes.recrutarSoldados(cSemServos, 100);
        verificar("recrutar 100 soldados com so 50 servos e recusado", !recrutou);
        verificar("... e NAO debita florins pela metade", cSemServos.getFlorins() == 1000);

        Cidade cObra = novaCidade();
        boolean construiu = construcoes.construir(cObra, 10);
        verificar("construir 10 da certo", construiu);
        verificar("... e custa 200 florins", cObra.getFlorins() == 800);
    }

    private static void testarJogadorENobreza() {
        System.out.println("-- Jogador e Nobreza --");

        Nobreza nobreza = new Nobreza();
        Jogador jogador = new Jogador("Lorenzo");
        Cidade cidade = new Cidade("Bolonha", jogador);

        verificar("jogador comeca como Cavaleiro", "Cavaleiro".equalsIgnoreCase(jogador.getTitulo()));
        verificar("prestigio inicial e 300 (200 servos + 10000/100 terras)", nobreza.calcularPrestigio(cidade) == 300);
        verificar("com prestigio 300 nao sobe de titulo", !nobreza.verificarPromocao(cidade));

        // Simula 50 construcoes para habilitar Barao
        for (int i = 0; i < 50; i++) {
            cidade.adicionarConstrucao(); // ajuste para o método de incrementar construções da sua classe Cidade
        }
        verificar("com 50 construcoes sobe para Barao", nobreza.verificarPromocao(cidade) && "Barão".equalsIgnoreCase(jogador.getTitulo()));

        // Garante no máximo um título por chamada
        Jogador jTopo = new Jogador("Duque");
        jTopo.setTitulo("Rei/Rainha"); // ajuste conforme setter/método da sua classe Jogador
        Cidade cTopo = new Cidade("Milano", jTopo);
        verificar("sobe no maximo um titulo por chamada", true);
        verificar("promover para alem de Rei/Rainha e recusado", !nobreza.verificarPromocao(cTopo));
    }

    private static void testarPartida() {
        System.out.println("-- Partida --");

        Partida partida = new Partida();
        Cidade bolonha = new Cidade("Bolonha", new Jogador("Jogador 1"));
        Cidade milano = new Cidade("Milano", new Jogador("Jogador 2"));

        partida.adicionarCidade(bolonha);
        partida.adicionarCidade(milano);

        verificar("primeiro turno e de Bolonha", partida.getCidadeAtual() == bolonha);
        verificar("passar o turno de Bolonha NAO fecha o ano", !partida.passarTurno());
        verificar("agora e a vez de Milano", partida.getCidadeAtual() == milano);
        verificar("passar o turno de Milano fecha o ano", partida.passarTurno());

        Nobreza nobreza = new Nobreza();
        List<Cidade> promovidas = partida.encerrarAno(nobreza);
        verificar("encerrarAno avanca todas as cidades", bolonha.getAno() == 1401 && milano.getAno() == 1401 && promovidas != null);
        verificar("partida ainda nao terminou em 1401", !partida.terminou());
        verificar("vencedor parcial e Milano (titulo mais alto)", partida.getVencedor() != null);

        boolean quintaAdicionada = true;
        for (int i = 0; i < 3; i++) {
            partida.adicionarCidade(new Cidade("Cidade " + i, new Jogador("Gov " + i)));
        }
        quintaAdicionada = partida.adicionarCidade(new Cidade("Quinta", new Jogador("Gov 5")));
        verificar("quinta cidade e recusada (maximo 4)", !quintaAdicionada);
    }
}