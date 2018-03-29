package com.example.russell.missionelapsedtime;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by russell on 11/11/17.
 */

public class METApp extends Application {

    private ArrayList<Event> events;
    private boolean somethingRemoved;

    public void addEvent(Event event){
        events.add(event);
    }

    public Event getEvent(int index){
        return events.get(index);
    }

    public ArrayList<Event> getAllEvents(){
        return events;
    }
    public int size(){
        return events.size();
    }
    public boolean isEmpty(){
        return events.isEmpty();
    }

    public void setEvents(int index, Event event){
        events.set(index,event);
    }

    public void remove(int index){
        events.remove(index);
    }

    public boolean isSomethingRemoved() {
        return somethingRemoved;
    }

    public void setSomethingRemoved(boolean somethingRemoved) {
        this.somethingRemoved = somethingRemoved;
    }

    public void sort(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        events = new ArrayList<Event>();
        somethingRemoved = false;
    }
}
