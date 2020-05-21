package algorithm;

import Model.Backtracking;
import Model.Workshops;

import java.io.FileNotFoundException;

public class Config_3 implements Backtracking {
    @Override
    public Workshops parseToObject(String rutaValida) throws FileNotFoundException {
        return null;
    }

    @Override
    public boolean buena(int[] configuracion, int k) {
        return false;
    }

    @Override
    public void seguienteHermano(int[] configuracion, int k) {

    }

    @Override
    public void prepararRecorrigoNivel(int[] configuracion, int k) {

    }

    @Override
    public boolean haySucesor(int[] configuracion, int k) {
        return false;
    }

    @Override
    public void tratarSolucion(int[] configuracion, int k) {

    }

    @Override
    public void backTracking(int[] configuracion, int k) {

    }
}
