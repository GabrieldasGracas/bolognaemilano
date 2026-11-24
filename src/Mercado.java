package bolonhaemilano;

/**
 * Mercado: compra e vende graos e terras com precos dinamicos
 * baseados em oferta, demanda e condicoes climaticas (inverno severo).
 */
public class Mercado {

    private static final int PRECO_BASE_GRAO = 5;     // florins por unidade de grao
    private static final int PRECO_BASE_TERRA = 20;   // florins por hectare

    private static final int LIMITE_COMPRA_GRANDE_GRAO = 100;
    private static final int LIMITE_COMPRA_GRANDE_TERRA = 50;

    private int demandaGrao;
    private int demandaTerra;
    private boolean invernoSevero;

    public Mercado() {
        this.demandaGrao = 0;
        this.demandaTerra = 0;
        this.invernoSevero = false;
    }

    public boolean isInvernoSevero() {
        return invernoSevero;
    }

    public void setInvernoSevero(boolean invernoSevero) {
        this.invernoSevero = invernoSevero;
    }

    /**
     * Atualiza as condicoes climaticas e alivia parcialmente a demanda
     * na virada de cada ano.
     */
    public void atualizarAno(int ano) {
        // Inverno severo ocorre ciclicamente (ex.: a cada 3 anos) ou pode ser ativado externamente
        this.invernoSevero = (ano % 3 == 0);

        // Na virada do ano, parte da demanda acumulada se estabiliza
        this.demandaGrao = Math.max(0, this.demandaGrao - 1);
        this.demandaTerra = Math.max(0, this.demandaTerra - 1);
    }

    /**
     * Preco dinamico do grao:
     * - Aumenta conforme a demanda acumulada por grandes compras (+1 florim por nivel de demanda)
     * - No inverno severo, sofre acrescimo de 60% pela escassez de oferta
     */
    public int getPrecoGrao() {
        int preco = PRECO_BASE_GRAO + demandaGrao;
        if (invernoSevero) {
            preco = (int) Math.ceil(preco * 1.6);
        }
        return Math.max(PRECO_BASE_GRAO, preco);
    }

    /**
     * Preco dinamico da terra:
     * - Aumenta +2 florins por nivel de demanda acumulada
     */
    public int getPrecoTerra() {
        return Math.max(PRECO_BASE_TERRA, PRECO_BASE_TERRA + (demandaTerra * 2));
    }

    public boolean comprarGraos(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) {
            return false;
        }

        int precoUnitario = getPrecoGrao();
        long custo = (long) quantidade * precoUnitario;

        if (custo > Integer.MAX_VALUE || !cidade.removerFlorins((int) custo)) {
            return false;
        }

        cidade.adicionarGraos(quantidade);

        // Compra em grande quantidade pressiona a demanda e sobe o preco
        if (quantidade >= LIMITE_COMPRA_GRANDE_GRAO) {
            int incremento = quantidade / LIMITE_COMPRA_GRANDE_GRAO;
            demandaGrao += incremento;
        }

        return true;
    }

    public boolean venderGraos(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) {
            return false;
        }

        int precoUnitario = getPrecoGrao();

        if (!cidade.removerGraos(quantidade)) {
            return false;
        }

        cidade.adicionarFlorins(quantidade * precoUnitario);

        // Venda em grande quantidade aumenta a oferta e reduz a pressao de demanda
        if (quantidade >= LIMITE_COMPRA_GRANDE_GRAO) {
            int reducao = quantidade / LIMITE_COMPRA_GRANDE_GRAO;
            demandaGrao = Math.max(0, demandaGrao - reducao);
        }

        return true;
    }

    public boolean comprarTerras(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) {
            return false;
        }

        int precoUnitario = getPrecoTerra();
        long custo = (long) quantidade * precoUnitario;

        if (custo > Integer.MAX_VALUE || !cidade.removerFlorins((int) custo)) {
            return false;
        }

        cidade.adicionarTerras(quantidade);

        if (quantidade >= LIMITE_COMPRA_GRANDE_TERRA) {
            int incremento = quantidade / LIMITE_COMPRA_GRANDE_TERRA;
            demandaTerra += incremento;
        }

        return true;
    }

    public boolean venderTerras(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) {
            return false;
        }

        int precoUnitario = getPrecoTerra();

        if (!cidade.removerTerras(quantidade)) {
            return false;
        }

        cidade.adicionarFlorins(quantidade * precoUnitario);

        if (quantidade >= LIMITE_COMPRA_GRANDE_TERRA) {
            int reducao = quantidade / LIMITE_COMPRA_GRANDE_TERRA;
            demandaTerra = Math.max(0, demandaTerra - reducao);
        }

        return true;
    }
}