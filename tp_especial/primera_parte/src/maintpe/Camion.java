package maintpe;

public class Camion {
    private final int id;
    private final String patente;
    private final boolean refrigerado;
    private final int capacidadKg;

    public Camion(int id, String patente, boolean refrigerado, int capacidadKg) {
        this.id = id;
        this.patente = patente;
        this.refrigerado = refrigerado;
        this.capacidadKg = capacidadKg;
    }

    public int getId() {
        return id;
    }

    public String getPatente() {
        return patente;
    }

    public boolean isRefrigerado() {
        return refrigerado;
    }

    public int getCapacidadKg() {
        return capacidadKg;
    }
}
