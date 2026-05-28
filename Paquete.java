import java.util.Scanner;

public class Paquete{
    private int id;
    private String codigo;
    private int peso;
    private boolean contieneAlimentos;
    private int urgencia;

    public Paquete(Scanner sc){
        this.id = sc.nextInt();
        this.codigo = sc.next();
        this.peso = sc.nextInt();
        int estado = sc.nextInt();

        if (estado == 1){
            this.contieneAlimentos = true;
        } else {
            this.contieneAlimentos = false;
        }

        this.urgencia = sc.nextInt();
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeso() {
        return this.peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    
    public int getUrgencia() {
        return this.urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }

    public boolean contieneAlimentos(){
        return this.contieneAlimentos;
    }

    public void setEstadoAlimentos(boolean estado) {
        this.contieneAlimentos = estado;
    }

    @Override
    public String toString() {
        return "ID paquete: " + this.id + " Codigo: " + this.codigo + " Peso: " + this.peso + " Urgencia: " +
                this.urgencia + " Contiene alimentos: " + this.contieneAlimentos;
    }
}