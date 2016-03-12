package com.example.sunsun1001.repfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.*;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import com.twitter.sdk.android.core.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class StartPage extends AppCompatActivity implements KeyEvent.Callback, OnTaskCompleted {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "S71tJtTHXhfxJS3Ak1cZDOeJd";
    private static final String TWITTER_SECRET = "1CfiDU7YavXeplHJOyW6oT2Atadf1W95O5MbocCoMKncCIdiv6";


    EditText zipText;
    Button repButton;
    ImageButton currLocButton;
    String zipCode;
    GeoCoding geoCoding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_start_page);


        // Twitter crap

        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession session = appSessionResult.data;
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
                twitterApiClient.getStatusesService().userTimeline(null, "junior_gut", 1,
                        null, null, false, false, false, true, new Callback<List<Tweet>>() {
                            @Override
                            public void success(Result<List<Tweet>> listResult) {
                                for(Tweet tweet: listResult.data) {
                                    Log.d("v", "result: " + tweet.text + "  " + tweet.createdAt);
                                }
                            }
                            @Override
                            public void failure(TwitterException e) {
                                e.printStackTrace();
                            }
                        });
            }
            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();
            }
        });






        repButton = (Button) findViewById(R.id.findRepButton);
        repButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(StartPage.this,
                        listReps.class);
                zipText = (EditText) findViewById(R.id.zipNum);
                zipCode = zipText.getText().toString();

                geoCoding = new GeoCoding(zipCode);
                geoCoding.execute();

                String myCounty = geoCoding.County; // this is null because it is asynchronous
                Log.v("TAG ","County: " + myCounty);


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

                myIntent.putExtra("inputCode", gen());
                startActivity(myIntent);

            }
        });
    }


    @Override
    public void onTaskCompleted(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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