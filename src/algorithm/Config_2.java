package Algorithm;
import Model.Backtracking;
import Model.Workshops;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class Config_2 implements Backtracking {

    private int maxHoras = 0;
    private int [] configMaxHoras;
    private int soluciones = 0;
    private Workshops workshops = new Workshops();

    public Workshops parseToObject(String rutaValida) throws FileNotFoundException {
        Gson gson = new Gson();
        workshops = gson.fromJson(new FileReader(rutaValida), Workshops.class);
        configMaxHoras = new int [workshops.getWorkshops().size()];
        return workshops;

    }

    public boolean buena(int configuracion[], int k) {  //
        if (configuracion[k] == 0) {
            return true;
        }
        int indice = 0;
        while (indice < k) {
            if (configuracion[indice] == 1) {
                if (workshops.getCompatibilityMatrix()[indice][k].equals(0)) {
                    return false;  // aqui hay incompati.  n cal seguir
                }
                for (int i = 0; i < workshops.getWorkshops().get(k).getTimetable().size(); i++) {
                    for (int j = 0; j < workshops.getWorkshops().get(indice).getTimetable().size(); j++) {
                        if (workshops.getWorkshops().get(k).getTimetable().get(i).getDay().equals(workshops
                                .getWorkshops().get(indice).getTimetable().get(j).getDay()) && workshops
                                .getWorkshops().get(k).getTimetable().get(i).getHour().equals(workshops
                                        .getWorkshops().get(indice).getTimetable().get(j).getHour())) {
                            return false;
                        }
                    }
                }
            }
            indice++;
        }
        return true;
    }
    public void seguienteHermano(int [] configuracion, int k) {
        configuracion[k]++;   // = configuracion [k]+1 ; // decidir ir ws
    }
    public void prepararRecorrigoNivel(int [] configuracion, int k) {
        configuracion [k] = -1;
    }
    public boolean haySucesor(int []configuracion, int k) {
        return configuracion[k] < 1;

    }

    public void tratarSolucion(int [] configuracion, int k) {
         int sum = sumaHoras(configuracion);
          if (sum > maxHoras){
                 System.arraycopy(configuracion, 0, configMaxHoras, 0, configuracion.length);
                 maxHoras = sum;
                 soluciones++;
          }
    }
    public void backTracking(int [] configuracion, int k) {
        prepararRecorrigoNivel(configuracion, k);
        while (haySucesor(configuracion, k)) {
            seguienteHermano(configuracion, k);
            if (k == workshops.getWorkshops().size() - 1) {
                if (buena(configuracion, k)) {
                    tratarSolucion(configuracion, k);
                }
            }
            if (k < workshops.getWorkshops().size() - 1) {
                if (buena(configuracion, k)) {
                    backTracking(configuracion, k + 1); // rec
                }
            }
        }
    }

    @Override
    public int totalSolucion() {
        return soluciones;
    }

    public int [] maxHoras () {
        return configMaxHoras;
    }

    public int sumaHoras(int [] configuracion) {
        int sum = 0;
        for (int i = 0; i < configuracion.length; i++) {
            if (configuracion [i] == 1 ){
                sum += workshops.getWorkshops().get(i).getTimetable().size();
            }
        }
        return sum;
     }

     public Integer getMaxHoras (){
        return maxHoras;
     }

     public Integer totalW (){
        Integer tmp = 0;
         for (int i = 0; i < configMaxHoras.length; i++) {
             if (configMaxHoras[i]==1){
                 tmp++;
             }
         }
         return tmp;
     }

}
