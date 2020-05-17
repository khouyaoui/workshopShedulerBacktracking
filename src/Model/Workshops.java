
package Model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class Workshops {


    private List<Workshop> workshops = new ArrayList<>();

    // private List<List<Integer>> compatibilityMatrix = new ArrayList<>();

    private Integer [][] compatibilityMatrix;

    /**
     * No args constructor for use in serialization
     *
     */
    public Workshops() {
    }

    /**
     * description ...
     * @param workshops
     * @param compatibilityMatrix
     */
    public Workshops(List<Workshop> workshops, Integer [][] compatibilityMatrix) {
        super();
        this.workshops = workshops;
        this.compatibilityMatrix = compatibilityMatrix;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }

    public Integer[][] getCompatibilityMatrix() {
        return compatibilityMatrix;
    }

    public void setCompatibilityMatrix(Integer [][] compatibilityMatrix) {
        this.compatibilityMatrix = compatibilityMatrix;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("workshops", workshops).append("compatibilityMatrix", compatibilityMatrix).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(workshops).append(compatibilityMatrix).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Workshops)) {
            return false;
        }
        Workshops rhs = ((Workshops) other);
        return new EqualsBuilder().append(workshops, rhs.workshops).append(compatibilityMatrix, rhs.compatibilityMatrix).isEquals();
    }

}
