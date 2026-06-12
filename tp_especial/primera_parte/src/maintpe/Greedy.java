package maintpe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Greedy {

    private int candidatosConsiderados;

    /*
     * estrategia greedy:
     * los candidatos son los camiones factibles para cada paquete (capacidad disponible y refrigeración si corresponde)
     * se ordenan los paquetes por peso descendente para asignar primero los más difíciles de ubicar
     * para cada paquete se elige el camión factible con menor espacio restante luego de la asignación
     * buscando aprovechar mejor la capacidad disponible
     * si ningún camión puede recibir el paquete, queda sin asignar
     * no garantiza la solución óptima, pero reduce el peso no asignado con bajo costo computacional
     *
     * complejidad temporal: O(P * C), con P cantidad de paquetes y C cantidad de camiones
     */
    public Solucion resolver(List<Camion> camiones, List<Paquete> paquetes) {
        candidatosConsiderados = 0;

        List<Camion> camionesTrabajo = new ArrayList<>(camiones);
        List<Paquete> paquetesOrdenados = new ArrayList<>(paquetes);
        paquetesOrdenados.sort(Comparator.comparingInt(Paquete::getPesoKg).reversed());

        int[] cargaPorCamion = new int[camionesTrabajo.size()];
        List<List<Paquete>> asignaciones = Solucion.crearAsignacionesVacias(camionesTrabajo.size());
        List<Paquete> noAsignados = new ArrayList<>();

        for (Paquete paquete : paquetesOrdenados) {
            int indiceCamionElegido = seleccionarCamion(paquete, camionesTrabajo, cargaPorCamion);

            if (indiceCamionElegido == -1) {
                noAsignados.add(paquete);
            } else {
                asignaciones.get(indiceCamionElegido).add(paquete);
                cargaPorCamion[indiceCamionElegido] += paquete.getPesoKg();
            }
        }

        return new Solucion(camionesTrabajo, asignaciones, noAsignados, candidatosConsiderados);
    }

    private int seleccionarCamion(Paquete paquete, List<Camion> camiones, int[] cargaPorCamion) {
        int mejorIndice = -1;
        int menorEspacioRestante = Integer.MAX_VALUE;

        for (int i = 0; i < camiones.size(); i++) {
            candidatosConsiderados++;

            if (!esFactible(paquete, camiones.get(i), cargaPorCamion[i])) {
                continue;
            }

            int espacioRestante = camiones.get(i).getCapacidadKg() - cargaPorCamion[i] - paquete.getPesoKg();
            if (espacioRestante < menorEspacioRestante) {
                menorEspacioRestante = espacioRestante;
                mejorIndice = i;
            }
        }

        return mejorIndice;
    }

    private boolean esFactible(Paquete paquete, Camion camion, int cargaActual) {
        if (paquete.isContieneAlimentos() && !camion.isRefrigerado()) {
            return false;
        }
        return cargaActual + paquete.getPesoKg() <= camion.getCapacidadKg();
    }

    public int getCandidatosConsiderados() {
        return candidatosConsiderados;
    }
}
