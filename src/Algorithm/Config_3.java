package Algorithm;

import Model.Backtracking;
import Model.Workshops;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * clase que implementar las operaciones de la tercera opcion
 */
public class Config_3 implements Backtracking {

    private boolean mejoras;
    private int soluciones = 0;
    private int[] configMaxPresupuesto;
    private Workshops workshops = new Workshops();
    private Double presupuestoUsuario = 2d;
    private Double presupuesto_tmp = 2d;
    private Double tmp = 2d, base;

    /**
     * funcion para parsear un fichero json a objeto java
     * @param rutaValida recibe la ruta del fichero a parsear
     * @return devuelve un unico objeto que los workshops y su matriz particular
     * @throws FileNotFoundException excepcion por indicar un fichero que no puede localizar (controlada)
     */
    public Workshops parseToObject(String rutaValida) throws FileNotFoundException {
        Gson gson = new Gson();
        workshops = gson.fromJson(new FileReader(rutaValida), Workshops.class);
        configMaxPresupuesto = new int[workshops.getWorkshops().size()];
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
    public boolean buena(int[] configuracion, int k) {  //
        if (configuracion[k] == 0) {
            return true;
        }

        if (mejoras) {
            if (!testPBMSC(configuracion, k)) {
                return false;
            }
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
    public void seguienteHermano(int[] configuracion, int k) {
        configuracion[k]++;   // = configuracion [k]+1 ; // decidir ir ws
    }

    /**
     * funcion para establecer una decision no tomada aun
     * @param configuracion sobre esta configuracion
     * @param k k
     */
    public void prepararRecorrigoNivel(int[] configuracion, int k) {
        configuracion[k] = -1;
    }

    /**
     * funcion para comprobar que aun tenemos opciones aun para decidir en una configuracion dada
     * @param configuracion la configuracion
     * @param k k
     * @return devuelve si en caso afirmativo
     */
    public boolean haySucesor(int[] configuracion, int k) {
        return configuracion[k] < 1;
    }

    /**
     * funcion para obtener la solucion encontrada y tratarla, vamos quedando con la configuracion que mas se acorda a nuestras restrcciones
     * @param configuracion recibe la configuracion de la solucion
     * @param k k
     */
    public void tratarSolucion(int[] configuracion, int k) {
        tmp = sumaPrecio(configuracion);
        if (tmp > presupuesto_tmp && tmp <= presupuestoUsuario) {
            System.arraycopy(configuracion, 0, configMaxPresupuesto, 0, configuracion.length);
            presupuesto_tmp = tmp;
            soluciones = 0;
        }
        if (tmp.equals(presupuesto_tmp)) {
            soluciones++;
        }
    }

    /**
     * algoritmo recursivo que trata de encontrar todas las posibles soluciones al problema, siguiendo con las restriccioens puestas
     * @param configuracion siempre recibe una configuracion sobre la cual irá construyendo el arbol de busqueda
     * @param k representa el nivel del arbol en el cual estado explorando
     */
    public void backTracking(int[] configuracion, int k) {
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
                    backTracking(configuracion, k + 1);
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
    @Override
    public Integer getMaxHoras() {
        return null;
    }

    /**
     * funcion que nos devuelve el total de workshops a los cuales asistiremos
     * @return un numero de workshops
     */
    @Override
    public Integer totalW() {
        return null;
    }

    /**
     * funcion que establece si el usuario quiere aplicar mejoras en la ejecucion del algoritmo
     * @param respuesta respuesta indicada por el usuario (true/false) (sí/no)
     */
    @Override
    public void setMejoras(boolean respuesta) {
        mejoras = respuesta;
    }

    /**
     * funcion que nos devuelve una configuracion provechosa de un presupuesto
     * @return devuelve la configuracion declarada como var global
     */
    public int[] maxPresupuesto() {

        return configMaxPresupuesto;
    }

    /**
     *
     * @param configuracion recibe la configuracion[n] 1 es y 0 no
     * @return funcion que nos devuelve el precio de un scheduler dada su configuracion
     */
    public Double sumaPrecio(int[] configuracion) {
        Double sum = 0d;
        List<Integer> categoras = new ArrayList<>();
        List<String> acronym = new ArrayList<>();
        for (int i = 0; i < configuracion.length; i++) {
            if (configuracion[i] == 1) {
                if (!acronym.contains(workshops.getWorkshops().get(i).getAcronym())) {
                    acronym.add(workshops.getWorkshops().get(i).getAcronym());
                    sum = sum + workshops.getWorkshops().get(i).getPrice();
                }
                if (!categoras.contains(workshops.getWorkshops().get(i).getCategory())) {
                    categoras.add(workshops.getWorkshops().get(i).getCategory());
                }
            }
        }
        base = sum;
        if (categoras.size() == 2) {
            return sum - (sum * 0.05);
        }
        if (categoras.size() > 2) {
            return sum - (sum * 0.15);
        }
        return sum;
    }

    /**
     *
     * @param p funcion para establecer el precio introducido como presupuesto del usuario
     */
    public void setMaxPresopuestoUsuario(Double p) {
        presupuestoUsuario = p;
    }

    /**
     *
     * @return funcion que nos devuelve el precio base del Scheduler
     */
    public Double getBase() {
        return base;
    }

    /**
     * funcion que nos devuelve la cantidad de cada categorias distintas de un scheduler
     * @return un array de enteros que representar cantidad de cada categoria
     */
    public int[] getCategorias() {
        int[] categorias = new int[5];
        List<String> aux = new ArrayList<>();
        for (int i = 0; i < configMaxPresupuesto.length; i++) {
            if (configMaxPresupuesto[i] == 1) {
                if (!aux.contains(workshops.getWorkshops().get(i).getAcronym())) {
                    aux.add(workshops.getWorkshops().get(i).getAcronym());
                    int categoria = workshops.getWorkshops().get(i).getCategory();
                    switch (categoria) {
                        case 1:
                            categorias[0]++;
                            break;
                        case 2:
                            categorias[1]++;
                            break;
                        case 3:
                            categorias[2]++;
                            break;
                        case 4:
                            categorias[3]++;
                            break;
                        case 5:
                            categorias[4]++;
                            break;
                    }
                }
            }
        }
        return categorias;
    }

    /**
     * funcion para comprobar si podemos reducir el espacio de busqueda de la solucion
     * @param configuracion configuracion actual
     * @param k complimos hasta k-esima
     * @return
     */
    private boolean testPBMSC(int[] configuracion, int k) {
        return sumaPrecio(configuracion, k) <= presupuestoUsuario+1;
    }

    /**
     * funcion para obtener la suma de precio de una configuracion dada o parte de ella, sumatorio segun las necesidades y solo hasta k-esima
     * @param configuracion sumatorio sobre esta configuracion
     * @param k hasta la posicion indicada
     * @return devuelve el sumatorio
     */
    public float sumaPrecio(int[] configuracion, int k) {
        float sum = 0;
        List<Integer> categoras = new ArrayList<>();
        List<String> acronym = new ArrayList<>();
        for (int i = 0; i <= k; i++) {
            if (configuracion[i] == 1) {
                if (!acronym.contains(workshops.getWorkshops().get(i).getAcronym())) {
                    acronym.add(workshops.getWorkshops().get(i).getAcronym());
                    sum = sum + workshops.getWorkshops().get(i).getPrice().floatValue();
                }
                if (!categoras.contains(workshops.getWorkshops().get(i).getCategory())) {
                    categoras.add(workshops.getWorkshops().get(i).getCategory());
                }
            }
        }
        if (categoras.size() == 2) {
            sum = (float) ( sum - (sum * 0.05));
            return sum;
        }
        if (categoras.size() > 2) {
            sum = (float) ( sum - (sum * 0.15));
            return sum;
        }
        return sum;
    }

}
