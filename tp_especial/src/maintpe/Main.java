package maintpe;

public class Main {
    public static void main(String[] args) {
        String pathCamiones;
        String pathPaquetes;

        if (args.length >= 2) {
            pathCamiones = args[0];
            pathPaquetes = args[1];
        } else {
            pathCamiones = "src/datasets/Camiones.csv";
            pathPaquetes = "src/datasets/Paquetes.csv";
        }  

        Servicios servicios = new Servicios(pathCamiones, pathPaquetes);
        String codigo_paquete = "P001";

        System.out.println("=== Servicio 1: búsqueda por código ===");
        Paquete p = servicios.servicio1(codigo_paquete);
        if (p != null) {
            imprimirPaquete(p);
        } else {
            System.out.println("No existe paquete con código " + codigo_paquete);
        }

        Paquete inexistente = servicios.servicio1("NO_EXISTE");
        System.out.print("Código NO_EXISTE -> ");
        imprimirPaquete(inexistente);

        System.out.println("\n=== Servicio 2: con alimentos ===");
        for (Paquete x : servicios.servicio2(true)) {
            imprimirPaquete(x);
        }
        System.out.println("\n=== Servicio 2: sin alimentos ===");
        for (Paquete x : servicios.servicio2(false)) {
            imprimirPaquete(x);
        }

        System.out.println("\n=== Servicio 3: urgencia 80 a 100 (inclusive) ===");
        for (Paquete x : servicios.servicio3(80, 100)) {
            imprimirPaquete(x);
        }

        System.out.println("\n==================== Segunda Parte ====================");
        ejecutarSegundaParte(servicios);
    }

    private static void ejecutarSegundaParte(Servicios servicios) {
        Backtracking backtracking = new Backtracking(servicios.getCamiones(), servicios.getPaquetes());
        Solucion solucionBacktracking = backtracking.resolver();
        if (solucionBacktracking != null) {
            System.out.println();
            solucionBacktracking.imprimir("Backtracking",
                    "Métrica para analizar el costo de la solución (cantidad de estados generados)");
        } else {
            System.out.println("\nBacktracking");
            System.out.println("No se encontró solución con Backtracking.");
        }

        Greedy greedy = new Greedy();
        Solucion solucionGreedy = greedy.resolver(servicios.getCamiones(), servicios.getPaquetes());
        System.out.println();
        solucionGreedy.imprimir("Greedy",
                "Métrica para analizar el costo de la solución (cantidad de candidatos considerados)");
    }

    private static void imprimirPaquete(Paquete p) {
        System.out.println(p);
    }
   
}

