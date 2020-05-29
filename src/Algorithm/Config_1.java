package Algorithm;
import Model.Backtracking;
import com.google.gson.Gson;
import Model.Workshops;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * clase que implementar las operaciones de la primera opcion
 */
public class Config_1 implements Backtracking {

    private int soluciones = 0;
    private int [] lastSolucion;
    private boolean mejoras;
    Workshops workshops = new Workshops();

    int p_actual =0;
    /**
     * funcion para parsear un fichero json a objeto java
     * @param rutaValida recibe la ruta del fichero a parsear
     * @return devuelve un unico objeto que los workshops y su matriz particular
     * @throws FileNotFoundException excepcion por indicar un fichero que no puede localizar (controlada)
     */
    public Workshops parseToObject(String rutaValida) throws FileNotFoundException {
        Gson gson = new Gson();
        workshops = gson.fromJson(new FileReader(rutaValida), Workshops.class);
        lastSolucion = new int [workshops.getWorkshops().size()];
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
        return configuracion [k] < 1;
    }

    public void tratarSolucion(int [] configuracion, int k) {
        soluciones++;
        System.arraycopy(configuracion, 0, lastSolucion, 0, configuracion.length);
    }
/*

    public void marcar(int configuracion [], int k){
         p_actual = p_actual + configuracion[k] * workshops.getWorkshops().get(k).getTimetable().size();
    }

    public void descarmar(int configuracion [], int k){
        p_actual = p_actual - configuracion[k] * workshops.getWorkshops().get(k).getTimetable().size();
    }   */

     public void backTracking(int [] configuracion, int k) {

        prepararRecorrigoNivel(configuracion, k);

        while (haySucesor(configuracion, k)) {
            if (mejoras){
                //marcar(configuracion,k);
            }

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
            if (mejoras){
                //descarmar(configuracion,k);
            }
        }
    }

    public int [] lastSolucion () {

         return lastSolucion;
    }

    public int  totalSolucion () {
        return soluciones;
    }
    @Override
    public Integer getMaxHoras() {return 0; }
    @Override
    public Integer totalW() { return null; }

    public void setMejoras(boolean respuesta){
        mejoras = respuesta;
    }
}
