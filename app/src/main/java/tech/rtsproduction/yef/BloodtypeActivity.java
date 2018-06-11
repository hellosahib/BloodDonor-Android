package tech.rtsproduction.yef;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BloodtypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodtype);
    }

    public void gotoRequestForm(View view) {
        int id = view.getId();
        Button btn = findViewById(id);
        String getBlood = btn.getText().toString();
        Intent gotoForm = new Intent(BloodtypeActivity.this, RequestFormActivity.class);
        gotoForm.putExtra("bloodType", getBlood);
        startActivity(gotoForm);
        finish();
    }
}
