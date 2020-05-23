package Model;
import java.io.FileNotFoundException;
public interface Backtracking {
     Workshops parseToObject(String rutaValida) throws FileNotFoundException;
     boolean buena(int configuracion[], int k);
     void seguienteHermano(int [] configuracion, int k);

     void prepararRecorrigoNivel(int [] configuracion, int k) ;

     boolean haySucesor(int []configuracion, int k) ;

     void tratarSolucion(int [] configuracion, int k);

     void backTracking(int [] configuracion, int k);

     int  totalSolucion ();

     Integer getMaxHoras ();

     Integer totalW();

     void setMejoras(boolean respuesta);
}
