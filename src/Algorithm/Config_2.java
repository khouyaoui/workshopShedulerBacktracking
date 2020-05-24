package Algorithm;
import Model.Backtracking;
import Model.Workshops;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 * clase que implementar las operaciones de la segunda opcion
 */
public class Config_2 implements Backtracking {

    private boolean mejoras;
    private int maxHoras = 0;
    private int [] configMaxHoras;
    private int soluciones = 0;
    private Workshops workshops = new Workshops();
    private int vMejor;
    /**
     * funcion para parsear un fichero json a objeto java
     * @param rutaValida recibe la ruta del fichero a parsear
     * @return devuelve un unico objeto que los workshops y su matriz particular
     * @throws FileNotFoundException excepcion por indicar un fichero que no puede localizar (controlada)
     */
    public Workshops parseToObject(String rutaValida) throws FileNotFoundException {
        Gson gson = new Gson();
        workshops = gson.fromJson(new FileReader(rutaValida), Workshops.class);
        configMaxHoras = new int [workshops.getWorkshops().size()];
        return workshops;

    }
    /**
     * funcion para comprobar que complimos con las pautas indicadas {no haya solapamento y que sean compatibles} hasta el momento
     * - segun la eleccion del usuario se aplican mejoras basadas en PBMSC
     * la respyesta del usuario esta en la variable @mejoras
     * @param configuracion configuracion
     * @param k complimos hasta la k-esima
     * @return nos devuelve si o no complimos
     */
    public boolean buena(int configuracion[], int k) {  //
        if (configuracion[k] == 0) {
            return true;
        }
        if (mejoras) {
             /*if (sumaHoras(configuracion, k) < vMejor) {
                return false;
             } */
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
    /**
     * funcion para generar una desicion en ese nivel
     * @param configuracion configuracion
     * @param k k
     */
    public void seguienteHermano(int [] configuracion, int k) {
        configuracion[k]++;   // = configuracion [k]+1 ; // decidir ir ws
    }
    /**
     * funcion para establecer una decision no tomada aun
     * @param configuracion sobre esta configuracion
     * @param k k
     */
    public void prepararRecorrigoNivel(int [] configuracion, int k) {
        configuracion [k] = -1;
    }
    /**
     * funcion para comprobar que aun tenemos opciones aun para decidir en una configuracion dada
     * @param configuracion la configuracion
     * @param k k
     * @return devuelve si en caso afirmativo
     */
    public boolean haySucesor(int []configuracion, int k) {
        return configuracion[k] < 1;

    }
    /**
     * funcion para obtener la solucion encontrada y tratarla, vamos quedando con la configuracion que mas se acorda a nuestras restrcciones
     * @param configuracion recibe la configuracion de la solucion
     * @param k k
     */
    public void tratarSolucion(int [] configuracion, int k) {
         int sum = sumaHoras(configuracion);
          if (sum > maxHoras){
                 System.arraycopy(configuracion, 0, configMaxHoras, 0, configuracion.length);
                 maxHoras = sum;
                 soluciones = 0;
          }
          if (sum == maxHoras){
              soluciones ++;
          }
    }
    /**
     * algoritmo recursivo que trata de encontrar todas las posibles soluciones al problema, siguiendo con las restriccioens puestas
     * @param configuracion siempre recibe una configuracion sobre la cual irá construyendo el arbol de busqueda
     * @param k representa el nivel del arbol en el cual estado explorando
     */
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
    /**
     * funcion que nos devuelve el total de las soluciones encontradas segun las restricciones puestas
     * @return un entero que las representa
     */
    @Override
    public int totalSolucion() {
        return soluciones;
    }
    /**
     * funcion que nos devuelve el maximo de horas de la configuracion
     * @return un entero que representa las horas de los workshops
     */
    public int [] maxHoras () {
        return configMaxHoras;
    }

    /**
     * funcion que nos devuelve el sumatorio de hora de una configuracion dada
     * @param configuracion la configuracion
     * @return devuelve un entero
     */
    public int sumaHoras(int [] configuracion) {
        int sum = 0;
        for (int i = 0; i < configuracion.length; i++) {
            if (configuracion [i] == 1 ){
                sum += workshops.getWorkshops().get(i).getTimetable().size();
            }
        }
        return sum;
     }

    /**
     * funcion que nos devuelve el sumatorio realizado por la funcion sumaHoras de la ultima configuracion tratada
     * @return un entero
     */
     public Integer getMaxHoras (){
        return maxHoras;
     }

    /**
     * dada una configuracion nos devuelve el sumatorio de sus correspondientes talletes que la conforman
     * @return un entero
     */
     public Integer totalW (){
        Integer tmp = 0;
         for (int i = 0; i < configMaxHoras.length; i++) {
             if (configMaxHoras[i]==1){
                 tmp++;
             }
         }
         return tmp;
     }

    /**
     * funcion para establecer si el usuario quiere aplicar las mejoras de eficiencia a la hora de ejecutar el algoritmo
     * @param respuesta recibe la respuesta del usuario (si/no)
     */
    @Override
    public void setMejoras(boolean respuesta) {
        mejoras = respuesta;
    }

    /**
     * funcion para realizar el sumatorio de horas de una configuracion, solo hasta una posicion dada.
     * @param configuracion la configuracion
     * @param k sumar hasta llegar a la k posicion de condifuracion
     * @return entero que representa la suma
     */
    public int sumaHoras(int [] configuracion, int k) {
        int sum = 0;
        for (int i = 0; i < configuracion.length; i++) {
            if (configuracion [i] == 1 ){
                sum += workshops.getWorkshops().get(i).getTimetable().size();
            }
        }
        return sum;
    }

    /**
     * funcion para aplicar las mejoras de eficiencia en ejecucion,
     * unicamente seguimos explorando en caso de encontrar mejor o igual resultado
     * @param configuracion configuracion a tratar
     * @param k hasta esta posicion de la configuracion
     * @return es mejor o no
     */
    private boolean testPBMSC(int[] configuracion, int k) {
        return sumaHoras(configuracion, k) > maxHoras;
    }
}
