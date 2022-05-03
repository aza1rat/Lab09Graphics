package com.example.lab09_graphicskashitsin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import math.interp;

public class MainActivity extends AppCompatActivity {
    MySurface s;
    int choiceGraph = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        s = findViewById(R.id.mySurface);
        s.setOnTouchListener(this.s);
        s.n = 100;
        s.x = new float[s.n];
        s.y = new float[s.n];

        for (int i = 0; i < s.n; i++)
        {
            s.x[i] = interp.map(i, 0,s.n - 1, 0.0f, (float) Math.PI * 4.0f);
            s.y[i] = (float) Math.cos(s.x[i]);
        }
        s.update();
        s.invalidate();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_graphic_navigation, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item_settings:
            {
                Intent i = new Intent(this, GraphicSettingsActivity.class);
                i.putExtra("dots", s.n);
                i.putExtra("xmax", s.xmax);
                i.putExtra("xmin", s.xmin);
                startActivityForResult(i, 555);
                return true;
             }
            case R.id.item_choice:
            {
                AlertDialog dlg = makeDialog("Выберите функцию графика");
                makeChoiceLayout(dlg);
                dlg.show();break;

            }
            case R.id.item_zoom_in:
            {
                s.setScaleX(s.getScaleX() + 0.2f);
                s.setScaleY(s.getScaleY() + 0.2f);break;
            }
            case R.id.item_zoom_out:
            {
                if (s.getScaleX() < 0.4)
                    break;
                s.setScaleX(s.getScaleX() - 0.2f);
                s.setScaleY(s.getScaleY() - 0.2f);break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int request, int result, @Nullable Intent data) {
        if (request == 555)
        {
            if (data != null)
            {
                s.n = data.getIntExtra("dots", 100);
                s.xmax = data.getFloatExtra("xmax", s.xmax);
                s.xmin = data.getFloatExtra("xmin", s.xmin);
                GraphicChange();
            }
        }
        super.onActivityResult(request, result, data);
    }

    AlertDialog makeDialog(String str)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dlg = builder.create();
        dlg.setTitle(str);
        return  dlg;
    }

    void makeChoiceLayout(AlertDialog dlg)
    {
        LinearLayout layout = new LinearLayout(getBaseContext());
        layout.setOrientation(layout.VERTICAL);
        ListView lw = new ListView(getBaseContext());
        ArrayList<String> arrayList = new ArrayList<String>();

        arrayList.clear();
        arrayList.add("Функция cos(x)");
        arrayList.add("Функция sin(x)");
        arrayList.add("Функция tan(x)");
        arrayList.add("Функция atan(x)");
        arrayList.add("Гипербола");
        arrayList.add("Парабола");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        lw.setAdapter(arrayAdapter);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                choiceGraph = i;
                GraphicChange();
                dlg.cancel();
            }
        });
        layout.addView(lw);
        dlg.setView(layout);
    }

    void GraphicChange()
    {
        for (int i = 0; i < s.n; i++)
        {
            s.x[i] = interp.map(i, 0, s.n -1, s.xmin, s.xmax);
            switch (choiceGraph)
            {
                case 0:
                    s.y[i] = (float) Math.cos(s.x[i]);break;
                case 1:
                    s.y[i] = (float) Math.sin(s.x[i]);break;
                case 2:
                    s.y[i] = (float) Math.tan(s.x[i]);break;
                case 3:
                    s.y[i] = (float) Math.atan(s.x[i]);break;
                case 4:
                    s.y[i] = (float) 1 / s.x[i];break;
                case 5:
                    s.y[i] = (float) s.x[i] * s.x[i]; break;
            }
        }
        s.update();
        s.invalidate();
    }
}