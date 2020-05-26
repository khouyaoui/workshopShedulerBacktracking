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
import java.util.concurrent.ExecutionException;

/**
 * funcion para generar xls con resumen de tiempos que tardan en ejecutar los algoritmos
 */
public class XLSGenerator {

    public XLSGenerator() {
    }

    public void generarXLS(String rutaFile ) throws IOException, WriteException {

        Workshops workshops = null;
        int configuracion[];
        LocalDateTime start = null, fin = null;
        WritableWorkbook writableWorkbook = null;
        Double celda;

        try {
            writableWorkbook = Workbook.createWorkbook(new File("extras\\"+rutaFile.substring(10,rutaFile.length()-5)+"TimeResume"+".xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

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

            Config_1 config_1 = new Config_1();
            Config_2 config_2 = new Config_2();
            Config_3 config_3 = new Config_3();

            Double sum_1_sinMejoras = 0d,sum_1_ConMejoras=0d,sum_2_sinMejoras=0d,sum_2_conMejoras=0d,sum_3_sinMejoras=0d,sum_3_conMejoras=0d;

            int i = 1;
            for (;i < 50; i++) {


                workshops = config_1.parseToObject(selectedFile);
                configuracion = new int[workshops.getWorkshops().size()];
                //justo antes de llamar al algoritmo
                config_1.setMejoras(false);
                start = LocalDateTime.now();
                config_1.backTracking(configuracion, 0);
                fin = LocalDateTime.now();
                Duration duration = Duration.between(start, fin);
                celda = timeFormatMiliSeconds(duration);
                sum_1_sinMejoras += celda;
                sheet.addCell(new jxl.write.Number(0, i, celda));

                //justo antes de llamar al algoritmo
                config_1.setMejoras(true);
                start = LocalDateTime.now();
                config_1.backTracking(configuracion, 0);
                fin = LocalDateTime.now();
                duration = Duration.between(start, fin);
                celda = timeFormatMiliSeconds(duration);
                sum_1_ConMejoras  += celda;
                sheet.addCell(new jxl.write.Number(1, i, celda));

                workshops = config_2.parseToObject(selectedFile);
                configuracion = new int[workshops.getWorkshops().size()];
                // justo antes de llamar al algoritmo
                config_2.setMejoras(false);
                start = LocalDateTime.now();
                config_2.backTracking(configuracion, 0);
                fin = LocalDateTime.now();
                duration = Duration.between(start, fin);
                celda = timeFormatMiliSeconds(duration);
                sum_2_sinMejoras += celda;
                sheet.addCell(new jxl.write.Number(2, i, celda));

                workshops = config_2.parseToObject(selectedFile);
                configuracion = new int[workshops.getWorkshops().size()];
                // justo antes de llamar al algoritmo
                config_2.setMejoras(true);
                start = LocalDateTime.now();
                config_2.backTracking(configuracion, 0);
                fin = LocalDateTime.now();
                duration = Duration.between(start, fin);
                celda = timeFormatMiliSeconds(duration);
                sum_2_conMejoras += celda;
                sheet.addCell(new jxl.write.Number(3, i, celda));

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
                sum_3_sinMejoras += celda;
                sheet.addCell(new jxl.write.Number(4, i, celda));

                workshops = config_3.parseToObject(selectedFile);
                configuracion = new int[workshops.getWorkshops().size()];
                // antes de llamar al algoritmo
                presupuesto = 40d;
                config_3.setMaxPresopuestoUsuario(presupuesto);
                config_3.setMejoras(true);
                start = LocalDateTime.now();
                config_3.backTracking(configuracion, 0);
                fin = LocalDateTime.now();  // tmp fin algoritmo
                duration = Duration.between(start, fin);
                celda = timeFormatMiliSeconds(duration);
                sum_3_conMejoras += celda;
                sheet.addCell(new jxl.write.Number(5, i, celda));

            }
            sheet.addCell(new jxl.write.Number(0, i+2, sum_1_sinMejoras));
            sheet.addCell(new jxl.write.Number(1, i+2, sum_1_ConMejoras));
            sheet.addCell(new jxl.write.Number(2, i+2, sum_2_sinMejoras));
            sheet.addCell(new jxl.write.Number(3, i+2, sum_2_conMejoras));
            sheet.addCell(new jxl.write.Number(4, i+2, sum_3_sinMejoras));
            sheet.addCell(new jxl.write.Number(5, i+2, sum_3_conMejoras));

            writableWorkbook.write();
            writableWorkbook.close();

        }catch (IOException e){
            e.getStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public static @NotNull Double timeFormatMiliSeconds (Duration duration){
        String source = String.valueOf(duration);
        String[] tokens = source.split("T");
        String string1 = tokens[1];
        tokens = null;
        tokens = string1.split("S");
        String miliseconds = tokens[0];
        Double m_l = Double.parseDouble(miliseconds);
        return m_l;
    }

}

