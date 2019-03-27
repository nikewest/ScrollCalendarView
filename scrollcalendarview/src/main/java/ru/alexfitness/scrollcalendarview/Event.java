package ru.alexfitness.scrollcalendarview;

import android.graphics.Color;

import java.util.Date;

public class Event {

    private String uid;
    private long id;
    private Date start;
    private Date end;
    private String name;
    private String description;
    private int color = Color.YELLOW;

    private class EventWrongDatesException extends Exception {
        public EventWrongDatesException(){
            super("Wrong dates for event!");
        }
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start){
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end){
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColor() {
        return color;
    }

    public boolean checkDates(){
        if(start!=null && end!=null){
            if(start.after(end) || start.getYear()!=end.getYear() || start.getMonth()!=end.getMonth() || start.getDate()!=end.getDate()){
                return false;
            }
        }
        return true;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
