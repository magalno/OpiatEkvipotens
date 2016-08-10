package com.example.normann.opiatekvipotens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<String> {
    private final Context context;
    private ArrayList<String> opioids;
    private double[] dosages;

    public MyAdapter(Context context, ArrayList<String> opioids, double[] dosages) {
        super(context, R.layout.rowlayout, opioids);
        this.context = context;
        this.opioids = opioids;
        this.dosages = dosages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView opioidTextview = (TextView) rowView.findViewById(R.id.listviewopioid);
        TextView dosageTextview = (TextView) rowView.findViewById(R.id.listviewdosage);
        opioidTextview.setText(opioids.get(position));
        dosageTextview.setText(""+dosages[position]);
        return rowView;
    }
}