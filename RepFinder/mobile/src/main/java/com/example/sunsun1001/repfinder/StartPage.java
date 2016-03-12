package com.example.sunsun1001.repfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.*;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import com.twitter.sdk.android.core.*;

import java.util.List;

public class StartPage extends AppCompatActivity implements KeyEvent.Callback {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "S71tJtTHXhfxJS3Ak1cZDOeJd";
    private static final String TWITTER_SECRET = "1CfiDU7YavXeplHJOyW6oT2Atadf1W95O5MbocCoMKncCIdiv6";


    EditText zipText;
    Button repButton;
    ImageButton currLocButton;
    String zipCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
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
            public void onClick(View v) {
                Intent myIntent = new Intent(StartPage.this,
                        LatLong.class);

                startActivity(myIntent);

            }
        });
    }


    public String gen() {
        Random r = new Random(System.currentTimeMillis());
        return Integer.toString(10000 + r.nextInt(20000));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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