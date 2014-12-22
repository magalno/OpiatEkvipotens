package com.example.normann.opiatekvipotens;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;


public class Converter extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        // Run the disclaimer EULA
        new SimpleEula(this).show();

        //String[] strings = { "Red", "Blue", "Green" };
        String[] strings = {"morfin po",
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

        MultiSelectSpinner mySpin = (MultiSelectSpinner)findViewById(R.id.my_spin);
        mySpin.setItems(strings);

// ...

        List<String> selected = mySpin.getSelectedStrings();

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