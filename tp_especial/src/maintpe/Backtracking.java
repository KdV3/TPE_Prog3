package maintpe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Backtracking {

    private final List<Camion> camiones;
    private final List<Paquete> paquetes;
    private final int[] cargaActualPorCamion;
    private final List<List<Paquete>> asignacionActual;
    private final List<Paquete> noAsignadosActual;

    private List<List<Paquete>> mejorAsignacion;
    private List<Paquete> mejorNoAsignados;
    private int estadosGenerados;
    private int pesoNoAsignadoActual;
    private int mejorPesoNoAsignado;

    public Backtracking(List<Camion> camiones, List<Paquete> paquetes) {
        this.camiones = new ArrayList<>(camiones);
        this.paquetes = new ArrayList<>(paquetes);
        this.paquetes.sort(Comparator.comparingInt(Paquete::getPesoKg).reversed());
        this.cargaActualPorCamion = new int[camiones.size()];
        this.asignacionActual = Solucion.crearAsignacionesVacias(camiones.size());
        this.noAsignadosActual = new ArrayList<>();
        this.mejorPesoNoAsignado = pesoTotal(this.paquetes);
    }

    /*
     * estrategia backtracking:
     * se procesan los paquetes en orden decreciente de peso para encontrar antes soluciones en las que quede poco peso sin asignar
     * en cada paso, para el paquete actual se generan dos ramas: dejarlo sin asignar o asignarlo a cada camión factible
     * un estado es solución cuando se procesaron todos los paquetes; se conserva la asignación con menor peso no asignado
     * se poda cuando el peso acumulado sin asignar ya es mayor o igual al mejor encontrado
     *
     * complejidad temporal: O(C^P) en el peor caso, con C cantidad de camiones y P cantidad de paquetes
     */
    public Solucion resolver() {
        estadosGenerados = 0;
        mejorAsignacion = null;
        mejorNoAsignados = null;
        pesoNoAsignadoActual = 0;
        noAsignadosActual.clear();
        for (List<Paquete> lista : asignacionActual) {
            lista.clear();
        }
        backtrack(0);

        if (mejorAsignacion == null) {
            return null;
        }
        return new Solucion(camiones, copiarAsignaciones(mejorAsignacion),
                new ArrayList<>(mejorNoAsignados), estadosGenerados);
    }

    private void backtrack(int indicePaquete) {
        estadosGenerados++;

        if (pesoNoAsignadoActual >= mejorPesoNoAsignado) {
            return;
        }

        if (indicePaquete == paquetes.size()) {
            if (mejorAsignacion == null || pesoNoAsignadoActual < mejorPesoNoAsignado) {
                mejorPesoNoAsignado = pesoNoAsignadoActual;
                mejorAsignacion = copiarAsignaciones(asignacionActual);
                mejorNoAsignados = new ArrayList<>(noAsignadosActual);
            }
            return;
        }

        Paquete paquete = paquetes.get(indicePaquete);

        noAsignadosActual.add(paquete);
        pesoNoAsignadoActual += paquete.getPesoKg();
        backtrack(indicePaquete + 1);
        pesoNoAsignadoActual -= paquete.getPesoKg();
        noAsignadosActual.remove(noAsignadosActual.size() - 1);

        for (int i = 0; i < camiones.size(); i++) {
            if (!esFactible(paquete, i)) {
                continue;
            }

            asignacionActual.get(i).add(paquete);
            cargaActualPorCamion[i] += paquete.getPesoKg();
            backtrack(indicePaquete + 1);
            cargaActualPorCamion[i] -= paquete.getPesoKg();
            asignacionActual.get(i).remove(asignacionActual.get(i).size() - 1);
        }
    }

    private boolean esFactible(Paquete paquete, int indiceCamion) {
        Camion camion = camiones.get(indiceCamion);
        if (paquete.isContieneAlimentos() && !camion.isRefrigerado()) {
            return false;
        }
        return cargaActualPorCamion[indiceCamion] + paquete.getPesoKg() <= camion.getCapacidadKg();
    }

    private static List<List<Paquete>> copiarAsignaciones(List<List<Paquete>> origen) {
        List<List<Paquete>> copia = new ArrayList<>();
        for (List<Paquete> paquetes : origen) {
            copia.add(new ArrayList<>(paquetes));
        }
        return copia;
    }

    private static int pesoTotal(List<Paquete> paquetes) {
        int total = 0;
        for (Paquete paquete : paquetes) {
            total += paquete.getPesoKg();
        }
        return total;
    }

    public int getEstadosGenerados() {
        return estadosGenerados;
    }
}
