package Program;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * clase para implementar las operaciones que tengan que ver con la entrada de datos, interactuacion con el usuario
 */
public class CLI {

    static Scanner s = new Scanner(System.in);

    /**
     * pregutna al usuario si quiere aplicar mejoras en la ejecucion
     * @return la respuesta
     */
    public static boolean aplicarMejoras(){
        String respuesta;
        do {
            System.out.print("¿Quieres aplicar mejoras en la eficiencia? '( s/n )' ");
            respuesta = s.next();
            if (respuesta.equalsIgnoreCase("s")){
                return true;
            }
            if (respuesta.equalsIgnoreCase("n")){
                return false;
            }
        } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
        return false;
    }

    /**
     * funcion para preguntar al usuario que opcion/operacion quiere ejecutar
     * @return la respuesta del usuario
     */
    public static String EleccionUsuario(){
        String opcion;
        do {
            System.out.println("Selecciona un objetivo");
            System.out.println("\t 1. Todas las configuraciones posibles.");
            System.out.println("\t 2. Maximizar horas.");
            System.out.println("\t 3. Maximizar presupuesto.");
            System.out.println();
            System.out.print("Objetivo: ");
            opcion = s.next();

        } while (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3"));

        return opcion;
    }

    /**
     * funcion para preguntar al usuario sobre que opcion quiiere
     * @return devuelve la ruta del nombre del fichero a tratar
     */
    public static String EleccionFile(){
        String selectedFile;
        boolean existeFile = false;
        List<String> rutas = null;
        System.out.println("--------------------------------------------------------------");
        System.out.println("-_-_--_--_--_-_--_--_ WorkshopScheduler _--_--_-_-_-_--_--_--_\n");
        System.out.println("This files contain workshops information");

        try (Stream<Path> walk = Files.walk(Paths.get("resources"))) {
            rutas = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            for (String filename : rutas) {
                System.out.println("\t- " + filename.substring(10) + " --> " + filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("--------------------------------------------------------------");
        do {
            System.out.print("Introduce una ruta de de algún fichero mostrado anteriormente: \n");
            selectedFile = s.next();
            for (String ruta : rutas) {
                if (ruta.equals(selectedFile)) {
                    existeFile = true;
                }
            }
            if (!existeFile) {
                System.out.println(":( Ruta no válida, vuelve a intentarlo");
            }
        } while (!existeFile);

        return selectedFile;
    }
    /**
     * funcion para preguntar al usaurio de que presupuesto dispone para la opcion 3
     * @return un precio con decimales
     */
    public static Double Eleccionpresupuesto(){
        Double presupuesto = 0d;
        System.out.print("\n¿Cual es el presupuesto disponible? (€) ");
        presupuesto = s.nextDouble();
        return presupuesto;
    }
    public static boolean generarCompareTime(){
        String respuesta;
        do {
            System.out.println("¡Advertencia!, la ejecucion puede necesitar horas para acabarse");
            System.out.print("¿Quieres generar XLS con varias ejecuciones para analizar los tiempos de espera? '( s/n )' ");
            respuesta = s.next();
            if (respuesta.equalsIgnoreCase("s")){
                return true;
            }
            if (respuesta.equalsIgnoreCase("n")){
                return false;
            }
        } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
        return false;
    }
}
