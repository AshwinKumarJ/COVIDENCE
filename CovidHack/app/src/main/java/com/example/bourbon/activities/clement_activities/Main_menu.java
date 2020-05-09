package com.example.bourbon.activities.clement_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bourbon.R;
import com.example.bourbon.activities.arumugam_activities.MapsActivity;
import com.example.bourbon.activities.arumugam_activities.MapsActivityGeofencing;
import com.example.bourbon.activities.harish_activities.recycler_view_acts.CovidStatusInfo;
import com.example.bourbon.activities.harish_activities.recycler_view_acts.CustomerOrderInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame_transition.Transition;
import print.Print;

public class Main_menu extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    private Print p;
    private Transition transition;
    DatabaseReference databaseReference;
    FirebaseUser auth;

    void init() {
        transition = new Transition(this);
        p = new Print(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        auth = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    textView.setText("Welcome  " + snapshot.child("users").child(auth.getUid()).child("Name").getValue()
                            .toString());
                }catch (Exception e){
                    Toast.makeText(Main_menu.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main_menu.this,databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
//        if(!lm.isProviderEnabled("gps"))
//        {
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivityForResult(intent,1);
//        }else{
//            p.sprintf("Please turn on your Location for some functionalities to work");
//        }


    }


    @OnClick({R.id.hospital, R.id.fund, R.id.lab, R.id.hotspot, R.id.course, R.id.toll, R.id.volunteer, R.id.donation, R.id.store, R.id.pass, R.id.logout, R.id.checkout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hospital:
                Intent intent6 = new Intent(Main_menu.this, MapsActivity.class);
                startActivity(intent6);
                break;
            case R.id.fund:
                Intent intent = new Intent(Main_menu.this, Upi_payments.class);
                startActivity(intent);
                break;
            case R.id.lab:
                Intent intent7 = new Intent(Main_menu.this, CovidStatusInfo.class);
                startActivity(intent7);
                break;
            case R.id.hotspot:
                Intent intent10 = new Intent(Main_menu.this, MapsActivityGeofencing.class);
                startActivity(intent10);
                break;
            case R.id.course:
                Intent intent1 = new Intent(Main_menu.this, YoutubeList.class);
                startActivity(intent1);
                break;
            case R.id.toll:
                Intent intent2 = new Intent(Main_menu.this, EmergencyContactInfo.class);
                startActivity(intent2);
                break;
            case R.id.volunteer:
                Intent intent3 = new Intent(Main_menu.this, Volunteer_Registration.class);
                startActivity(intent3);
                break;
            case R.id.donation:
                Intent intent4 = new Intent(Main_menu.this, Donation.class);
                startActivity(intent4);
                break;
            case R.id.store:
                Intent intent8 = new Intent(this, Store_Menu.class);
                startActivity(intent8);
                break;
            case R.id.pass:
                Intent intent9 = new Intent(Main_menu.this, E_Pass.class);
                startActivity(intent9);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent11 = new Intent(Main_menu.this, Startact.class);
                intent11.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent11);
                break;
            case R.id.checkout:
                Intent intent15 = new Intent(this, CustomerOrderInfo.class);
                startActivity(intent15);
                break;
        }
    }


}
