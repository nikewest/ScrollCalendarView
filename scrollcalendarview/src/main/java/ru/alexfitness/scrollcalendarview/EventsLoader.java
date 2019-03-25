package ru.alexfitness.scrollcalendarview;

import java.util.Date;

public interface EventsLoader {

    void loadEvents(Date startDate, Date endDate);
    void setOnLoadListener(LoadEventsListener loadEventsListener);
    void onLoad();

}
