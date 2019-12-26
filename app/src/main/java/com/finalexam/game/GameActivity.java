package com.finalexam.game;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finalexam.alarm.AlarmSoundService;
import com.finalexam.alarm.MainActivity;
import com.finalexam.alarm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

    public static ArrayList<String> arrayName;
    ImageView imgGoc, imgNhan;
    String tenHinhGoc = "";
    public static int diem = 0;
    private TextView txtDiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imgGoc = (ImageView) findViewById(R.id.imgGoc);
        imgNhan = (ImageView) findViewById(R.id.imgNhan);
        txtDiem = (TextView) findViewById(R.id.txtDiem);

        txtDiem.setText(diem + " / 10");

        String[] mangTen = getResources().getStringArray(R.array.list_name);
        arrayName = new ArrayList<>(Arrays.asList(mangTen));

        Collections.shuffle(arrayName);
        tenHinhGoc = arrayName.get(10);

        int idHinh = getResources().getIdentifier(arrayName.get(10), "drawable", getPackageName());

        imgGoc.setImageResource(idHinh);

        imgNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(GameActivity.this, com.finalexam.game.ImageActivity.class), 23);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 23 && resultCode == RESULT_OK && data != null) {
            String tenHinhNhan = data.getStringExtra("tenhinhchon");
            int idHinhNhan = getResources().getIdentifier(tenHinhNhan, "drawable", getPackageName());
            imgNhan.setImageResource(idHinhNhan);

            if (tenHinhNhan.equals(tenHinhGoc) && diem < 10) {
                diem++;
                txtDiem.setText(diem + " / 10");
                Toast.makeText(this, "Bạn đã chọn đúng!", Toast.LENGTH_LONG).show();

                new CountDownTimer(2000, 100) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        Collections.shuffle(arrayName);
                        tenHinhGoc = arrayName.get(0);
                        int idHinh = getResources().getIdentifier(arrayName.get(0), "drawable", getPackageName());
                        imgGoc.setImageResource(idHinh);
                    }
                }.start();
            } else {
                Toast.makeText(this, "Bạn chọn sai" + "\n" + "Vui lòng chọn lại", Toast.LENGTH_LONG).show();
            }
            if (diem == 10){
                if (MainActivity.pendingIntent != null)
                {
                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    manager.cancel(MainActivity.pendingIntent);
                }

                stopService(new Intent(this,AlarmSoundService.class));

                NotificationManager notificationManager = (NotificationManager) this
                        .getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.cancel(0);
                MainActivity.pendingIntent = null;

                finish();

                //Toast.makeText(this, "Alarm Canceled/Stop by User.", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
