package Model;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Timetable {

    private Integer day;

    private Integer hour;

    public Timetable() {
    }

    /**
     *
     * @param hour horas
     * @param day dias
     */

    public Timetable(Integer day, Integer hour) {
        super();
        this.day = day;
        this.hour = hour;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).append("day", day).append("hour", hour).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(hour).append(day).toHashCode();
    }


    @Override
    public boolean equals(Object other) {

        Timetable rhs = ((Timetable) other);
        return new EqualsBuilder().append(hour, rhs.hour).append(day, rhs.day).isEquals();
    }


}