package Model;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;

public class Workshop {

    private String acronym;

    private Double price;

    private Integer category;

    private List<Timetable> timetable = new ArrayList<>();

    private List<Integer> rgbColor = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Workshop() {
    }

    /**
     *
     * @param rgbColor etg
     * @param acronym  rtgerg
     * @param price  rtgerg
     * @param category  rtgeth
     * @param timetable rtget
     */
    public Workshop(String acronym, Double price, Integer category, List<Timetable> timetable, List<Integer> rgbColor) {
        super();
        this.acronym = acronym;
        this.price = price;
        this.category = category;
        this.timetable = timetable;
        this.rgbColor = rgbColor;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public List<Timetable> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<Timetable> timetable) {
        this.timetable = timetable;
    }

    public List<Integer> getRgbColor() {
        return rgbColor;
    }

    public void setRgbColor(List<Integer> rgbColor) {
        this.rgbColor = rgbColor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("acronym", acronym).append("price", price).append("category", category).append("timetable", timetable).append("rgbColor", rgbColor).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(rgbColor).append(category).append(acronym).append(price).append(timetable).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Workshop)) {
            return false;
        }
        Workshop rhs = ((Workshop) other);
        return new EqualsBuilder().append(rgbColor, rhs.rgbColor).append(category, rhs.category).append(acronym, rhs.acronym).append(price, rhs.price).append(timetable, rhs.timetable).isEquals();
    }

}
