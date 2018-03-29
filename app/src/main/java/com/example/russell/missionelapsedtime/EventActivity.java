package com.example.russell.missionelapsedtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TimePicker;

/**
 * Created by russell on 11/10/17.
 */

public class EventActivity extends AppCompatActivity
implements OnEditorActionListener, OnClickListener{

    private TimePicker time;
    private EditText description;
    private Button save, discard,delete;
    private METApp app;
    private boolean isNew;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        time = (TimePicker) findViewById(R.id.time);
        description = (EditText) findViewById(R.id.descriptionTextView);
        save = (Button) findViewById(R.id.saveButton);
        discard = (Button) findViewById(R.id.discardButton);
        delete = (Button) findViewById(R.id.deleteButton);
        app =(METApp) getApplication();

        save.setOnClickListener(this);
        discard.setOnClickListener(this);
        delete.setOnClickListener(this);

        Intent intent = getIntent();

        description.setOnEditorActionListener(this);
        time.setIs24HourView(true);
        isNew = intent.getBooleanExtra("new", true);
        index = intent.getIntExtra("index",0);

        if (isNew) {
            time.setMinute(0);
            time.setHour(0);
        }
        else{
            time.setMinute(intent.getIntExtra("minute",0));
            time.setHour(intent.getIntExtra("hour",0));
            description.setText(intent.getStringExtra("description"));
        }
    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        int keyCode = -1;
        if (keyEvent != null) {
            keyCode = keyEvent.getKeyCode();
        }
        if (i == EditorInfo.IME_ACTION_DONE || keyCode == KeyEvent.KEYCODE_ENTER) {
            //hide keyboard
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.saveButton:
                //use intent to see if this is new or edited
                //if edited use set(index,object) to overwrite
                //if new add
                int hour = time.getHour();
                int minute = time.getMinute();
                String d = description.getText().toString();
                Event event = new Event(hour,minute,d);
                if (isNew){
                    app.addEvent(event);
                }
                else{
                    app.setEvents(index,event);
                }
                finish();
                break;
            case R.id.discardButton:
                finish();
                break;
            case R.id.deleteButton:
                if (!isNew){
                    app.remove(index);
                    app.setSomethingRemoved(true);
                }
                finish();
                break;
        }
    }
}
