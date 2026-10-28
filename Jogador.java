package bolonhaemilano;

/**
 * Representa o jogador (governante da cidade).
 * Comeca com o titulo de nobreza "Cavaleiro".
 */
public class Jogador {

    private String nome;
    private String titulo;

    public Jogador(String nome) {
        this.nome = nome;
        this.titulo = "Cavaleiro";
    }

    public String getNome() {
        return nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
