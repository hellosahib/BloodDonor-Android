package tech.rtsproduction.yef;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class NotificationDetail extends AppCompatActivity {

    /*
    * XML Variables
    */
    TextView msgView,bgView;

    /*
    * Firebase Instances
    */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference alertRef = database.getReference("ALERTS");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        msgView = findViewById(R.id.msgText);
        bgView = findViewById(R.id.bloodGroupText);

        alertRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                bgView.setText(dataSnapshot.child("BloodGroup").getValue().toString());
                msgView.setText(dataSnapshot.child("Msg").getValue().toString());
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
