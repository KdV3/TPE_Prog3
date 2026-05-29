package maintpe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Servicios {

    private final List<Camion> camiones;
    private final Map<String, Paquete> paquetesPorCodigo;
    private final List<Paquete> paquetesConAlimentos;
    private final List<Paquete> paquetesSinAlimentos;
    // indice i = nivel de urgencia i (1..100), 0 no se utiliza
    //private final List<List<Paquete>> paquetesPorUrgencia;
    private Map<Integer, List<Paquete>> paquetesPorUrgencia; /*  Cambio la lista de listas de paquetes por urgencia por un hashmap.
                                                                con key urgencia y value de listas de paquetes. Se crean las listas
                                                                cuando hay un paquete con ese nivel de urgencia*/
    /*
     * complejidad temporal: O(C + P), con C cantidad de camiones y P cantidad de paquetes
     * leo cada línea de ambos archivos una vez y se inserta cada paquete en distintas estructuras 
     * (HashMap y listas por bucket de urgencia en rango 1..100)
     */
    public Servicios(String pathCamiones, String pathPaquetes) {
        this.camiones = new ArrayList<>();
        this.paquetesPorCodigo = new HashMap<>();
        this.paquetesConAlimentos = new ArrayList<>();
        this.paquetesSinAlimentos = new ArrayList<>();
        this.paquetesPorUrgencia = new HashMap<>();

        /*this.paquetesPorUrgencia = new ArrayList<>(101);
        //for (int i = 0; i <= 100; i++) { <-- Si se llegara a hacer esto en vez del hashmap, el valor mínimo y máximo del rango tendrían
                                                que ser variables.
        //    this.paquetesPorUrgencia.add(new ArrayList<>());
        }*/

        try {
            cargarCamiones(pathCamiones);
            cargarPaquetes(pathPaquetes);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /*
     * complejidad temporal: O(1) amortizado — búsqueda en tabla hash por código.
     */
    public Paquete servicio1(String codigoPaquete) {
        if (codigoPaquete == null) {
            return null;
        }
        return paquetesPorCodigo.get(codigoPaquete);
    }

    /*
     * complejidad temporal: O(n), con n la cantidad de paquetes que cumplen el
     * criterio, se devuelve una copia de la lista armada previamente en el constructor
     */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
        List<Paquete> origen = contieneAlimentos ? paquetesConAlimentos : paquetesSinAlimentos;
        return new ArrayList<>(origen);
    }

    /*
     * complejidad temporal: O((max - min + 1) + r), con r la cantidad de paquetes
     * en el rango de urgencia se concatenan los buckets de urgencia previamente armados
     * si min > max, se retorna lista vacía.
     */

    /*public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        if (urgenciaMinima > urgenciaMaxima) {
            return new ArrayList<>();
        }
        int desde = Math.max(1, urgenciaMinima);
        int hasta = Math.min(100, urgenciaMaxima);
        if (desde > hasta) {
            return new ArrayList<>();
        }
        List<Paquete> resultado = new ArrayList<>();
        for (int u = desde; u <= hasta; u++) {
            resultado.addAll(paquetesPorUrgencia.get(u));
        }
        return resultado;
    }*/


    /* complejidad temporal: O((max - min + 1) + r), siendo  r la cantidad de paquetes en el rango de urgencia */

    /*Comentario aparte: No estoy seguro sobre la complejidad temporal, pareciera ser la mísma solo que cambia como va a estar creada 
    la lista de paquetes por urgencia.
    A diferencia del otro método servicio3, no agregué la verificación de min > max, ya que si eso llega a ocurrir, no se debería
    entrar al bucle for y tendría que retornar un arreglo vacío de todas formas*/
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        ArrayList<Paquete> paquetes = new ArrayList<>();
        for (int i=urgenciaMinima; i<=urgenciaMaxima; i++){
            if (paquetesPorUrgencia.get(i) != null){
               paquetes.addAll(paquetesPorUrgencia.get(i)); 
            }
        }
        return paquetes;
    }

    private void cargarCamiones(String path) throws IOException {
        List<String[]> lineas = leerCsv(path);
        if (lineas.isEmpty()) {
            return;
        }
        for (int i = 1; i < lineas.size(); i++) {
            String[] campos = lineas.get(i);
            if (campos.length < 4) {
                continue;
            }
            int id = Integer.parseInt(campos[0].trim());
            String patente = campos[1].trim();
            boolean refrigerado = parseBooleano(campos[2].trim());
            int capacidad = Integer.parseInt(campos[3].trim());
            camiones.add(new Camion(id, patente, refrigerado, capacidad));
        }
    }

    private void cargarPaquetes(String path) throws IOException {
        List<String[]> lineas = leerCsv(path);
        if (lineas.isEmpty()) {
            return;
        }
        for (int i = 1; i < lineas.size(); i++) {
            String[] campos = lineas.get(i);
            if (campos.length < 5) {
                continue;
            }
            int id = Integer.parseInt(campos[0].trim());
            String codigo = campos[1].trim();
            int peso = Integer.parseInt(campos[2].trim());
            boolean alimentos = parseBooleano(campos[3].trim());
            int urgencia = Integer.parseInt(campos[4].trim());

            Paquete paquete = new Paquete(id, codigo, peso, alimentos, urgencia);
            paquetesPorCodigo.put(codigo, paquete);
            if (alimentos) {
                paquetesConAlimentos.add(paquete);
            } else {
                paquetesSinAlimentos.add(paquete);
            }
            /*if (urgencia >= 1 && urgencia <= 100) {
                paquetesPorUrgencia.get(urgencia).add(paquete);
            } */

            if (paquetesPorUrgencia.get(urgencia) == null){ //Si la key urgencia es null en el hashmap, crea un arreglo en esa posición.
                paquetesPorUrgencia.put(urgencia, new ArrayList<>());
                paquetesPorUrgencia.get(urgencia).add(paquete);
            } else { //si no es null, que agregue el paquete a la lista con ese nivel de urgencia.
                paquetesPorUrgencia.get(urgencia).add(paquete); 
            }
                
        }
    }

    private static boolean parseBooleano(String valor) {
        return "1".equals(valor) || "true".equalsIgnoreCase(valor);
    }

    private static List<String[]> leerCsv(String path) throws IOException {
        List<String[]> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }
                lineas.add(linea.split(";"));
            }
        }
        return lineas;
    }
}
