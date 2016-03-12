package com.example.sunsun1001.repfinder;

import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.content.*;
import android.widget.ListView;
import android.widget.ImageView;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;


import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class listReps extends AppCompatActivity {

    ListView lv;
    Context context;
    listReps varTemp;

    public static final String sunlightApiKey = "apikey=2cd94ab183e8467d9a9bb2229c20ab55";

    public static String[] senatorNames = {"Barbara Boxer (D)", "Barbara Boxer (D)",
            "Barbara Boxer (D)"};
    public static String[] url = {"http://boxer.senate.gov", "http://boxer.senate.gov",
            "http://boxer.senate.gov"};
    public static String[] emailAdd = {"senator@boxer.senate.gov", "senator@boxer.senate.gov",
            "senator@boxer.senate.gov"};
    public static String[] tweet = {"Democrats have 3 words...", "Democrats have 3 words...",
            "Democrats have 3 words..."};
    public static String[] bioID = {"F000062", "F000062", "F000062"};
    public static String county = "nothing";
    public static String state = "nothing";
    public static String[] termDate = {"May 2016", "May 2016", "May 2016"};
    public static String[] party = {"D", "D", "D", "D"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reps);

        context = this;
        varTemp = this;
        lv = (ListView) findViewById(R.id.listView);

        // Get Election County
        countyFind obj = new countyFind();
        obj.execute();
    }


    public class countyFind extends AsyncTask<Void, Void, Void> {

        StringBuilder sb;
        BufferedReader br;

        @Override
        protected Void doInBackground(Void... params) {
        /* Sunlight API Call */
            StringBuilder urlString =
                    new StringBuilder("http://maps.google.com/maps/api/geocode/json?");

            String inputCode = getIntent().getStringExtra("inputCode");

                /* Check if we need to query lat/long or zip */
            try {
                if (inputCode.equals("-1")) {
                    String lat = getIntent().getStringExtra("lat");
                    String mLong = getIntent().getStringExtra("mLong");
                    urlString.append("latlng=" + lat + "," + mLong);
                } else {
                    urlString.append("address=" +
                            URLEncoder.encode(getIntent().getStringExtra("inputCode"), "utf8"));
                }
                urlString.append("&sensor=false");

                java.net.URL url = new URL(urlString.toString());
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
                JSONArray Results = data.getJSONArray("results");
                JSONObject zero = Results.getJSONObject(0);
                JSONArray address_components = zero.getJSONArray("address_components");

                for (int i = 0; i < address_components.length(); i++) {
                    JSONObject zero2 = address_components.getJSONObject(i);
                    String long_name = zero2.getString("long_name");
                    String short_name = zero2.getString("short_name");
                    JSONArray mtypes = zero2.getJSONArray("types");
                    String Type = mtypes.getString(0);

                    if (!TextUtils.isEmpty(long_name) || !long_name.equals(null)
                            || long_name.length() > 0 || !long_name.equals("")) {
                        if (Type.equalsIgnoreCase("administrative_area_level_2")) {
                            varTemp.county = long_name;
                            Log.v("v", "County: " + varTemp.county);
                        }
                    }
                    if (!TextUtils.isEmpty(short_name) || !short_name.equals(null)
                            || short_name.length() > 0 || !short_name.equals("")) {
                        if (Type.equalsIgnoreCase("administrative_area_level_1")) {
                            varTemp.state = short_name;
                            Log.v("v", "State: " + varTemp.state);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // Now call Sunlight Data to get Info

            SunlightData obj = new SunlightData();
            obj.execute();

            super.onPostExecute(aVoid);
        }
    }


    public class SunlightData extends AsyncTask<Void, Void, Void> {

        StringBuilder sb;
        BufferedReader br;

        @Override
        protected Void doInBackground(Void... params) {
        /* Sunlight API Call */
            String urlQuery = "http://congress.api.sunlightfoundation.com/legislators/locate?"
                    + sunlightApiKey;
            StringBuilder urlStringBuilder = new StringBuilder(urlQuery);

            try {
                String inputCode = getIntent().getStringExtra("inputCode");

                /* Check if we need to query lat/long or zip */
                if (inputCode.equals("-1")) {
                    String lat = getIntent().getStringExtra("lat");
                    String mLong = getIntent().getStringExtra("mLong");
                    urlStringBuilder.append("&latitude=" + lat + "&longitude=" + mLong);
                } else {
                    urlStringBuilder.append("&zip=" +
                            URLEncoder.encode(getIntent().getStringExtra("inputCode"), "utf8"));
                }

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

            try {
                data = new JSONObject(sb.toString());
                String inputCode = getIntent().getStringExtra("inputCode");
                Log.v("v", "Data: " + data);

                JSONArray arr = null;
                arr = new JSONArray(data.getString("results"));
                JSONObject tempJSON = null;

                varTemp.senatorNames = new String[arr.length()];
                varTemp.url = new String[arr.length()];
                varTemp.emailAdd = new String[arr.length()];
                varTemp.bioID = new String[arr.length()];
                varTemp.party = new String[arr.length()];
                varTemp.tweet = new String[arr.length()];
                varTemp.termDate = new String[arr.length()];

                for (int i = 0; i < arr.length(); i++) {
                    varTemp.tweet[i] = "loading...";
                }

                for (int i = 0; i < arr.length(); i++) {
                    tempJSON = arr.getJSONObject(i);

                    varTemp.senatorNames[i] = tempJSON.getString("title") + ". "
                            + tempJSON.getString("first_name") + " "
                            + tempJSON.getString("last_name") + " ("
                            + tempJSON.getString("party") + ")";
                    varTemp.url[i] = tempJSON.getString("website");
                    varTemp.emailAdd[i] = tempJSON.getString("oc_email");
                    varTemp.party[i] = tempJSON.getString("party");
                    varTemp.bioID[i] = tempJSON.getString("bioguide_id");
                    varTemp.termDate[i] = tempJSON.getString("term_end");

                    getTweet(tempJSON.getString("twitter_id"), i, arr.length() - 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 2012 Data
            String temp;
            JSONObject tempJSON;
            String elData = "";
            try {
                InputStream is = context.getAssets().open("eldata.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                temp = new String(buffer, "UTF-8");
                JSONArray jsonArray = new JSONArray(temp);
                for (int i = 0; i < jsonArray.length(); i++) {
                    tempJSON = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                    String name = tempJSON.getString("county-name");
                    String state = tempJSON.getString("state-postal");

                    if (state.equalsIgnoreCase(varTemp.state)
                            && name.equalsIgnoreCase(varTemp.county
                            .substring(0, varTemp.county.length() - 7))) {
                        elData += tempJSON.getString("obama-percentage") + "|"
                                + tempJSON.getString("romney-percentage");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.v("v", "Election Data: " + elData);


            // Send to Watch Stuff
            String toWatch = varTemp.county + "|";
            for (int i = 0; i < varTemp.senatorNames.length; i++) {
                toWatch += varTemp.senatorNames[i] + ",";
                toWatch += varTemp.party[i] + ",";
                toWatch += varTemp.termDate[i] + ",";
                toWatch += varTemp.bioID[i] + ",";
                toWatch += "|";
            }
            toWatch += elData;

            Log.v("v", "Data To Phone: " + toWatch);

            /* Send to phonetowatch */
            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
            sendIntent.putExtra("data", toWatch);
            sendIntent.putExtra("inputCode", getIntent().getStringExtra("inputCode"));
            startService(sendIntent);

            super.onPostExecute(aVoid);
        }
    }

    /* Get most recent tweet */
    private void getTweet(final String twitterID, final int tweet_index, final int maxTweet) {
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {

            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession session = appSessionResult.data;
                TwitterApiClient twitterApiClient =
                        TwitterCore.getInstance().getApiClient(session);
                twitterApiClient.getStatusesService().userTimeline(null, twitterID, 1,
                        null, null, false, false, false, true, new Callback<List<Tweet>>() {

                            @Override
                            public void success(Result<List<Tweet>> listResult) {
                                for (Tweet tweet : listResult.data) {
                                    Log.d("v", "result: " + tweet.text + "  " + tweet.createdAt);
                                    varTemp.tweet[tweet_index] = "Recent Tweet" + tweet.text;
                                    Log.v("v", "changed tweet inside: " + varTemp.tweet[tweet_index]);
                                }

                                if (tweet_index == maxTweet) {
                                    String inputCode = getIntent().getStringExtra("inputCode");

                                    // CustomAdapter Organize Data
                                    CustomAdapter tempAdapter = new CustomAdapter(varTemp, senatorNames,
                                            url, emailAdd, tweet, bioID, party, termDate);
                                    lv.setAdapter(tempAdapter);

                                    // Detailed View Intent Stuff Here:
                                    Intent newIntent = new Intent(listReps.this,
                                            detail_view.class);
                                    newIntent.putExtra("inputCode", getIntent().getStringExtra("inputCode"));
                                    tempAdapter.newIntent = newIntent;
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
    }


}