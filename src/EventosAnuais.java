package bolonhaemilano;

import java.util.Random;

/**
 * Sorteador de eventos anuais (bonus e mazelas) aplicados as cidades
 * na virada de cada ano.
 */
public class EventosAnuais {

    private final Random random;

    public EventosAnuais() {
        this.random = new Random();
    }

    public EventosAnuais(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Sorteia e aplica um evento anual para a cidade informada,
     * retornando a mensagem descritiva do ocorrido.
     */
    public String sortearEAplicarEvento(Cidade cidade) {
        if (cidade == null) {
            return "Nenhum evento ocorreu.";
        }

        int sorteio = random.nextInt(6);

        switch (sorteio) {
            case 0: {
                int bonusGraos = 150;
                cidade.adicionarGraos(bonusGraos);
                return "Colheita farta em " + cidade.getNome()
                        + ": +" + bonusGraos + " graos nos celeiros.";
            }
            case 1: {
                int bonusFlorins = 200;
                cidade.adicionarFlorins(bonusFlorins);
                return "Feira comercial prospera em " + cidade.getNome()
                        + ": +" + bonusFlorins + " florins no tesouro.";
            }
            case 2: {
                int novosServos = Math.max(5, cidade.getServos() / 15);
                cidade.adicionarServos(novosServos);
                return "Onda migratoria para " + cidade.getNome()
                        + ": +" + novosServos + " servos chegaram a cidade.";
            }
            case 3: {
                int perdasServos = Math.min(
                        cidade.getServos() - 1,
                        Math.max(0, cidade.getServos() / 10)
                );
                if (perdasServos > 0) {
                    cidade.removerServos(perdasServos);
                }
                return "Surto de doenca em " + cidade.getNome()
                        + ": -" + perdasServos + " servos.";
            }
            case 4: {
                int perdasGraos = Math.min(cidade.getGraos(), 120);
                if (perdasGraos > 0) {
                    cidade.removerGraos(perdasGraos);
                }
                return "Praga nos celeiros de " + cidade.getNome()
                        + ": -" + perdasGraos + " graos perdidos.";
            }
            default:
                return "Ano tranquilo em " + cidade.getNome()
                        + ": nenhum evento extraordinario.";
        }
    }
}