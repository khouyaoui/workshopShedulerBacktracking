package Program;
import Model.Workshops;
 import Algorithm.*;
import view.ScheduleView;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
     public static void main(String[] args) throws FileNotFoundException {
        // vars

         String opcion;
         Workshops workshops;
         final ScheduleView view = new ScheduleView();
         int configuracion[];
         int configuracion_Final [];
         boolean existe = false;
         List<String> rutas = null;
         String selectedFile;
         Scanner scanner = new Scanner(System.in);

        System.out.println("--------------------------------------------------------------");
        System.out.println("-_-_--_--_--_-_--_--_ WorkshopScheduler _--_--_-_-_-_--_--_--_\n");
        System.out.println("This files contain workshops information");

         try (Stream<Path> walk = Files.walk(Paths.get("resources"))) {
            rutas = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            for (String filename: rutas) {
                System.out.println("- "+filename.substring(10)+" --> ruta valida: "+filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("--------------------------------------------------------------");
         do {
             System.out.print("Introduce una ruta de de algún fichero mostrado anteriormente: \n");
             selectedFile = scanner.nextLine();
             for (String ruta: rutas) {
                 if (ruta.equals(selectedFile)){
                     existe = true;
                 }
             }
             if (!existe){
                 System.out.println(":( Ruta no válida, vuelve a intentarlo");
             }
         } while (!existe);


         do {
             System.out.println("Selecciona un objetivo");
             System.out.println("\t 1. Todas las configuraciones posibles.");
             System.out.println("\t 2. Maximizar horas");
             System.out.println("\t 3. Maximizar presupuesto.");
             System.out.println();
             System.out.print("Objetivo: ");
             opcion = scanner.nextLine();

         } while (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3"));

         switch (opcion){
             case "1":
                 Config_1 configs_1 = new Config_1();
                 workshops = configs_1.parseToObject(selectedFile);
                 configuracion = new int[workshops.getWorkshops().size()];
                 configs_1.backTracking(configuracion,0);
                 configuracion_Final = configs_1.lastSolucion();
                 for (int i = 0; i < workshops.getWorkshops().size(); i++) {
                     if (configuracion_Final[i] == 1){
                         System.out.print(" --> "+workshops.getWorkshops().get(i).getAcronym());
                     }
                 }

                 break;
             case "2":
                 Config_2 configs_2 = new Config_2();
                 workshops = configs_2.parseToObject(selectedFile);
                 configuracion = new int[workshops.getWorkshops().size()];
                 configs_2.backTracking(configuracion, 0);
                 configuracion_Final = configs_2.maxHoras();
                 for (int i = 0; i < workshops.getWorkshops().size(); i++) {
                     if (configuracion_Final [i] == 1) {
                         System.out.print(" --> " + workshops.getWorkshops().get(i).getAcronym());
                     }
                 }
                 //
                 break;
             case "3":
                 Double presupuesto = 0d;
                 do {
                     try {
                         System.out.print("\n¿Cual es el presupuesto disponible? (€) ");
                         presupuesto = scanner.nextDouble();
                     }catch (NumberFormatException e){
                         System.out.println("Ups, se esperaba un precio en numeros");
                     }
                 }while (!scanner.hasNextDouble());
                 Config_3 configs_3 = new Config_3();
                 workshops = configs_3.parseToObject(selectedFile);
                 configuracion = new int[workshops.getWorkshops().size()];
                 configs_3.setMaxPresopuestoUsuario(presupuesto);
                 configs_3.backTracking(configuracion, 0);
                 configuracion_Final = configs_3.maxPresupuesto();
                 for (int i = 0; i < workshops.getWorkshops().size(); i++) {
                     if (configuracion_Final [i] == 1) {
                         System.out.print(" --> " + workshops.getWorkshops().get(i).getAcronym());
                     }
                 }


                 break;
             default:
                 System.out.println("bye!");
         }

            System.exit(0);

         SwingUtilities.invokeLater(() -> view.setVisible(true));

        /*****************
            SAMPLE CODE
         ****************/

        //Set cells content
        int[] categories = new int[5];
        float cost = 0;
        for(int i = 0; i < 12; i++) {
            for(int j = 0; j < 5; j++) {
                if (i % 2 == 0) {
                    if(j % 2 == 0) {
                        view.setCellContent(String.format("Row: %d - Col: %d", i, j), 1,
                                i * j, Color.ORANGE, i, j);
                    } else {
                        view.setCellContent(String.format("Row: %d - Col: %d", i, j), 1,
                                i * j, Color.PINK, i, j);
                    }
                    categories[1 - 1]++;
                } else {
                    if(j % 2 == 0) {
                        view.setCellContent(String.format("Row: %d - Col: %d", i, j), 2,
                                i * j, Color.CYAN, i, j);
                    } else {
                        view.setCellContent(String.format("Row: %d - Col: %d", i, j), 2,
                                i * j, Color.GREEN, i, j);
                    }
                    categories[2 - 1]++;
                }
                cost += i * j;
            }
        }

        //Erase some cells
        view.resetCellContent(4, 0);
        categories[1 - 1]--;
        view.resetCellContent(11, 1);
        categories[2 - 1]--;
        cost -= 11.0f;
        view.resetCellContent(0, 1);
        categories[1 - 1]--;

        //Set generic information
        view.setStartDateContent(LocalDateTime.now());
        view.setFinishDateContent(LocalDateTime.now());
        view.setDurationContent(Duration.ZERO);
        view.setSolutionsContent(10);

        //Set time optimization information
        view.setTotalWorkshopsContent(57);
        view.setTotalHoursContent(57);

        //Set cost information
        view.setLimitCostContent(10000);
        view.setBaseCostContent(cost);
        view.setDiscountContent(15);
        view.setFinalCostContent(cost * 0.85f);
        for(int i = 0; i < categories.length; i++) {
            view.setCategoryContent(i + 1, categories[i]);
        }





    }




}
