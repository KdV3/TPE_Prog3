package maintpe;

public class Paquete {
    private final int id;
    private final String codigoPaquete;
    private final int pesoKg;
    private final boolean contieneAlimentos;
    private final int nivelUrgencia;

    public Paquete(int id, String codigoPaquete, int pesoKg, boolean contieneAlimentos, int nivelUrgencia) {
        this.id = id;
        this.codigoPaquete = codigoPaquete;
        this.pesoKg = pesoKg;
        this.contieneAlimentos = contieneAlimentos;
        this.nivelUrgencia = nivelUrgencia;
    }

    public int getId() {
        return id;
    }

    public String getCodigoPaquete() {
        return codigoPaquete;
    }

    public int getPesoKg() {
        return pesoKg;
    }

    public boolean isContieneAlimentos() {
        return contieneAlimentos;
    }

    public int getNivelUrgencia() {
        return nivelUrgencia;
    }

    @Override
    public String toString() {
        return "ID paquete: " + this.id + " Codigo: " + this.codigoPaquete + " Peso: " + this.pesoKg + " Urgencia: " +
                this.nivelUrgencia + " Contiene alimentos: " + this.contieneAlimentos;
    }

}
