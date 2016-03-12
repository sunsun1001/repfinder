package com.example.sunsun1001.repfinder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import java.nio.charset.StandardCharsets;

/**
 * Created by sunsun1001 on 3/3/16.
 */


public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String DETAILED = "/detailed";
    private static final String NEW = "/new";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.v("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase(DETAILED) ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            // Make a toast with the String
            Context context = getApplicationContext();

            /* Use Value to launch the intent of detailed*/
            Intent intent = new Intent(this, detail_view.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("name", value);

            startActivity(intent);

        } else if (messageEvent.getPath().equalsIgnoreCase(NEW)){
            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            // Make a toast with the String
            Context context = getApplicationContext();

                        /* Use Value to launch the intent of rep*/
            Intent intent = new Intent(this, listReps.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("inputCode", value);

            startActivity(intent);

        } else
        {
            super.onMessageReceived( messageEvent );
        }

    }
}
