package Model;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public interface Backtracking {

    public Workshops parseToObject(String rutaValida) throws FileNotFoundException;
    public boolean buena(int configuracion[], int k);
    public void seguienteHermano(int [] configuracion, int k);

    public void prepararRecorrigoNivel(int [] configuracion, int k) ;

    public boolean haySucesor(int []configuracion, int k) ;

    public void tratarSolucion(int [] configuracion, int k);

    public void backTracking(int [] configuracion, int k);



}
