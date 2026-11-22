package bolonhaemilano;

/**
 * Representa a cidade governada por um Jogador.
 * Guarda ano atual e os recursos: florins, terras (hectares), graos e servos (populacao).
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
    private int servos;
    private int taxaImposto;
    private String nivelJustica;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = 1400;
        this.florins = 1000;
        this.terras = 10000;
        this.graos = 500;
        this.servos = 200;
        this.taxaImposto = 10;
    this.nivelJustica = "Moderada";
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

    public int getServos() {
        return servos;
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

    public void adicionarServos(int quantidade) {
        servos += quantidade;
    }

    public boolean removerServos(int quantidade) {
        if (quantidade < 0 || quantidade > servos) {
            return false;
        }
        servos -= quantidade;
        return true;
    }

    /** Imprime nome, governante, ano e os quatro recursos. */
    public void exibirStatus() {
        System.out.println();
        System.out.println("--- STATUS DA CIDADE ---");
        System.out.println("Cidade: " + nome);
        System.out.println("Governante: " + dono.getTitulo() + " " + dono.getNome());
        System.out.println("Ano atual: " + anoAtual);
        System.out.println("Florins: " + florins);
        System.out.println("Terras: " + terras + " hectares");
        System.out.println("Graos: " + graos + " unidades");
        System.out.println("Servos: " + servos);
        System.out.println("Taxa de imposto: " + taxaImposto + "%");
        System.out.println("Nível de justiça: " + nivelJustica);
    }

    public int getTaxaImposto() {
    return taxaImposto;
    }

    public void setTaxaImposto(int taxaImposto) {
        this.taxaImposto = taxaImposto;
    }

    public String getNivelJustica() {
        return nivelJustica;
    }

    public void setNivelJustica(String nivelJustica) {
        this.nivelJustica = nivelJustica;
    }


public class Governo {

    private static final int TAXA_MAXIMA = 50;

    private static final double MULTIPLICADOR_JUSTA = 0.8;
    private static final double MULTIPLICADOR_MODERADA = 1.0;
    private static final double MULTIPLICADOR_SEVERA = 1.3;

    public int arrecadarImpostos(Cidade cidade) {

        int base = cidade.getServos()
                * cidade.getTaxaImposto() / 100;

        double multiplicador = multiplicadorPara(
                cidade.getNivelJustica()
        );

        int arrecadado = (int) (base * multiplicador);

        cidade.adicionarFlorins(arrecadado);

        return arrecadado;
    }

    public boolean definirTaxaImposto(Cidade cidade, int taxa) {

        if (taxa < 0 || taxa > TAXA_MAXIMA) {
            return false;
        }

        cidade.setTaxaImposto(taxa);

        return true;
    }

    public boolean definirJustica(Cidade cidade, String nivel) {

        if (nivel == null) {
            return false;
        }

        if (!nivel.equalsIgnoreCase("Justa")
                && !nivel.equalsIgnoreCase("Moderada")
                && !nivel.equalsIgnoreCase("Severa")) {
            return false;
        }

        if (nivel.equalsIgnoreCase("Justa")) {
            cidade.setNivelJustica("Justa");
        } else if (nivel.equalsIgnoreCase("Moderada")) {
            cidade.setNivelJustica("Moderada");
        } else {
            cidade.setNivelJustica("Severa");
        }

        return true;
    }

    private double multiplicadorPara(String nivel) {

        if (nivel.equals("Justa")) {
            return MULTIPLICADOR_JUSTA;
        }

        if (nivel.equals("Moderada")) {
            return MULTIPLICADOR_MODERADA;
        }

        if (nivel.equals("Severa")) {
            return MULTIPLICADOR_SEVERA;
        }

        return MULTIPLICADOR_MODERADA;
    }
}

}
