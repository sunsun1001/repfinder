package com.example.sunsun1001.repfinder;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;

import com.squareup.picasso.Picasso;
import com.example.sunsun1001.repfinder.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class detail_view extends AppCompatActivity {

    Button goBack;
    Context context;
    detail_view varTemp;

    public static final String sunlightApiKey = "apikey=2cd94ab183e8467d9a9bb2229c20ab55";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        varTemp = this;

        // Get the last Intent
        final Intent prevIntent = getIntent();

        // Sets Image
        String bioID = getIntent().getStringExtra("bioID");
        String url = "https://theunitedstates.io/images/congress/original/" + bioID + ".jpg";
        ImageView img = (ImageView) findViewById(R.id.detailedImage);
        Picasso.with(context)
                .load(url)
                .into(img);

        String name = prevIntent.getStringExtra("name");
        String party = prevIntent.getStringExtra("party");
        String term = prevIntent.getStringExtra("term");

        // Text view formatting

        // Sets name of person
        TextView names = (TextView) findViewById(R.id.nameView);
        SpannableString mName = new SpannableString(name);

        // Background
        TextView background = (TextView) findViewById(R.id.background);
        background.setText("Background", TextView.BufferType.SPANNABLE);
        TextView backgroundBody = (TextView) findViewById(R.id.backgroundBody);
        SpannableString text = null;
        String partyStr = null;

        Log.v("v", "Name: " + name);
        Log.v("v", "Party: " + party);

        if (party.equalsIgnoreCase("D")) {
            partyStr = "Democratic Party\nTerm Conclusion: " + term;
            text = new SpannableString(partyStr);
            text.setSpan(new ForegroundColorSpan(Color.parseColor("#2F80ED")),
                    0, partyStr.length(), 0);

        }
        if (party.equalsIgnoreCase("I")) {
            partyStr = "Independent Party\nTerm Conclusion: " + term;
            text = new SpannableString(partyStr);
            text.setSpan(new ForegroundColorSpan(Color.parseColor("#219653")),
                    0, partyStr.length(), 0);

        }
        if (party.equalsIgnoreCase("R")) {
            partyStr = "Republican Party\nTerm Conclusion: " + term;
            text = new SpannableString(partyStr);
            text.setSpan(new ForegroundColorSpan(Color.parseColor("#EB5757")),
                    0, partyStr.length(), 0);

        }

        backgroundBody.setText(text,
                TextView.BufferType.SPANNABLE);
        names.setText(name, TextView.BufferType.SPANNABLE);

        /* Sunlight Dataset */
        GetCommittee obj = new GetCommittee();
        obj.execute();

        goBack = (Button) findViewById(R.id.backButton);

        goBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent myIntent = new Intent(Detailed.this,
//                        RepView.class);
//
//                myIntent.putExtra("inputCode", prevIntent.getStringExtra("inputCode"));
//
//                startActivity(myIntent);
                finish();
            }
        });
    }

    public class GetCommittee extends AsyncTask<Void, Void, Void> {

        StringBuilder sb;
        BufferedReader br;

        @Override
        protected Void doInBackground(Void... params) {
        /* Sunlight API Call */
            String urlQuery = "http://congress.api.sunlightfoundation.com/committees?"
                    + sunlightApiKey;
            StringBuilder urlStringBuilder = new StringBuilder(urlQuery);

            try {
                String inputCode = getIntent().getStringExtra("inputCode");

                /* Check if we need to query lat/long or zip */
                urlStringBuilder.append("&member_ids=" + URLEncoder
                        .encode(getIntent().getStringExtra("bioID"), "utf8"));

                String URL = urlStringBuilder.toString();
                Log.v("v", "URL: " + URL);

                java.net.URL url = new URL(URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb = sb.append(line + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JSONObject data = null;
            String comm = "";
            try {
                data = new JSONObject(sb.toString());
                Log.v("v", "Data: " + data);

                JSONArray arr = null;
                arr = new JSONArray(data.getString("results"));
                JSONObject tempJSON = null;

                int maxComms = 4;
                for (int i = 0; i < arr.length(); i++) {
                    if (maxComms != 0) {
                        tempJSON = arr.getJSONObject(i);
                        comm += tempJSON.getString("name") + "\n";
                        maxComms--;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Com's
            TextView coms = (TextView) findViewById(R.id.com);
            coms.setText("Committees", TextView.BufferType.SPANNABLE);

            TextView comsBody = (TextView) findViewById(R.id.comBody);
            SpannableString text = new SpannableString(comm);

            if (getIntent().getStringExtra("party").equalsIgnoreCase("R")) {
                text.setSpan(new ForegroundColorSpan(Color.parseColor("#EB5757")),
                        0, comm.length(), 0);
            } else if (getIntent().getStringExtra("party").equalsIgnoreCase("I")) {
                text.setSpan(new ForegroundColorSpan(Color.parseColor("#219653")),
                        0, comm.length(), 0);
            } else if (getIntent().getStringExtra("party").equalsIgnoreCase("D")) {
                text.setSpan(new ForegroundColorSpan(Color.parseColor("#2F80ED")),
                        0, comm.length(), 0);
            }
            comsBody.setText(text, TextView.BufferType.SPANNABLE);

            Bills obj = new Bills();
            obj.execute();

            super.onPostExecute(aVoid);
        }
    }

    public class Bills extends AsyncTask<Void, Void, Void> {

        StringBuilder sb;
        BufferedReader br;

        @Override
        protected Void doInBackground(Void... params) {
        /* Sunlight API Call */
            String urlQuery = "http://congress.api.sunlightfoundation.com/bills?"
                    + sunlightApiKey;
            StringBuilder urlStringBuilder = new StringBuilder(urlQuery);

            try {
                String inputCode = getIntent().getStringExtra("inputCode");

                /* Check if we need to query lat/long or zip */
                urlStringBuilder.append("&sponsor_id=" + URLEncoder
                        .encode(getIntent().getStringExtra("bioID"), "utf8"));

                String URL = urlStringBuilder.toString();
                Log.v("v", "URL: " + URL);

                java.net.URL url = new URL(URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb = sb.append(line + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JSONObject data = null;
            String sBills = "";

            try {
                data = new JSONObject(sb.toString());
                Log.v("v", "Data: " + data);

                JSONArray arr = null;
                arr = new JSONArray(data.getString("results"));
                JSONObject tempJSON = null;

                int maxBills = 4;
                for (int i = 0; i < arr.length(); i++) {
                    tempJSON = arr.getJSONObject(i);
                    if ((!tempJSON.getString("short_title").equalsIgnoreCase("null"))
                            && maxBills != 0) {
                        sBills += tempJSON.getString("introduced_on") + ": "
                                + tempJSON.getString("short_title") + "\n";
                        maxBills--;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Bills
            TextView bills = (TextView) findViewById(R.id.bills);
            bills.setText("Bills", TextView.BufferType.SPANNABLE);

            TextView billsBody = (TextView) findViewById(R.id.billsBody);
            SpannableString text = new SpannableString(sBills);

            if (getIntent().getStringExtra("party").equalsIgnoreCase("R")) {
                text.setSpan(new ForegroundColorSpan(Color.parseColor("#EB5757")),
                        0, sBills.length(), 0);
            } else if (getIntent().getStringExtra("party").equalsIgnoreCase("I")) {
                text.setSpan(new ForegroundColorSpan(Color.parseColor("#219653")),
                        0, sBills.length(), 0);
            } else if (getIntent().getStringExtra("party").equalsIgnoreCase("D")) {
                text.setSpan(new ForegroundColorSpan(Color.parseColor("#2F80ED")),
                        0, sBills.length(), 0);
            }
            billsBody.setText(text, TextView.BufferType.SPANNABLE);

            super.onPostExecute(aVoid);
        }
    }
}