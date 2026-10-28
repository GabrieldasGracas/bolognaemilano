package bolonhaemilano;

import java.util.Scanner;

/**
 * Menu principal do jogo: Novo jogo, Sobre o jogo e Sair.
 * Cada metodo tem uma unica responsabilidade.
 */
public class Menu {

    private Scanner scanner;
    private boolean emExecucao;

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
        System.out.println("2 - Sobre o jogo");
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
                System.out.println("\nO novo jogo sera implementado na Etapa 2.");
                break;
            case 2:
                exibirSobre();
                break;
            case 0:
                System.out.println("\nAte logo!");
                emExecucao = false;
                break;
            default:
                System.out.println("\nOpcao invalida! Digite 1, 2 ou 0.");
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
