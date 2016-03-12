package com.example.sunsun1001.repfinder;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.*;
import android.util.Log;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.example.sunsun1001.repfinder.R;

public class listReps extends AppCompatActivity {

    String zipCode;
    ListView lv;
    Context context;

    public static int[] picture = {R.drawable.boxer, R.drawable.boxer,
            R.drawable.boxer};
    public static String[] senatorNames = {"Barbara Boxer (D)", "Barbara Boxer (D)",
            "Barbara Boxer (D)"};
    public static String [] url = {"http://boxer.senate.gov", "http://boxer.senate.gov",
            "http://boxer.senate.gov"};
    public static String [] emailAdd = {"senator@boxer.senate.gov", "senator@boxer.senate.gov",
            "senator@boxer.senate.gov"};
    public static String [] tweet = {"Democrats have 3 words...", "Democrats have 3 words...",
            "Democrats have 3 words..."};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reps);

        context = this;

        lv = (ListView) findViewById(R.id.listView);
        CustomAdapter tempAdapter = new CustomAdapter(this, picture, senatorNames, url, emailAdd, tweet);
        lv.setAdapter(tempAdapter);

        Intent newIntent = new Intent(listReps.this,
                detail_view.class);
        newIntent.putExtra("inputCode", getIntent().getStringExtra("inputCode"));
        tempAdapter.newIntent = newIntent;


//        Intent i = getIntent();
//        zipCode = i.getStringExtra("zip");

        // Send Zip to Watch
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("inputCode", getIntent().getStringExtra("inputCode"));
        startService(sendIntent);

    }
}