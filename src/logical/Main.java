package logical;

import algorithm.Configs;
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

        Workshops configuracion;
        boolean existe = false;
        List<String> rutas = null;
        String selectedFile;
        Scanner scanner = new Scanner(System.in);
        //Prepare view
        final ScheduleView view = new ScheduleView();
        System.out.println("--------------------------------------------------------------");
        System.out.println("_-_-_- WorkshopScheduler -_-_-_\n");
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
                 System.out.println(ruta+"   "+selectedFile);
                 if (ruta.equals(selectedFile)){
                     existe = true;
                 }else{
                 }
                 if (!existe){
                     System.out.println(":( Ruta no válida, vuelve a intentarlo");
                 }
             }
         }while (!existe);

         configuracion = Configs.parseToObject(selectedFile);

        System.out.println(configuracion);

        //Show view
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
