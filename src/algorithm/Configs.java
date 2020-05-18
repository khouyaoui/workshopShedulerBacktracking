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

    ArrayList <Schedule> schedules = new ArrayList<>();

    Workshops workshops = new Workshops();  // toda la info de workshops parsed in object

    public Workshops parseToObject(String rutaValida) throws FileNotFoundException {
        Gson gson = new Gson();
        workshops = gson.fromJson(new FileReader(rutaValida), Workshops.class);
        return workshops;
    }


    public boolean buena( int configuracion[] , int k) {  // array de config y una posición dada

        int indice = 0;

        boolean libre = true;

        boolean incompatible = true;

        if (configuracion[k] == 0) {
            return true;
        }
        while (indice < k && libre) {

            //if (configuracion[indice] == 1) {

                if (workshops.getCompatibilityMatrix()[indice][k-1] == 1) { // error solved  (-1 para que no salga de matx 0....n-1)

                    incompatible = false;

                     //implementado paara comprar 2 objects
                    for (int i = 0; i < workshops.getWorkshops().get(indice).getTimetable().size();i++){

                             if (workshops.getWorkshops().get(indice).getTimetable().get(i).equals(workshops.getWorkshops().get(k).getTimetable())) {
                                 libre = false;
                             }
                    }
                    for (int i = 0; i < workshops.getWorkshops().get(k).getTimetable().size();i++){

                        if (workshops.getWorkshops().get(k).getTimetable().get(i).equals(workshops.getWorkshops().get(indice).getTimetable())) {
                            libre = false;
                        }
                    }


                }
            //}
            indice++;
        }

        return (libre && !incompatible);
    }

    public boolean solucion(int configuracion[], int k) {

        if (k == workshops.getWorkshops().size()) {
            return true;
        } else {
            return false;
        }
    }

    public void seguienteHermano(int configuracion[], int k) {

        configuracion[k]++;   // = configuracion [k]+1 ; // decidir ir ws
    }

    public void prepararRecorrigoNivel(int configuracion[], int k) {
        configuracion[k] = - 1;
    }
    // vector de present talleres config [0.1.2.3.4.5.6.7.8.n]

    public boolean haySucesor(int configuracion[], int k) {

        return configuracion[k] < 1;

    }

    public void tratarSolucion(int configuracion[], int k) {

        ArrayList <Workshop> workshopsWrapper = new ArrayList<>();

        for (int i = 0; i < configuracion.length; i++) {
            if (configuracion[i] == 1){
                workshopsWrapper.add(workshops.getWorkshops().get(i));
            }
        }
        Schedule scheduleWrapper = new Schedule(workshopsWrapper);
        schedules.add(scheduleWrapper);
    }

    public void backTracking(int[] configuracion, int k) {

        prepararRecorrigoNivel(configuracion, k);

        while (haySucesor(configuracion, k)) {



            seguienteHermano(configuracion, k);

            if (k == configuracion.length -1) {

                if (buena(configuracion, k)) {

                    tratarSolucion(configuracion, k);
                    System.out.println();
                 }
            }
            if ( k < configuracion.length -1) {

                if (buena(configuracion, k)) {
                    backTracking(configuracion, k+1); // rec
                }
                //nada
            }
        }
     }
}
