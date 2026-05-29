package maintpe;

public class Main {
    public static void main(String[] args) {
        String pathCamiones;
        String pathPaquetes;
        System.out.println(System.getProperty("user.dir")); //Esto muestra en que ruta estas parado, no es relevante al trabajo.
        
        // Si te paras en la carpeta src no anda. Eso es por la ruta de los archivos en el else. No logré encontrar una solución aún
        // aunque la solución puede que sea que el trabajo siempre se abra en la misma carpeta.
        if (args.length >= 2) {
            pathCamiones = args[0];
            pathPaquetes = args[1];
        } else {
            pathCamiones = "src/datasets/Camiones.csv";
            pathPaquetes = "src/datasets/Paquetes.csv";
        }  

        Servicios servicios = new Servicios(pathCamiones, pathPaquetes);
        String codigo_paquete = "P001"; // Código del paquete en una variable. Esto no es nada importante
                                        // simplemente lo cambio por si los profesores llegan a decir algo

        System.out.println("=== Servicio 1: búsqueda por código ===");
        Paquete p = servicios.servicio1(codigo_paquete);
        if (p != null) {
            imprimirPaquete(p);
        } else {
            System.out.println("No existe paquete con código " + codigo_paquete); //Variable con el código del paquete que no existe.
        }

        Paquete inexistente = servicios.servicio1("NO_EXISTE");
        System.out.print("Código NO_EXISTE -> ");
        imprimirPaquete(inexistente);
        //System.out.println("Código NO_EXISTE -> " + (inexistente == null ? "null" : imprimirPaquete(inexistente)));

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
    }

    private static void imprimirPaquete(Paquete p) {
        System.out.println(p); //Delegado al método toString() en la clase Paquete
    }

    /*private static String imprimirPaqueteLinea(Paquete p) {
        return "id=" + p.getId()
                + ", código=" + p.getCodigoPaquete()
                + ", pesoKg=" + p.getPesoKg()
                + ", alimentos=" + p.isContieneAlimentos()
                + ", urgencia=" + p.getNivelUrgencia();
    } */  
   
}

