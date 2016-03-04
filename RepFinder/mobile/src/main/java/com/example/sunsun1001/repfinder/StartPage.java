package com.example.sunsun1001.repfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.*;
import java.util.Random;

public class StartPage extends AppCompatActivity {

    EditText zipText;
    Button repButton;
    ImageButton currLocButton;
    String zipCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        repButton = (Button) findViewById(R.id.findRepButton);
        repButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(StartPage.this,
                        listReps.class);
                zipText = (EditText) findViewById(R.id.zipNum);
                zipCode = zipText.getText().toString();
                myIntent.putExtra("inputCode", zipCode);
                startActivity(myIntent);
            }


        });

        currLocButton = (ImageButton) findViewById(R.id.locationButton);
        currLocButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View v) {
                Intent myIntent = new Intent(StartPage.this,
                        listReps.class);

                myIntent.putExtra("inputCode", gen());
                startActivity(myIntent);

            }
        });
    }

    public String gen() {
        Random r = new Random( System.currentTimeMillis() );
        return Integer.toString(10000 + r.nextInt(20000));
    }


    @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_start_page, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
}