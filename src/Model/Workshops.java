package Model;
import java.util.ArrayList;
import java.util.List;
/**
 * clase para representar un conjunto de talleres con una matriz cuadrada de nTalleres para representar sus compatibilidades
 */
public class Workshops {

    private List<Workshop> workshops = new ArrayList<>();
    private Integer [][] compatibilityMatrix;

    /**
     * constructor por defecto, sin parametros
     */
    public Workshops() {
    }
    public List<Workshop> getWorkshops() {
        return workshops;
    }
    public Integer[][] getCompatibilityMatrix() {
        return compatibilityMatrix;
    }


}
