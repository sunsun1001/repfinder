package com.example.sunsun1001.repfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WatchStart extends Activity {

    TextView text;
    Button startSend;
    String zipCode = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_start);


        text = (TextView) findViewById(R.id.text);

        Intent intent = getIntent();
        zipCode = intent.getStringExtra("zip");
        text.setText(" "+ zipCode);
    }



        //watchToPhone Stuff Here:

//        startSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
//                startService(sendIntent);
//            }
//        });
    }

