package maintpe;

import java.util.ArrayList;
import java.util.List;

public class Solucion {

    private final List<Camion> camiones;
    private final List<List<Paquete>> paquetesPorCamion;
    private final List<Paquete> paquetesNoAsignados;
    private final int pesoNoAsignado;
    private final int metrica;

    public Solucion(List<Camion> camiones, List<List<Paquete>> paquetesPorCamion,
                    List<Paquete> paquetesNoAsignados, int metrica) {
        this.camiones = camiones;
        this.paquetesPorCamion = paquetesPorCamion;
        this.paquetesNoAsignados = paquetesNoAsignados;
        this.pesoNoAsignado = calcularPesoNoAsignado(paquetesNoAsignados);
        this.metrica = metrica;
    }

    private static int calcularPesoNoAsignado(List<Paquete> paquetesNoAsignados) {
        int peso = 0;
        for (Paquete paquete : paquetesNoAsignados) {
            peso += paquete.getPesoKg();
        }
        return peso;
    }

    public int getPesoNoAsignado() {
        return pesoNoAsignado;
    }

    public int getMetrica() {
        return metrica;
    }

    public void imprimir(String nombreAlgoritmo, String nombreMetrica) {
        System.out.println(nombreAlgoritmo);
        System.out.println("Solución obtenida:");
        for (int i = 0; i < camiones.size(); i++) {
            System.out.println("  " + camiones.get(i) + " -> " + paquetesPorCamion.get(i));
        }
        if (!paquetesNoAsignados.isEmpty()) {
            System.out.println("  Paquetes no asignados: " + paquetesNoAsignados);
        }
        System.out.println("Peso no asignado: " + pesoNoAsignado + " kg.");
        System.out.println(nombreMetrica + ": " + metrica);
    }

    public static List<List<Paquete>> crearAsignacionesVacias(int cantidadCamiones) {
        List<List<Paquete>> asignaciones = new ArrayList<>();
        for (int i = 0; i < cantidadCamiones; i++) {
            asignaciones.add(new ArrayList<>());
        }
        return asignaciones;
    }

    public static Solucion copiarSolucion(List<Camion> camiones, List<List<Paquete>> asignaciones,
                                           List<Paquete> noAsignados, int metrica) {
        List<List<Paquete>> copiaAsignaciones = new ArrayList<>();
        for (List<Paquete> paquetes : asignaciones) {
            copiaAsignaciones.add(new ArrayList<>(paquetes));
        }
        return new Solucion(camiones, copiaAsignaciones, new ArrayList<>(noAsignados), metrica);
    }
}
