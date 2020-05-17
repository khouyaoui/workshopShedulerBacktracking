package algorithm;

import Model.Workshop;
import com.google.gson.Gson;
import Model.Workshops;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Configs {

    List<Workshop> schedule;  // h fin
    Workshops workshops = new Workshops();  // toda la info de workshops parsed in object

    public Workshops parseToObject(String rutaValida) throws FileNotFoundException {
        Gson gson = new Gson();
        workshops = gson.fromJson(new FileReader(rutaValida), Workshops.class);
        return workshops;
    }




    public boolean buena(int configuracion [], int k){  // array de config y una posición dada

        int indice = 0;

        boolean libre = true;

        boolean incompatible = true;

        if(configuracion[k] == 0){
            return true;
        }
        while(indice < k && libre){


            if (configuracion[indice] == 1){

                if (workshops.getCompatibilityMatrix()[indice][k] == 1){ // error detected 

                    incompatible = false;

                    if (workshops.getWorkshops().get(indice).getTimetable().equals( workshops.getWorkshops().get(k).getTimetable())){

                        libre = false;
                    }

                }
            }
            indice ++;
        }

        return (libre && !incompatible);
    }

    public boolean solucion(int configuracion [], int k){

        if (k == workshops.getWorkshops().size()){
            return true;
        }else{
            return false;
        }
    }

    public void seguienteHermano(int configuracion [], int k){
        configuracion [k] = configuracion [k+1] ;
    }

    public void prepararRecorrigoNivel(int configuracion [], int k){
        configuracion [k] = -1;
    }

    public boolean haySucesor(int configuracion [], int k){
        return configuracion [k] < 1;
    }

    public void tratarSolucion(int configuracion [], int k){

        for (int i = 0; i < configuracion.length; i++) {
            if (configuracion[i] == 1){
                schedule.add(workshops.getWorkshops().get(i));
            }
        }


    }

    public void backTracking (int[] configuracion, int k) {

        prepararRecorrigoNivel(configuracion,k);

        while (haySucesor(configuracion,k)){

            seguienteHermano(configuracion,k);

            if (k == workshops.getWorkshops().size()){

                if (buena(configuracion,k)){

                    seguienteHermano(configuracion,k);
                }
                tratarSolucion(configuracion, k);

            }
            if (k < workshops.getWorkshops().size()){
                if (buena(configuracion, k)){
                    seguienteHermano(configuracion,k);
                 }

                backTracking(configuracion, k++);   //rec

            }

        }

    }


}
