package com.finalexam.game;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.finalexam.alarm.R;

import java.util.Collections;

public class ImageActivity extends Activity {

    TableLayout tableLayoutImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        tableLayoutImage = (TableLayout) findViewById(R.id.tableLayoutImage);

        int soDong = 6;
        int soCot = 3;

        Collections.shuffle(com.finalexam.game.GameActivity.arrayName);

        for (int i = 1; i <= soDong; i++)
        {
            TableRow tableRow = new TableRow(this);

            for (int j = 1; j <= soCot; j++)
            {
                ImageView imageView = new ImageView(this);
                Resources r = getResources();
                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,r.getDisplayMetrics());
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(px,px);
                imageView.setLayoutParams(layoutParams);

                final int vitri = soCot * (i - 1 )+ j - 1;

                int idHinh = getResources().getIdentifier(com.finalexam.game.GameActivity.arrayName.get(vitri),"drawable",getPackageName());
                imageView.setImageResource(idHinh);

                tableRow.addView(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("tenhinhchon", com.finalexam.game.GameActivity.arrayName.get(vitri));
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
            tableLayoutImage.addView(tableRow );
        }
    }
}
