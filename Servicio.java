import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class Servicio {
    private Hashtable<String,Paquete> paquetes;
    // private Hashtable<String,Camion> camiones;
    private String pathCamiones;
    private String pathPaquetes;

    /* 
     * Expresar la complejidad temporal del constructor. 
     */ 
    public Servicio(String pathCamiones, String pathPaquetes) {
        this.pathCamiones = pathCamiones;
        this.pathPaquetes = pathPaquetes;
        this.paquetes = new Hashtable<>();
    } 
 
    public void cargarPaquetes() throws IOException {
        File archivo = new File(this.pathPaquetes);
        Scanner scanner = new Scanner(archivo);
        scanner.useDelimiter(";");
        while(scanner.hasNextLine()){
            Paquete aux = new Paquete(scanner);
            this.paquetes.put(aux.getCodigo(),aux);
            scanner.nextLine();
        }
    }

    /* 
     * Complejidad temporal tiempo constante de Ө(1). 
     */ 
    public Paquete servicio1(String codigoPaquete)
    {
        return this.paquetes.get(codigoPaquete);
    } 
 
    /* 
     * Expresar la complejidad temporal del servicio 2. 
     */ 
    //public List<Paquete> servicio2(boolean contieneAlimentos) { 
//} 
 
    /* 
     * Expresar la complejidad temporal del servicio 3. 
     */ 
    //public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) { } 
 
} 
