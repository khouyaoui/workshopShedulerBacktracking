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


    int soluciones = 0;

    int [] lastSolucion;

    ArrayList<Schedule> schedules = new ArrayList<>();

    Workshops workshops = new Workshops();  // toda la info de workshops parsed in object

    public Workshops parseToObject(String rutaValida) throws FileNotFoundException {
        Gson gson = new Gson();
        workshops = gson.fromJson(new FileReader(rutaValida), Workshops.class);
        return workshops;
    }


    public boolean buena(int configuracion[], int k) {  // array de config y una posición dada

        int indice = 0;

        boolean libre = true;

        if (configuracion[k] == 0) {

            return true;
        }
        while ( indice < k ) {

            //if (configuracion[indice] == 1) {

            if (workshops.getCompatibilityMatrix()[indice][k] == 1) {

                return false;

            }
            //implementado paara comprar 2 objects

            for (int i = 0; i < workshops.getWorkshops().get(indice).getTimetable().size(); i++) {

                for (int j = 0; j < workshops.getWorkshops().get(k).getTimetable().size(); j++) {

                    if (workshops.getWorkshops().get(indice).getTimetable().get(i).equals(workshops.getWorkshops().get(k).getTimetable().get(j))) {

                        return false;
                    }
                }
            }

        indice ++;
        }
        return true;
    }

    /*
        public boolean solucion(int configuracion[], int k) {

            if (k == workshops.getWorkshops().size()) {
                return true;
            } else {
                return false;
            }
        }
    */

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
        lastSolucion = configuracion;

    }

    public void backTracking(int[] configuracion, int k) {

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
                //nada
            }
        }
    }


    public int [] soluciones (){

        return lastSolucion;
    }
}
