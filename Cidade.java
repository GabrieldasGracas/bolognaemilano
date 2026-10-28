package bolonhaemilano;

/**
 * Representa a cidade governada por um Jogador.
 * Guarda ano atual e os recursos: florins, terras (hectares) e graos.
 * Os metodos remover* devolvem false (sem alterar nada) se nao houver
 * quantidade suficiente, para que nenhum recurso fique negativo.
 */
public class Cidade {

    private String nome;
    private int anoAtual;
    private Jogador dono;
    private int florins;
    private int terras;
    private int graos;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = 1400;
        this.florins = 1000;
        this.terras = 10000;
        this.graos = 500;
    }

    public String getNome() {
        return nome;
    }

    public int getAnoAtual() {
        return anoAtual;
    }

    public Jogador getDono() {
        return dono;
    }

    public int getFlorins() {
        return florins;
    }

    public int getTerras() {
        return terras;
    }

    public int getGraos() {
        return graos;
    }

    /** Incrementa o ano em 1 (so sera usado a partir da Etapa 7). */
    public void avancarAno() {
        anoAtual++;
    }

    public void adicionarFlorins(int quantidade) {
        florins += quantidade;
    }

    public boolean removerFlorins(int quantidade) {
        if (quantidade < 0 || quantidade > florins) {
            return false;
        }
        florins -= quantidade;
        return true;
    }

    public void adicionarTerras(int quantidade) {
        terras += quantidade;
    }

    public boolean removerTerras(int quantidade) {
        if (quantidade < 0 || quantidade > terras) {
            return false;
        }
        terras -= quantidade;
        return true;
    }

    public void adicionarGraos(int quantidade) {
        graos += quantidade;
    }

    public boolean removerGraos(int quantidade) {
        if (quantidade < 0 || quantidade > graos) {
            return false;
        }
        graos -= quantidade;
        return true;
    }

    /** Imprime nome, governante, ano e os tres recursos. */
    public void exibirStatus() {
        System.out.println();
        System.out.println("--- STATUS DA CIDADE ---");
        System.out.println("Cidade: " + nome);
        System.out.println("Governante: " + dono.getTitulo() + " " + dono.getNome());
        System.out.println("Ano atual: " + anoAtual);
        System.out.println("Florins: " + florins);
        System.out.println("Terras: " + terras + " hectares");
        System.out.println("Graos: " + graos + " unidades");
    }
}
