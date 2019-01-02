package com.example.techvalley.wegx;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//GAS
public class Tab2 extends Fragment  {
    private Switch gswitch;
    private TextView gstatus;
    private DatabaseReference gswitchs;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View RootView = inflater.inflate(R.layout.tab2, container, false);
        gstatus = (TextView)RootView.findViewById(R.id.gas_status);
        gswitch = (Switch) RootView.findViewById(R.id.gas_switch);
        gswitchs = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("gas valve status");


        gswitchs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer status = Integer.valueOf(dataSnapshot.getValue().toString());
                switch (status) {
                    case 0 :
                        gstatus.setText("OFF");
                        gswitch.setChecked(false);
                        break;
                    case 1 :
                        gstatus.setText("ON");
                        gswitch.setChecked(true);
                        break;
                    default:

                        break;


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        gswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (gswitch.isChecked()) {
                    gswitchs.setValue(1);
                    gstatus.setText("ON");
                } else {
                    gswitchs.setValue(0);
                    gstatus.setText("OFF");
                }
            }
        });


        return RootView;
    }



}
