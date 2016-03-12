package com.example.sunsun1001.repfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class electionData extends Activity {

    private TextView romney;
    private TextView obama;
    private Button goBack;
    private TextView dist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("T", "Inside electionData");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_data);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        Intent thisIntent = getIntent();
        final String data = thisIntent.getStringExtra("data");
        String delims = "[|]";
        String[] dataSplit = data.split(delims);
        String romneyP = dataSplit[dataSplit.length - 1];
        String obamaP = dataSplit[dataSplit.length - 2];
        String countyP = dataSplit[0];

        /* Api Call to get 2012 data */
        dist = (TextView) findViewById(R.id.district);
        romney = (TextView) findViewById(R.id.romneyText);
        obama = (TextView) findViewById(R.id.obamaText);
        goBack = (Button) findViewById(R.id.back);

        romney.setText("Romney\t\t\t\t" + romneyP + "%");
        obama.setText("Obama\t\t\t\t\t" + obamaP + "%");
        dist.setText("County: " + countyP);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(electionData.this,
                        WatchStart.class);
                back.putExtra("data", data);
                startActivity(back);
            }

        });
    }

    SensorManager mSensorManager;
    float mAccel;
    float mAccelCurrent;
    float mAccelLast;

    final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            if (mAccel > 12) {
                Toast toast = Toast.makeText(getApplicationContext(), "Device has shaken.", Toast.LENGTH_LONG);
                toast.show();

                Log.v("T", "Shaking");
                Intent election = new Intent(electionData.this,
                        WatchStart.class);

                /* API Generate random zip code */

                Random gen = new Random();
                String newInput = "928" + Integer.toString(gen.nextInt(10))
                        + Integer.toString(gen.nextInt(10));

                /* Tell phone to do a new search */
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                sendIntent.putExtra("command", "new");
                sendIntent.putExtra("data", newInput);
                startService(sendIntent);

                // Change watch screens
                election.putExtra("inputCode", newInput);
                Log.v("T", "Created shaking Intent");
                startActivity(election);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}
