
package bolonhaemilano;

import java.util.Scanner;

/**
 * Menu principal do jogo e submenus de Mercado,
 * Agricultura, Governo e Construcoes.
 */
public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;
    private Governo governo;
    private Construcoes construcoes;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.emExecucao = true;
        this.mercado = new Mercado();
        this.agricultura = new Agricultura();
        this.governo = new Governo();
        this.construcoes = new Construcoes();
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
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Construcoes e soldados");
        System.out.println("7 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    /** Le uma opcao do teclado. */
    private int lerOpcao() {
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
                exibirSobre();
                break;

            case 0:
                System.out.println("\nAte logo!");
                emExecucao = false;
                break;

            default:
                System.out.println("\nOpcao invalida! Digite uma opcao de 0 a 7.");
        }
    }

    /** Inicia um novo jogo. */
    private void iniciarNovoJogo() {
        String nomeJogador = lerNome("Digite o nome do jogador: ");
        String nomeCidade = lerNome("Digite o nome da cidade: ");

        if (!emExecucao) {
            return;
        }

        jogador = new Jogador(nomeJogador);
        cidade = new Cidade(nomeCidade, jogador);
        cidade.exibirStatus();
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

    /** Exibe o status da cidade. */
    private void exibirStatus() {
        if (cidade == null) {
            System.out.println("\nNenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
        } else {
            cidade.exibirStatus();
        }
    }

    /** Submenu do Mercado. */
    private void abrirMercado() {
        if (cidade == null) {
            System.out.println("\nNenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
            return;
        }

        boolean noMercado = true;

        while (noMercado && emExecucao) {
            System.out.println();
            System.out.println("--- MERCADO ---");
            System.out.println("Florins disponiveis: " + cidade.getFlorins());
            System.out.println("Graos: " + cidade.getGraos()
                    + " | Terras: " + cidade.getTerras() + " hectares");
            System.out.println("Preco do grao: " + mercado.getPrecoGrao() + " florins");
            System.out.println("Preco do hectare: " + mercado.getPrecoTerra() + " florins");
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

    /** Le e valida uma quantidade positiva. */
    private int lerQuantidade() {
        System.out.print("Quantidade: ");

        if (!scanner.hasNextLine()) {
            emExecucao = false;
            return -1;
        }

        String linha = scanner.nextLine().trim();

        try {
            int quantidade = Integer.parseInt(linha);
            return quantidade > 0 ? quantidade : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void comprarGraos() {
        int quantidade = lerQuantidade();

        if (!emExecucao) {
            return;
        }

        if (quantidade <= 0) {
            System.out.println("\nQuantidade invalida! Digite um numero inteiro positivo.");
        } else if (mercado.comprarGraos(cidade, quantidade)) {
            System.out.println("\nCompra realizada: +" + quantidade + " graos.");
        } else {
            System.out.println("\nFlorins insuficientes para essa compra.");
        }
    }

    private void venderGraos() {
        int quantidade = lerQuantidade();

        if (!emExecucao) {
            return;
        }

        if (quantidade <= 0) {
            System.out.println("\nQuantidade invalida! Digite um numero inteiro positivo.");
        } else if (mercado.venderGraos(cidade, quantidade)) {
            System.out.println("\nVenda realizada: -" + quantidade + " graos.");
        } else {
            System.out.println("\nGraos insuficientes para essa venda.");
        }
    }

    private void comprarTerras() {
        int quantidade = lerQuantidade();

        if (!emExecucao) {
            return;
        }

        if (quantidade <= 0) {
            System.out.println("\nQuantidade invalida! Digite um numero inteiro positivo.");
        } else if (mercado.comprarTerras(cidade, quantidade)) {
            System.out.println("\nCompra realizada: +" + quantidade + " hectares.");
        } else {
            System.out.println("\nFlorins insuficientes para essa compra.");
        }
    }

    private void venderTerras() {
        int quantidade = lerQuantidade();

        if (!emExecucao) {
            return;
        }

        if (quantidade <= 0) {
            System.out.println("\nQuantidade invalida! Digite um numero inteiro positivo.");
        } else if (mercado.venderTerras(cidade, quantidade)) {
            System.out.println("\nVenda realizada: -" + quantidade + " hectares.");
        } else {
            System.out.println("\nTerras insuficientes para essa venda.");
        }
    }

    /** Submenu de Agricultura. */
    private void abrirAgricultura() {
        if (cidade == null) {
            System.out.println("\nNenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
            return;
        }

        boolean naAgricultura = true;

        while (naAgricultura && emExecucao) {
            System.out.println();
            System.out.println("--- AGRICULTURA ---");
            System.out.println("Terras: " + cidade.getTerras() + " hectares");
            System.out.println("Servos: " + cidade.getServos());
            System.out.println("Graos: " + cidade.getGraos());
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

        System.out.println("\nColheita realizada: " + hectares + " hectares cultivados.");
        System.out.println("Graos: " + graosAntes + " -> " + cidade.getGraos());
    }

    /** Delega a distribuicao de alimentos para Agricultura. */
    private void distribuirAlimentos() {
        int minimo = agricultura.getPercentualMinimo();
        int maximo = agricultura.getPercentualMaximo();

        System.out.print("Percentual do estoque de graos a distribuir ("
                + minimo + " a " + maximo + "): ");

        if (!scanner.hasNextLine()) {
            emExecucao = false;
            return;
        }

        int percentual;

        try {
            percentual = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            percentual = -1;
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
        if (cidade == null) {
            System.out.println("\nNenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
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

        System.out.println("\nImpostos arrecadados com sucesso!");
        System.out.println("Valor arrecadado: " + arrecadado + " florins.");
        System.out.println("Florins disponiveis: " + cidade.getFlorins());
    }

    /** Define uma nova taxa de imposto. */
    private void definirTaxaImposto() {
        System.out.print("Digite a nova taxa de imposto (0 a 50): ");

        if (!scanner.hasNextLine()) {
            emExecucao = false;
            return;
        }

        int taxa;

        try {
            taxa = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            taxa = -1;
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
        if (cidade == null) {
            System.out.println("\nNenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
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
                    + construcoes.getPrecoConstrucao() + " florins por construcao");
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
                    + construcoes.getPrecoConstrucao() + " florins.");
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
