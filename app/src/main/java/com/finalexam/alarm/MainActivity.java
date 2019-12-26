package com.finalexam.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.TimePicker;
import android.widget.Toast;

import com.finalexam.game.GameActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private Button btnHenGio, btnHuy;
    private TimePicker timePicker;
    private ArrayList<File> mySongs;
    private String[] items;
    private String songList;
    private CheckBox ckLap;
    private int idHinh;
    public static PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHenGio = (Button) findViewById(R.id.btnHenGio);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        ckLap = (CheckBox) findViewById(R.id.ckLap);

        mySongs = new ArrayList<>();
        items = new String[mySongs.size()];


        for (int i = 0; i < mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName().toString()
                    .replace(".mp3", "");
        }


        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarmManager();
            }
        });

        }

    public void AlarmManager() {

        if (mySongs.size()>0){
            songList = mySongs.get(idHinh).getAbsolutePath();
        }

        Intent alarmIntent = new Intent(MainActivity.this, com.finalexam.alarm.AlarmReceiver.class);

        alarmIntent.putExtra("songList",songList);

        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        Calendar cal = Calendar.getInstance();

        cal.set(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(),
                0
        );

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (ckLap.isChecked()){
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else {
            manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }


        Toast.makeText(this, "Alarm Set for " + cal.get(Calendar.HOUR) + " : " + cal.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
    }

    //Stop/Cancel alarm manager
    public void stopAlarmManager() {
        if (pendingIntent != null) {
            startActivity(new Intent(this, GameActivity.class));
        } else {
            Toast.makeText(this, "You need set Alarm", Toast.LENGTH_SHORT).show();
        }

    }
}


