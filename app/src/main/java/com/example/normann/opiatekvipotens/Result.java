package com.example.normann.opiatekvipotens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Result extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout
        setContentView(R.layout.activity_result);

        // Get passed objects
        Intent intent = getIntent();
        String original_opiod = intent.getStringExtra("original opioid");
        double original_amount = intent.getDoubleExtra("original amount", 0);
        ArrayList<String> selected = intent.getStringArrayListExtra("target opioids");
        double[] dosages = intent.getDoubleArrayExtra("target amount");

        // Display original opioid and dosage
        TextView originalTextView = (TextView)findViewById(R.id.original);
        originalTextView.setText("Fra " + original_amount + " mg med " + original_opiod);


        // Display selected opioids as listview
        MyAdapter adapter = new MyAdapter(this, selected, dosages);
        ListView resultListView = (ListView) findViewById(R.id.resultListView);
        resultListView.setAdapter(adapter);
    }
}