package bolonhaemilano;

import java.util.Scanner;

/**
 * Menu principal do jogo e submenu do Mercado.
 * Cada metodo tem uma unica responsabilidade.
 */
public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.emExecucao = true;
        this.mercado = new Mercado();
        this.agricultura = new Agricultura();
    }

    /** Laco principal: repete ate o jogador escolher sair. */
    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
        scanner.close();
    }

    /** Apenas imprime o menu; nao le nada do teclado. */
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
        System.out.println("5 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    /** Le uma linha e converte para inteiro; devolve -1 se for invalida. */
    private int lerOpcao() {
        if (!scanner.hasNextLine()) {   // fim da entrada (Ctrl+D / Ctrl+Z): encerra
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

    /** Decide a acao de acordo com a opcao lida. */
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
                exibirSobre();
                break;
            case 0:
                System.out.println("\nAte logo!");
                emExecucao = false;
                break;
            default:
                System.out.println("\nOpcao invalida! Digite 1, 2, 3, 4, 5 ou 0.");
        }
    }

    /** Pergunta os nomes, cria Jogador e Cidade e mostra o status inicial. */
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

    /** Le um nome nao vazio (repete a pergunta se vier em branco). */
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

    /** Mostra o status da cidade, ou avisa se nenhum jogo foi iniciado. */
    private void exibirStatus() {
        if (cidade == null) {
            System.out.println("\nNenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
        } else {
            cidade.exibirStatus();
        }
    }

    /** Submenu do Mercado, em laco, ate o jogador voltar (opcao 0). */
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

    /** Le e valida a quantidade digitada; devolve -1 se invalida. */
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

    /** Submenu de Agricultura, em laco, ate o jogador voltar (opcao 0). */
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

    /** Delega a colheita para Agricultura e imprime o resultado. */
    private void colher() {
        int graosAntes = cidade.getGraos();
        int hectares = agricultura.processarColheita(cidade);
        System.out.println("\nColheita realizada: " + hectares + " hectares cultivados.");
        System.out.println("Graos: " + graosAntes + " -> " + cidade.getGraos());
    }

    /** Le o percentual (20 a 80) e delega a distribuicao para Agricultura. */
    private void distribuirAlimentos() {
        int minimo = agricultura.getPercentualMinimo();
        int maximo = agricultura.getPercentualMaximo();
        System.out.print("Percentual do estoque de graos a distribuir (" + minimo + " a " + maximo + "): ");
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
            System.out.println("\nPercentual invalido! Digite um numero de " + minimo + " a " + maximo + ".");
        }
    }

    /** Explica o jogo ao jogador. */
    private void exibirSobre() {
        System.out.println();
        System.out.println("--- SOBRE O JOGO ---");
        System.out.println("Bolonha e Milano e um jogo de estrategia economica medieval.");
        System.out.println("Voce governa uma cidade-estado italiana: administra florins,");
        System.out.println("terras, graos e populacao, define impostos, constroi e recruta");
        System.out.println("soldados, competindo com outros governantes para prosperar.");
    }
}
