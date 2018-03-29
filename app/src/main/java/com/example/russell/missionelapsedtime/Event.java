package com.example.russell.missionelapsedtime;

import java.util.Comparator;

/**
 * Created by russell on 11/11/17.
 */

public class Event {

    private int hour, minute, totalMinutes;
    private String description;

    public Event(int hour, int minute, String description) {
        this.hour = hour;
        this.minute = minute;
        this.totalMinutes = (hour * 60) + minute;
        this.description = description;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setOrder(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }



    @Override
    public String toString() {
        if (minute < 10){
            return hour + ":0" + minute;
        }
        else {
            return hour + ":" + minute;
        }
    }

    public static Comparator<Event> totalMinutesComparator = new Comparator<Event>() {
        @Override
        public int compare(Event e1, Event e2) {
            int total1 = e1.getTotalMinutes();
            int total2 = e2.getTotalMinutes();

            return total1-total2;
        }
    };
}
