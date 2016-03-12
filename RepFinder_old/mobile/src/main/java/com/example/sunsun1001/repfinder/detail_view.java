package com.example.sunsun1001.repfinder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;

import com.example.sunsun1001.repfinder.R;

public class detail_view extends AppCompatActivity {

    Button goBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        // Get the last Intent
        final Intent prevIntent = getIntent();

        // Get the corresponding Images and Names
        int pic = prevIntent.getIntExtra("picture", -1);

        // Checks if image came through, otherwise "query" for it in API
        if (pic != -1) {
            ImageView img = (ImageView) findViewById(R.id.detailedImage);
            img.setImageResource(pic);
        } else {
            ImageView img = (ImageView) findViewById(R.id.detailedImage);
            img.setImageResource(R.drawable.boxer);
        }

        String name = prevIntent.getStringExtra("name");

        // Text view formatting
        TextView names = (TextView) findViewById(R.id.nameView);
        names.setText(name, TextView.BufferType.SPANNABLE);

        // Information Layout
        TextView info=(TextView) findViewById(R.id.background);
        SpannableString content = new SpannableString("Senator Party Affiliation\n");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        info.setText(content);

        TextView backgroundBody=(TextView) findViewById(R.id.backgroundBody);

        backgroundBody.setText("Democratic Party\nTerm End Date: Jan. 9 2019",
                TextView.BufferType.SPANNABLE);

        TextView committees=(TextView) findViewById(R.id.com);
        SpannableString content2 = new SpannableString("Committees Served On:\n");
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        committees.setText(content2);

        TextView committeesBody=(TextView) findViewById(R.id.comBody);
        committeesBody.setText("Vice Chair of the SSIC\nHealth\nEducation\nEnergy", TextView.BufferType.SPANNABLE);

        TextView bills=(TextView) findViewById(R.id.bills);
        SpannableString content3 = new SpannableString("Recent Bills:\n");
        content3.setSpan(new UnderlineSpan(), 0, content3.length(), 0);
        bills.setText(content3);

        TextView billsBody=(TextView) findViewById(R.id.billsBody);
        billsBody.setText("S. 2372: Online Terrorist Activity Act\nS. 2337: Airport Security Act",
                TextView.BufferType.SPANNABLE);


        goBack = (Button) findViewById(R.id.backButton);

        goBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(detail_view.this,
                        listReps.class);

                myIntent.putExtra("sendCode", prevIntent.getStringExtra("sendCode"));

                startActivity(myIntent);
            }
        });
    }

}