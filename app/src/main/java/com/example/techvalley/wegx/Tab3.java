package com.example.techvalley.wegx;


import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import de.nitri.gauge.Gauge;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//water
public class Tab3 extends Fragment  {

    private Switch wswitch;
    private TextView wstatus;
    private DatabaseReference wswitchs;
    private DatabaseReference water_current_usage;
    private TextView water_current_usage_D;

    private Gauge water_gauge;



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View RootView = inflater.inflate(R.layout.tab3, container, false);
        wstatus = (TextView)RootView.findViewById(R.id.water_status);
        wswitch = (Switch) RootView.findViewById(R.id.water_switch);
        water_gauge = (Gauge) RootView.findViewById(R.id.gaugeW);
        water_current_usage_D = (TextView)RootView.findViewById(R.id.c_w_usage);

        wswitchs = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("water valve status");
//        water_leakage = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("waterleak");
        water_current_usage = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("water usage");




        wswitchs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer status = Integer.valueOf(dataSnapshot.getValue().toString());
                switch (status) {
                    case 0 :
                        wstatus.setText("OFF");
                        wswitch.setChecked(false);
                        break;
                    case 1 :
                        wstatus.setText("ON");
                        wswitch.setChecked(true);
                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        wswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (wswitch.isChecked()) {
                    wswitchs.setValue(1);
                    wstatus.setText("ON");
                } else {
                    wswitchs.setValue(0);
                    wstatus.setText("OFF");
                }
            }
        });


        water_current_usage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Float currentvalue = Float.valueOf(dataSnapshot.getValue().toString());
               String cv = String.valueOf(dataSnapshot.getValue().toString());

               water_current_usage_D.setText(cv);
               water_gauge.moveToValue(currentvalue);
               water_gauge.animate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return  RootView;
    }




}
