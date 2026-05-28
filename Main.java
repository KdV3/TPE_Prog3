import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        String paq = "P001";
        String paq2 = "P009";
        String paq3 = "P002";
        Servicio s1 = new Servicio("Camiones.csv","Paquetes.csv");
        s1.cargarPaquetes();
        System.out.println(s1.servicio1(paq));
        System.out.println(s1.servicio1(paq2));
        System.out.println(s1.servicio1(paq3));
    }
}