package tech.rtsproduction.yef;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DonatorActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ProgressBar progressBar;
    FloatingActionButton requestBloodBtn;
    ListView donatorList;
    ArrayList<String> keyValues = new ArrayList<>();
    ArrayList<DonatorClass> donatorResults = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("RECORDS");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donators__page);
        setUpToolBar();
        progressBar = findViewById(R.id.homeProgress);
        requestBloodBtn = findViewById(R.id.bloodRequest);
        donatorList = findViewById(R.id.postList);

        final RequestAdapter adapter = new RequestAdapter(this, donatorResults);

        requestBloodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoBloodType = new Intent(DonatorActivity.this, BloodtypeActivity.class);
                gotoBloodType.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoBloodType);
            }
        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    keyValues.add(dsp.getKey());
                    populateDonatorResults(dsp);

                }
                donatorList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("FirebaseError", databaseError.getMessage());
            }
        });

    }

    void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    void populateDonatorResults(DataSnapshot dsp) {
        DonatorClass object = new DonatorClass();
        object.setDonatorName(dsp.child("donatorName").getValue().toString());
        object.setBloodRequired(dsp.child("bloodRequired").getValue().toString());
        object.setBloodType(dsp.child("bloodType").getValue().toString());
        object.setLocation(dsp.child("location").getValue().toString());
        object.setPhoneNo(dsp.child("phoneNo").getValue().toString());
        object.setReason(dsp.child("reason").getValue().toString());
        object.setReleation(dsp.child("releation").getValue().toString());
        object.setRequestDate(dsp.child("requestDate").getValue().toString());
        donatorResults.add(object);
    }
}
