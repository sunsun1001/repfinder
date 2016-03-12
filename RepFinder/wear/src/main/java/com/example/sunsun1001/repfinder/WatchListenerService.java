package com.example.sunsun1001.repfinder;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by sunsun1001 on 3/2/16.
 */


public class WatchListenerService extends WearableListenerService {
    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
    private static final String ZIP_CODE = "/inputCode";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        //use the 'path' field in sendmessage to differentiate use cases
//        if( messageEvent.getPath().equalsIgnoreCase( ZIP_CODE ) ) {
        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
        Intent intent = new Intent(this, WatchStart.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("data", value);
        Log.v("T", "about to start watch MainActivity with zip: " + value);
        startActivity(intent);
    }
}