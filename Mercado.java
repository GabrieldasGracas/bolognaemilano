package bolonhaemilano;

/**
 * Mercado: compra e vende graos e terras a precos fixos.
 * Nao guarda nenhuma Cidade; recebe a cidade como parametro em cada operacao.
 */
public class Mercado {

    private static final int PRECO_GRAO = 5;    // florins por unidade de grao
    private static final int PRECO_TERRA = 20;  // florins por hectare

    public int getPrecoGrao() {
        return PRECO_GRAO;
    }

    public int getPrecoTerra() {
        return PRECO_TERRA;
    }

    public boolean comprarGraos(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }
        long custo = (long) quantidade * PRECO_GRAO;
        if (custo > Integer.MAX_VALUE || !cidade.removerFlorins((int) custo)) {
            return false;
        }
        cidade.adicionarGraos(quantidade);
        return true;
    }

    public boolean venderGraos(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }
        if (!cidade.removerGraos(quantidade)) {
            return false;
        }
        cidade.adicionarFlorins(quantidade * PRECO_GRAO);
        return true;
    }

    public boolean comprarTerras(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }
        long custo = (long) quantidade * PRECO_TERRA;
        if (custo > Integer.MAX_VALUE || !cidade.removerFlorins((int) custo)) {
            return false;
        }
        cidade.adicionarTerras(quantidade);
        return true;
    }

    public boolean venderTerras(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }
        if (!cidade.removerTerras(quantidade)) {
            return false;
        }
        cidade.adicionarFlorins(quantidade * PRECO_TERRA);
        return true;
    }
}
