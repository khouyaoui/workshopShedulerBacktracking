package algorithm;

import Model.Schedule;
import Model.Workshop;
import com.google.gson.Gson;
import Model.Workshops;
import com.google.gson.internal.bind.util.ISO8601Utils;
import org.w3c.dom.ls.LSOutput;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Configs {


    public static int soluciones = 0;

    int sTotal = 0;

    Workshops workshops = new Workshops();  // toda la info de workshops parsed in object


    public Workshops parseToObject(String rutaValida) throws FileNotFoundException {
        Gson gson = new Gson();
        workshops = gson.fromJson(new FileReader(rutaValida), Workshops.class);
        return workshops;

    }

    int[] lastSolucion;

    public boolean buena(int configuracion[], int k) {  //

        int indice = 0;

        if (configuracion[k] == 0) {

            return true;
        }

          while (indice < workshops.getWorkshops().size()) {

            if (workshops.getCompatibilityMatrix()[k][indice].equals(0)) {

                return false;  // aqui hay incompati.  n cal seguir
            }
             for (int i = 0; i < workshops.getWorkshops().get(k).getTimetable().size(); i++) {

                for (int j = 0; j < workshops.getWorkshops().get(indice).getTimetable().size(); j++) {

                    if (workshops.getWorkshops().get(k).getTimetable().get(i).getDay().equals(workshops.getWorkshops().get(indice).getTimetable().get(j).getDay()) &&

                            workshops.getWorkshops().get(k).getTimetable().get(i).getHour().equals(workshops.getWorkshops().get(indice).getTimetable().get(j).getHour())){

                            return  true;

                    }

                }

            }


            indice++;
         }
        return false;
    }

    public void seguienteHermano(int configuracion[], int k) {
        configuracion[k]++;   // = configuracion [k]+1 ; // decidir ir ws
    }

    public void prepararRecorrigoNivel(int configuracion[], int k) {
        configuracion[k] = -1;
    }

    public boolean haySucesor(int configuracion[], int k) {

        return configuracion[k] < 1;

    }


    public void tratarSolucion(int configuracion[], int k) {

        soluciones++;

        lastSolucion = new int[configuracion.length];

        System.arraycopy(configuracion, 0, lastSolucion, 0, configuracion.length);

        /*
        lastSolucion = new int[configuracion.length];
        for (int i = 0; i < configuracion.length; i++) {
            lastSolucion[i] = configuracion[i];
        }  */

                sumarSoluciones(soluciones);
                System.out.println(soluciones);




    }

    public int sumarSoluciones(int soluciones) {
        sTotal = sTotal+ soluciones;
        return sTotal;
    }

    public void backTracking(int [] configuracion, int k) {

        prepararRecorrigoNivel(configuracion, k);

        while (haySucesor(configuracion, k)) {

             seguienteHermano(configuracion, k);

            if (k == configuracion.length - 1) {

                if (buena(configuracion, k)) {

                    tratarSolucion(configuracion, k);

                }
            }
            if (k < configuracion.length - 1) {

                if (buena(configuracion, k)) {

                    backTracking(configuracion, k + 1); // rec
                }
            }
        }
    }


    public int[] lastS() {
        return lastSolucion;

    }

}
