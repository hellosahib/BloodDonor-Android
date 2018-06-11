package tech.rtsproduction.yef;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailViewActivity extends AppCompatActivity {

    TextView dateText, nameText, bloodGroupText, bloodTypeText, phoneText, locationText, reasonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        initialSetup();

        Intent i = getIntent();
        DonatorClass current = (DonatorClass) i.getSerializableExtra("currentObject");
        setupField(current);

    }

    private void initialSetup() {
        dateText = findViewById(R.id.dateHeadingText);
        nameText = findViewById(R.id.personNameText);
        bloodGroupText = findViewById(R.id.bloodGroupText);
        bloodTypeText = findViewById(R.id.requiredText);
        phoneText = findViewById(R.id.phoneText);
        locationText = findViewById(R.id.locationText);
        reasonText = findViewById(R.id.reasonText);
    }

    private void setupField(DonatorClass current) {
        dateText.setText(current.getRequestDate());
        nameText.setText(current.getDonatorName());
        bloodGroupText.setText(current.getBloodType());
        bloodTypeText.setText(current.getBloodRequired());
        phoneText.setText(current.getPhoneNo());
        locationText.setText(current.getLocation());
        reasonText.setText(current.getReason());
    }
}
