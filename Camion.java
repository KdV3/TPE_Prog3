public class Camion{
    private int id;
    private String patente;
    private boolean estadoRefrigeracion;
    private int capacidad;

    public Camion(){}

    public int getCapacidad() {
        return this.capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatente() {
        return this.patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public boolean estadoRefrigeracion(){
        return this.estadoRefrigeracion;
    }

    public void setEstadoRefrigeracion(boolean estado) {
        this.estadoRefrigeracion = estado;
    }
    
}