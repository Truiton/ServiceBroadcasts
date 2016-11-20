package com.truiton.servicebroadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String mBroadcastStringAction = "com.truiton.broadcast.string";
    public static final String mBroadcastIntegerAction = "com.truiton.broadcast.integer";
    public static final String mBroadcastArrayListAction = "com.truiton.broadcast.arraylist";
    private TextView mTextView;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textview1);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(mBroadcastStringAction);
        mIntentFilter.addAction(mBroadcastIntegerAction);
        mIntentFilter.addAction(mBroadcastArrayListAction);

        Intent serviceIntent = new Intent(this, BroadcastService.class);
        startService(serviceIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mTextView.setText(mTextView.getText()
                    + "Broadcast From Service: \n");
            if (intent.getAction().equals(mBroadcastStringAction)) {
                mTextView.setText(mTextView.getText()
                        + intent.getStringExtra("Data") + "\n\n");
            } else if (intent.getAction().equals(mBroadcastIntegerAction)) {
                mTextView.setText(mTextView.getText().toString()
                        + intent.getIntExtra("Data", 0) + "\n\n");
            } else if (intent.getAction().equals(mBroadcastArrayListAction)) {
                mTextView.setText(mTextView.getText()
                        + intent.getStringArrayListExtra("Data").toString()
                        + "\n\n");
                Intent stopIntent = new Intent(MainActivity.this,
                        BroadcastService.class);
                stopService(stopIntent);
            }
        }
    };

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }
}
