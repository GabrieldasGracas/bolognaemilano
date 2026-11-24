package bolonhaemilano;

public class Governo {

    private static final int TAXA_MAXIMA = 50;

    private static final double MULTIPLICADOR_JUSTA = 0.8;
    private static final double MULTIPLICADOR_MODERADA = 1.0;
    private static final double MULTIPLICADOR_SEVERA = 1.3;

    public int arrecadarImpostos(Cidade cidade) {

        if (cidade.isImpostosArrecadados()) {
            return -1;
        }

        int base = cidade.getServos()
                * cidade.getTaxaImposto() / 100;

        double multiplicador = multiplicadorPara(
                cidade.getNivelJustica()
        );

        int arrecadado = (int) (base * multiplicador);

        cidade.adicionarFlorins(arrecadado);
        cidade.marcarImpostosArrecadados();

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