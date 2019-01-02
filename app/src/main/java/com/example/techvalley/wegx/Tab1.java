package com.example.techvalley.wegx;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
//import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//homepage

public class Tab1 extends Fragment {
    private TextView ausername;
    private DatabaseReference mdatabaseref;
    private DatabaseReference gas_leakage;
    private DatabaseReference water_leakage;
    private TextView water_warning;
    private TextView elec_warning;
    private TextView gas_warning;
    public static int gas_ID=0;
    public static int water_ID=1;
    public static  Integer Gleak;
    public static  Integer wleak;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.tab1, container, false); //fragment layout

        ausername = (TextView) RootView.findViewById (R.id.xusername);

        water_warning= (TextView) RootView.findViewById(R.id.waterw);
       elec_warning= (TextView) RootView.findViewById(R.id.elecw);
        gas_warning= (TextView) RootView.findViewById(R.id.gasw);

        mdatabaseref = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("username");
        gas_leakage = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("gasleak");
        water_leakage = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("waterleak");




        mdatabaseref.addValueEventListener(new ValueEventListener() {
           @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
               ausername.setText("Welcome Mr." + name);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        gas_leakage.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Gleak = Integer.valueOf(dataSnapshot.getValue().toString());
                switch (Gleak)
                {
                    case 1 :

                        MainActivity.displayNotification(getContext(), "HOME WARNING !!!", "GAS LEAKAGE DETECTED",gas_ID);
                        gas_warning.setText("GAS LEAKAGE DETECTED PLEASE CHECK IT ");



                        break;
                    case 0:
                        gas_warning.setText("YOUR GAS SYSTEM IS OK.");
                        break;


                    default:
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });
        water_leakage.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wleak = Integer.valueOf(dataSnapshot.getValue().toString());
                switch (wleak)
                {
                    case 1 :


                        MainActivity.displayNotification(getContext(), "HOME WARNING !!!", "WATER LEAKAGE DETECTED",water_ID);
                        water_warning.setText("WATER LEAKAGE DETECTED PLEASE CHECK IT ");


                        break;
                    case 0:
                        water_warning.setText("YOUR WATER SYSTEM IS OK. ");
                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });



        return RootView;

    }

}



