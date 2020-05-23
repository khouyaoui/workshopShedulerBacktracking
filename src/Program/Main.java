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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Workshops workshops = null;
        final ScheduleView view = new ScheduleView();
        int configuracion[];
        int configuracion_Final[] = null;
        Integer totalSoluciones = null;
        LocalDateTime start = null, fin = null;

        String selectedFile = CLI.EleccionFile();
        switch (CLI.EleccionUsuario()) {
            case "1":
                Config_1 config_1 = new Config_1();
                workshops = config_1.parseToObject(selectedFile);
                configuracion = new int[workshops.getWorkshops().size()];
                // justo antes de llamar al algoritmo

                config_1.setMejoras(CLI.aplicarMejoras());
                start = LocalDateTime.now();
                config_1.backTracking(configuracion, 0);
                configuracion_Final = config_1.lastSolucion();
                totalSoluciones = config_1.totalSolucion();
                fin = LocalDateTime.now();
                break;
            case "2":
                Config_2 config_2 = new Config_2();
                workshops = config_2.parseToObject(selectedFile);
                configuracion = new int[workshops.getWorkshops().size()];
                // justo antes de llamar al algoritmo

                config_2.setMejoras(CLI.aplicarMejoras());
                start = LocalDateTime.now();
                config_2.backTracking(configuracion, 0);
                configuracion_Final = config_2.maxHoras();
                totalSoluciones = config_2.totalSolucion();
                fin = LocalDateTime.now();
                view.setTotalWorkshopsContent(config_2.totalW());
                view.setTotalHoursContent(config_2.getMaxHoras());
                break;
            case "3":
                Double presupuesto = CLI.Eleccionpresupuesto();
                Config_3 config_3 = new Config_3();
                workshops = config_3.parseToObject(selectedFile);
                configuracion = new int[workshops.getWorkshops().size()];
                // antes de llamar al algoritmo
                config_3.setMaxPresopuestoUsuario(presupuesto);
                config_3.setMejoras(CLI.aplicarMejoras());
                start = LocalDateTime.now();
                config_3.backTracking(configuracion, 0);
                configuracion_Final = config_3.maxPresupuesto();
                totalSoluciones = config_3.totalSolucion();
                fin = LocalDateTime.now();  // tmp fin algoritmo
                //Set cost information
                Double precioFinal = config_3.sumaPrecio(configuracion_Final);
                Double precioBase = config_3.getBase();

                float descuento = (float) (((precioBase - precioFinal) / precioBase) * 100);
                descuento = Math.round(descuento);
                view.setLimitCostContent(presupuesto.floatValue());
                view.setBaseCostContent(precioBase.floatValue());
                view.setFinalCostContent(precioFinal.floatValue());  //OK
                view.setDiscountContent((int) descuento);
                int categorias[] = config_3.getCategorias();
                for (int j = 0; j < categorias.length; j++) {
                    view.setCategoryContent(j + 1, categorias[j]);
                }
                break;
        }
        SwingUtilities.invokeLater(() -> view.setVisible(true));
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
        //Set generic information
        view.setStartDateContent(start);
        view.setFinishDateContent(fin);
        view.setDurationContent(Duration.between(start, fin));
        view.setSolutionsContent(totalSoluciones);

    }


}
