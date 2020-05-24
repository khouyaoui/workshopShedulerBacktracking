package Output;

import Algorithm.Config_1;
import Algorithm.Config_2;
import Algorithm.Config_3;
import Model.Workshops;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * funcion para generar xls con resumen de tiempos que tardan en ejecutar los algoritmos
 */
public class XLSGenerator {

    public void generarXLS(String rutaFile ) throws IOException, WriteException {

        Workshops workshops = null;
        int configuracion[];
        LocalDateTime start = null, fin = null;
        WritableWorkbook writableWorkbook = null;
        Double celda;

        try {
            writableWorkbook = Workbook.createWorkbook(new File(rutaFile.substring(12,rutaFile.length()-4)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String selectedFile = rutaFile;
        WritableSheet sheet = writableWorkbook.createSheet("Tiempos de ejecución", 0);
        Label label11 = new Label(0, 0, "Opcion 1 sin mejoras");
        sheet.addCell(label11);
        Label label1c = new Label(1, 0, "Opcion 1 con mejoras");
        sheet.addCell(label1c);
        Label label2 = new Label(2, 0, "Opcion 2 sin mejoras");
        sheet.addCell(label2);
        Label label2c = new Label(3, 0, "Opcion 2 con mejoras");
        sheet.addCell(label2c);
        Label label3 = new Label(4, 0, "Opcion 3 sin mejoras");
        sheet.addCell(label3);
        Label label3c = new Label(5, 0, "Opcion 3 con mejoras");
        sheet.addCell(label3c);

        for (int i = 1; i < 80; i++) {


            Config_1 config_1 = new Config_1();
            workshops = config_1.parseToObject(selectedFile);
            configuracion = new int[workshops.getWorkshops().size()];
            //justo antes de llamar al algoritmo
            config_1.setMejoras(false);
            start = LocalDateTime.now();
            config_1.backTracking(configuracion, 0);
            fin = LocalDateTime.now();
            Duration duration = Duration.between(start, fin);
            celda = timeFormatMiliSeconds(duration);
            sheet.addCell(new jxl.write.Number(0, i, celda));

            //justo antes de llamar al algoritmo
            config_1.setMejoras(true);
            start = LocalDateTime.now();
            config_1.backTracking(configuracion, 0);
            fin = LocalDateTime.now();
            duration = Duration.between(start, fin);
            celda = timeFormatMiliSeconds(duration);
            sheet.addCell(new jxl.write.Number(1, i, celda));


            Config_2 config_2 = new Config_2();
            workshops = config_2.parseToObject(selectedFile);
            configuracion = new int[workshops.getWorkshops().size()];
            // justo antes de llamar al algoritmo
            config_2.setMejoras(false);
            start = LocalDateTime.now();
            config_2.backTracking(configuracion, 0);
            fin = LocalDateTime.now();
            duration = Duration.between(start, fin);
            celda = timeFormatMiliSeconds(duration);
            sheet.addCell(new jxl.write.Number(2, i, celda));

            config_2 = new Config_2();
            workshops = config_2.parseToObject(selectedFile);
            configuracion = new int[workshops.getWorkshops().size()];
            // justo antes de llamar al algoritmo
            config_2.setMejoras(true);
            start = LocalDateTime.now();
            config_2.backTracking(configuracion, 0);
            fin = LocalDateTime.now();
            duration = Duration.between(start, fin);
            celda = timeFormatMiliSeconds(duration);
            sheet.addCell(new jxl.write.Number(3, i, celda));


            Config_3 config_3 = new Config_3();
            workshops = config_3.parseToObject(selectedFile);
            configuracion = new int[workshops.getWorkshops().size()];
            // antes de llamar al algoritmo
            Double presupuesto = 40d;
            config_3.setMaxPresopuestoUsuario(presupuesto);
            config_3.setMejoras(false);
            start = LocalDateTime.now();
            config_3.backTracking(configuracion, 0);
            fin = LocalDateTime.now();  // tmp fin algoritmo
            duration = Duration.between(start, fin);
            celda = timeFormatMiliSeconds(duration);
            sheet.addCell(new jxl.write.Number(4, i, celda));

            config_3 = new Config_3();
            workshops = config_3.parseToObject(selectedFile);
            configuracion = new int[workshops.getWorkshops().size()];
            // antes de llamar al algoritmo
            presupuesto = 40d;
            config_3.setMaxPresopuestoUsuario(presupuesto);
            config_3.setMejoras(false);
            start = LocalDateTime.now();
            config_3.backTracking(configuracion, 0);
            fin = LocalDateTime.now();  // tmp fin algoritmo
            duration = Duration.between(start, fin);
            celda = timeFormatMiliSeconds(duration);
            sheet.addCell(new jxl.write.Number(5, i, celda));
        }
        writableWorkbook.write();
        writableWorkbook.close();
    }
    public static @NotNull Double timeFormatMiliSeconds (Duration duration){
        String source = String.valueOf(duration);
        String[] tokens = source.split("T");
        String string1 = tokens[1];
        tokens = null;
        tokens = string1.split("S");
        String miliseconds = tokens[0];
        //miliseconds = miliseconds.replace('.',',');
        Double m_l = Double.parseDouble(miliseconds);
        return m_l;
    }

}

