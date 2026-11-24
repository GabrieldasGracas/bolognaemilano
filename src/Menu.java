package bolonhaemilano;

import java.util.List;
import java.util.Scanner;

/**
 * Menu principal do jogo e submenus de Mercado,
 * Agricultura, Governo, Construcoes e Partida.
 */
public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Partida partida;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;
    private Governo governo;
    private Construcoes construcoes;
    private Nobreza nobreza;
    private EventosAnuais eventosAnuais;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.emExecucao = true;
        this.mercado = new Mercado();
        this.agricultura = new Agricultura();
        this.governo = new Governo();
        this.construcoes = new Construcoes();
        this.nobreza = new Nobreza();
        this.eventosAnuais = new EventosAnuais();
    }

    /** Laco principal do jogo. */
    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }

        scanner.close();
    }

    /** Exibe o menu principal. */
    private void exibirOpcoes() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println(" BOLONHA E MILANO");
        System.out.println(" Um jogo de estrategia economica medieval");
        System.out.println("==========================================");

        if (partida != null && cidade != null) {
            System.out.println("Ano: " + cidade.getAnoAtual()
                    + " | Vez de: " + cidade.getDono().getTitulo()
                    + " " + cidade.getDono().getNome()
                    + " (" + cidade.getNome() + ")");
            System.out.println("------------------------------------------");
        }

        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Construcoes e soldados");
        System.out.println("7 - Encerrar turno");
        System.out.println("8 - Ver classificacao");
        System.out.println("9 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    /** Le um numero inteiro do teclado; devolve -1 se invalido. */
    private int lerInteiro() {
        if (!scanner.hasNextLine()) {
            emExecucao = false;
            return -1;
        }

        String linha = scanner.nextLine().trim();

        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Le uma opcao do teclado delegando para lerInteiro(). */
    private int lerOpcao() {
        return lerInteiro();
    }

    /** Le e valida uma quantidade positiva delegando para lerInteiro(). */
    private int lerQuantidade() {
        System.out.print("Quantidade: ");
        int quantidade = lerInteiro();
        return quantidade > 0 ? quantidade : -1;
    }

    /** Verifica se ha uma partida ativa antes de abrir submenus. */
    private boolean exigirJogoEmAndamento() {
        if (partida == null || cidade == null) {
            System.out.println("\nNenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return false;
        }
        return true;
    }

    /** Processa a opcao escolhida. */
    private void processarOpcao(int opcao) {
        if (!emExecucao) {
            return;
        }

        switch (opcao) {
            case 1:
                iniciarNovoJogo();
                break;

            case 2:
                exibirStatus();
                break;

            case 3:
                abrirMercado();
                break;

            case 4:
                abrirAgricultura();
                break;

            case 5:
                abrirGoverno();
                break;

            case 6:
                abrirConstrucoes();
                break;

            case 7:
                encerrarTurno();
                break;

            case 8:
                exibirClassificacao();
                break;

            case 9:
                exibirSobre();
                break;

            case 0:
                System.out.println("\nAte logo!");
                emExecucao = false;
                break;

            default:
                System.out.println("\nOpcao invalida! Digite uma opcao de 0 a 9.");
        }
    }

    /** Inicia um novo jogo de 1 a 4 cidades. */
    private void iniciarNovoJogo() {
        System.out.print("Quantidade de jogadores (1 a "
                + Partida.MAXIMO_JOGADORES + "): ");
        int totalJogadores = lerInteiro();

        if (!emExecucao) {
            return;
        }

        if (totalJogadores < 1 || totalJogadores > Partida.MAXIMO_JOGADORES) {
            System.out.println("\nQuantidade invalida! Digite um numero entre 1 e "
                    + Partida.MAXIMO_JOGADORES + ".");
            return;
        }

        Partida novaPartida = new Partida();
        Mercado novoMercado = new Mercado();

        for (int i = 1; i <= totalJogadores; i++) {
            System.out.println("\n--- Jogador " + i + " ---");
            String nomeJogador = lerNome("Digite o nome do jogador: ");
            String nomeCidade = lerNome("Digite o nome da cidade (ex: Bolonha ou Milano): ");

            if (!emExecucao) {
                return;
            }

            Jogador jogador = new Jogador(nomeJogador);
            Cidade novaCidade = new Cidade(nomeCidade, jogador);
            novaPartida.adicionarCidade(novaCidade);

            exibirBonusDaCidade(novaCidade);
        }

        this.partida = novaPartida;
        this.mercado = novoMercado;
        this.cidade = partida.getCidadeAtual();

        System.out.println("\nPartida iniciada! Agora e a vez de "
                + cidade.getDono().getNome() + " (" + cidade.getNome() + ").");
        cidade.exibirStatus();
    }

    /** Mostra o bonus passivo especifico da cidade escolhida, se houver. */
    private void exibirBonusDaCidade(Cidade c) {
        if (c.getNome().equalsIgnoreCase("Bolonha")) {
            System.out.println("Bonus de Bolonha: +25% de producao de graos nas colheitas!");
        } else if (c.getNome().equalsIgnoreCase("Milano")
                || c.getNome().equalsIgnoreCase("Milao")) {
            System.out.println("Bonus de Milano: -20% no custo de novas construcoes (16 florins)!");
        }
    }

    /** Le um nome nao vazio. */
    private String lerNome(String pergunta) {
        while (emExecucao) {
            System.out.print(pergunta);

            if (!scanner.hasNextLine()) {
                emExecucao = false;
                break;
            }

            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("O nome nao pode ficar vazio.");
        }

        return "";
    }

    /** Exibe o status da cidade atual. */
    private void exibirStatus() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        cidade.exibirStatus();
        System.out.println("Prestigio atual: " + nobreza.calcularPrestigio(cidade));
        exibirBonusDaCidade(cidade);
    }

    /** Submenu do Mercado com precos dinamicos. */
    private void abrirMercado() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean noMercado = true;

        while (noMercado && emExecucao) {
            System.out.println();
            System.out.println("--- MERCADO ---");
            System.out.println("Clima atual: "
                    + (mercado.isInvernoSevero()
                    ? "INVERNO SEVERO (escassez de trigo!)"
                    : "Estavel"));
            System.out.println("Florins disponiveis: " + cidade.getFlorins());
            System.out.println("Graos: " + cidade.getGraos()
                    + " | Terras: " + cidade.getTerras() + " hectares");
            System.out.println("Preco atual do grao: " + mercado.getPrecoGrao() + " florins");
            System.out.println("Preco atual do hectare: " + mercado.getPrecoTerra() + " florins");
            System.out.println("1 - Comprar graos");
            System.out.println("2 - Vender graos");
            System.out.println("3 - Comprar terras");
            System.out.println("4 - Vender terras");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerOpcao();

            if (!emExecucao) {
                break;
            }

            switch (opcao) {
                case 1:
                    comprarGraos();
                    break;
                case 2:
                    venderGraos();
                    break;
                case 3:
                    comprarTerras();
                    break;
                case 4:
                    venderTerras();
                    break;
                case 0:
                    noMercado = false;
                    break;
                default:
                    System.out.println("\nOpcao invalida! Digite 1, 2, 3, 4 ou 0.");
            }
        }
    }

    private void comprarGraos() {
        int precoAntes = mercado.getPrecoGrao();
        int quantidade = lerQuantidade();

        if (!emExecucao) {
            return;
        }

        if (quantidade <= 0) {
            System.out.println("\nQuantidade invalida! Digite um numero inteiro positivo.");
        } else if (mercado.comprarGraos(cidade, quantidade)) {
            System.out.println("\nCompra realizada: +" + quantidade + " graos.");
            if (mercado.getPrecoGrao() > precoAntes) {
                System.out.println("Alta demanda! O preco do grao subiu para "
                        + mercado.getPrecoGrao() + " florins.");
            }
        } else {
            System.out.println("\nFlorins insuficientes para essa compra.");
        }
    }

    private void venderGraos() {
        int precoAntes = mercado.getPrecoGrao();
        int quantidade = lerQuantidade();

        if (!emExecucao) {
            return;
        }

        if (quantidade <= 0) {
            System.out.println("\nQuantidade invalida! Digite um numero inteiro positivo.");
        } else if (mercado.venderGraos(cidade, quantidade)) {
            System.out.println("\nVenda realizada: -" + quantidade + " graos.");
            if (mercado.getPrecoGrao() < precoAntes) {
                System.out.println("Maior oferta no mercado! O preco do grao caiu para "
                        + mercado.getPrecoGrao() + " florins.");
            }
        } else {
            System.out.println("\nGraos insuficientes para essa venda.");
        }
    }

    private void comprarTerras() {
        int precoAntes = mercado.getPrecoTerra();
        int quantidade = lerQuantidade();

        if (!emExecucao) {
            return;
        }

        if (quantidade <= 0) {
            System.out.println("\nQuantidade invalida! Digite um numero inteiro positivo.");
        } else if (mercado.comprarTerras(cidade, quantidade)) {
            System.out.println("\nCompra realizada: +" + quantidade + " hectares.");
            if (mercado.getPrecoTerra() > precoAntes) {
                System.out.println("Alta demanda! O preco da terra subiu para "
                        + mercado.getPrecoTerra() + " florins.");
            }
        } else {
            System.out.println("\nFlorins insuficientes para essa compra.");
        }
    }

    private void venderTerras() {
        int precoAntes = mercado.getPrecoTerra();
        int quantidade = lerQuantidade();

        if (!emExecucao) {
            return;
        }

        if (quantidade <= 0) {
            System.out.println("\nQuantidade invalida! Digite um numero inteiro positivo.");
        } else if (mercado.venderTerras(cidade, quantidade)) {
            System.out.println("\nVenda realizada: -" + quantidade + " hectares.");
            if (mercado.getPrecoTerra() < precoAntes) {
                System.out.println("Maior oferta no mercado! O preco da terra caiu para "
                        + mercado.getPrecoTerra() + " florins.");
            }
        } else {
            System.out.println("\nTerras insuficientes para essa venda.");
        }
    }

    /** Submenu de Agricultura. */
    private void abrirAgricultura() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean naAgricultura = true;

        while (naAgricultura && emExecucao) {
            System.out.println();
            System.out.println("--- AGRICULTURA ---");
            System.out.println("Terras: " + cidade.getTerras() + " hectares");
            System.out.println("Servos: " + cidade.getServos());
            System.out.println("Graos: " + cidade.getGraos());
            if (cidade.getNome().equalsIgnoreCase("Bolonha")) {
                System.out.println("Bonus ativo: Bolonha produz +25% de graos na colheita!");
            }
            System.out.println("1 - Colher (processar safra)");
            System.out.println("2 - Distribuir alimentos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerOpcao();

            if (!emExecucao) {
                break;
            }

            switch (opcao) {
                case 1:
                    colher();
                    break;
                case 2:
                    distribuirAlimentos();
                    break;
                case 0:
                    naAgricultura = false;
                    break;
                default:
                    System.out.println("\nOpcao invalida! Digite 1, 2 ou 0.");
            }
        }
    }

    /** Delega a colheita para Agricultura. */
    private void colher() {
        int graosAntes = cidade.getGraos();
        int hectares = agricultura.processarColheita(cidade);

        if (hectares == -1) {
            System.out.println("\nA colheita deste ano ja foi realizada!");
            return;
        }

        System.out.println("\nColheita realizada: " + hectares + " hectares cultivados.");
        System.out.println("Graos: " + graosAntes + " -> " + cidade.getGraos());
    }

    /** Delega a distribuicao de alimentos para Agricultura. */
    private void distribuirAlimentos() {
        int minimo = agricultura.getPercentualMinimo();
        int maximo = agricultura.getPercentualMaximo();

        System.out.print("Percentual do estoque de graos a distribuir ("
                + minimo + " a " + maximo + "): ");
        int percentual = lerInteiro();

        if (!emExecucao) {
            return;
        }

        int servosAntes = cidade.getServos();
        int graosAntes = cidade.getGraos();

        if (agricultura.distribuirAlimentos(cidade, percentual)) {
            System.out.println("\nAlimentos distribuidos.");
            System.out.println("Graos: " + graosAntes + " -> " + cidade.getGraos());
            System.out.println("Servos: " + servosAntes + " -> " + cidade.getServos());
        } else {
            System.out.println("\nPercentual invalido! Digite um numero de "
                    + minimo + " a " + maximo + ".");
        }
    }

    /** Submenu de Governo. */
    private void abrirGoverno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean noGoverno = true;

        while (noGoverno && emExecucao) {
            System.out.println();
            System.out.println("--- GOVERNO ---");
            System.out.println("Florins disponiveis: " + cidade.getFlorins());
            System.out.println("Servos: " + cidade.getServos());
            System.out.println("Taxa de imposto: " + cidade.getTaxaImposto() + "%");
            System.out.println("Nivel de justica: " + cidade.getNivelJustica());
            System.out.println("1 - Arrecadar impostos");
            System.out.println("2 - Definir taxa de imposto");
            System.out.println("3 - Definir nivel de justica");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerOpcao();

            if (!emExecucao) {
                break;
            }

            switch (opcao) {
                case 1:
                    arrecadarImpostos();
                    break;
                case 2:
                    definirTaxaImposto();
                    break;
                case 3:
                    definirJustica();
                    break;
                case 0:
                    noGoverno = false;
                    break;
                default:
                    System.out.println("\nOpcao invalida! Digite 1, 2, 3 ou 0.");
            }
        }
    }

    /** Delega a arrecadacao para Governo. */
    private void arrecadarImpostos() {
        int arrecadado = governo.arrecadarImpostos(cidade);

        if (arrecadado == -1) {
            System.out.println("\nOs impostos deste ano ja foram arrecadados!");
            return;
        }

        System.out.println("\nImpostos arrecadados com sucesso!");
        System.out.println("Valor arrecadado: " + arrecadado + " florins.");
        System.out.println("Florins disponiveis: " + cidade.getFlorins());
    }

    /** Define uma nova taxa de imposto. */
    private void definirTaxaImposto() {
        System.out.print("Digite a nova taxa de imposto (0 a 50): ");
        int taxa = lerInteiro();

        if (!emExecucao) {
            return;
        }

        if (governo.definirTaxaImposto(cidade, taxa)) {
            System.out.println("\nTaxa de imposto atualizada para "
                    + cidade.getTaxaImposto() + "%.");
        } else {
            System.out.println("\nTaxa invalida! Digite um numero entre 0 e 50.");
        }
    }

    /** Define o nivel de justica do governo. */
    private void definirJustica() {
        System.out.println("\n--- NIVEL DE JUSTICA ---");
        System.out.println("1 - Justa");
        System.out.println("2 - Moderada");
        System.out.println("3 - Severa");
        System.out.print("Escolha uma opcao: ");

        int opcao = lerOpcao();

        if (!emExecucao) {
            return;
        }

        String nivel;

        switch (opcao) {
            case 1:
                nivel = "Justa";
                break;
            case 2:
                nivel = "Moderada";
                break;
            case 3:
                nivel = "Severa";
                break;
            default:
                System.out.println("\nOpcao invalida!");
                return;
        }

        if (governo.definirJustica(cidade, nivel)) {
            System.out.println("\nNivel de justica atualizado para "
                    + cidade.getNivelJustica() + ".");
        } else {
            System.out.println("\nNao foi possivel alterar o nivel de justica.");
        }
    }

    /** Submenu de Construcoes e soldados. */
    private void abrirConstrucoes() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean nasConstrucoes = true;

        while (nasConstrucoes && emExecucao) {
            System.out.println();
            System.out.println("--- CONSTRUCOES E SOLDADOS ---");
            System.out.println("Florins disponiveis: " + cidade.getFlorins());
            System.out.println("Servos disponiveis: " + cidade.getServos());
            System.out.println("Soldados: " + cidade.getSoldados());
            System.out.println("Construcoes: " + cidade.getConstrucoes());
            System.out.println("--------------------------------");
            System.out.println("Recrutar soldado: "
                    + construcoes.getPrecoSoldado() + " florins e 1 servo");
            System.out.println("Construir: "
                    + construcoes.getPrecoConstrucaoPara(cidade) + " florins por construcao");
            System.out.println("--------------------------------");
            System.out.println("1 - Recrutar soldados");
            System.out.println("2 - Construir");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerOpcao();

            if (!emExecucao) {
                break;
            }

            switch (opcao) {
                case 1:
                    recrutarSoldados();
                    break;

                case 2:
                    construir();
                    break;

                case 0:
                    nasConstrucoes = false;
                    break;

                default:
                    System.out.println("\nOpcao invalida! Digite 1, 2 ou 0.");
            }
        }
    }

    /** Delega o recrutamento para Construcoes. */
    private void recrutarSoldados() {
        int quantidade = lerQuantidade();

        if (!emExecucao) {
            return;
        }

        if (quantidade <= 0) {
            System.out.println("\nQuantidade invalida! Digite um numero inteiro positivo.");
            return;
        }

        if (construcoes.recrutarSoldados(cidade, quantidade)) {
            System.out.println("\nRecrutamento realizado com sucesso!");
            System.out.println("Soldados recrutados: " + quantidade);
            System.out.println("Florins restantes: " + cidade.getFlorins());
            System.out.println("Servos restantes: " + cidade.getServos());
            System.out.println("Total de soldados: " + cidade.getSoldados());
        } else {
            System.out.println("\nRecursos insuficientes para recrutar essa quantidade.");
            System.out.println("Cada soldado custa "
                    + construcoes.getPrecoSoldado() + " florins e 1 servo.");
        }
    }

    /** Delega a construcao para Construcoes. */
    private void construir() {
        int quantidade = lerQuantidade();

        if (!emExecucao) {
            return;
        }

        if (quantidade <= 0) {
            System.out.println("\nQuantidade invalida! Digite um numero inteiro positivo.");
            return;
        }

        if (construcoes.construir(cidade, quantidade)) {
            System.out.println("\nConstrucoes realizadas com sucesso!");
            System.out.println("Construcoes realizadas: " + quantidade);
            System.out.println("Florins restantes: " + cidade.getFlorins());
            System.out.println("Total de construcoes: " + cidade.getConstrucoes());
        } else {
            System.out.println("\nFlorins insuficientes para realizar essa construcao.");
            System.out.println("Cada construcao custa "
                    + construcoes.getPrecoConstrucaoPara(cidade) + " florins.");
        }
    }

    /** Encerra o turno da cidade atual, fecha o ano e sorteia eventos anuais. */
    private void encerrarTurno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean fimDoAno = partida.passarTurno();

        if (fimDoAno) {
            System.out.println("\n=== FIM DO ANO " + partida.getAnoAtual() + " ===");
            List<Cidade> promovidas = partida.encerrarAno(nobreza);
            mercado.atualizarAno(partida.getAnoAtual());

            System.out.println("\n--- EVENTOS DO ANO " + partida.getAnoAtual() + " ---");
            for (Cidade c : partida.getCidades()) {
                String resumoEvento = eventosAnuais.sortearEAplicarEvento(c);
                System.out.println("* " + resumoEvento);
            }

            if (promovidas.isEmpty()) {
                System.out.println("\nNenhum governante foi promovido neste ano.");
            } else {
                System.out.println();
                for (Cidade c : promovidas) {
                    System.out.println("Promocao! " + c.getDono().getNome()
                            + " (" + c.getNome() + ") agora e "
                            + c.getDono().getTitulo() + "!");
                }
            }

            if (mercado.isInvernoSevero()) {
                System.out.println("AVISO CLIMATICO: Um inverno severo atingiu a regiao! O preco do trigo subiu no mercado.");
            }
        }

        if (partida.terminou()) {
            Cidade vencedora = partida.determinarVencedor(nobreza);
            System.out.println("\n==========================================");
            System.out.println(" FIM DE JOGO!");
            if (vencedora != null) {
                System.out.println(" Vencedor: " + vencedora.getDono().getTitulo()
                        + " " + vencedora.getDono().getNome()
                        + " (" + vencedora.getNome() + ")");
                System.out.println(" Prestigio final: "
                        + nobreza.calcularPrestigio(vencedora));
            }
            System.out.println("==========================================");
            partida = null;
            cidade = null;
            return;
        }

        this.cidade = partida.getCidadeAtual();
        System.out.println("\nAgora e a vez de "
                + cidade.getDono().getTitulo() + " "
                + cidade.getDono().getNome()
                + " (" + cidade.getNome() + ") - Ano " + cidade.getAnoAtual() + ".");
    }

    /** Exibe a classificacao atual dos jogadores. */
    private void exibirClassificacao() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        System.out.println();
        System.out.println("--- CLASSIFICACAO ATUAL (ANO " + partida.getAnoAtual() + ") ---");

        for (Cidade c : partida.getCidades()) {
            System.out.println("- " + c.getNome()
                    + " | Governante: " + c.getDono().getTitulo() + " " + c.getDono().getNome()
                    + " | Prestigio: " + nobreza.calcularPrestigio(c)
                    + " | Florins: " + c.getFlorins());
        }

        Cidade lider = partida.determinarVencedor(nobreza);
        if (lider != null) {
            System.out.println("Lider atual: " + lider.getDono().getTitulo()
                    + " " + lider.getDono().getNome() + " (" + lider.getNome() + ")");
        }
    }

    /** Exibe informacoes sobre o jogo. */
    private void exibirSobre() {
        System.out.println();
        System.out.println("--- SOBRE O JOGO ---");
        System.out.println("Bolonha e Milano e um jogo de estrategia economica medieval.");
        System.out.println("Voce governa uma cidade-estado italiana: administra florins,");
        System.out.println("terras, graos e populacao, define impostos, constroi e recruta");
        System.out.println("soldados, competindo com outros governantes para prosperar.");
    }
}