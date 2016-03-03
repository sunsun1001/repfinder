package com.example.sunsun1001.repfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;


import com.example.sunsun1001.repfinder.R;

public class send_test extends AppCompatActivity {

    TextView tvPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_test);

        // 1. get passed intent
        Intent intent = getIntent();

        // 2. get person object from intent
        Person person = (Person) intent.getSerializableExtra("person");

        // 3. get reference to person textView
        tvPerson = (TextView) findViewById(R.id.tvPerson);

        // 4. display name & age on textView
        tvPerson.setText(person.toString());





    }
}

