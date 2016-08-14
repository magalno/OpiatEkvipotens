package com.example.normann.opiatekvipotens;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Converter extends ActionBarActivity {
    Spinner originalSpin;
    TextView unitTextView;
    // Coeffient factor for oral medicins to oral morphine
    public final static double COEF_MORPHINE_PO_TO_MORPHINE_PO = 1;
    public final static double COEF_KETOBEMIDON_PO_TO_MORPHINE_PO = 1;
    public final static double COEF_KODEIN_PO_TO_MORPHINE_PO = 0.1;
    public final static double COEF_TRAMADOL_PO_TO_MORPHINE_PO = 0.1;
    public final static double COEF_OXYKODON_PO_TO_MORPHINE_PO = 1.5;
    public final static double COEF_TAPENTADOL_PO_TO_MORPHINE_PO  = 0.3333333333333;
    public final static double COEF_HYDROMORFON_PO_TO_MORPHINE_PO = 5;
    public final static double COEF_BUPRENORFIN_SL_TO_MORPHINE_PO = 75;
    // Coeffient factor for plaster medicins to oral morphine
    public final static double COEF_BUPRENORFIN_PLASTER_TO_MORPHINE_PO = 1.8;	// baset på 1:75 ratio ?
    public final static double COEF_FENTANYL_PLASTER_TO_MORPHINE_PO = 2.4; // basert på ratio 1:100?
    // Coeffient factor for oral medicins to oral morphine
    public final static double COEF_MORPHINE_SC_IV_TO_MORPHINE_PO = 3;
    public final static double COEF_KETOBEMIDON_SC_IV_TO_MORPHINE_PO = 1*3;
    public final static double COEF_FENTANYL_SC_IV_TO_MORPHINE_PO = 0.15;//50*3/1000;
    public final static double COEF_OXYKODON_SC_IV_TO_MORPHINE_PO = 1*3;
    public final static double COEF_HYDROMORFON_SC_IV_TO_MORPHINE_PO = 5*3;
    public static final String[] opioids = {
        "morfin po",
        "ketobemidon po",
        "kodein po",
        "tramadol po",
        "oxykodon po",
        "tapentadol po",
        "hydromorfon po",
        "buprenorfin sl",
        "buprenorfin plaster",
        "fentanyl plaster",
        "morfin sc/iv",
        "ketobemidon sc/iv",
        "fentanyl sc/iv",
        "oxykodon sc/iv",
        "hydromorfon sc/iv"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        // Run the disclaimer EULA
        new SimpleEula(this).show();

        unitTextView = (TextView)findViewById(R.id.unitTextView);

        // Input selector
        originalSpin = (Spinner)findViewById(R.id.fromSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opioids);
        originalSpin.setAdapter(adapter);
        originalSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                unitTextView.setText(getUnit(originalSpin.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // Output selector
        MultiSelectSpinner resultSpin = (MultiSelectSpinner)findViewById(R.id.my_spin);
        resultSpin.setItems(opioids);
        String[] morphine = {opioids[0]};
        resultSpin.setSelection(morphine);

// ...

        //List<String> selected = fromSpin.getSelectedStrings();

}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.converter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.exit_app) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onConvertClick(View view) {
        // Get amount_original
        EditText dosageEditText = (EditText)findViewById(R.id.dosage);
        String dosageTextString = dosageEditText.getText().toString();


        if (dosageTextString.length()>0) {
            double amount_original= Double.parseDouble(dosageTextString);
            // Get input opioid
            String opioid_original = originalSpin.getSelectedItem().toString();


            // Get target opiods from input selector
            MultiSelectSpinner fromSpin = (MultiSelectSpinner) findViewById(R.id.my_spin);
            ArrayList<String> selected = fromSpin.getSelectedStrings();

            // Convert medicine to Morphine before further convertion
            double amount_morphine_po = convert2morphine(opioid_original, amount_original);

            // Array to hold the result values after the convertion
            double[] amount_result = convert2results(amount_morphine_po, selected);

            // We have to state that are intention is to open another Activity. We do so
            // by passing a Context and the Activity that we want to open
            Intent getNameScreenIntent = new Intent(this, Result.class);

            // Send data to result activity
            getNameScreenIntent.putExtra("callingActivity", "MainActivity");
            getNameScreenIntent.putExtra("original opioid", opioid_original);
            getNameScreenIntent.putExtra("original amount", amount_original);
            getNameScreenIntent.putStringArrayListExtra("target opioids", selected);
            getNameScreenIntent.putExtra("target amount", amount_result);

            // Start the result activity
            startActivity(getNameScreenIntent);
        }
        else {
            Toast.makeText(Converter.this, "Mengden kan ikke være tom.", Toast.LENGTH_SHORT).show();
        }
    }

    public String getUnit(String opioid){
        if (isPatch(opioid)){
            return "mcg/t";
        }
        else if (opioid.equals("fentanyl sc/iv")){
            return "mcg";
        }
        return "mg";
    }

    private boolean isPatch(String opioid){
        String[] words = opioid.split(" ");
        if (words[words.length-1].equals("plaster")){
            return true;
        }
        return false;
    };

    private double convert2morphine(String opioid_original, double amount_original){
        double amount_morphine_po=0;
        if(opioid_original == opioids[0]) {     // "morphine po",
            amount_morphine_po = amount_original * COEF_MORPHINE_PO_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[1]) {    // "ketobemidon po",
            amount_morphine_po = amount_original * COEF_KETOBEMIDON_PO_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[2]) {    // "kodein po",
            amount_morphine_po = amount_original * COEF_KODEIN_PO_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[3]) {    // "tramadol po",
            amount_morphine_po = amount_original * COEF_TRAMADOL_PO_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[4]) {    // "oxykodon po",
            amount_morphine_po = amount_original * COEF_OXYKODON_PO_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[5]) {    // "tapentadol po",
            amount_morphine_po = amount_original * COEF_TAPENTADOL_PO_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[6]) {    // "hydromorfon po",
            amount_morphine_po = amount_original * COEF_HYDROMORFON_PO_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[7]) {    // "buprenorfin sl",
            amount_morphine_po = amount_original * COEF_BUPRENORFIN_SL_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[8]) {    // "buprenorfin bandaid",
            amount_morphine_po = amount_original * COEF_BUPRENORFIN_PLASTER_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[9]) {    // "fentanyl bandaid",
            amount_morphine_po = amount_original * COEF_FENTANYL_PLASTER_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[10]) {    // "morfin sc/iv",
            amount_morphine_po = amount_original * COEF_MORPHINE_SC_IV_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[11]) {    // "ketobemidon sc/iv",
            amount_morphine_po = amount_original * COEF_KETOBEMIDON_SC_IV_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[12]) {    // "fentanyl sc/iv",
            amount_morphine_po = amount_original * COEF_FENTANYL_SC_IV_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[13]) {    // "oxykodon sc/iv",
            amount_morphine_po = amount_original * COEF_OXYKODON_SC_IV_TO_MORPHINE_PO;
        }else if(opioid_original == opioids[14]) {    // "hydromorfon sc/iv",
            amount_morphine_po = amount_original * COEF_HYDROMORFON_SC_IV_TO_MORPHINE_PO;
        }
        return amount_morphine_po;
    }

    private double[] convert2results(double amount_morphine_po, ArrayList<String> selected){
        double[] amount_result = new double[selected.size()];

        for(int i=0; i< selected.size(); i++) {
            if(selected.get(i) == opioids[0]) {     // "morfin po",
                amount_result[i] = amount_morphine_po / COEF_MORPHINE_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[1]) {    // "ketobemidon po",
                amount_result[i] = amount_morphine_po / COEF_KETOBEMIDON_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[2]) {    // "kodein po",
                amount_result[i] = amount_morphine_po / COEF_KODEIN_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[3]) {    // "tramadol po",
                amount_result[i] = amount_morphine_po / COEF_TRAMADOL_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[4]) {    // "oxykodon po",
                amount_result[i] = amount_morphine_po / COEF_OXYKODON_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[5]) {    // "tapentadol po",
                amount_result[i] = amount_morphine_po / COEF_TAPENTADOL_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[6]) {    // "hydromorfon po",
                amount_result[i] = amount_morphine_po / COEF_HYDROMORFON_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[7]) {    // "buprenorfin sl",
                amount_result[i] = amount_morphine_po / COEF_BUPRENORFIN_SL_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[8]) {    // "buprenorfin bandaid",
                amount_result[i] = amount_morphine_po / COEF_BUPRENORFIN_PLASTER_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[9]) {    // "fentanyl bandaid",
                amount_result[i] = amount_morphine_po / COEF_FENTANYL_PLASTER_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[10]) {    // "morfin sc/iv",
                amount_result[i] = amount_morphine_po / COEF_MORPHINE_SC_IV_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[11]) {    // "ketobemidon sc/iv",
                amount_result[i] = amount_morphine_po / COEF_KETOBEMIDON_SC_IV_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[12]) {    // "fentanyl sc/iv",
                amount_result[i] = amount_morphine_po / COEF_FENTANYL_SC_IV_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[13]) {    // "oxykodon sc/iv",
                amount_result[i] = amount_morphine_po / COEF_OXYKODON_SC_IV_TO_MORPHINE_PO;
            }else if(selected.get(i) == opioids[14]) {    // "hydromorfon sc/iv",
                amount_result[i] = amount_morphine_po / COEF_HYDROMORFON_SC_IV_TO_MORPHINE_PO;
            }
        }
        return amount_result;
    }

    public void open_table_pdf(MenuItem item) {
        Intent tablePdfIntent = new Intent(this, TablePdf.class);
        startActivity(tablePdfIntent);
    }
}