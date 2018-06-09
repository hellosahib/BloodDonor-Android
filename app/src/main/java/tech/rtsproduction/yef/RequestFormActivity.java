package tech.rtsproduction.yef;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class RequestFormActivity extends AppCompatActivity {

    Button bloodType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);
        bloodType = findViewById(R.id.bloodSelected);

        Bundle parentBundle = getIntent().getExtras();
        String getBlood = parentBundle.getString("bloodType");
        Toast.makeText(this, getBlood, Toast.LENGTH_SHORT).show();
        bloodType.setText(getBlood);
    }
}
