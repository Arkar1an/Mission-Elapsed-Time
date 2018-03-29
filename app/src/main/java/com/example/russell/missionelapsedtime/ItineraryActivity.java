package com.example.russell.missionelapsedtime;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

public class ItineraryActivity extends AppCompatActivity
implements OnItemClickListener{

    private ListView eventsListView;
    private METApp app;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        eventsListView = (ListView) findViewById(R.id.eventsListView);
        eventsListView.setOnItemClickListener(this);

        app = (METApp) getApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.itinerary,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDisplay();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void updateDisplay(){

        ArrayList<Event> events = app.getAllEvents();
        Collections.sort(events,Event.totalMinutesComparator);
        ArrayList<HashMap<String,String>> data =
                new ArrayList<HashMap<String,String>>();
        for (Event event : events){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("time",event.toString());
            map.put("description",event.getDescription());
            data.add(map);
        }

        if (!events.isEmpty()) {

            int resource = R.layout.listview_event;
            String[] from = {"time", "description"};
            int[] to = {R.id.metTextView, R.id.eventAtMetTextView};
            adapter = new SimpleAdapter(this, data, resource, from, to);
            eventsListView.setAdapter(adapter);
        }

        else {
            eventsListView.setAdapter(null);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Event selected = app.getEvent(i);
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("hour", selected.getHour());
        intent.putExtra("minute", selected.getMinute());
        intent.putExtra("description", selected.getDescription());
        intent.putExtra("new", false);
        intent.putExtra("index",i);
        this.startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Calendar rightNow = Calendar.getInstance();
        if (requestCode < app.size()-1) {
            Intent intent2 = new Intent(AlarmClock.ACTION_SET_ALARM);
            Event current = app.getEvent(requestCode + 1);
            intent2.putExtra(AlarmClock.EXTRA_HOUR,
                    rightNow.get(Calendar.HOUR_OF_DAY) + current.getHour());
            intent2.putExtra(AlarmClock.EXTRA_MINUTES,
                    rightNow.get(Calendar.MINUTE) + current.getMinute());
            intent2.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
            intent2.putExtra(AlarmClock.EXTRA_MESSAGE, current.getDescription());
            startActivityForResult(intent2, requestCode + 1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.new_event:
                Intent intent = new Intent(this, EventActivity.class);
                intent.putExtra("new", true);
                this.startActivity(intent);
                return true;
            case R.id.set_alarms:

                Calendar rightNow = Calendar.getInstance();
                Intent intent2 = new Intent(AlarmClock.ACTION_SET_ALARM);
                Event current = app.getEvent(0);
                intent2.putExtra(AlarmClock.EXTRA_HOUR,
                        rightNow.get(Calendar.HOUR_OF_DAY)+current.getHour());
                intent2.putExtra(AlarmClock.EXTRA_MINUTES,
                        rightNow.get(Calendar.MINUTE)+current.getMinute());
                intent2.putExtra(AlarmClock.EXTRA_SKIP_UI,true);
                intent2.putExtra(AlarmClock.EXTRA_MESSAGE, current.getDescription());
                startActivityForResult(intent2,0);

                return true;
            case R.id.cancel_alarms:
                Intent intent3 = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
                intent3.putExtra(AlarmClock.EXTRA_ALARM_SEARCH_MODE,AlarmClock.ALARM_SEARCH_MODE_ALL);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
