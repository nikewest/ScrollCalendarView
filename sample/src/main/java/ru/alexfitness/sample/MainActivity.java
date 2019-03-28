package ru.alexfitness.sample;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ru.alexfitness.scrollcalendarview.Event;
import ru.alexfitness.scrollcalendarview.EventDoubleClickListener;
import ru.alexfitness.scrollcalendarview.EventDragListener;
import ru.alexfitness.scrollcalendarview.EventSingleClickListener;
import ru.alexfitness.scrollcalendarview.EventsLoader;
import ru.alexfitness.scrollcalendarview.EventsLoaderListener;
import ru.alexfitness.scrollcalendarview.ScrollCalendarView;
import ru.alexfitness.scrollcalendarview.CalendarDoubleClickListener;
import ru.alexfitness.scrollcalendarview.TableLongPressListener;
import ru.alexfitness.scrollcalendarview.TableSingleClickListener;
import ru.alexfitness.scrollcalendarview.WaitingStateListener;

public class MainActivity extends AppCompatActivity {

    private ScrollCalendarView calendar;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        calendar = findViewById(R.id.calendar_view);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);

        calendar.setEventsLoader(new EventsLoader() {

            Handler eventHandler = new Handler();
            Runnable eventCreator = new Runnable() {
                @Override
                public void run() {
                    events.clear();
                    try {

                        Calendar calendar = Calendar.getInstance();
                        Date start = calendar.getTime();
                        calendar.add(Calendar.HOUR, 1);
                        Date end = calendar.getTime();
                        events.add(new Event("0", start, end, "Событие 1", "Описание события 1"));

                        calendar.add(Calendar.HOUR, 3);
                        start = calendar.getTime();
                        calendar.add(Calendar.HOUR, 1);
                        end = calendar.getTime();
                        events.add(new Event("1", start, end, "Событие 2", "Описание события 2"));

                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    onLoad();
                }
            };

            ArrayList<Event> events = new ArrayList<>();
            private EventsLoaderListener eventsLoaderListener;

            @Override
            public void loadEvents(Calendar startDate, Calendar endDate) {
                /*events.clear();
                Event event = new Event();
                Calendar calendar = Calendar.getInstance();
                event.setStart(calendar.getTime());
                calendar.add(Calendar.HOUR, 1);
                event.setEnd(calendar.getTime());
                event.setName("Какое то наименование!");
                event.setDescription("Event for testing \nContains multiply String \nУже третья строка");
                events.add(event);
                onLoad();*/
                eventHandler.postDelayed(eventCreator, 2000);
            }

            @Override
            public void setOnLoadListener(EventsLoaderListener eventsLoaderListener) {
                this.eventsLoaderListener = eventsLoaderListener;
            }

            @Override
            public void onLoad() {
                try {
                    eventsLoaderListener.onLoad(events);
                } catch (Event.EventsIntersectionException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
        calendar.setEventSingleClickListener(new EventSingleClickListener() {
            @Override
            public void onClick(Event event) {
                Toast.makeText(MainActivity.this, "EVENT", Toast.LENGTH_SHORT).show();
            }
        });
        calendar.setTableSingleClickListener(new TableSingleClickListener() {
            @Override
            public void onClick(Date date) {
                Toast.makeText(MainActivity.this, DateFormat.getDateTimeInstance().format(date), Toast.LENGTH_SHORT).show();
            }
        });
        calendar.setEventDoubleClickListener(new EventDoubleClickListener() {
            @Override
            public void onDoubleClick(Event event) {
                Toast.makeText(MainActivity.this, "DOUBLE CLICK " + event.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        calendar.setCalendarDoubleClickListener(new CalendarDoubleClickListener() {
            @Override
            public void onDoubleClick(Date date) {
                Toast.makeText(MainActivity.this, DateFormat.getDateTimeInstance().format(date) + " DOUBLE CLICK", Toast.LENGTH_SHORT).show();
            }
        });
        calendar.setTableLongPressListener(new TableLongPressListener() {
            @Override
            public void onLongPress(Date date) {
                Toast.makeText(MainActivity.this, "LONG PRESS " + DateFormat.getDateTimeInstance().format(date), Toast.LENGTH_SHORT).show();
            }
        });
        calendar.setWaitingStateListener(new WaitingStateListener() {
            @Override
            public void onWaitingStateChange(boolean state) {
                if(state) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        calendar.setEventDragListener(new EventDragListener() {
            @Override
            public void onDrop(final ScrollCalendarView.EventMover eventMover) {
                Event movedEvent = eventMover.getEvent();
                Toast.makeText(MainActivity.this, movedEvent.getDescription(), Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            eventMover.accept();
                        } catch (Event.EventsIntersectionException e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 2000);
            }
        });

        calendar.setRowHeight(100);
    }

    public void onInfoClick(View view) {
        /*
        try {
            calendar.setTimeStep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.setVisibleColumnCount(4);
        calendar.setRowHeight(100);
        */

        setTimeStepDialog();
    }

    private void setTimeStepDialog(){
        final EditText timestepEditText;
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.timestep_picker_layout, null);
        timestepEditText = customView.findViewById(R.id.timestepEditText);
        b.setView(customView);
        b.setTitle("Time step");
        b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int newTimeStep = Integer.valueOf(timestepEditText.getText().toString());
                try {
                    calendar.setTimeStep(newTimeStep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        b.show();
    }
}
