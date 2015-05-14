package edu.washington.apache78.Awty;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
    Button submit;
    EditText message;
    EditText interval;
    EditText number;
    boolean send;
    String sendMessage;
    String phoneNumber;
    private AlarmManager alarmManger;
    private PendingIntent pendingIntent;
    int time;
    private final static String TAG= "AWTY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit = (Button) findViewById(R.id.btnStart);
        message = (EditText) findViewById(R.id.message);
        interval = (EditText) findViewById(R.id.interval);
        number = (EditText) findViewById(R.id.phoneNumber);
        send=false;
        alarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
//        //1000*60*30
//        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage = message.getText().toString();
                phoneNumber = number.getText().toString();
                if(filledout() && !send){
                    //allow user to proceed
                    Log.i(TAG,"Starting");
                    Log.i(TAG, "interval: "+time);
                    Log.i(TAG, sendMessage);
                    submit.setText("Stop");
                    Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                    alarmIntent.putExtra("MESSAGE", sendMessage);
                    alarmIntent.putExtra("NUMBER", phoneNumber);
                    //1000*60*30
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
                    send=true;
                    alarmManger.setRepeating(AlarmManager.ELAPSED_REALTIME, 0, time * 1000* 60, pendingIntent);
                }else{
                    Log.i(TAG, "STOPPING");
                    send=false;
                    submit.setText("Start");
                    alarmManger.cancel(pendingIntent);
                    pendingIntent.cancel();
                }


            }
        });
    }

    public boolean filledout(){
        //check to see if user gave valid number
        String num = interval.getText().toString();
        int numberGiven;
        try{
            numberGiven = Integer.parseInt(num);
            time = numberGiven;
            if(numberGiven>0&& !message.getText().toString().equals("")&& !this.number.getText().toString().equals("")){
                return true;
            }else{
                Log.e(TAG, "Num must be non-zero positive number");
            }
        }catch (NumberFormatException e){
            Log.e(TAG, "user did not enter valid number for interval");
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "STOPPING");
        alarmManger.cancel(pendingIntent);
        pendingIntent.cancel();
    }
}
