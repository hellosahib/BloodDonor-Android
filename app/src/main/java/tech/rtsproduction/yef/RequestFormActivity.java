package tech.rtsproduction.yef;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestFormActivity extends AppCompatActivity {

    Button bloodType;
    EditText dontorName,releation,mobile,location,reason;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("RECORDS");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);
        bloodType = findViewById(R.id.bloodSelected);
        Bundle parentBundle = getIntent().getExtras();
        String getBlood = parentBundle.getString("bloodType");
        bloodType.setText(getBlood);

        dontorName = findViewById(R.id.patientNameEdit);
        releation = findViewById(R.id.releationEdit);
        mobile = findViewById(R.id.mobileEdit);
        location = findViewById(R.id.locationEdit);
        reason = findViewById(R.id.reasonEdit);


    }
}
