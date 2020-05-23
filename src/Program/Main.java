package Program;

import Model.Timetable;
import Model.Workshops;
import Algorithm.*;
import View.ScheduleView;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // vars

        String opcion;
        Workshops workshops = null;
        final ScheduleView view = new ScheduleView();
        int configuracion[];
        int configuracion_Final[] = null;
        int totalSoluciones = 0;
        boolean existe = false;
        List<String> rutas = null;
        String selectedFile;
        Scanner scanner = new Scanner(System.in);
        LocalDateTime start = LocalDateTime.now();
        view.setStartDateContent(LocalDateTime.now());
        System.out.println("--------------------------------------------------------------");
        System.out.println("-_-_--_--_--_-_--_--_ WorkshopScheduler _--_--_-_-_-_--_--_--_\n");
        System.out.println("This files contain workshops information");

         try (Stream<Path> walk = Files.walk(Paths.get("resources"))) {
            rutas = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            for (String filename: rutas) {
                System.out.println("\t- "+filename.substring(10)+" --> "+filename);
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
             System.out.println("\t 2. Maximizar horas.");
             System.out.println("\t 3. Maximizar presupuesto.");
             System.out.println();
             System.out.print("Objetivo: ");
             opcion = scanner.nextLine();

         } while (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3"));
         switch (opcion){
             case "1":
                 Config_1 config_1 = new Config_1();
                 workshops = config_1.parseToObject(selectedFile);
                 configuracion = new int[workshops.getWorkshops().size()];
                 config_1.backTracking(configuracion,0);
                 configuracion_Final = config_1.lastSolucion();
                 totalSoluciones = config_1.totalSolucion();

                 break;
             case "2":
                 Config_2 config_2 = new Config_2();
                 workshops = config_2.parseToObject(selectedFile);
                 configuracion = new int[workshops.getWorkshops().size()];
                 config_2.backTracking(configuracion, 0);
                 configuracion_Final = config_2.maxHoras();
                 totalSoluciones = config_2.totalSolucion();

                 view.setTotalWorkshopsContent(config_2.totalW());
                 view.setTotalHoursContent(config_2.getMaxHoras());
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
                 Config_3 config_3 = new Config_3();
                 workshops = config_3.parseToObject(selectedFile);
                 configuracion = new int[workshops.getWorkshops().size()];
                 config_3.setMaxPresopuestoUsuario(presupuesto);
                 config_3.backTracking(configuracion, 0);
                 configuracion_Final = config_3.maxPresupuesto();
                 totalSoluciones = config_3.totalSolucion();
                 break;
         }

        SwingUtilities.invokeLater(() -> view.setVisible(true));

        /*****************
         SAMPLE CODE
         ****************/

        //Set cells content
        int[] categories = new int[5];
        float cost = 0;

        for (int w = 0; w < configuracion_Final.length; w++) {
            if (configuracion_Final[w] == 1) {
                for (int t = 0; t < workshops.getWorkshops().get(w).getTimetable().size(); t++) {
                    for (int i = 0; i < 12; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (workshops.getWorkshops().get(w).getTimetable().get(t).getHour() == i) {
                                if (workshops.getWorkshops().get(w).getTimetable().get(t).getDay() == j) {
                                    view.setCellContent(String.format(workshops.getWorkshops().get(w).getAcronym()),
                                            workshops.getWorkshops().get(w).getCategory(),
                                            workshops.getWorkshops().get(w).getPrice().floatValue(),
                                            new Color(
                                                    workshops.getWorkshops().get(w).getRgbColor().get(0),
                                                    workshops.getWorkshops().get(w).getRgbColor().get(1),
                                                    workshops.getWorkshops().get(w).getRgbColor().get(2)), i, j);

                            }
                        }
                    }
                }
            }
        }
    }


    //Erase some cells
        view.resetCellContent(4,0);
    categories[1-1]--;
        view.resetCellContent(11,1);
    categories[2-1]--;
    cost -=11.0f;
        view.resetCellContent(0,1);
    categories[1-1]--;

    //Set generic information
        LocalDateTime fin = LocalDateTime.now();
        view.setFinishDateContent(fin); // fin
        view.setDurationContent(Duration.between(start,fin));
        view.setSolutionsContent(totalSoluciones);

    //Set time optimization information




    //Set cost information
        view.setLimitCostContent(10000);
        view.setBaseCostContent(cost);
        view.setDiscountContent(15);
        view.setFinalCostContent(cost *0.85f);
        for(int i = 0; i<categories.length;i++)

    {
        view.setCategoryContent(i + 1, categories[i]);
    }


}




}
