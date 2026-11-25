package bolonhaemilano;

public class Construcoes {

    private static final int PRECO_SOLDADO = 5;
    private static final int SERVOS_POR_SOLDADO = 1;
    private static final int PRECO_CONSTRUCAO = 20;
    private static final int PRECO_CONSTRUCAO_MILANO = 16; // -20% de custo para Milano

    public boolean recrutarSoldados(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) {
            return false;
        }

        if (quantidade > Integer.MAX_VALUE / PRECO_SOLDADO) {
            return false;
        }

        int custo = quantidade * PRECO_SOLDADO;
        int servosNecessarios = quantidade * SERVOS_POR_SOLDADO;

        if (cidade.getFlorins() < custo
                || cidade.getServos() < servosNecessarios) {
            return false;
        }

        cidade.removerFlorins(custo);
        cidade.removerServos(servosNecessarios);
        cidade.adicionarSoldados(quantidade);

        return true;
    }

    public boolean construir(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) {
            return false;
        }

        int precoUnitario = getPrecoConstrucaoPara(cidade);

        if (quantidade > Integer.MAX_VALUE / precoUnitario) {
            return false;
        }

        int custo = quantidade * precoUnitario;

        if (!cidade.removerFlorins(custo)) {
            return false;
        }

        cidade.adicionarConstrucoes(quantidade);

        return true;
    }

    public int getPrecoSoldado() {
        return PRECO_SOLDADO;
    }

    public int getPrecoConstrucao() {
        return PRECO_CONSTRUCAO;
    }

    public int getPrecoConstrucaoPara(Cidade cidade) {
        if (cidade != null
                && (cidade.getNome().equalsIgnoreCase("Milano")
                || cidade.getNome().equalsIgnoreCase("Milao"))) {
            return PRECO_CONSTRUCAO_MILANO;
        }
        return PRECO_CONSTRUCAO;
    }
}