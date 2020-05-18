package Model;

import java.util.ArrayList;
import java.util.Objects;

public class Schedule {

    private ArrayList <Workshop> Schedules;

    public Schedule(ArrayList<Workshop> schedules) {
        Schedules = schedules;
    }

    public ArrayList<Workshop> getSchedules() {
        return Schedules;
    }

    public void setSchedules(ArrayList<Workshop> schedules) {
        Schedules = schedules;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "Schedules=" + Schedules +
                '}';
    }


}
