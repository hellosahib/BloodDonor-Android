package tech.rtsproduction.yef;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
    FloatingActionButton requestBloodBtn;
    ProgressBar homeProgress;
    ListView donatorList;
    SwipeRefreshLayout swipeRefreshLayout;
    NavigationView navigationView;
    ArrayList<String> keyValues = new ArrayList<>();
    ArrayList<DonatorClass> donatorResults = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("RECORDS");
    DatabaseReference alertRef = database.getReference("ALERTS");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donators__page);

        requestBloodBtn = findViewById(R.id.bloodRequest);
        homeProgress = findViewById(R.id.homeProgress);
        donatorList = findViewById(R.id.postList);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        navigationView = findViewById(R.id.navigationView);
        setUpToolBar();
        final RequestAdapter adapter = new RequestAdapter(this, donatorResults);
        fetchData(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData(adapter);
            }
        });

        requestBloodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoBloodType = new Intent(DonatorActivity.this, BloodtypeActivity.class);
                gotoBloodType.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoBloodType);
            }
        });

        donatorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent gotoDetailView = new Intent(DonatorActivity.this, DetailViewActivity.class);
                gotoDetailView.putExtra("currentObject", donatorResults.get(position));
                startActivity(gotoDetailView);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.homeBtn: {
                        break;
                    }
                    case R.id.sendAlertBtn: {
                        Intent gotoAlert = new Intent(DonatorActivity.this, AlertActivity.class);
                        startActivity(gotoAlert);
                        break;
                    }
                    case R.id.logoutBtn: {
                        FirebaseAuth.getInstance().signOut();
                        Intent gotoLogin = new Intent(DonatorActivity.this, LoginActivity.class);
                        gotoLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(gotoLogin);
                        finish();
                        break;
                    }
                }
                item.setChecked(false);
                drawerLayout.closeDrawers();
                return true;
            }
        });
        alertRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(DonatorActivity.this, "0");
                mBuilder.setSmallIcon(R.drawable.ic_stat_name);
                mBuilder.setContentTitle("Notification Alert, Click Me!");
                mBuilder.setContentText("Hi, This is Android Notification Detail!");
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
                mNotificationManager.notify(0, mBuilder.build());
                Toast.makeText(DonatorActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
        });

    }

    private void fetchData(final RequestAdapter adapter) {
        keyValues.clear();
        donatorResults.clear();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    keyValues.add(dsp.getKey());
                    populateDonatorResults(dsp);
                    homeProgress.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
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
