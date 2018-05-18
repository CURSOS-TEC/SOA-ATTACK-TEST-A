package com.soa.jnavarro.soascaleattacka;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Class to make operations when an Intent is sent.
 */
public class ActionReceiver extends BroadcastReceiver {
    @Override
    /** Overwritten method that runs when the intent is catch */
    public void onReceive(Context context, Intent intent) {
        Log.i("SendSMS", "SMS recibido");
    }
}
