package com.example.normann.opiatekvipotens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Result extends Activity{
    private Converter converter = new Converter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout
        setContentView(R.layout.activity_result);

        // Get passed objects
        Intent intent = getIntent();
        String original_opiod = intent.getStringExtra("original opioid");
        double original_amount = intent.getDoubleExtra("original amount", 0);
        final ArrayList<String> selected = intent.getStringArrayListExtra("target opioids");
        double[] dosages = intent.getDoubleArrayExtra("target amount");

        // Display original opioid and dosage
        String unit = " "+converter.getUnit(original_opiod)+" ";
        TextView originalTextView = (TextView)findViewById(R.id.original);
        originalTextView.setText("Fra " + original_amount + unit + original_opiod);


        // Display selected opioids as listview
        MyAdapter adapter = new MyAdapter(this, selected, dosages);
        ListView resultListView = (ListView) findViewById(R.id.resultListView);
        resultListView.setAdapter(adapter);

        // Add onItemClickListener in case it is needed
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(Result.this, selected.get(arg2), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }
}