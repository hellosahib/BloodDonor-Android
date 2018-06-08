package tech.rtsproduction.yef;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    EditText userName, userEmail, userPass, userConfirmPass;
    CheckBox termsCheck;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        userName = findViewById(R.id.username);
        userEmail = findViewById(R.id.email);
        userPass = findViewById(R.id.pass);
        userConfirmPass = findViewById(R.id.confirm_pass);
        termsCheck = findViewById(R.id.termsCheckbox);

    }

    public void onClickSignUp(View view) {
        if (!userName.getText().toString().isEmpty() && !userEmail.getText().toString().isEmpty() && !userPass.getText().toString().isEmpty() && !userConfirmPass.getText().toString().isEmpty()) {
            if (userPass.getText().toString().matches(userConfirmPass.getText().toString())) {
                if (termsCheck.isChecked()) {
                    mAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest nameUpdate = new UserProfileChangeRequest.Builder().setDisplayName(userName.getText().toString()).build();
                                user.updateProfile(nameUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.i("Firebase-Private", "Name Updated");
                                        } else {
                                            Log.i("Firebase-Private", task.getException().getLocalizedMessage());
                                        }
                                    }
                                });
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.i("Firebase", "Verification Send");
                                            signupSuccessful();
                                        } else {
                                            Log.i("Firebase", task.getException().getLocalizedMessage());
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "Plese Check Terms and Condition", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Password Don't Match", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please Fill All Blanks", Toast.LENGTH_SHORT).show();
        }
    }

    private void signupSuccessful() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
