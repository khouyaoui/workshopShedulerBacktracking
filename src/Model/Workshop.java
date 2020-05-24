package Model;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;

/**
 * clase para representar un unico taller con sus especificaciones.
 */
public class Workshop {
    private String acronym;
    private Double price;
    private Integer category;
    private List<Timetable> timetable = new ArrayList<>();
    private List<Integer> rgbColor = new ArrayList<>();

    public String getAcronym() {
        return acronym;
    }
    public Double getPrice() {
        return price;
    }
    public Integer getCategory() {
        return category;
    }
    public List<Timetable> getTimetable() {
        return timetable;
    }
    public List<Integer> getRgbColor() {
        return rgbColor;
    }
}
