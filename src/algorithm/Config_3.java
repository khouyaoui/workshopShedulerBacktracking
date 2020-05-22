package Algorithm;
import Model.Backtracking;
 import Model.Workshops;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Config_3 implements Backtracking {

    private Double presupuestoUsuario = 0d;
    private Double presupuesto_tmp = 0d;

    int [] configMaxPresupuesto;
    Workshops workshops = new Workshops();  // toda la info de workshops parsed in object

    public Workshops parseToObject(String rutaValida) throws FileNotFoundException {
        Gson gson = new Gson();
        workshops = gson.fromJson(new FileReader(rutaValida), Workshops.class);
        configMaxPresupuesto = new int [workshops.getWorkshops().size()];
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

        Double tmp =  sumaPrecio(configuracion);

        if (tmp > presupuesto_tmp && tmp <= presupuestoUsuario  ){
            System.arraycopy(configuracion, 0, configMaxPresupuesto, 0, configuracion.length);
            presupuesto_tmp = tmp;
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

    public int [] maxPresupuesto () {
        return configMaxPresupuesto;
    }

    public Double sumaPrecio(int [] configuracion) {
        Double sum = 0d;
        String acron [] = new String[configuracion.length];

         for (int i = 0,j=0; i < configuracion.length; i++) {
            if (configuracion [i] == 1 && Arrays.asList(acron).contains(workshops.getWorkshops().get(i).getAcronym()) ){
                acron[j] = workshops.getWorkshops().get(i).getAcronym();
                j++;
                sum += workshops.getWorkshops().get(i).getPrice();
                System.out.println(i+"  "+j+"  "+acron[i]);
            }
        }
         return sum;
    }

    public void setMaxPresopuestoUsuario(){
/*
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println("Cual es el presupuesto disponible? (€) ");
                presupuestoUsuario = scanner.nextDouble();
            }catch (NumberFormatException e){
                System.out.println("Ups, se esperaba un precio en numeros");
            }

        }while (!scanner.hasNextDouble());
*/
        presupuestoUsuario = 40d;

    }
}
