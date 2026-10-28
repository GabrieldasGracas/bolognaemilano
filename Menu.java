package bolonhaemilano;

import java.util.Scanner;

/**
 * Menu principal do jogo: Novo jogo, Ver status da cidade, Sobre o jogo e Sair.
 * Cada metodo tem uma unica responsabilidade.
 */
public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.emExecucao = true;
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
        System.out.println("3 - Sobre o jogo");
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
                exibirStatusCidade();
                break;
            case 3:
                exibirSobre();
                break;
            case 0:
                System.out.println("\nAte logo!");
                emExecucao = false;
                break;
            default:
                System.out.println("\nOpcao invalida! Digite 1, 2, 3 ou 0.");
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
    private void exibirStatusCidade() {
        if (cidade == null) {
            System.out.println("\nNenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
        } else {
            cidade.exibirStatus();
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
