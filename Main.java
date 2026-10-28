package bolonhaemilano;

/**
 * Ponto de entrada do jogo Bolonha e Milano.
 * Apenas cria um Menu e delega a execucao a ele.
 */
public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.executar();
    }
}
