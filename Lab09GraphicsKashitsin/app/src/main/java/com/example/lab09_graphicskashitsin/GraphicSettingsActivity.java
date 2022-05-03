package com.example.lab09_graphicskashitsin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GraphicSettingsActivity extends AppCompatActivity {
    EditText points;
    EditText xmax;
    EditText xmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_settings);
        points = findViewById(R.id.input_points);
        xmax = findViewById(R.id.input_xmax);
        xmin = findViewById(R.id.input_xmin);

        Intent i = getIntent();
       points.setText(Integer.toString(i.getIntExtra("dots", 0)));
       xmax.setText(Float.toString(i.getFloatExtra("xmax", 0.0f)));
       xmin.setText(Float.toString(i.getFloatExtra("xmin", 0.0f)));
    }

    public void onCancel(View v)
    {
        finish();
    }

    public void onSave(View v)
    {
        int n;
        float Xmax, Xmin;
        try {
            n = Integer.valueOf(points.getText().toString());
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Требуется целое число", Toast.LENGTH_LONG).show();
            return;
        }
        if (n <= 0)
        {
            Toast.makeText(this,"Число точек должно быть больше 0", Toast.LENGTH_LONG).show();
            return;
        }
        try {
        Xmax = Float.valueOf(xmax.getText().toString());
        Xmin = Float.valueOf(xmin.getText().toString());}
        catch (Exception e)
        {
            Toast.makeText(this,"Требуется вещественное число", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("dots", n);
        intent.putExtra("xmax", Xmax);
        intent.putExtra("xmin", Xmin);
        setResult(RESULT_OK, intent);
        finish();
    }



}