package ru.alexfitness.scrollcalendarview;

import java.util.Calendar;

public class CalendarEvent {

    private String uid;
    private long id;
    private String name;
    private String description;
    private int color;

    private int year;
    private int dayOfYear;

    private Calendar start;
    private int startHour;
    private int startMinute;

    private Calendar end;
    private int endHour;
    private int endMinute;

    private int length; //minutes

    public CalendarEvent(String uid, String name, String description, int color, int year, int dayOfYear, int startHour, int startMinute, int endHour, int endMinute){
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.color = color;

        start = Calendar.getInstance();
        start.set(Calendar.SECOND, 0);
        end = Calendar.getInstance();
        end.set(Calendar.SECOND, 0);

        setDate(year, dayOfYear);

        setStart(startHour, startMinute);
        setEnd(endHour, endMinute);

        computeLength();
    }

    private void setDate(int year, int dayOfYear){
        this.year = year;
        this.dayOfYear = dayOfYear;

        start.set(Calendar.YEAR, year);
        start.set(Calendar.DAY_OF_YEAR, dayOfYear);
        end.set(Calendar.YEAR, year);
        end.set(Calendar.DAY_OF_YEAR, dayOfYear);
    }

    private void setStart(int startHour, int startMinute){
        this.startHour = startHour;
        this.startMinute = startMinute;
        start.set(Calendar.HOUR_OF_DAY, startHour);
        start.set(Calendar.MINUTE, startMinute);
    }

    private void setEnd(int endHour, int endMinute){
        this.endHour = endHour;
        this.endMinute = endMinute;
        end.set(Calendar.HOUR_OF_DAY, endHour);
        end.set(Calendar.MINUTE, endMinute);
    }

    private void computeLength(){
        length = (endHour - startHour) * 60 + endMinute - startMinute;
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

    public void setColor(int color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }
}
