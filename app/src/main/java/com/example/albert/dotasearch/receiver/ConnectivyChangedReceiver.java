package com.example.albert.dotasearch.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivyChangedReceiver extends BroadcastReceiver {

    public static final String TAG = "ConnectivyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnected()){
            Log.e(TAG, "Data connected");
        } else {
            Log.e(TAG, "Data not connected");
        }
    }
}
