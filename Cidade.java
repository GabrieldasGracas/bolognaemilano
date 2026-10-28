package bolonhaemilano;

/**
 * Representa a cidade governada por um Jogador.
 * Ainda sem economia: apenas nome, ano atual e dono.
 */
public class Cidade {

    private String nome;
    private int anoAtual;
    private Jogador dono;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = 1400;
    }

    /** Incrementa o ano em 1 (so sera usado a partir da Etapa 7). */
    public void avancarAno() {
        anoAtual++;
    }

    /** Imprime nome da cidade, governante e ano atual. */
    public void exibirStatus() {
        System.out.println();
        System.out.println("--- STATUS DA CIDADE ---");
        System.out.println("Cidade: " + nome);
        System.out.println("Governante: " + dono.getTitulo() + " " + dono.getNome());
        System.out.println("Ano atual: " + anoAtual);
    }
}
