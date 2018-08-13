package com.example.albert.dotasearch.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.example.albert.dotasearch.R;

public class ConnectivyChangedReceiver extends BroadcastReceiver {

    public static final String TAG = "ConnectivyReceiver";
    private View view;

   /* public ConnectivyChangedReceiver(View view) {
        this.view = view;
    }*/

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnected()){
            Log.e(TAG, "Data connected");
        } else {
            Log.e(TAG, "Data not connected");
            Snackbar.make(view, context.getText(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
        }
    }
}
