package com.example.techvalley.wegx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import static com.example.techvalley.wegx.Tab1.Gleak;
import static com.example.techvalley.wegx.Tab1.gas_ID;
import static com.example.techvalley.wegx.Tab1.water_ID;
import static com.example.techvalley.wegx.Tab1.wleak;


public class BroadcastReceiverNotification extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {
             if(Gleak==1)
             {
                 MainActivity.displayNotification(context, "HOME WARNING !!!", "GAS LEAKAGE DETECTED", gas_ID);

             }
             else if (wleak==1)

             {
                 MainActivity.displayNotification(context, "HOME WARNING !!!", "WATER LEAKAGE DETECTED", water_ID);

             }









    }
}
