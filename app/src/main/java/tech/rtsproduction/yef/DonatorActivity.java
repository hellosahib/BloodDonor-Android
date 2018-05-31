package tech.rtsproduction.yef;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
    private NotificationManagerCompat manager;
    ArrayList<DonatorClass> donatorResults = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference recordRef = database.getReference("RECORDS");
    DatabaseReference alertRef = database.getReference("ALERTS");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donators__page);
        createNotificationChannel();

        manager = NotificationManagerCompat.from(this);
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
                String bloodGroup = dataSnapshot.child("BloodGroup").getValue().toString();
                String msg = dataSnapshot.child("Msg").getValue().toString();
                Bitmap notificationIcon = BitmapFactory.decodeResource(getResources(), R.drawable.blood);
                Notification notification = new NotificationCompat.Builder(DonatorActivity.this, "Channel1")
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle("YEF Urgent Request")
                        .setLargeIcon(notificationIcon)
                        .setColor(Color.BLUE)
                        .setOnlyAlertOnce(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg).setBigContentTitle(bloodGroup + " Required"))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();
                manager.notify(1, notification);
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
        donatorResults.clear();
        recordRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
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

    public void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    private void populateDonatorResults(@NonNull DataSnapshot dsp) {
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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel("Channel1", "Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription("This is Notification");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }


}
