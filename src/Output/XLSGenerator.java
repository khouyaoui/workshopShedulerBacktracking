package Output;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class XLSGenerator {

    /**
     * funcion para generar xls con resultado de tiempos
     * @param start inicio de ejecucion
     * @param fin fin de ejecucion
     */
    public static void generateFileXLS(LocalDateTime start, LocalDateTime fin) {

            WritableWorkbook workbook = null;
            try {
                workbook = Workbook.createWorkbook(new File("extras\\50wTimeResume.xls"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                WritableSheet sheet = workbook.createSheet("First Sheet", 0);
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

                Duration duration = Duration.between(start, fin);
                Double celda = timeFormatMiliSeconds(duration);
                sheet.addCell(new jxl.write.Number(0, 1, celda)); //la i es la del for   // el 0 es la columna

                workbook.write();

                workbook.close();

            } catch (WriteException e) {
                e.printStackTrace();
            } catch (IOException ea){
                ea.printStackTrace();
            }

        }


    public static Double timeFormatMiliSeconds(Duration duration) {
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

