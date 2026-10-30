package bolonhaemilano;

/**
 * Agricultura: processa a colheita do ano e a distribuicao de alimentos.
 * Nao guarda nenhuma Cidade; recebe a cidade como parametro em cada operacao.
 */
public class Agricultura {

    private static final int HECTARES_POR_SERVO = 5;
    private static final int RENDIMENTO_POR_HECTARE = 2;
    private static final int SERVOS_POR_GRAO = 5;
    private static final int CRESCIMENTO_PERCENTUAL = 5;
    private static final int PERCENTUAL_MINIMO = 20;
    private static final int PERCENTUAL_MAXIMO = 80;

    public int getPercentualMinimo() {
        return PERCENTUAL_MINIMO;
    }

    public int getPercentualMaximo() {
        return PERCENTUAL_MAXIMO;
    }

    /**
     * Colhe: hectares cultivaveis = min(terras, servos x 5); gasta sementes
     * (hectares / 2, reduzidas se faltar grao) e produz hectares x 2 graos.
     * Devolve os hectares cultivados.
     */
    public int processarColheita(Cidade cidade) {
        int hectares = Math.min(cidade.getTerras(), cidade.getServos() * HECTARES_POR_SERVO);
        int sementes = hectares / 2;
        if (sementes > cidade.getGraos()) {
            sementes = cidade.getGraos();
            hectares = sementes * 2;
        }
        cidade.removerGraos(sementes);
        int producao = hectares * RENDIMENTO_POR_HECTARE;
        cidade.adicionarGraos(producao);
        return hectares;
    }

    /**
     * Distribui um percentual (20 a 80) do estoque de graos a populacao.
     * Se o alimento cobrir a necessidade (servos / 5), a populacao cresce 5%;
     * senao, perde o deficit em servos (nunca abaixo de zero).
     * Devolve false se o percentual for invalido.
     */
    public boolean distribuirAlimentos(Cidade cidade, int percentual) {
        if (percentual < PERCENTUAL_MINIMO || percentual > PERCENTUAL_MAXIMO) {
            return false;
        }
        int alimentoDistribuido = (int) ((long) cidade.getGraos() * percentual / 100);
        cidade.removerGraos(alimentoDistribuido);
        int necessidade = cidade.getServos() / SERVOS_POR_GRAO;
        if (alimentoDistribuido >= necessidade) {
            int crescimento = cidade.getServos() * CRESCIMENTO_PERCENTUAL / 100;
            cidade.adicionarServos(crescimento);
        } else {
            int deficit = necessidade - alimentoDistribuido;
            cidade.removerServos(Math.min(deficit, cidade.getServos()));
        }
        return true;
    }
}
