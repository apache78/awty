package edu.washington.apache78.Awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by apache78 on 5/14/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("MESSAGE");
        String number = intent.getStringExtra("NUMBER");
        Toast.makeText(context, number +" : " + message, Toast.LENGTH_LONG).show();
        Log.i("AWTY", "Fired");
    }
}
