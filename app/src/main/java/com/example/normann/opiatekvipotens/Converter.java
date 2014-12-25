package com.example.normann.opiatekvipotens;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class Converter extends ActionBarActivity {
    public static final String[] opiates = {"morfin po",
            "ketobemidon po",
            "kodein po",
            "tramadol po",
            "oxykodon po",
            "tapentadol po",
            "hydromorfon po",
            "buprenorfin sl",
            "metadon po",
            "buprenorfin bandaid",
            "fentanyl bandaid",
            "morfin sc/iv",
            "ketobemidon sc/iv",
            "fentanyl sc/iv",
            "oxykodon sc/iv",
            "hydromorfon sc/iv",
            "metadon sc/iv" };

    /* Coeffient factor for oral medicins to oral morphine  */
    public final static double COEF_MORPHINE_PO_TO_MORPHINE_PO = 1;
    public final static double COEF_KETOBEMIDON_PO_TO_MORPHINE_PO = 1;
    public final static double COEF_KODEIN_PO_TO_MORPHINE_PO = 0.1;
    public final static double COEF_TRAMADOL_PO_TO_MORPHINE_PO = 0.1;
    public final static double COEF_OXYKODON_PO_TO_MORPHINE_PO = 1.5;
    public final static double COEF_TAPENTADOL_PO_TO_MORPHINE_PO  = (1/3);
    public final static double COEF_HYDROMORFON_PO_TO_MORPHINE_PO = 5;
    public final static double COEF_BUPRENORFIN_SL_TO_MORPHINE_PO = 75;

    public final static double COEF_BUPRENORFIN_BANDAID_TO_MORPHINE_PO = 1.8;	// baset på 1:75 ratio ?
    public final static double COEF_FENTANYL_BANDAID_TO_MORPHINE_PO = 2.4; // basert på ratio 1:100?

    public final static double COEF_MORPHINE_SC_IV_TO_MORPHINE_PO = 3;
    public final static double COEF_KETOBEMIDON_SC_IV_TO_MORPHINE_PO = 1*3;
    public final static double COEF_FENTANYL_SC_IV_TO_MORPHINE_PO = 50*3;
    public final static double COEF_OXYKODON_SC_IV_TO_MORPHINE_PO = 1*3;
    public final static double COEF_HYDROMORFON_SC_IV_TO_MORPHINE_PO = 5*3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        // Run the disclaimer EULA
        new SimpleEula(this).show();

        //String[] strings = { "Red", "Blue", "Green" };

        Spinner toSpin = (Spinner)findViewById(R.id.toSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opiates);
        toSpin.setAdapter(adapter);
        MultiSelectSpinner fromSpin = (MultiSelectSpinner)findViewById(R.id.my_spin);
        fromSpin.setItems(opiates);

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
        return super.onOptionsItemSelected(item);
    }

    public void onGetNameClick(View view) {

        // We have to state that are intention is to open another Activity. We do so
        // by passing a Context and the Activity that we want to open

        Intent getNameScreenIntent = new Intent(this, Result.class);

        // We ask for the Activity to start and don't expect a result to be sent back
        // startActivity(getNameScreenIntent);

        // We use startActivityForResult when we expect a result to be sent back

        final int result = 1;

        // To send data use putExtra with a String name followed by its value

        getNameScreenIntent.putExtra("callingActivity", "MainActivity");

        startActivityForResult(getNameScreenIntent, result);

        MultiSelectSpinner fromSpin = (MultiSelectSpinner)findViewById(R.id.my_spin);
        List<String> selected = fromSpin.getSelectedStrings();

        String sel1 = selected.get(0);
        Toast.makeText(getApplicationContext(), (CharSequence) sel1, Toast.LENGTH_LONG).show();

        double amount_original=0, amount_morphine_po=0, amount_result=0;

        for(int i=0; i< selected.size(); i++) {
            if(selected.get(i) == opiates[0]) {     // "morfin po",
                amount_morphine_po = amount_original * COEF_MORPHINE_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[1]) {    // "ketobemidon po",
                amount_morphine_po = amount_original * COEF_KETOBEMIDON_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[2]) {    // "kodein po",
                amount_morphine_po = amount_original * COEF_KODEIN_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[3]) {    // "tramadol po",
                amount_morphine_po = amount_original * COEF_TRAMADOL_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[4]) {    // "oxykodon po",
                amount_morphine_po = amount_original * COEF_OXYKODON_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[5]) {    // "tapentadol po",
                amount_morphine_po = amount_original * COEF_TAPENTADOL_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[6]) {    // "hydromorfon po",
                amount_morphine_po = amount_original * COEF_HYDROMORFON_PO_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[7]) {    // "buprenorfin sl",
                amount_morphine_po = amount_original * COEF_BUPRENORFIN_SL_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[8]) {    // "metadon po",
               // amount_morphine_po = amount_original * getCoef_Metadon_To_MorphinePo(amount_orignal);
            }else if(selected.get(i) == opiates[9]) {    // "buprenorfin bandaid",
                amount_morphine_po = amount_original * COEF_BUPRENORFIN_BANDAID_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[10]) {    // "fentanyl bandaid",
                amount_morphine_po = amount_original * COEF_FENTANYL_BANDAID_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[11]) {    // "morfin sc/iv",
                amount_morphine_po = amount_original * COEF_MORPHINE_SC_IV_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[12]) {    // "ketobemidon sc/iv",
                amount_morphine_po = amount_original * COEF_KETOBEMIDON_SC_IV_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[13]) {    // "fentanyl sc/iv",
                amount_morphine_po = amount_original * COEF_FENTANYL_SC_IV_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[14]) {    // "oxykodon sc/iv",
                amount_morphine_po = amount_original * COEF_OXYKODON_SC_IV_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[15]) {    // "hydromorfon sc/iv",
                amount_morphine_po = amount_original * COEF_HYDROMORFON_SC_IV_TO_MORPHINE_PO;
            }else if(selected.get(i) == opiates[16]) {    // "metadon sc/iv"
                //amount_morphine_po = amount_original *
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Create the TextView so I can put the users name on it
        TextView usersNameMessage = (TextView) findViewById(R.id.users_name_message);

        // Get the users name from the previous Activity
        String nameSentBack = data.getStringExtra("UsersName");

        // Add the users name to the end of the textView
        usersNameMessage.append(" " + nameSentBack);

    }
}