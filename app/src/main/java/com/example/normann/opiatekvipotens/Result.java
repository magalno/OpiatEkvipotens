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
        TextView originalTextView = (TextView)findViewById(R.id.original);
        originalTextView.setText(original_opiod);
        ArrayList<String> selected = intent.getStringArrayListExtra("target opioids");

        // Display selected opioids as listview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.rowlayout, R.id.listviewopioid, selected);
        ListView resultListView = (ListView) findViewById(R.id.resultListView);
        resultListView.setAdapter(adapter);
    }
}